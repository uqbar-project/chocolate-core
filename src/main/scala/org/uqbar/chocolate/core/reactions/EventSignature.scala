package org.uqbar.chocolate.core.reactions;

import java.lang.annotation.Annotation
import java.lang.reflect.Method
import org.uqbar.chocolate.core.reactions.annotations.GameTrigger
import org.uqbar.chocolate.core.reactions.events.GameEvent;
import scala.collection.mutable.HashMap

object EventSignature {
  val CACHE_L1 = new HashMap[Class[_], EventSignature]
  val CACHE_L2 = new HashMap[Class[_], HashMap[Any, EventSignature]]

  def apply(eventType: Class[_], keys: Any*): EventSignature = forKeys(eventType, keys)

  def forKeys(eventType: Class[_], keys: Seq[Any]): EventSignature = {
    keys.length match {
      case 0 =>
        CACHE_L1.getOrElseUpdate(eventType, new EventSignature(eventType, keys))

      case 1 =>
        val subcache = CACHE_L2.getOrElseUpdate(eventType, new HashMap)
        subcache.getOrElseUpdate(keys(0), new EventSignature(eventType, keys))

      case _ => new EventSignature(eventType, keys);
    }
  }

  def forAnnotation(annotation: Annotation): EventSignature = {
    val annotationType = annotation.annotationType
    val eventType = annotationType.getAnnotation(classOf[GameTrigger]).value
    val methods = annotationType.getMethods

    val keyCount = methods.length - 4
    val keys = for (i <- 0 until keyCount) yield methods(i).invoke(annotation)

    forKeys(eventType, keys)
  }
}

class EventSignature(keys: Seq[Any]) {

  // ****************************************************************
  // ** CONSTRUCTORS
  // ****************************************************************

  def this(eventType: Class[_], keys: Seq[Any]) = this(eventType +: keys)

  override def toString = keys.mkString(this.getClass.getSimpleName + "(", ", ", ")")
}