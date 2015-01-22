package org.uqbar.chocolate.core.appearances;

import org.uqbar.cacao.Renderer
import org.uqbar.chocolate.core.dimensions.Bounded
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.math.spaces.R2._
import org.uqbar.chocolate.core.dimensions.Scalable

/*
 * COSAS A CONSIDERAR:
 *  > No efecto colateral?
 *  
 *  * Poder hacer todas las transformaciones primarias:
 *  	(QUE ONDA HACER ESTO CON EL GRAPHICS2D??????)
 *   	- rotate
 *  	- shear
 *	* Mascaras!
 *	* Optimizar siempre que sea posible
 *	* Tratar de no perder calidad al hacer transformaciones "opuestas"
 *	* Binding de propiedades
 *	* Tests
 */

//TODO: Achicar este contrato. Ahora que es un trait, podemos limpiarlo al mínimo indispensabe y dejar las transformaciones y blehs en los implementadores
trait Appearance extends Bounded with Positioned {
	def left = translation(X)
	def top = translation(Y)

	def update(delta: Double)

	def renderAt(basePosition: Vector)(renderer: Renderer) = doRenderAt(basePosition + translation, renderer)
	protected def doRenderAt(position: Vector, renderer: Renderer): Unit
}

trait ComplexAppearance extends Appearance with Scalable {

	//	TODO: DEPRECATED CACAO
	//	// ****************************************************************
	//	// ** ROTATING
	//	// ****************************************************************
	//
	//	//TODO
	//
	//	// ****************************************************************
	//	// ** REPEATING
	//	// ****************************************************************
	//
	//	def repeat(horizontalRepetitions : Double, verticalRepetitions : Double)
	//
	//	def repeatHorizontally(repetitions : Double) = repeat(repetitions, 1)
	//	def repeatVertically(repetitions : Double) = repeat(1, repetitions)
	//
	//	def repeatHorizontallyToCover(widthToCover : Double) = repeatHorizontally(widthToCover / width)
	//	def repeatVerticallyToCover(heightToCover : Double) = repeatVertically(heightToCover / height)
	//	def repeatToCover(areaToCover : Vector) = repeat(areaToCover(X) / width, areaToCover(Y) / height)
	//
	//	// ****************************************************************
	//	// ** CROPPING
	//	// ****************************************************************
	//
	//	def crop(x : Double, y : Double, width : Double, height : Double) : this.type
	//	def crop(width : Double, height : Double) : this.type = crop(0, 0, width, height)
	//
	//	// ****************************************************************
	//	// ** FLIPPING
	//	// ****************************************************************
	//
	//	def flipHorizontally
	//	def flipVertically
}