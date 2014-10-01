package org.uqbar.chocolate.core.components;

import org.uqbar.cacao.Renderer
import org.uqbar.chocolate.core.appearances.Appearance
import org.uqbar.chocolate.core.dimensions.Bounded
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.chocolate.core.reactions.events.Render
import org.uqbar.chocolate.core.reactions.events.Update

trait Visible extends GameComponent with Bounded with Positioned {

	def appearance: Appearance

	override def left = translation.x + appearance.left
	override def top = translation.y + appearance.top
	override def size = appearance.size

	in {
		case Update(delta) ⇒ appearance.update(delta)
		case Render(renderer) ⇒ render(renderer)
	}

	def render(renderer: Renderer) = appearance.renderAt(translation)(renderer)
}