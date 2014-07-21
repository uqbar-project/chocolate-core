package org.uqbar.chocolate.core.components.debug

import java.awt.Color
import java.awt.Graphics2D
import scala.Int.int2double
import org.uqbar.chocolate.core.Camera
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.dimensions.MutableVector
import org.uqbar.chocolate.core.dimensions.Vector.touple_to_vector
import org.uqbar.chocolate.core.reactions.annotations.io.keyboard.OnKeyHold
import org.uqbar.chocolate.core.reactions.annotations.io.keyboard.OnKeyPressed
import org.uqbar.chocolate.core.reactions.events.KeyHold
import org.uqbar.chocolate.core.reactions.events.KeyPressed
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.chocolate.core.reactions.annotations.scene.OnSceneSetAsCurrent
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key
import org.uqbar.chocolate.core.reactions.events.KeyPressed

object NavigatorCamera extends GameComponent {
	var INSTANCE : NavigatorCamera = null

	@OnKeyPressed(Key.F2)
	def showOrHide {
		if (INSTANCE == null) {
			INSTANCE = new NavigatorCamera
			INSTANCE.screenSize.set(game.displaySize / 2)
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
	val screenPosition : MutableVector = (0, 0)
	val screenSize : MutableVector = (0, 0)
	val zoom : MutableVector = (0.5, 0.5)

	z = 12000

	@OnKeyHold
	def translate(e : KeyHold) = e.key match {
		case Key.LEFT ⇒ move(-1, 0)
		case Key.RIGHT ⇒ move(1, 0)
		case Key.UP ⇒ move(0, -1)
		case Key.DOWN ⇒ move(0, 1)

		case Key.A ⇒ screenPosition += (-5, 0)
		case Key.D ⇒ screenPosition += (5, 0)
		case Key.W ⇒ screenPosition += (0, -5)
		case Key.S ⇒ screenPosition += (0, 5)

		case _ ⇒
	}

	@OnKeyPressed
	def adjustZoom(e : KeyPressed) = e.key match {
		case Key.PLUS ⇒ zoom *= 2
		case Key.MINUS ⇒ zoom /= 2

		case Key.E ⇒ screenSize *= 2
		case Key.Q ⇒ screenSize /= 2

		case _ ⇒
	}

	override def apply(graphics : Graphics2D) = {
		graphics.setColor(Color.WHITE)
		graphics.clearRect(screenPosition.x, screenPosition.y, screenSize.x, screenSize.y)
		graphics.fillRect(screenPosition.x, screenPosition.y, screenSize.x, screenSize.y)

		super.apply(graphics)
	}
}