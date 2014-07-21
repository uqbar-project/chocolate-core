package org.uqbar.chocolate.core.reactions.events;

import java.util.ArrayList
import java.util.Collection

import org.uqbar.chocolate.core.reactions.EventSignature;

trait SimpleEvent extends GameEvent {

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	override def mainSignature = EventSignature(getClass)

	override def matchingSignatures = List(mainSignature)
}