package org.uqbar.chocolate.core.appearances

import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.math.vectors._
import org.uqbar.cacao.Color
import org.uqbar.cacao.Renderer
import org.uqbar.cacao.{ Circle => CircleShape }
import org.uqbar.cacao.{ Rectangle => RectangleShape }
import org.uqbar.cacao.{ Shape => CacaoShape }

//class Shape(shape: CacaoShape, color: Color = Color.Black, filled: Boolean = true) extends Appearance {
//	def update(delta: Double) {}
//
//	def doRenderAt(position: Vector, renderer: Renderer) {
//		renderer.color = color
//		if (filled) renderer.fill(shape)
//		else renderer.draw(shape)
//	}
//}

class Rectangle(val size: Vector)(color: Color) extends Appearance {
	def update(delta: Double) {}
	def doRenderAt(position: Vector, renderer: Renderer) = {
		renderer.color = color
		renderer.fill(RectangleShape(position, size))
	}
}

class Circle(val radius: Double, filled: Boolean = true)(color: Color) extends Appearance {
	val size: Vector = (2 * radius, 2 * radius)

	translation = (-radius, -radius)

	def update(delta: Double) {}
	def doRenderAt(position: Vector, renderer: Renderer) = {
		renderer.color = color
		if (filled) renderer.fill(CircleShape(translation, radius))
		else renderer.draw(CircleShape(translation, radius))
	}
}