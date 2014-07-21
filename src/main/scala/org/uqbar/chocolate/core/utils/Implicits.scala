package org.uqbar.chocolate.core.utils

import java.awt.Dimension
import java.awt.Point
import java.awt.geom.Point2D
import java.lang.annotation.Annotation
import java.lang.reflect.Method
import math.pow

import scala.language.existentials
import scala.language.implicitConversions
import scala.collection.JavaConversions.setAsJavaSet

object Implicits {
	implicit def tuple_to_dimension(t : (Int, Int)) : Dimension = new Dimension(t._1, t._2)

	implicit def double_to_int(double : Double) = double.toInt

	def min(numbers : Double*) = numbers.min

	implicit class MethodExtender(method : Method) {
		def allAnnotations : Set[Annotation] = {
			val answer = method.getAnnotations.toSet
			val superClass = method.getDeclaringClass.getSuperclass

			if (superClass != null) {
				try {
					val superMethod = superClass.getMethod(method.getName(), method.getParameterTypes() : _*)
					answer.addAll(superMethod.allAnnotations)
				} catch {
					case e : NoSuchMethodException ⇒ {}
				}
			}

			answer
		}
	}

	implicit class DoubleExtender(n : Double) {
		def even = n % 2 == 0
		def odd = !even

		def **(exponent : Double) = pow(n, exponent)

		def isBetween(min : Double)(max : Double) = min <= n && n <= max
	}
}