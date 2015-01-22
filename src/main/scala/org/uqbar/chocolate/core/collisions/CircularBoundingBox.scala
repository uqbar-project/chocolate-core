package org.uqbar.chocolate.core.collisions;

import org.uqbar.cacao.Renderer
import java.awt.geom.Point2D
import org.uqbar.chocolate.core.utils.Implicits._
import scala.math.{ sqrt, pow }
import org.uqbar.math.spaces.R2._

case class CircularBoundingBox(radius: Double) extends BoundingBox {
	def left = translation(X) - radius
	def top = translation(Y) - radius

	def size = (diameter, diameter)

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	def scale(hRatio: Double)(vRatio: Double) = null //TODO

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def diameter = radius * 2

	override def toString = s"Circle($radius) @ ($center,$middle)"

	// ****************************************************************
	// ** COLLISION DETECTION
	// ****************************************************************

	protected def collidesWith(translation: Vector)(targetTranslation: Vector) = {
		case circle: CircularBoundingBox ⇒ collidesWithCircle(translation)(circle, targetTranslation)
		case rectangle: RectangularBoundingBox ⇒ collidesWithRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collidesWithCircle(translation: Vector)(target: CircularBoundingBox, targetTranslation: Vector): Boolean = {
		val distanceSq = ((center, middle) + translation).squareDistanceTo((target.center, target.middle) + targetTranslation)
		val radiusSum = radius + target.radius

		distanceSq < radiusSum * radiusSum
	}

	protected def collidesWithRectangle(translation: Vector)(target: RectangularBoundingBox, targetTranslation: Vector): Boolean = {
		val targetLeft = target.left + targetTranslation(X)
		val targetRight = target.right + targetTranslation(X)
		val targetTop = target.top + targetTranslation(Y)
		val targetBottom = target.bottom + targetTranslation(Y)
		val extendedTargetTop = targetTop - radius
		val extendedTargetRight = targetRight + radius
		val extendedTargetBottom = targetBottom + radius
		val extendedTargetLeft = targetLeft - radius
		val radiusSq = radius * radius
		val centerPoint: Vector = (center, middle) + translation

		if (centerPoint(X) >= targetLeft)
			if (centerPoint(X) <= targetRight)
				centerPoint(Y) > extendedTargetTop && centerPoint(Y) < extendedTargetBottom
			else if (centerPoint(Y) >= targetTop) {
				if (centerPoint(Y) <= targetBottom) centerPoint(X) < extendedTargetRight
				else centerPoint.squareDistanceTo(targetRight, targetBottom) < radiusSq
			} else centerPoint.squareDistanceTo(targetRight, targetTop) < radiusSq
		else if (centerPoint(Y) >= targetTop)
			if (centerPoint(Y) <= targetBottom) centerPoint(X) > extendedTargetLeft
			else centerPoint.squareDistanceTo(targetLeft, targetBottom) < radiusSq
		else centerPoint.squareDistanceTo(targetLeft, targetTop) < radiusSq
	}

	// ****************************************************************
	// ** COLLISION CORRECTION QUERIES
	// ****************************************************************

	def collisionCorrectionVector(translation: Vector)(targetTranslation: Vector) = {
		case circle: CircularBoundingBox ⇒ collisionCorrectionVectorAgainstCircle(translation)(circle, targetTranslation)
		case rectangle: RectangularBoundingBox ⇒ collisionCorrectionVectorAgainstRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collisionCorrectionVectorAgainstCircle(translation: Vector)(target: CircularBoundingBox, targetTranslation: Vector) = {
		val vector = (center(translation), middle(translation)) - (target.center(targetTranslation), target.middle(targetTranslation))
		val scale = 1 - vector.module / (radius + target.radius)

		vector * scale
	}

	protected def collisionCorrectionVectorAgainstRectangle(translation: Vector)(target: RectangularBoundingBox, targetTranslation: Vector): Vector = {
		val half: Vector = (center(translation), middle(translation))

		val targetTop = target.top(targetTranslation)
		val targetBottom = target.bottom(targetTranslation)
		val targetLeft = target.left(targetTranslation)
		val targetRight = target.right(targetTranslation)

		val bottomDistance = (targetBottom - top(translation)).abs
		val leftDistance = (targetLeft - right(translation)).abs
		val topDistance = (targetTop - bottom(translation)).abs
		val rightDistance = (targetRight - left(translation)).abs
		val minDistance = min(bottomDistance, leftDistance, topDistance, rightDistance)

		val radiusSq = radius * radius

		if (minDistance == bottomDistance) {
			if (targetRight < half(X))
				-(0, half(Y) - sqrt(radiusSq - pow(half(X) - targetRight, 2)) - targetBottom)
			else if (targetLeft > half(X))
				-(0, half(Y) - sqrt(radiusSq - pow(half(X) - targetLeft, 2)) - targetBottom)
			else
				(0, bottomDistance)
		} else if (minDistance == topDistance) {
			if (targetRight < half(X))
				-(0, half(Y) + sqrt(radiusSq - pow(half(X) - targetRight, 2)) - targetTop)
			else if (targetLeft > half(X))
				-(0, half(Y) + sqrt(radiusSq - pow(half(X) - targetLeft, 2)) - targetTop)
			else
				(0, -topDistance)
		} else if (minDistance == leftDistance) {
			if (targetBottom < half(Y))
				-(half(X) + sqrt(radiusSq - pow(half(Y) - targetBottom, 2)) - targetLeft, 0)
			else if (targetTop > half(Y))
				-(half(X) + sqrt(radiusSq - pow(half(Y) - targetTop, 2)) - targetLeft, 0)
			else
				(-leftDistance, 0)
		} else {
			if (targetBottom < half(Y))
				(-half(X) + sqrt(radiusSq + pow(half(Y) - targetBottom, 2)) + targetRight, 0)
			else if (targetTop > half(Y))
				(-half(X) + sqrt(radiusSq + pow(half(Y) - targetTop, 2)) + targetRight, 0)
			else
				(rightDistance, 0)
		}

		//		def hCorrection(SSS : Double, TTT : Double, UUU : Double) =
		//			if (targetBottom < half(Y)) (-half(X) + UUU * sqrt(radiusSq + (half(Y) - targetBottom) ** 2) + SSS, 0)
		//			else if (targetTop > half(Y)) (-half(X) + UUU * sqrt(radiusSq + (half(Y) - targetTop) ** 2) + SSS, 0)
		//			else (UUU * TTT, 0)

		//Left
		//		SSS = targetLeft
		//		TTT = leftDistance
		//		UUU = -1

		//Right
		//		SSS = targetRight
		//		TTT = rightDistance
		//		UUU = 1
	}
}