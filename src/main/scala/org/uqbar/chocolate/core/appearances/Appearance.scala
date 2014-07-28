package org.uqbar.chocolate.core.appearances;

import java.awt.Graphics2D

import org.uqbar.chocolate.core.dimensions.Bounded
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.math.vectors.Vector

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
	def left = translation.x
	def top = translation.y

	def update(delta : Double)

	def renderAt(basePosition : Vector, graphics : Graphics2D) = doRenderAt(basePosition.x + translation.x, basePosition.y + translation.y, graphics)
	protected def doRenderAt(x : Double, y : Double, graphics : Graphics2D) : Unit
}