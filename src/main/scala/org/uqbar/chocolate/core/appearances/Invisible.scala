package org.uqbar.chocolate.core.appearances;

import java.awt.Graphics2D;
import org.uqbar.math.vectors.Vector

class Invisible extends Appearance {
	def width = 0
	def height = 0

	def update(delta : Double) = {}
	protected def doRenderAt(x : Double, y : Double, graphics : Graphics2D) = {}
}