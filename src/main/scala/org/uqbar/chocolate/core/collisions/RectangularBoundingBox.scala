package org.uqbar.chocolate.core.collisions;

import scala.collection.immutable.List

import org.uqbar.math.spaces.R2._

case class RectangularBoundingBox(asize: Vector) extends BoundingBox {

	def left = translation(X)
	def top = translation(Y)
	def size = asize

	// ****************************************************************
	// ** COLLISION DETECTION
	// ****************************************************************

	protected def collidesWith(translation: Vector)(targetTranslation: Vector) = {
		case rectangle: RectangularBoundingBox ⇒ collidesWithRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collidesWithRectangle(translation: Vector)(target: RectangularBoundingBox, targetTranslation: Vector): Boolean = {
		val ownLeft = left + translation(X)
		val ownTop = top + translation(Y)
		val ownRight = right + translation(X)
		val ownBottom = bottom + translation(Y)
		val targetLeft = target.left + targetTranslation(X)
		val targetTop = target.top + targetTranslation(Y)
		val targetRight = target.right + targetTranslation(X)
		val targetBottom = target.bottom + targetTranslation(Y)

		(ownLeft <= targetLeft && targetLeft < ownRight || targetLeft <= ownLeft && ownLeft < targetRight) &&
			(ownTop <= targetTop && targetTop < ownBottom || targetTop <= ownTop && ownTop < targetBottom)
	}

	// ****************************************************************
	// ** COLLISION CORRECTION QUERIES
	// ****************************************************************

	def collisionCorrectionVector(translation: Vector)(targetTranslation: Vector) = {
		case rectangle: RectangularBoundingBox ⇒ collisionCorrectionVectorAgainstRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collisionCorrectionVectorAgainstRectangle(translation: Vector)(target: RectangularBoundingBox, targetTranslation: Vector) = List[Vector](
		(0, target.bottom(targetTranslation) - top(translation)),
		(target.right(targetTranslation) - left(translation), 0),
		(0, target.top(targetTranslation) - bottom(translation)),
		(target.left(targetTranslation) - right(translation), 0)
	).minBy(_.module)
}