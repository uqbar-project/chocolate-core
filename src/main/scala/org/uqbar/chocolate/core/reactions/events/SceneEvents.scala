package org.uqbar.chocolate.core.reactions.events

import java.awt.Graphics2D
import scala.collection.Map
import scala.collection.Set
import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.chocolate.core.components.GameComponent

case class ComponentAdded(component: GameComponent) extends GameEvent
case class ComponentRemoved(component: GameComponent) extends GameEvent

case class SceneSetAsCurrent() extends GameEvent

case class RenderRequired(graphics: Graphics2D) extends GameEvent

case class Destroyed(component: GameComponent) extends GameEvent

case class Update(delta: Double) extends GameEvent with ContinuableEvent {
	def this() = this(0)
	def next(d: Double) = copy(delta = d)
}