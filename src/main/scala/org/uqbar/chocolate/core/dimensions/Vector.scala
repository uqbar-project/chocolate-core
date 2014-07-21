package org.uqbar.chocolate.core.dimensions

import scala.math._
import scala.language.implicitConversions

object Vector {
	val ORIGIN : Vector = (0, 0)

	implicit def touple_to_vector[T <% Double, U <% Double](t : (T, U)) : MutableVector = MutableVector(t._1, t._2)

	implicit class DoubleVectorExtender(val d : Double) {
		def *(v : Vector) = v * d
	}
}

trait Vector {
	def x : Double
	def y : Double

	def +(v : Vector) : Vector = (x + v.x, y + v.y)
	def *(d : Double) : Vector = (x * d, y * d)
	def -(v : Vector) = this + -v
	def /(d : Double) = this * (1 / d)
	def unary_- = this * -1

	def *(v : Vector) : Double = x * v.x + y * v.y
	def **(v : Vector) : Vector = (x * v.x, y * v.y)
	def /(v : Vector) : Vector = (x / v.x, y / v.y)

	def asVersor = this / module

	def min(v : Vector) : Vector = (x min v.x, y min v.y)
	def max(v : Vector) : Vector = (x max v.x, y max v.y)

	def map(f : Double ⇒ Double) : Vector = (f(x), f(y))
	def reduce[T](f : (Double, Double) ⇒ T) = f(x, y)

	def module = sqrt(x * x + y * y)

	def distanceTo(v : Vector) = (this - v).module

	def squareDistanceTo(p : Vector) = {
		val v = (p - this)
		v.x * v.x + v.y * v.y
	}

	def manhattanDistance(v : Vector) = (v - this).reduce((i : Double, j : Double) ⇒ (i.abs + j.abs).toInt)

	override def toString = (x, y).toString
}

case class MutableVector(var x : Double, var y : Double) extends Vector {
	def +=(v : Vector) = set(x + v.x, y + v.y)
	def *=(d : Double) = set(x * d, y * d)
	def -=(v : Vector) = set(x - v.x, y - v.y)
	def /=(d : Double) = set(x / d, y / d)

	def set(v : Vector) : this.type = set(v.x, v.y)
	def set(x : Double, y : Double) : this.type = {
		this.x = x
		this.y = y
		this
	}
}