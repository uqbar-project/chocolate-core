package org.uqbar.chocolate.core.reactions.io

import org.uqbar.math.vectors.Vector
import org.uqbar.chocolate.core.reactions.events.Pressable

trait MouseButton extends Pressable

object MouseButton {
	case object Left extends MouseButton
	case object Middle extends MouseButton
	case object Right extends MouseButton
}