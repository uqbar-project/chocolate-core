package org.uqbar.chocolate.core;

import java.awt.Dimension
import java.util.Arrays
import java.util.Collection
import org.uqbar.chocolate.core.collisions.CircularBoundingBox
import org.uqbar.chocolate.core.collisions.RectangularBoundingBox
import org.uqbar.chocolate.core.components.CollisionTestComponent
import scala.math._
import collection.JavaConversions._
import org.uqbar.chocolate.core.collisions.CircularBoundingBox
import org.uqbar.chocolate.core.collisions.CircularBoundingBox

import org.uqbar.math.spaces.R2._

import org.scalatest.fixture.FunSuite
import org.scalatest.Outcome

class CollisionTest extends FunSuite {
	val A_GROUP = 0

	val COMPONENT_SETS = {
		val delta = sin(Pi / 4) + 0.000000000000001

		List(
			List(
				new CollisionTestComponent(CircularBoundingBox(5), (10, 10), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (10, 10), 6),
				new CollisionTestComponent(CircularBoundingBox(4), (10, 10), 2),
				new CollisionTestComponent(CircularBoundingBox(5), (10, 1), 2),
				new CollisionTestComponent(CircularBoundingBox(5), (19, 10), 2),
				new CollisionTestComponent(CircularBoundingBox(5), (10, 19), 2),
				new CollisionTestComponent(CircularBoundingBox(5), (1, 10), 2)
			),
			List(
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (10, 10), 6),
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (10, 10), 6),
				new CollisionTestComponent(new RectangularBoundingBox(8, 8), (11, 11), 2),
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (10, 1), 5),
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (19, 10), 5),
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (10, 19), 5),
				new CollisionTestComponent(new RectangularBoundingBox(10, 10), (1, 10), 5),
				new CollisionTestComponent(new RectangularBoundingBox(16, 4), (7, 3), 1),
				new CollisionTestComponent(new RectangularBoundingBox(4, 16), (23, 7), 1),
				new CollisionTestComponent(new RectangularBoundingBox(16, 4), (7, 23), 1),
				new CollisionTestComponent(new RectangularBoundingBox(4, 16), (3, 7), 1)
			),
			List(
				new CollisionTestComponent(new RectangularBoundingBox(2, 2), (14, 14), 6),
				new CollisionTestComponent(new RectangularBoundingBox(6, 20), (12, 5), 8),
				new CollisionTestComponent(new RectangularBoundingBox(4, 18), (13, 6), 8),
				new CollisionTestComponent(new RectangularBoundingBox(20, 6), (5, 12), 16),
				new CollisionTestComponent(new RectangularBoundingBox(18, 4), (6, 13), 8),
				new CollisionTestComponent(CircularBoundingBox(2), (15, 15), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (15, 15), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (15, 5), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (25, 15), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (15, 25), 6),
				new CollisionTestComponent(CircularBoundingBox(5), (5, 15), 6),
				new CollisionTestComponent(CircularBoundingBox(1), (12 - delta, 5 - delta), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (15, 4), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (18 + delta, 5 - delta), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (19, 7), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (25, 12), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (25, 15), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (25, 18), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (22, 18), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (18 + delta, 25 + delta), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (15, 26), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (12 - delta, 25 + delta), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (11, 23), 1),
				new CollisionTestComponent(CircularBoundingBox(1), (5, 18), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (5, 15), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (5, 12), 2),
				new CollisionTestComponent(CircularBoundingBox(1), (8, 12), 2)
			)
		)
	}

	val ZONES_SIZES = List(
		new Dimension(100, 100),
		new Dimension(10, 10),
		new Dimension(5, 5),
		new Dimension(1, 1)
	)


	type FixtureParam = (List[CollisionTestComponent],Dimension)

	def withFixture(test: OneArgTest): Outcome = {
		(for {
			zoneSize <- ZONES_SIZES
			components <- COMPONENT_SETS
		} yield try test((components,zoneSize))
		finally components foreach { _.reset }).last
	}

	// ****************************************************************
	// ** TEST CASES
	// ****************************************************************

	test("collisionCountsAreAsExpected") { case (components, zoneSize) =>
		val scene = new GameScene(components : _*)
		scene.collisionZoneSize = zoneSize

		components foreach { _.triggerCollisions }

		for(component ← components) assert(component.expectedCollisionCount === component.collisionCount)
	}
}