package org.uqbar.chocolate.core.collisions;

import scala.collection.immutable.List

import org.uqbar.chocolate.core.dimensions.Vector
import org.uqbar.chocolate.core.dimensions.Vector._

case class RectangularBoundingBox(asize : Vector) extends BoundingBox {

	def left = translation.x
	def top = translation.y
	def width = asize.x
	def height = asize.y

	// ****************************************************************
	// ** COLLISION DETECTION
	// ****************************************************************

	protected def collidesWith(translation : Vector)(targetTranslation : Vector) = {
		case rectangle : RectangularBoundingBox ⇒ collidesWithRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collidesWithRectangle(translation : Vector)(target : RectangularBoundingBox, targetTranslation : Vector) : Boolean = {
		val ownLeft = left + translation.x
		val ownTop = top + translation.y
		val ownRight = right + translation.x
		val ownBottom = bottom + translation.y
		val targetLeft = target.left + targetTranslation.x
		val targetTop = target.top + targetTranslation.y
		val targetRight = target.right + targetTranslation.x
		val targetBottom = target.bottom + targetTranslation.y

		(ownLeft <= targetLeft && targetLeft < ownRight || targetLeft <= ownLeft && ownLeft < targetRight) &&
			(ownTop <= targetTop && targetTop < ownBottom || targetTop <= ownTop && ownTop < targetBottom)
	}

	// ****************************************************************
	// ** COLLISION CORRECTION QUERIES
	// ****************************************************************

	def collisionCorrectionVector(translation : Vector)(targetTranslation : Vector) = {
		case rectangle : RectangularBoundingBox ⇒ collisionCorrectionVectorAgainstRectangle(translation)(rectangle, targetTranslation)
	}

	protected def collisionCorrectionVectorAgainstRectangle(translation : Vector)(target : RectangularBoundingBox, targetTranslation : Vector) = List[Vector](
		(0, target.bottom(targetTranslation) - top(translation)),
		(target.right(targetTranslation) - left(translation), 0),
		(0, target.top(targetTranslation) - bottom(translation)),
		(target.left(targetTranslation) - right(translation), 0)
	).minBy(_.module)
}