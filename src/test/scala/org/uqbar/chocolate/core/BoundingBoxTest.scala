package org.uqbar.chocolate.core;

import scala.Int.int2double

import org.uqbar.chocolate.core.collisions.RectangularBoundingBox
import org.uqbar.math.vectors._
import org.scalatest.FunSuite

//TODO: More exhaustive testing
class BoundingBoxTest extends FunSuite {

	test("collisionCorrectionVectorsAreAsExpected") {
		val t = (10, 10)

		val a = RectangularBoundingBox(10, 10)
		a.move(-5, -5)

		val b = RectangularBoundingBox(5, 5)
		b.move(-2.5, 4)

		val expected: Vector = (0, 1)

		assert(expected === b.collisionCorrectionVectorAgainst(t)(a, t))
	}

}
