package org.uqbar.chocolate.core.appearances

import java.awt.Color
import java.awt.Graphics2D
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.chocolate.core.dimensions.Vector

class Rectangle(size : Vector)(color : Color) extends Appearance {
	val width = size.x
	val height = size.y

	def update(delta : Double) {}
	def doRenderAt(x : Double, y : Double, graphics : Graphics2D) {
		graphics.setColor(color)

		graphics.fillRect(x, y, width, height)
	}
}