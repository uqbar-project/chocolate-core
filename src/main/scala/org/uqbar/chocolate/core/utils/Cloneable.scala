package org.uqbar.chocolate.core.utils

trait Cloneable extends scala.Cloneable {
	override def clone : this.type = super.clone.asInstanceOf[this.type]
}