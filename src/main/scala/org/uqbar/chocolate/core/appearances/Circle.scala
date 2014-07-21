package org.uqbar.chocolate.core.appearances

import java.awt.Color
import java.awt.Graphics2D
import org.uqbar.chocolate.core.utils.Implicits._

class Circle(val radius : Double, filled : Boolean = true)(color : Color) extends Appearance {
	val width = 2 * radius
	val height = 2 * radius

	translation = (-radius, -radius)

	def update(delta : Double) {}
	def doRenderAt(x : Double, y : Double, graphics : Graphics2D) {
		graphics.setColor(color)

		if (filled) graphics.fillOval(x, y, width, height)
		else graphics.drawOval(x, y, width, height)
	}
}