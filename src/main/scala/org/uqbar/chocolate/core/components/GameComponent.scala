package org.uqbar.chocolate.core.components;

import org.uqbar.chocolate.core.GameScene
import org.uqbar.chocolate.core.exceptions.GameException

import org.uqbar.chocolate.core.reactions.ReactionRegistry
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.utils.SimpleToString
import scala.collection.mutable.ListBuffer
import scala.reflect._
import org.uqbar.chocolate.core.utils.Cloneable
import org.uqbar.chocolate.core.reactions.events.Destroyed

trait GameComponent extends SimpleToString {

	var z: Double = 0 // TODO: DOCUMENTAR QUE ES EL Z.
	var destroyPending = false

	var _scene: GameScene = null
	def scene[T <: GameScene]: T = _scene.asInstanceOf[T]
	def scene_=(value: GameScene) = _scene = value

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def reactions = ReactionRegistry(this)

	def game = scene.game

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def destroy {
		destroyPending = true
		scene.pushEvent(Destroyed(this))
	}

	def reactTo(event: GameEvent) = for {
		reaction ← reactions
		if (reaction.isDefinedAt((event, this)))
	} reaction(event, this)

	def in = ReactionRegistry.register(this)(_)
}