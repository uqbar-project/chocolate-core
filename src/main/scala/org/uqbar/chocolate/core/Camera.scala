package org.uqbar.chocolate.core

import org.uqbar.cacao.Renderer
import scala.Int.int2double
import scala.collection.mutable.Seq
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.chocolate.core.reactions.events.Render

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

	def apply(renderer: Renderer) = renderer
		.cropped(screenPosition)(screenSize)
		.scaled(zoom)
		.translated(-translation)

	def shoot(components: Seq[GameComponent], renderer: Renderer) {

		val adjustedRenderer = this(renderer)
		components filter (_.z < z) foreach { _ reactTo Render(adjustedRenderer) }
	}
}