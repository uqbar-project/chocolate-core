package org.uqbar.chocolate.core;

import java.awt.Canvas
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Toolkit
import java.awt.image.MemoryImageSource

import org.uqbar.chocolate.core.reactions.EventAdapter
import org.uqbar.chocolate.core.utils.Implicits.tuple_to_dimension

class GamePlayer(val game : Game) extends Canvas with Runnable {

	final val BACKBUFFER_COUNT = 2

	@volatile
	var playerThread : Thread = null

	addEventListener(new EventAdapter(game))
	setPreferredSize(game.displaySize)
	setMinimumSize(game.displaySize)
	setMaximumSize(game.displaySize)
	setIgnoreRepaint(true)
	setFocusTraversalKeysEnabled(false)
	setFocusable(true)
	hideMouse

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def isPaused = playerThread != Thread.currentThread

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def run {
		createBufferStrategy(BACKBUFFER_COUNT)
		playerThread = Thread.currentThread

		while (!isPaused)
			takeStep
	}

	def resume {
		playerThread = new Thread(GamePlayer.this, game.title)

		playerThread.start
	}

	def pause {
		playerThread = null

		game.pause
	}

	def takeStep {
		val graphics = getBufferStrategy.getDrawGraphics.asInstanceOf[Graphics2D]
		graphics.clearRect(0, 0, getWidth, getHeight)

		game.takeStep(graphics)

		graphics.dispose

		Thread.sleep(0, 1)

		getBufferStrategy.show
	}

	def addEventListener(eventListener : EventAdapter) {
		addMouseListener(eventListener)
		addMouseMotionListener(eventListener)
		addKeyListener(eventListener)
	}

	def hideMouse {
		val image = createImage(new MemoryImageSource(16, 16, new Array[Int](16 * 16), 0, 16))
		setCursor(Toolkit.getDefaultToolkit.createCustomCursor(image, new Point(0, 0), ""))
	}
}