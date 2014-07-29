package org.uqbar.chocolate.core;

import java.awt.Color
import java.awt.Graphics2D
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
import org.uqbar.chocolate.core.reactions.events.RenderRequired
import org.uqbar.chocolate.core.reactions.events.SceneSetAsCurrent
import org.uqbar.chocolate.core.utils.Implicits.double_to_int
import org.uqbar.chocolate.core.utils.Implicits.min
import org.uqbar.chocolate.core.components.debug.NavigatorCamera
import org.uqbar.chocolate.core.reactions.events.ContinuableEvent
import org.uqbar.chocolate.core.reactions.events.EventDispatcher

// TODO: IDEAS LOCAS:
//	*	Logueo por pantalla y a archivo, etc. debería "agregarse" de forma transparente al juego.
//
//	*	Quisiera tener un DSL para describir las escenas y sus inicializaciones. La herramienta gráfica podría generar eso e interpretarlo.
//		Esta linea de pensamiento me lleva a creer que los componentes no deberían inicializarse en el constructor, sino ser algo así como beans, los cuales
//		puedan ser seteados facilmente por reflection. Esto llevaría a algo similar a la inyección de dependencias. 

trait Game extends EventDispatcher {
	var _currentScene: GameScene = null
	def currentScene = _currentScene
	def currentScene_=(scene: GameScene) {
		if (currentScene != null) currentScene.game = null

		_currentScene = scene
		scene.game = this
		this.pushEvent(SceneSetAsCurrent())
	}

	currentScene = new GameScene

	def title: String
	def displaySize: (Int, Int)

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def displayWidth = displaySize._1

	def displayHeight = displaySize._2

	val resourceLoader = new CachedResourceLoader(new SimpleResourceLoader)

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	override def pushEvent(event: GameEvent): Unit = currentScene pushEvent event

	override def startPushingEvent(event: ContinuableEvent): Unit = currentScene startPushingEvent event

	override def stopPushingEvent(event: ContinuableEvent): Unit = currentScene stopPushingEvent event

	def takeStep(graphics: Graphics2D) = currentScene takeStep graphics

	def pause = currentScene.pause

	// ****************************************************************
	// ** GAME MODES
	// ****************************************************************

	def default {}

	def debug {
		currentScene.addComponent(new StatisticsReader(5))
		currentScene.addComponent(new FloorShower)
		currentScene.addComponent(NavigatorCamera)

		ReactionRegistry.+=[Visible] {
			case (RenderRequired(graphics), target: Visible) ⇒
				graphics.setColor(Color.YELLOW)
				val dx = target.translation.x
				val dy = target.translation.y
				val bounds = target.appearance

				graphics.drawLine(dx, dy, dx + bounds.translation.x, dy + bounds.translation.y)

				graphics.drawRect(bounds.left + dx, bounds.top + dy, bounds.width, bounds.height)
				graphics.drawRect(bounds.left + dx, bounds.top + dy, 5, 5)
				graphics.drawRect(bounds.right + dx - 5, bounds.top + dy, 5, 5)
				graphics.drawRect(bounds.right + dx - 5, bounds.bottom + dy - 5, 5, 5)
				graphics.drawRect(bounds.left + dx, bounds.bottom + dy - 5, 5, 5)
		}

		ReactionRegistry.+=[Collisionable] {
			case (RenderRequired(graphics), collisionable) ⇒
				graphics.setColor(Color.BLUE)
				val box = collisionable.boundingBox

				val crossSize = min(15, box.width, box.height) / 3
				val basePosition = collisionable.translation

				// Center
				graphics.drawLine(basePosition.x - crossSize, basePosition.y - crossSize, basePosition.x + crossSize, basePosition.y + crossSize)
				graphics.drawLine(basePosition.x + crossSize, basePosition.y - crossSize, basePosition.x - crossSize, basePosition.y + crossSize)

				// Line to component position
				graphics.drawLine(basePosition.x, basePosition.y, basePosition.x + box.translation.x, basePosition.y + box.translation.y)

				// Contour
				box match {
					case c: CircularBoundingBox ⇒ graphics.drawOval(basePosition.x + c.left, basePosition.y + c.top, c.width, c.height)
					case r: RectangularBoundingBox ⇒ graphics.drawRect(basePosition.x + r.left, basePosition.y + r.top, r.width, r.height)
				}
		}

	}
}
