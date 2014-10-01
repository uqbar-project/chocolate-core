package org.uqbar.chocolate.core.appearances;

import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.math.vectors.Vector
import org.uqbar.math.vectors._
import org.uqbar.cacao.Renderer
import org.uqbar.cacao.Image

class Sprite(var image: Image) extends ComplexAppearance {

	def size = image.size

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	// TODO: DEPRECATE COCOA
	//	def ::(target: Sprite) = {
	//		val nleft = left min target.left
	//		val nright = right max target.right
	//		val ntop = top min target.top
	//		val nbottom = bottom max target.bottom
	//		val nwidth = nright - nleft
	//		val nheight = nbottom - ntop
	//
	//		val img = new BufferedImage(nwidth, nheight, image.getType)
	//		val g = img.createGraphics
	//
	//		g.translate(-nleft, -ntop)
	//
	//		renderAt(Origin)(g)
	//		target.renderAt(Origin)(g)
	//
	//		g.dispose
	//
	//		val answer = new Sprite(img)
	//		answer.translation = (nleft, ntop)
	//		answer
	//	}

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	override def scale(ratio: Vector) = {
		image = image.scaled(ratio)
		this
	}

	def rotate(radians: Double) = {
		image = image.rotated(radians: Double)
		this
	}

	// TODO: DEPRECATE COCOA
	//	def flipHorizontally = this.image = scale(-1)().transformedImage(AffineTransform.getTranslateInstance(image.getWidth, 0))

	// TODO: DEPRECATE COCOA
	//	def flipVertically = this.image = scale()(-1).transformedImage(AffineTransform.getTranslateInstance(0, image.getHeight))

	def crop(from: Vector)(size: Vector) = {
		this.image = image.cropped(from)(size)
	}

	def repeat(repetitions: Vector) = {
		image = image.repeated(repetitions)
		this
	}

	// ****************************************************************
	// ** GAME LOOP OPERATIONS
	// ****************************************************************

	def update(delta: Double) = {}

	protected def doRenderAt(position: Vector, renderer: Renderer) = renderer.draw(image)(position)
}