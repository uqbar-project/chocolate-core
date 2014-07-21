package org.uqbar.chocolate.core.components;

import java.awt.Rectangle
import scala.collection.mutable.HashSet
import org.uqbar.chocolate.core.GameScene
import org.uqbar.chocolate.core.collisions.BoundingBox
import org.uqbar.chocolate.core.dimensions.Bounded
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.chocolate.core.dimensions.Vector
import org.uqbar.chocolate.core.reactions.annotations.scene.OnUpdate
import org.uqbar.chocolate.core.reactions.events.CollisionEnd
import org.uqbar.chocolate.core.reactions.events.Collision
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import scala.collection.mutable.Set

trait Collisionable extends GameComponent with Bounded with Positioned {
	def boundingBox : BoundingBox

	//TODO: Representar esto de una forma mejor...
	private var _coveredZones : Rectangle = null
	def coveredZones = if (_coveredZones == null) recalculateCoveredZones else _coveredZones
	def coveredZones_=(newCoveredZones : Rectangle) = _coveredZones = newCoveredZones

	override def left = translation.x + boundingBox.left
	override def top = translation.y + boundingBox.top
	override def width = boundingBox.width
	override def height = boundingBox.height

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: Registrar al inicio los grupos que me interesan e ignorar el resto
	def collidesWith(target : Collisionable) = boundingBox(this.translation)(target.boundingBox, target.translation)

	// ****************************************************************
	// ** COLLISION OPERATIONS
	// ****************************************************************

	//TODO: así? Está bien que esto sea miembro de collissionable?
	protected val collidingComponents = Set[Collisionable]()

	@OnUpdate
	def triggerCollisions = {
		val zones = coveredZones
		val alreadyChecked = Set[GameComponent]()

		for {
			i ← zones.x to zones.getMaxX
			j ← zones.y to zones.getMaxY
			target ← scene.collisionZones.getOrElse((i, j), Nil)

			if !(this == target || alreadyChecked.contains(target))
		} {
			if (collidesWith(target)) {
				this reactTo Collision(target)
				collidingComponents add target // TODO: Hacer con trigger?
			} else if (collidingComponents contains target) {
				this reactTo CollisionEnd(target)
				collidingComponents remove target
			}

			alreadyChecked += target
		}
	}

	def shouldTriggerCollisionAgainst(target : Collisionable, componentsToIgnore : Seq[GameComponent]) =
		!(componentsToIgnore contains target) &&
			(this != target) &&
			(this collidesWith target)

	def recalculateCoveredZones : Rectangle = {
		val zoneSize = scene.collisionZoneSize
		val standingX = (left / zoneSize.width).floor
		val standingY = (top / zoneSize.height).floor
		val coveredX = (width / zoneSize.width).ceil + (if (boundingBox.left % zoneSize.width == 0) 0 else 1)
		val coveredY = (height / zoneSize.height).ceil + (if (boundingBox.top % zoneSize.height == 0) 0 else 1)

		_coveredZones = new Rectangle(standingX, standingY, coveredX, coveredY)

		_coveredZones
	}

	// ****************************************************************
	// ** ACCESORS
	// ****************************************************************

	protected def foreachZone(action : (Int, Int) ⇒ Unit) = {
		val zones = coveredZones
		for {
			x ← zones.x until zones.getMaxX
			y ← zones.y until zones.getMaxY
		} action(x, y)
	}

	override def scene_=(newScene : GameScene) = {
		if (scene != null) foreachZone((x, y) ⇒ scene.removeFromCollisionZone(x, y, this))

		super.scene_=(newScene)

		if (newScene != null) {
			recalculateCoveredZones
			foreachZone((x, y) ⇒ newScene.addToCollisionZone(x, y, this))
		}
	}

	override def translation_=(newPosition : Vector) {
		if ((translation.x.toInt != newPosition.x.toInt || translation.y.toInt != newPosition.y.toInt) && scene != null) {
			foreachZone { (x, y) ⇒ scene.removeFromCollisionZone(x, y, this) }
			super.translation = (newPosition)
			recalculateCoveredZones
			foreachZone { (x, y) ⇒ scene.addToCollisionZone(x, y, this) }
		} else super.translation = (newPosition)
	}
}