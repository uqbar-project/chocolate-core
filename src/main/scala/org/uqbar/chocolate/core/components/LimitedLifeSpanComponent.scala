package org.uqbar.chocolate.core.components;

import org.uqbar.chocolate.core.appearances.Appearance
import org.uqbar.chocolate.core.reactions.events.Update;
import org.uqbar.math.vectors.Vector

class LimitedLifeSpanComponent(val appearance: Appearance, var lifeSpan: Double) extends Visible {

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	in {
		case Update(delta) =>
			lifeSpan = lifeSpan - delta
			if (lifeSpan <= 0) destroy
	}
}