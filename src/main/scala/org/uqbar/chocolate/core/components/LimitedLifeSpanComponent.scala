package org.uqbar.chocolate.core.components;

import org.uqbar.chocolate.core.appearances.Appearance
import org.uqbar.chocolate.core.reactions.annotations.scene.OnUpdate
import org.uqbar.chocolate.core.reactions.events.Update;
import org.uqbar.chocolate.core.dimensions.Vector

class LimitedLifeSpanComponent(val appearance : Appearance, var lifeSpan : Double) extends Visible {

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	@OnUpdate
	def updateLifeSpan(event : Update) = {
		lifeSpan = lifeSpan - event.delta
		if (lifeSpan <= 0) destroy
	}
}