package org.uqbar.chocolate.core;

import org.uqbar.cacao.Color
import org.uqbar.cacao.Renderer
import org.uqbar.chocolate.core.collisions.CircularBoundingBox
import org.uqbar.chocolate.core.collisions.RectangularBoundingBox
import org.uqbar.chocolate.core.components.Collisionable
import org.uqbar.chocolate.core.components.Visible
import org.uqbar.chocolate.core.components.debug.FloorShower
import org.uqbar.chocolate.core.components.debug.StatisticsReader
import org.uqbar.chocolate.core.loaders.CachedResourceLoader
import org.uqbar.chocolate.core.loaders.SimpleResourceLoader
import org.uqbar.chocolate.core.reactions.ReactionRegistry
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.reactions.events.SceneSetAsCurrent
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.chocolate.core.utils.Implicits.min
import org.uqbar.chocolate.core.components.debug.NavigatorCamera
import org.uqbar.chocolate.core.reactions.events.ContinuableEvent
import org.uqbar.chocolate.core.reactions.events.EventDispatcher
import org.uqbar.math.vectors.Vector
import scala.collection.mutable.Map
import org.uqbar.chocolate.core.loaders.ResourceLoader

object Game {
	val DEFAULT_GAME_MODE = "default"
}
trait Game extends EventDispatcher {
	protected val modeSetups = Map[String, () => Unit]()

	private var _currentScene: GameScene = null
	def currentScene = _currentScene
	def currentScene_=(scene: GameScene) {
		if (currentScene != null) currentScene.game = null

		_currentScene = scene
		scene.game = this
		pushEvent(SceneSetAsCurrent)
	}

	currentScene = new GameScene

	def title: String
	def displaySize: Vector

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	override def pushEvent(event: GameEvent): Unit = currentScene pushEvent event

	override def startPushingEvent(event: ContinuableEvent): Unit = currentScene startPushingEvent event

	override def stopPushingEvent(event: ContinuableEvent): Unit = currentScene stopPushingEvent event

	def takeStep(renderer: Renderer) = currentScene takeStep renderer

	def pause = currentScene.pause

	// ****************************************************************
	// ** GAME MODES
	// ****************************************************************

	def mode(name: String)(setup: () => Unit) {
		modeSetups(name) = modeSetups.get(name).fold(setup)(prev => { () => prev(); setup() })
	}

	def setMode(name: String) = modeSetups(name)()

	mode(Game.DEFAULT_GAME_MODE){ () => }

	mode("debug") { () =>
		currentScene.addComponent(new FloorShower)
	}
}