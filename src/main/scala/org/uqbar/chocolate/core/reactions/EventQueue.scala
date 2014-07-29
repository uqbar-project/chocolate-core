package org.uqbar.chocolate.core.reactions;

import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.reactions.events.Update
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer
import org.uqbar.chocolate.core.reactions.events.EventContinuation
import org.uqbar.chocolate.core.reactions.events.ContinuableEvent

class EventQueue {
	var events = new ArrayBuffer[GameEvent]
	val continuations = new HashMap[GameEvent, EventContinuation]

	reset

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def pushEvent(event: GameEvent) = synchronized { events += event }

	def startPushingEvent(event: ContinuableEvent) = synchronized {
		continuations put (event, new EventContinuation(event))
	}

	def stopPushingEvent(event: ContinuableEvent) = synchronized {
		continuations remove event
	}

	def takePendingEvents = synchronized {
		val answer = events ++ takeContinuationEvents
		resetEvents
		answer
	}

	def reset {
		resetContinuations
		resetEvents

		startPushingEvent(new Update)
	}

	def pause = synchronized { continuations.values foreach { _.reset } }

	protected def takeContinuationEvents = synchronized {
		continuations.values map (_.createEvent)
	}

	protected def resetEvents = synchronized { events.clear }

	protected def resetContinuations = synchronized { continuations.clear }

}