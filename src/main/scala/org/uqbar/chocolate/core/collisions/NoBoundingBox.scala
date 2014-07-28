package org.uqbar.chocolate.core.collisions;

import java.awt.Graphics2D
import java.awt.geom.Point2D;
import org.uqbar.math.vectors.Vector
import org.uqbar.math.vectors._

case object NoBoundingBox extends BoundingBox {
	def left = 0
	def top = 0
	def width = 0
	def height = 0

	protected def collidesWith(translation : Vector)(targetTranslation : Vector) = {
		case _ ⇒ false
	}

	def collisionCorrectionVector(translation : Vector)(targetTranslation : Vector) = {
		case _ ⇒ Origin
	}
}