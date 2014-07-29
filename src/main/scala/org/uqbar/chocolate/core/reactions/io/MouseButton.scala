package org.uqbar.chocolate.core.reactions.io

import org.uqbar.math.vectors.Vector
import org.uqbar.chocolate.core.reactions.events.Pressable

trait MouseButton extends Pressable { def position: Vector }

object MouseButton {
	case class Left(position: Vector) extends MouseButton
	case class Middle(position: Vector) extends MouseButton
	case class Right(position: Vector) extends MouseButton
}