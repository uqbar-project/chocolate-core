package org.uqbar.chocolate.core

import scala.Int.int2double
import scala.collection.mutable.Seq

import org.uqbar.cacao.Renderer
import org.uqbar.cacao.{ Camera => CacaoCamera }
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.chocolate.core.reactions.events.Render
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.math.vectors._

class DefaultCamera(scene: GameScene) extends Camera {
	val innerCamera = CacaoCamera(screenSize = scene.game.displaySize)
	val z = Double.MaxValue
}

class TargetedCamera(override val screenPosition: MutableVector, override val screenSize: MutableVector)(var z: Double)(override val zoom: MutableVector = (1, 1))(var target: Positioned) extends Camera {
	def innerCamera = CacaoCamera(target.translation - screenSize / 2, screenPosition, screenSize, zoom)
}

trait Camera {
	def innerCamera: CacaoCamera
	def z: Double

	def zoom = innerCamera.zoom
	def screenSize = innerCamera.screenSize
	def screenPosition = innerCamera.screenPosition

	def apply(renderer: Renderer) {
		renderer.camera = this.innerCamera
	}

	def shoot(components: Seq[GameComponent], renderer: Renderer) {
		this(renderer)
		components filter (_.z < z) foreach { _ reactTo Render(renderer) }
	}
}