package org.uqbar.chocolate.core.components.debug;

import java.awt.Color
import java.awt.Graphics2D
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.events.RenderRequired

class FloorShower extends GameComponent {

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	z = Integer.MIN_VALUE + 10

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	in {
		case RenderRequired(graphics) =>
			graphics.setColor(Color.MAGENTA)

			val gameWidth = game.displayWidth
			val gameHeight = game.displayHeight
			val collisionZonesWidth = scene.collisionZoneSize.getWidth().toInt
			val collisionZonesHeight = scene.collisionZoneSize.getHeight().toInt

			for (i ← 0.to(gameWidth, collisionZonesWidth)) graphics.drawLine(i, 0, i, gameHeight)
			for (j ← 0.to(gameHeight, collisionZonesHeight)) graphics.drawLine(0, j, gameWidth, j)

			graphics.setColor(Color.YELLOW)
			graphics.drawRect(0, 0, gameWidth - 1, gameHeight - 1)
	}
}