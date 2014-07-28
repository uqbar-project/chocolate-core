package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.vectors.Vector

trait Scalable {
	//TODO: Estas operaciones deberían ser destructivas, no destructivas o ambas?
	// R: Destructivas! Pero retornandose a si mismo. Además tenés el clone, para cuando no quieras efecto

	def width : Double
	def height : Double

	def scale(hRatio : Double = 1)(vRatio : Double = 1) : this.type

	def scale(scaleRatios : Vector) : this.type = scale(scaleRatios.x)(scaleRatios.y)

	def scaleTo(targetWidth : Double = width)(targetHeight : Double = height) : this.type = scale(targetWidth / width, targetHeight / height)
	def scaleTo(targetSize : Vector) : this.type = scaleTo(targetSize.x)(targetSize.y)

	def scaleBy(ratio : Double) : this.type = scale(ratio, ratio)

	def scaleHorizontallyTo(desiredWidth : Double, keepAspectRatio : Boolean) : this.type = {
		val ratio = desiredWidth / width

		if (keepAspectRatio) scaleBy(ratio) else scale(ratio)()
	}

	def scaleVerticallyTo(desiredHeight : Double, keepAspectRatio : Boolean) : this.type = {
		val ratio = desiredHeight / height

		if (keepAspectRatio) scaleBy(ratio) else scale()(ratio)
	}

	def scaleWithin(maxSize : Vector) : this.type = {
		if ((maxSize.x - width).abs < (maxSize.y - height).abs) scaleHorizontallyTo(maxSize.x, true) else scaleVerticallyTo(maxSize.y, true)
	}
}