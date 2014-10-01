package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.vectors.Vector

trait Scalable {
	//TODO: Estas operaciones deberían ser destructivas, no destructivas o ambas?
	// R: Destructivas! Pero retornandose a si mismo. Además tenés el clone, para cuando no quieras efecto

	def size: Vector

	def scale(scaleRatios: Vector): this.type

	def scale(hRatio: Double = 1)(vRatio: Double = 1): this.type = scale(hRatio, vRatio)

	def scaleTo(targetWidth: Double = size.x)(targetHeight: Double = size.y): this.type = scale(targetWidth / size.x, targetHeight / size.y)
	def scaleTo(targetSize: Vector): this.type = scaleTo(targetSize.x)(targetSize.y)

	def scaleBy(ratio: Double): this.type = scale(ratio, ratio)

	def scaleHorizontallyTo(desiredWidth: Double, keepAspectRatio: Boolean): this.type = {
		val ratio = desiredWidth / size.x

		if (keepAspectRatio) scaleBy(ratio) else scale(ratio)()
	}

	def scaleVerticallyTo(desiredHeight: Double, keepAspectRatio: Boolean): this.type = {
		val ratio = desiredHeight / size.y

		if (keepAspectRatio) scaleBy(ratio) else scale()(ratio)
	}

	def scaleWithin(maxSize: Vector): this.type = {
		if ((maxSize.x - size.x).abs < (maxSize.y - size.y).abs) scaleHorizontallyTo(maxSize.x, true) else scaleVerticallyTo(maxSize.y, true)
	}
}