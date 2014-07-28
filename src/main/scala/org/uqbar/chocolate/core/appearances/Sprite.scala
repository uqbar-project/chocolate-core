package org.uqbar.chocolate.core.appearances;

import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.math.vectors.Vector
import org.uqbar.math.vectors._

class Sprite(var image: BufferedImage) extends ComplexAppearance {

	def width = image.getWidth
	def height = image.getHeight

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def ::(target: Sprite) = {
		val nleft = left min target.left
		val nright = right max target.right
		val ntop = top min target.top
		val nbottom = bottom max target.bottom
		val nwidth = nright - nleft
		val nheight = nbottom - ntop

		val img = new BufferedImage(nwidth, nheight, image.getType)
		val g = img.createGraphics

		g.translate(-nleft, -ntop)

		renderAt(Origin, g)
		target.renderAt(Origin, g)

		g.dispose

		val answer = new Sprite(img)
		answer.translation = (nleft, ntop)
		answer
	}

	protected def transformedImage(transformation: AffineTransform) =
		new AffineTransformOp(transformation, AffineTransformOp.TYPE_BICUBIC).filter(image, new BufferedImage(
			width * transformation.getScaleX.abs,
			height * transformation.getScaleY.abs,
			image.getType
		))

	// ****************************************************************
	// ** TRANSFORMATIONS
	// ****************************************************************

	def scale(hRatio: Double = 1)(vRatio: Double = 1) = {
		this.image = transformedImage(AffineTransform.getScaleInstance(hRatio, vRatio))
		this
	}

	def rotate(radians: Double): this.type = {
		val newImage = new BufferedImage(width, height, image.getType)

		val graphics = newImage.createGraphics
		graphics.rotate(radians, center, middle)
		graphics.drawImage(image, null, 0, 0)
		graphics.dispose

		image = newImage

		this
	}

	def flipHorizontally = this.image = scale(-1)().transformedImage(AffineTransform.getTranslateInstance(image.getWidth, 0))

	def flipVertically = this.image = scale()(-1).transformedImage(AffineTransform.getTranslateInstance(0, image.getHeight))

	def crop(x: Double, y: Double, width: Double, height: Double) = {
		this.image = image.getSubimage(x, y, width, height)
		this
	}

	def repeat(horizontalRepetitions: Double, verticalRepetitions: Double) = {
		val horizontalIterations = horizontalRepetitions.ceil.toInt
		val verticalIterations = verticalRepetitions.ceil.toInt
		val newImage = new BufferedImage(
			(width * horizontalRepetitions).toInt,
			(height * verticalRepetitions).toInt,
			image.getType())

		val graphics = newImage.createGraphics()

		for {
			i ← 0 until horizontalIterations
			j ← 0 until verticalIterations
		} graphics.drawImage(image, i * width.toInt, j * height.toInt, null)

		graphics.dispose

		this.image = newImage
	}

	// ****************************************************************
	// ** GAME LOOP OPERATIONS
	// ****************************************************************

	def update(delta: Double) = {}

	protected def doRenderAt(x: Double, y: Double, graphics: Graphics2D) = graphics.drawImage(image, x, y, null)
}