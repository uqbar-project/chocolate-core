package org.uqbar.chocolate.core.components

import org.uqbar.chocolate.core.CollisionTest
import org.uqbar.chocolate.core.collisions.BoundingBox
import org.uqbar.chocolate.core.reactions.events.Collision
import scala.collection.mutable.HashSet
import org.uqbar.math.spaces.R2._

class CollisionTestComponent(
		var boundingBox : BoundingBox,
		initialPosition : Vector,
		val expectedCollisionCount : Int) extends Collisionable {

	var collided = new HashSet[Collisionable]

	translation = initialPosition

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	in {case Collision(target) => collided add target }

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