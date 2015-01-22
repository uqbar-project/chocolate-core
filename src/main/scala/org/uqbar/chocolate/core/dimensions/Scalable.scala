package org.uqbar.chocolate.core.dimensions

import org.uqbar.math.spaces.R2._

trait Scalable {
	//TODO: Estas operaciones deberían ser destructivas, no destructivas o ambas?
	// R: Destructivas! Pero retornandose a si mismo. Además tenés el clone, para cuando no quieras efecto

	def size: Vector

	def scale(scaleRatios: Vector): this.type

	def scale(hRatio: Double = 1)(vRatio: Double = 1): this.type = scale(hRatio, vRatio)

	def scaleTo(targetWidth: Double = size(X))(targetHeight: Double = size(Y)): this.type = scale(targetWidth / size(X), targetHeight / size(Y))
	def scaleTo(targetSize: Vector): this.type = scaleTo(targetSize(X))(targetSize(Y))

	def scaleBy(ratio: Double): this.type = scale(ratio, ratio)

	def scaleHorizontallyTo(desiredWidth: Double, keepAspectRatio: Boolean): this.type = {
		val ratio = desiredWidth / size(X)

		if (keepAspectRatio) scaleBy(ratio) else scale(ratio)()
	}

	def scaleVerticallyTo(desiredHeight: Double, keepAspectRatio: Boolean): this.type = {
		val ratio = desiredHeight / size(Y)

		if (keepAspectRatio) scaleBy(ratio) else scale()(ratio)
	}

	def scaleWithin(maxSize: Vector): this.type = {
		if ((maxSize(X) - size(X)).abs < (maxSize(Y) - size(Y)).abs) scaleHorizontallyTo(maxSize(X), true) else scaleVerticallyTo(maxSize(Y), true)
	}
}