package org.uqbar.chocolate.core.appearances;

import org.uqbar.math.spaces.R2._
import org.uqbar.cacao.Renderer

class Animation(val meanTime: Double, val sprites: Sprite*) extends ComplexAppearance {
	var currentIndex = 0
	var remainingTime = meanTime

	def size = currentSprite.size

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	def scale(ratio: Vector) = {
		sprites.map(_.scale(ratio))
		this
	}

	//TODO: DEPRECATED CACAO
	//	def crop(x: Double, y: Double, width: Double, height: Double) = {
	//		sprites.map(_.crop(x, y, width, height))
	//		this
	//	}
	//	def flipHorizontally = sprites.map(_.flipHorizontally)
	//	def flipVertically = sprites.map(_.flipVertically)

	def repeat(horizontalRepetitions: Double, verticalRepetitions: Double) {
		sprites.map(_.repeat(horizontalRepetitions, verticalRepetitions))
	}

	def rotate(angle: Double) = sprites.map(_.rotate(angle))

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def duration = meanTime * this.sprites.length

	override def clone = {
		val answer = super.clone
		answer.currentIndex = 0
		answer.remainingTime = answer.meanTime
		answer
	}

	def currentSprite = sprites(currentIndex)

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	override def update(delta: Double) = {
		remainingTime -= delta
		if (remainingTime <= 0) advance
	}

	protected def doRenderAt(position: Vector, renderer: Renderer) = currentSprite.renderAt(position)(renderer)

	protected def advance = {
		currentIndex = (currentIndex + 1) % sprites.length
		remainingTime = meanTime - remainingTime
	}
}
