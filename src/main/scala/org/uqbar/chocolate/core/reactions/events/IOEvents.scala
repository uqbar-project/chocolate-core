package org.uqbar.chocolate.core.reactions.events

import org.uqbar.math.vectors.Vector

trait IOEvent extends GameEvent
trait Pressable

object Hold {
	def apply(target: Pressable): Hold = Hold(target, 0)
}
case class Hold(target: Pressable, delta: Double) extends IOEvent with ContinuableEvent {
	def next(d: Double) = copy(delta = d)
}
case class Pressed(target: Pressable) extends IOEvent
case class Released(target: Pressable) extends IOEvent

case class Typed(char: Char) extends GameEvent

case class MouseMoved(position: Vector) extends GameEvent