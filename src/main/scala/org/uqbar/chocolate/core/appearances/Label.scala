package org.uqbar.chocolate.core.appearances

import java.awt.Canvas
import org.uqbar.cacao.Color
import java.awt.Font.PLAIN
import java.awt.Font.MONOSPACED
import org.uqbar.cacao.Renderer
import Label._
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.math.vectors.Vector
import org.uqbar.cacao._

object Label {
	val DEFAULT_COLOR = Color.Black
}

class Label(var font: Font, var color: Color = DEFAULT_COLOR)(someTextLines: String = "") extends Appearance {
	var textLines = someTextLines.lines.toIndexedSeq

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	def scale(hRatio: Double)(vRatio: Double) = null //TODO
	def crop(x: Double, y: Double, width: Double, height: Double) = null // TODO
	def flipHorizontally {} // TODO
	def flipVertically {} // TODO
	def repeat(horizontalRepetitions: Double, verticalRepetitions: Double) {} //TODO
	def rotate(angle: Double) {} //TODO

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def size: Vector = ((0.0 +: textLines.map(font.sizeOf(_).x)).max, linesCount * lineHeight)

	def linesCount = textLines.size
	def lineHeight = font.sizeOf(textLines.headOption.getOrElse("")).y

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def text: String = textLines.mkString("\n")
	def text_=(text: String): Unit = textLines = text.lines.toIndexedSeq

	// ****************************************************************
	// ** GAME LOOP OPERATIONS
	// ****************************************************************

	override def update(delta: Double) = {}

	protected override def doRenderAt(position: Vector, renderer: Renderer) = {
		renderer.font = font
		renderer.color = color

		for (index ← 0 until textLines.size) {
			renderer.draw(textLines(index))(position + translation + (0, lineHeight * (index + 1)))
		}
	}
}