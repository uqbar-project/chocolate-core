package org.uqbar.chocolate.core.appearances

import java.awt.Canvas
import java.awt.Color
import java.awt.Color.BLACK
import java.awt.Font
import java.awt.Font.PLAIN
import java.awt.Font.MONOSPACED
import java.awt.Graphics2D
import Label._

import org.uqbar.chocolate.core.utils.Implicits.double_to_int

object Label {
	val DEFAULT_FONT = new Font(MONOSPACED, PLAIN, 15)
	val DEFAULT_COLOR = BLACK
}

class Label(var font : Font = DEFAULT_FONT)(var color : Color = DEFAULT_COLOR)(someTextLines : String = "") extends Appearance {

	var textLines = someTextLines.lines.toIndexedSeq
	def width = (0 +: textLines.map(new Canvas().getFontMetrics(font).stringWidth(_))).max
	def height = linesCount * lineHeight

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	def scale(hRatio : Double)(vRatio : Double) = null //TODO
	def crop(x : Double, y : Double, width : Double, height : Double) = null // TODO
	def flipHorizontally {} // TODO
	def flipVertically {} // TODO
	def repeat(horizontalRepetitions : Double, verticalRepetitions : Double) {} //TODO
	def rotate(angle : Double) {} //TODO

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def linesCount = textLines.size
	def lineHeight = font.getSize2D * 1.05

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def text : String = textLines.mkString("\n")
	def text_=(text : String) : Unit = textLines = text.lines.toIndexedSeq

	// ****************************************************************
	// ** GAME LOOP OPERATIONS
	// ****************************************************************

	override def update(delta : Double) = {}

	protected override def doRenderAt(x : Double, y : Double, graphics : Graphics2D) = {
		graphics.setFont(this.font)
		graphics.setColor(this.color)

		for (index ← 0 until textLines.size) {
			graphics.drawString(textLines(index), x + translation.x, y + translation.y + lineHeight * (index + 1));
		}
	}
}