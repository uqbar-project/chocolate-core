package org.uqbar.chocolate.core.reactions.events

import java.awt.geom.Point2D
import org.uqbar.chocolate.core.reactions.EventSignature
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton._
import org.uqbar.math.vectors.Vector

case class MouseMoved(position : Vector) extends SimpleEvent

trait MouseButtonEvent extends SimpleEvent {
	def button : MouseButton

	override def mainSignature = EventSignature(getClass, button)
	override def matchingSignatures = super.matchingSignatures :+ EventSignature(getClass, ANY)
}

case class MousePressed(button : MouseButton, position : Vector) extends MouseButtonEvent
case class MouseReleased(button : MouseButton, position : Vector) extends MouseButtonEvent