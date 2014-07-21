package org.uqbar.chocolate.core.reactions.events

import org.uqbar.chocolate.core.reactions.EventSignature
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key
import org.uqbar.chocolate.core.reactions.ContinuableEvent

trait KeyEvent extends SimpleEvent {
	val key : Key

	override def mainSignature = EventSignature(getClass, key)

	override def matchingSignatures = super.matchingSignatures :+ EventSignature(getClass, Key.ANY)
}

case class KeyPressed(key : Key) extends KeyEvent
case class KeyReleased(key : Key) extends KeyEvent

case class KeyHold(key : Key, delta : Double) extends KeyEvent with ContinuableEvent {
	def this(key : Key) = this(key, 0)

	def next(d : Double) = copy(delta = d)
}