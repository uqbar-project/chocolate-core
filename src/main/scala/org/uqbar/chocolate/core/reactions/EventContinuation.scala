package org.uqbar.chocolate.core.reactions;

import org.uqbar.chocolate.core.reactions.events.GameEvent;

trait ContinuableEvent extends GameEvent {
	def next(delta : Double) : ContinuableEvent
}

class EventContinuation(event : ContinuableEvent) {
	var lastCallTime : Long = 0

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def createEvent = {
		val now = System.nanoTime
		val delta = if (lastCallTime > 0) (now - lastCallTime) / 1000000000.0 else 0

		lastCallTime = now
		event.next(delta)
	}

	def reset = lastCallTime = 0
}