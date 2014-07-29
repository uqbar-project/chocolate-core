package org.uqbar.chocolate.core.reactions.events.adapters

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.reactions.events.MouseMoved
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.reactions.events.MouseMoved
import org.uqbar.chocolate.core.reactions.io.MouseButton
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Released
import org.uqbar.chocolate.core.reactions.events.EventDispatcher

trait MouseAdapter extends MouseMotionListener with MouseListener {

	def target: EventDispatcher

	//*********************************************************************************************
	// MOUSE LISTENING
	//*********************************************************************************************

	override def mouseDragged(e: MouseEvent) = {
		target pushEvent MouseMoved(e.getPoint)
	}

	override def mouseMoved(e: MouseEvent) = {
		target pushEvent MouseMoved(e.getPoint)
	}

	override def mouseClicked(e: MouseEvent) = {}

	override def mouseEntered(e: MouseEvent) = {}

	override def mouseExited(e: MouseEvent) = {}

	override def mousePressed(e: MouseEvent) = target //pushEvent Pressed(MouseButton.fromCode(e.getButton), e.getPoint)

	override def mouseReleased(e: MouseEvent) = target //pushEvent Released(MouseButton.fromCode(e.getButton), e.getPoint)
}