package org.uqbar.chocolate.core.components.debug;

import org.uqbar.cacao.Renderer
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.events.Render
import org.uqbar.cacao.Line
import org.uqbar.cacao.Color
import org.uqbar.cacao.Rectangle
import org.uqbar.math.spaces.R2._

class FloorShower extends GameComponent {

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	z = Integer.MIN_VALUE + 10

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	in {
		case Render(renderer) =>
			val gameWidth = game.displaySize(X).toInt
			val gameHeight = game.displaySize(Y).toInt
			val collisionZonesWidth = scene.collisionZoneSize.getWidth.toInt
			val collisionZonesHeight = scene.collisionZoneSize.getHeight.toInt

			renderer.color = Color.Magenta
			for (i ← 0.to(gameWidth, collisionZonesWidth)) renderer draw Line((i, 0), (i, gameHeight))
			for (j ← 0.to(gameHeight, collisionZonesHeight)) renderer draw Line((0, j), (gameWidth, j))

			renderer.color = Color.Yellow
			renderer draw Rectangle((0, 0), (gameWidth - 1, gameHeight - 1))
	}
}