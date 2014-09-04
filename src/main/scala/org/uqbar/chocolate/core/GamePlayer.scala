package org.uqbar.chocolate.core;

import java.awt.Canvas
import java.awt.Graphics2D
import java.awt.Point
import java.awt.Toolkit
import java.awt.image.MemoryImageSource
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.utils.Implicits.tuple_to_dimension
import org.uqbar.chocolate.core.reactions.events.adapters.KeyboardAdapter
import org.uqbar.chocolate.core.reactions.events.adapters.MouseAdapter

class GamePlayer(val target: Game) extends Canvas with Runnable with KeyboardAdapter with MouseAdapter {

	final val BACKBUFFER_COUNT = 2

	@volatile
	var playerThread: Thread = null

	addMouseListener(this)
	addMouseMotionListener(this)
	addKeyListener(this)
	setPreferredSize(target.displaySize)
	setMinimumSize(target.displaySize)
	setMaximumSize(target.displaySize)
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
		playerThread = new Thread(GamePlayer.this, target.title)

		playerThread.start
	}

	def pause {
		playerThread = null

		target.pause
	}

	def takeStep {
		val graphics = getBufferStrategy.getDrawGraphics.asInstanceOf[Graphics2D]
		graphics.clearRect(0, 0, getWidth, getHeight)

		target.takeStep(graphics)

		graphics.dispose

		Thread.sleep(0, 1)

		getBufferStrategy.show
	}

	def hideMouse {
		val image = createImage(new MemoryImageSource(16, 16, new Array[Int](16 * 16), 0, 16))
		setCursor(Toolkit.getDefaultToolkit.createCustomCursor(image, new Point(0, 0), ""))
	}
}