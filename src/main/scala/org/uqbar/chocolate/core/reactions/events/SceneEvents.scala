package org.uqbar.chocolate.core.reactions.events

import java.awt.Graphics2D
import scala.collection.Map
import scala.collection.Set
import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.ContinuableEvent

case class ComponentAdded(component : GameComponent) extends SimpleEvent
case class ComponentRemoved(component : GameComponent) extends SimpleEvent

case class SceneSetAsCurrent() extends SimpleEvent

case class RenderRequired(graphics : Graphics2D) extends SimpleEvent

case class Destroyed(component : GameComponent) extends SimpleEvent

case class Update(delta : Double) extends SimpleEvent with ContinuableEvent {
	def this() = this(0)
	def next(d : Double) = copy(delta = d)
}