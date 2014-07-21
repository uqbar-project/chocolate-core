package org.uqbar.chocolate.core.reactions.events

import java.util.Collection
import org.uqbar.chocolate.core.reactions.EventSignature
import org.uqbar.chocolate.core.utils.Cloneable

trait GameEvent extends Cloneable {
	def mainSignature : EventSignature

	def matchingSignatures : List[EventSignature]
}