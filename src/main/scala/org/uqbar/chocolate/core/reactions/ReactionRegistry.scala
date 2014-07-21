package org.uqbar.chocolate.core.reactions

import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.reactions.events.Update
import org.uqbar.chocolate.core.utils.Implicits.MethodExtender
import scala.reflect.runtime.universe._
import scala.reflect._
import org.uqbar.chocolate.core.reactions.annotations.GameTrigger
import org.uqbar.chocolate.core.exceptions.GameException
import scala.collection.mutable.Set
import scala.collection.immutable.Nil

object ReactionRegistry {

	protected val reactions = Map[Class[_ <: GameComponent], ListBuffer[PartialFunction[(GameEvent, GameComponent), Unit]]]()

	protected val annotationInitializedClasses = Set[Class[_ <: GameComponent]]()

	protected val componentReactions = Map[GameComponent, ListBuffer[PartialFunction[GameEvent, Unit]]]()

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def apply(component : GameComponent) = {
		val typeReactions = for {
			reactionAssociation ← reactions
			if (reactionAssociation._1.isInstance(component))
			reaction ← reactionAssociation._2
		} yield reaction

		val individualReactions = componentReactions.getOrElse(component, Nil).map { f ⇒
			{ case (e, c) ⇒ if (f.isDefinedAt(e)) f(e) } : PartialFunction[(GameEvent, GameComponent), Unit]
		}

		individualReactions ++ typeReactions
	}

	def hasBeenInitializedFromAnnotations(componentClass : Class[_ <: GameComponent]) = annotationInitializedClasses(componentClass)

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def initializeFromAnnotations(componentClass : Class[_ <: GameComponent]) {
		for {
			method ← componentClass.getMethods
			annotation ← method.allAnnotations
			if annotation.annotationType.isAnnotationPresent(classOf[GameTrigger])
			annotationSignature = EventSignature.forAnnotation(annotation)
		} register(componentClass, {
			case (event, component) ⇒ for (signature ← event.matchingSignatures)
				if (annotationSignature == signature) {
					try if (method.getParameterTypes.length > 0) method.invoke(component, event)
					else method.invoke(component)
					catch {
						case e : IllegalArgumentException ⇒ throw new GameException(method.toGenericString + " has an invalid signature. Did you miss a parameter?")
						case e : IllegalAccessException ⇒ throw new GameException("The method is inaccessible")
					}
				}
		})

		annotationInitializedClasses += componentClass
	}

	def register(component : GameComponent)(reaction : PartialFunction[GameEvent, Unit]) =
		componentReactions.getOrElseUpdate(component, ListBuffer()) += reaction

	def register(componentClass : Class[_ <: GameComponent], reaction : PartialFunction[(GameEvent, GameComponent), Unit]) =
		reactions.getOrElseUpdate(componentClass, ListBuffer()) += reaction

	def +=[C <: GameComponent](reaction : PartialFunction[(GameEvent, C), Unit])(implicit t : TypeTag[C]) = t.tpe match {
		case TypeRef(_, symbol, _) ⇒
			val componentClass = runtimeMirror(getClass.getClassLoader).runtimeClass(symbol.asClass).asInstanceOf[Class[C]]

			register(componentClass, reaction.asInstanceOf[PartialFunction[(GameEvent, GameComponent), Unit]])
	}
}