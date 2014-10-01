package org.uqbar.chocolate.core.appearances;

import org.uqbar.cacao.Renderer
import org.uqbar.math.vectors._

class Invisible extends Appearance {
	def size = Origin

	def update(delta: Double) {}
	protected def doRenderAt(position: Vector, renderer: Renderer) {}
}