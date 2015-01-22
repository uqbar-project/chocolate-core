package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.spaces.R2._
import org.uqbar.chocolate.core.utils.Implicits._

object Bounded {
	def apply(position: Vector)(aSize: Vector) = new Bounded {
		def top = position(Y)
		def left = position(X)
		def size = aSize
	}
}
trait Bounded {
	def top: Double
	def left: Double
	def size: Vector

	def bottom = top + size(Y)
	def right = left + size(X)
	def center = left + size(X) / 2
	def middle = top + size(Y) / 2

	def corner(tx: Bounded ⇒ Double, ty: Bounded ⇒ Double): Vector = (tx(this), ty(this))

	def top(translation: Vector): Double = top + translation(Y)
	def left(translation: Vector): Double = left + translation(X)
	def bottom(translation: Vector): Double = bottom + translation(Y)
	def right(translation: Vector): Double = right + translation(X)
	def center(translation: Vector): Double = center + translation(X)
	def middle(translation: Vector): Double = middle + translation(Y)

	def shortestAlignVector(target: Bounded) = List[Vector](
		(0, target.bottom - top),
		(target.left - right, 0),
		(0, target.top - bottom),
		(target.right - left, 0)
	).minBy(_.module)
}