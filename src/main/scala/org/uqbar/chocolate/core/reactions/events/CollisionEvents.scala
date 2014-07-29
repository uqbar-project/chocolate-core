package org.uqbar.chocolate.core.reactions.events

import org.uqbar.chocolate.core.components.Collisionable


case class Collision(collidedComponent : Collisionable) extends GameEvent

case class CollisionEnd(collidedComponent : Collisionable) extends GameEvent