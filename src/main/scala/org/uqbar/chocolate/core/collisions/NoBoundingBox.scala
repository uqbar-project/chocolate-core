package org.uqbar.chocolate.core.collisions;

import org.uqbar.cacao.Renderer
import java.awt.geom.Point2D;
import org.uqbar.math.vectors.Vector
import org.uqbar.math.vectors._

case object NoBoundingBox extends BoundingBox {
	def left = 0
	def top = 0
	def size = Origin

	protected def collidesWith(translation: Vector)(targetTranslation: Vector) = {
		case _ ⇒ false
	}

	def collisionCorrectionVector(translation: Vector)(targetTranslation: Vector) = {
		case _ ⇒ Origin
	}
}