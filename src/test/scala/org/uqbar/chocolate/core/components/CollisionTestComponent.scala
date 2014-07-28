package org.uqbar.chocolate.core.components

import org.uqbar.chocolate.core.CollisionTest
import org.uqbar.chocolate.core.collisions.BoundingBox
import org.uqbar.chocolate.core.reactions.annotations.collision.OnCollision
import org.uqbar.chocolate.core.reactions.events.Collision
import scala.collection.mutable.HashSet
import org.uqbar.math.vectors.Vector

class CollisionTestComponent(
		var boundingBox : BoundingBox,
		initialPosition : Vector,
		val expectedCollisionCount : Int) extends Collisionable {

	var collided = new HashSet[Collisionable]

	translation = initialPosition

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	@OnCollision
	def collidedWithA(event : Collision) = collided add event.collidedComponent

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def collisionCount = collided.size

	override def toString = "CollisionTestComponent[" + boundingBox + "] @ " + translation

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def reset = collided.clear

}