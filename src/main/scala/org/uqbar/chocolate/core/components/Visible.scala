package org.uqbar.chocolate.core.components;

import java.awt.Graphics2D
import java.awt.geom.Point2D
import org.uqbar.chocolate.core.appearances.Appearance
import org.uqbar.chocolate.core.appearances.Invisible
import org.uqbar.chocolate.core.reactions.events.Update
import org.uqbar.chocolate.core.dimensions._
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.chocolate.core.reactions.events.RenderRequired
import org.uqbar.chocolate.core.reactions.events.RenderRequired

trait Visible extends GameComponent with Bounded with Positioned {

	def appearance: Appearance

	override def left = translation.x + appearance.left
	override def top = translation.y + appearance.top
	override def width = appearance.width
	override def height = appearance.height

	in {
		case Update(delta) ⇒ appearance.update(delta)
		case RenderRequired(graphics) ⇒ render(graphics)
	}

	def render(graphics: Graphics2D) = appearance.renderAt(translation, graphics)
}