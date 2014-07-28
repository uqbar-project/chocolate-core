package org.uqbar.chocolate.core

import java.awt.Graphics2D

import scala.Int.int2double
import scala.collection.mutable.Seq

import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.reactions.events.RenderRequired
import org.uqbar.chocolate.core.utils.Implicits.double_to_int

class DefaultCamera(scene: GameScene) extends Camera {
	val zoom: Vector = (1, 1)
	val screenSize: Vector = scene.game.displaySize
	val screenPosition = Origin
	val z = Double.MaxValue
}

class TargetedCamera(var screenPosition: Vector, var screenSize: Vector)(var z: Double)(var zoom: Vector = (1, 1))(var target: Positioned) extends Camera {
	override def translation = target.translation - screenSize / 2
}

trait Camera extends Positioned {
	def zoom: Vector
	def screenSize: Vector
	def screenPosition: Vector
	def z: Double

	def apply(graphics: Graphics2D) = {
		val answer = graphics.create(screenPosition.x, screenPosition.y, screenSize.x, screenSize.y).asInstanceOf[Graphics2D]

		val t = translation

		answer.scale(zoom.x, zoom.y)

		answer.translate(-translation.x, -translation.y)

		answer
	}

	def shoot(components: Seq[GameComponent], graphics: Graphics2D) {
		val g = this(graphics)

		components filter (_.z < z) foreach { _.reactTo(new RenderRequired(g)) }

		g.dispose
	}
}