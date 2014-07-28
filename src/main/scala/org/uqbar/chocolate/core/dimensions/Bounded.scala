package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.vectors._
import org.uqbar.math.vectors.Vector
import org.uqbar.chocolate.core.utils.Implicits._

object Bounded {
	def apply(position : Vector)(aSize : Vector) = new Bounded {
		def top = position.y
		def left = position.x
		def width = aSize.x
		def height = aSize.y
	}
}
trait Bounded {
	def top : Double
	def left : Double
	def width : Double
	def height : Double

	def bottom = top + height
	def right = left + width
	def center = left + width / 2
	def middle = top + height / 2

	def corner(tx : Bounded ⇒ Double, ty : Bounded ⇒ Double) : Vector = (tx(this), ty(this))

	def top(translation : Vector) : Double = top + translation.y
	def left(translation : Vector) : Double = left + translation.x
	def bottom(translation : Vector) : Double = bottom + translation.y
	def right(translation : Vector) : Double = right + translation.x
	def center(translation : Vector) : Double = center + translation.x
	def middle(translation : Vector) : Double = middle + translation.y

	def size : Vector = (width, height)

	def shortestAlignVector(target : Bounded) = List[Vector](
		(0, target.bottom - top),
		(target.left - right, 0),
		(0, target.top - bottom),
		(target.right - left, 0)
	).minBy(_.module)
}