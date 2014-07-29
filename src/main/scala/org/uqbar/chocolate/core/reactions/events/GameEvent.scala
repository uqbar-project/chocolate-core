package org.uqbar.chocolate.core.reactions.events

import java.util.Collection

import org.uqbar.chocolate.core.utils.Cloneable

trait GameEvent

trait ContinuableEvent extends GameEvent {
	def next(delta: Double): ContinuableEvent
}

class EventContinuation(event: ContinuableEvent) {
	var lastCallTime = 0L

	def reset = lastCallTime = 0

	def createEvent = {
		val now = System.nanoTime
		val delta = if (lastCallTime > 0) (now - lastCallTime) / 1000000000.0 else 0

		lastCallTime = now
		event.next(delta)
	}
}

trait EventDispatcher {
	def pushEvent(e: GameEvent)
	def stopPushingEvent(e: ContinuableEvent)
	def startPushingEvent(e: ContinuableEvent)
}
