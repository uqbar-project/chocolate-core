package org.uqbar.chocolate.core.appearances

import org.uqbar.chocolate.core.dimensions.Scalable
import org.uqbar.math.vectors.Vector

trait ComplexAppearance extends Appearance with Scalable {

	// ****************************************************************
	// ** ROTATING
	// ****************************************************************

	//TODO

	// ****************************************************************
	// ** REPEATING
	// ****************************************************************

	def repeat(horizontalRepetitions : Double, verticalRepetitions : Double)

	def repeatHorizontally(repetitions : Double) = repeat(repetitions, 1)
	def repeatVertically(repetitions : Double) = repeat(1, repetitions)

	def repeatHorizontallyToCover(widthToCover : Double) = repeatHorizontally(widthToCover / width)
	def repeatVerticallyToCover(heightToCover : Double) = repeatVertically(heightToCover / height)
	def repeatToCover(areaToCover : Vector) = repeat(areaToCover.x / width, areaToCover.y / height)

	// ****************************************************************
	// ** CROPPING
	// ****************************************************************

	def crop(x : Double, y : Double, width : Double, height : Double) : this.type
	def crop(width : Double, height : Double) : this.type = crop(0, 0, width, height)

	// ****************************************************************
	// ** FLIPPING
	// ****************************************************************

	def flipHorizontally
	def flipVertically
}