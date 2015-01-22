package org.uqbar.chocolate.core.components.debug

import org.uqbar.cacao.Color
import org.uqbar.cacao.{ Camera => CacaoCamera }
import org.uqbar.cacao.Renderer
import scala.Int.int2double
import org.uqbar.chocolate.core.Camera
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.math.spaces.R2._
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.chocolate.core.reactions.io.Key._
import org.uqbar.chocolate.core.reactions.io._
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Hold
import org.uqbar.cacao.Rectangle

object NavigatorCamera extends GameComponent {
	val MOVE_SPEED = 50

	var INSTANCE: NavigatorCamera = null

	in {
		case Pressed(Function(2)) => if (INSTANCE == null) {
			INSTANCE = new NavigatorCamera
			INSTANCE.innerCamera.screenSize = (game.displaySize / 2)
			scene.addCamera(INSTANCE)
			scene.addComponent(INSTANCE)
		} else {
			scene.removeCamera(INSTANCE)
			INSTANCE.destroy
			INSTANCE = null
		}
	}
}

class NavigatorCamera extends Camera with GameComponent {
	val innerCamera = CacaoCamera(zoom = (0.5, 0.5))

	z = 12000

	in {
		case Hold(Navigation.Arrow.Left(_), delta) => innerCamera.translation += (-delta * NavigatorCamera.MOVE_SPEED, 0)
		case Hold(Navigation.Arrow.Right(_), delta) => innerCamera.translation += (delta * NavigatorCamera.MOVE_SPEED, 0)
		case Hold(Navigation.Arrow.Up(_), delta) => innerCamera.translation += (0, -delta * NavigatorCamera.MOVE_SPEED)
		case Hold(Navigation.Arrow.Down(_), delta) => innerCamera.translation += (0, delta * NavigatorCamera.MOVE_SPEED)
		case Hold(Letter.A, delta) => innerCamera.screenPosition += (-delta * NavigatorCamera.MOVE_SPEED, 0)
		case Hold(Letter.D, delta) => innerCamera.screenPosition += (delta * NavigatorCamera.MOVE_SPEED, 0)
		case Hold(Letter.W, delta) => innerCamera.screenPosition += (0, -delta * NavigatorCamera.MOVE_SPEED)
		case Hold(Letter.S, delta) => innerCamera.screenPosition += (0, delta * NavigatorCamera.MOVE_SPEED)

		case Pressed(Symbol.+(KeyLocation.Anywhere)) => innerCamera.zoom *= 2
		case Pressed(Symbol.-(KeyLocation.Anywhere)) => innerCamera.zoom /= 2

		case Pressed(Letter.E) => innerCamera.screenSize *= 2
		case Pressed(Letter.Q) => innerCamera.screenSize /= 2
	}

	override def apply(renderer: Renderer) = {
		renderer.color = Color.White
		renderer.clear(screenPosition, screenSize)
		renderer.fill(Rectangle(screenPosition, screenSize))

		super.apply(renderer)
	}
}