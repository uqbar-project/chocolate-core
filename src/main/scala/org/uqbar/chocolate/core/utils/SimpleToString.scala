package org.uqbar.chocolate.core.utils

trait SimpleToString {
  override def toString = this.getClass.getSimpleName
}