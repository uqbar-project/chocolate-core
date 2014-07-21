package org.uqbar.chocolate.core.reactions.events;

import org.uqbar.chocolate.core.reactions.EventSignature;

case class CustomEvent(keys : Any*) extends SimpleEvent {

	override def mainSignature = EventSignature(getClass, keys : _*)

}