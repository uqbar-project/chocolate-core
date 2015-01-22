package org.uqbar.chocolate.core.reactions.events.adapters

import java.awt.event.MouseEvent
import java.awt.event.MouseEvent._
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import scala.language.implicitConversions
import org.uqbar.chocolate.core.reactions.events.EventDispatcher
import org.uqbar.chocolate.core.reactions.events.Hold
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Released
import org.uqbar.chocolate.core.reactions.io.MouseButton
import org.uqbar.math.spaces.R2._
import org.uqbar.chocolate.core.reactions.events.MouseMoved

//TODO: Mouse Weel?
//TODO: Multiple button mouse?
trait MouseAdapter extends MouseMotionListener with MouseListener {

	def target: EventDispatcher

	implicit def EventToMouseButton(e: MouseEvent): MouseButton = e.getButton match {
		case BUTTON1 => MouseButton.Left
		case BUTTON2 => MouseButton.Middle
		case BUTTON3 => MouseButton.Right
	}

	//*********************************************************************************************
	// MOUSE LISTENING
	//*********************************************************************************************

	override def mouseDragged(e: MouseEvent) {
		target pushEvent MouseMoved(e.getPoint.getX, e.getPoint.getY)
	}

	override def mouseMoved(e: MouseEvent) {
		target pushEvent MouseMoved(e.getPoint.getX, e.getPoint.getY)
	}

	override def mouseClicked(e: MouseEvent) {}

	override def mouseEntered(e: MouseEvent) {}

	override def mouseExited(e: MouseEvent) {}

	override def mousePressed(e: MouseEvent) {
		target pushEvent Pressed(e)
		target startPushingEvent Hold(e)
	}

	override def mouseReleased(e: MouseEvent) {
		target pushEvent Released(e)
		target stopPushingEvent Hold(e)
	}
}