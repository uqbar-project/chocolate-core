package org.uqbar.chocolate.core.reactions.events

import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.chocolate.core.reactions.EventSignature

case class Collision(collidedComponent : Collisionable) extends SimpleEvent

case class CollisionEnd(collidedComponent : Collisionable) extends SimpleEvent