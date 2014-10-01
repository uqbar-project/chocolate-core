package org.uqbar.chocolate.core;

import java.awt.Canvas
import org.uqbar.cacao.Renderer
import java.awt.Point
import java.awt.Toolkit
import java.awt.image.MemoryImageSource
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.reactions.events.adapters.KeyboardAdapter
import org.uqbar.chocolate.core.reactions.events.adapters.MouseAdapter

class GamePlayer(val target: Game, renderer: Renderer) extends Runnable {

	@volatile
	var playerThread: Thread = null

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def isPaused = playerThread != Thread.currentThread

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def run {
		playerThread = Thread.currentThread

		while (!isPaused) takeStep
	}

	def resume {
		playerThread = new Thread(GamePlayer.this, target.title)

		playerThread.start
	}

	def pause {
		playerThread = null

		target.pause
	}

	def takeStep = {
		renderer.beforeRendering

		renderer.clear(Origin, target.displaySize)

		target.takeStep(renderer)

		renderer.afterRendering

		Thread.sleep(0, 1)
	}
}