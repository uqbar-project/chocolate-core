package org.uqbar.chocolate.core.reactions.events

import org.uqbar.cacao.Renderer
import scala.collection.Map
import scala.collection.Set
import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.cacao.Renderer

case class ComponentAdded(component: GameComponent) extends GameEvent
case class ComponentRemoved(component: GameComponent) extends GameEvent

case object SceneSetAsCurrent extends GameEvent

case class Render(renderer: Renderer) extends GameEvent

case class Destroyed(component: GameComponent) extends GameEvent

case class Update(delta: Double) extends GameEvent with ContinuableEvent {
	def this() = this(0)
	def next(d: Double) = copy(delta = d)
}