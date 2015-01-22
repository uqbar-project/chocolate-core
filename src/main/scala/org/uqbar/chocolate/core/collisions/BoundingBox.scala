package org.uqbar.chocolate.core.collisions;

import org.uqbar.cacao.Renderer
import java.awt.geom.Point2D
import org.uqbar.chocolate.core.dimensions.Bounded
import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.math.spaces.R2._
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.chocolate.core.dimensions.Scalable
import org.uqbar.chocolate.core.utils.Cloneable

abstract class BoundingBox extends Bounded with Positioned {

	def apply(translation: Vector)(target: BoundingBox, targetTranslation: Vector) =
		if (collidesWith(translation)(targetTranslation) isDefinedAt target) collidesWith(translation)(targetTranslation)(target)
		else target.collidesWith(targetTranslation)(translation)(this)

	def collisionCorrectionVectorAgainst(translation: Vector, target: Collisionable): Vector = collisionCorrectionVectorAgainst(translation)(target.boundingBox, target.translation)
	def collisionCorrectionVectorAgainst(translation: Vector)(target: BoundingBox, targetTranslation: Vector) =
		if (collisionCorrectionVector(translation)(targetTranslation) isDefinedAt target)
			collisionCorrectionVector(translation)(targetTranslation)(target)
		else target.collisionCorrectionVector(targetTranslation)(translation)(this)

	protected def collidesWith(translation: Vector)(targetTranslation: Vector): PartialFunction[BoundingBox, Boolean]

	protected def collisionCorrectionVector(translation: Vector)(targetTranslation: Vector): PartialFunction[BoundingBox, Vector]
}