package org.uqbar.chocolate.core.dimensions

import org.uqbar.chocolate.core.dimensions.Vector._
import org.uqbar.chocolate.core.utils.Cloneable

trait Positioned extends Cloneable {

	protected var _translation : MutableVector = (0, 0)

	def translation : Vector = _translation
	def translation_=(delta : Vector) : Unit = _translation.set(delta.x, delta.y)

	def move(delta : Vector) : Unit = translation = (translation + delta)

	def alignVertically(zone : this.type ⇒ Double)(target : Double) = move(0, target - zone(this))
	def alignHorizontally(zone : this.type ⇒ Double)(target : Double) = move(target - zone(this), 0)
	def align(hZone : this.type ⇒ Double, vZone : this.type ⇒ Double)(target : Vector) = move(target.x - hZone(this), target.y - vZone(this))

	override def clone = {
		val answer = super.clone
		answer._translation = (translation.x, translation.y)
		answer
	}
}