package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.vectors._
import org.uqbar.math.vectors.Vector
import org.uqbar.chocolate.core.utils.Implicits._

object Bounded {
	def apply(position: Vector)(aSize: Vector) = new Bounded {
		def top = position.y
		def left = position.x
		def size = aSize
	}
}
trait Bounded {
	def top: Double
	def left: Double
	def size: Vector

	def bottom = top + size.y
	def right = left + size.x
	def center = left + size.x / 2
	def middle = top + size.y / 2

	def corner(tx: Bounded ⇒ Double, ty: Bounded ⇒ Double): Vector = (tx(this), ty(this))

	def top(translation: Vector): Double = top + translation.y
	def left(translation: Vector): Double = left + translation.x
	def bottom(translation: Vector): Double = bottom + translation.y
	def right(translation: Vector): Double = right + translation.x
	def center(translation: Vector): Double = center + translation.x
	def middle(translation: Vector): Double = middle + translation.y

	def shortestAlignVector(target: Bounded) = List[Vector](
		(0, target.bottom - top),
		(target.left - right, 0),
		(0, target.top - bottom),
		(target.right - left, 0)
	).minBy(_.module)
}