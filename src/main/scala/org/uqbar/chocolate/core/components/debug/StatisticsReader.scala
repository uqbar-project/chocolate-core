package org.uqbar.chocolate.core.components.debug;

import org.uqbar.cacao.Color
import java.awt.Dimension
import org.uqbar.cacao.Renderer
import org.uqbar.chocolate.core.appearances.Label
import org.uqbar.chocolate.core.components.Visible
import org.uqbar.chocolate.core.reactions.events.Update
import org.uqbar.math.spaces.R2._
import StatisticsReader._
import org.uqbar.chocolate.core.reactions.events.Update
import org.uqbar.cacao._

object StatisticsReader {
	val DEBUG_FONT_SIZE = 14
	val INITIAL_UPDATES_TO_IGNORE = 5
	val DEBUG_FONT_COLOR = Color.Green.brighter()
}

class StatisticsReader(font: Font, windowSize: Int = 5) extends Visible {
	translation = (10, 10)
	val appearance = new Label(font, DEBUG_FONT_COLOR)("")

	var currentFPS: Double = 0
	var minFPS: Double = 0
	var maxFPS: Double = 0
	var lastWindowMinFPS: Double = 0
	var lastWindowMaxFPS: Double = 0
	var lastWindowFPSAverage: Double = 0
	var windowMinFPS: Double = 0
	var windowMaxFPS: Double = 0
	var windowStartTime: Double = System.nanoTime
	var windowSum: Double = 0
	var windowCount: Double = 0
	var updatesToIgnore: Int = StatisticsReader.INITIAL_UPDATES_TO_IGNORE

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	z = 12000

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	in {
		case Update(delta) =>
			if (updatesToIgnore > 0) {
				updatesToIgnore = updatesToIgnore - 1
			} else if (delta > 0) {
				currentFPS = (1 / delta).toLong

				if (windowExpired) {
					resetWindow
				} else {
					windowCount = windowCount + 1
					windowSum = windowSum + currentFPS
				}

				updateMinValues
				updateMaxValues
			}

			appearance.asInstanceOf[Label].text = statisticsDescription
	}

	// ****************************************************************
	// ** QUERIES
	// ****************************************************************

	def windowExpired = System.nanoTime() - windowStartTime > windowSize * 1000000000L

	def windowFPSAverage = if (windowCount > 0) windowSum / windowCount else 0

	def statisticsDescription = {
		val zonesSize = scene.collisionZoneSize
		val runtime = Runtime.getRuntime()

		"""Screen Size: %sx%s
			Collision Zones Size: %sx%s
			Current FPS: %-3.1f [%s/%s]
			Average FPS: %-3.1f [%3.1f/%3.1f] (every %s secs)
			Component Count: %s
			Used Memory: %9.2f Kb
			Total Memory: %5.2f Kb
			""" format
			(game.displaySize(X), game.displaySize(Y),
				zonesSize.getWidth.toInt, zonesSize.getHeight.toInt,
				currentFPS, minFPS, maxFPS,
				lastWindowFPSAverage, lastWindowMinFPS, lastWindowMaxFPS, windowSize,
				scene.componentCount - 1,
				(runtime.totalMemory() - runtime.freeMemory()) / 1024f,
				runtime.totalMemory() / 1024f)
	}

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def resetWindow = {
		lastWindowFPSAverage = windowFPSAverage
		lastWindowMinFPS = windowMinFPS
		lastWindowMaxFPS = windowMaxFPS
		windowCount = 0
		windowSum = 0
		windowMaxFPS = 0
		windowMinFPS = 0
		windowStartTime = System.nanoTime
	}

	def updateMinValues =
		if (currentFPS < windowMinFPS || windowMinFPS == 0) {
			windowMinFPS = currentFPS
			if (currentFPS < minFPS || minFPS == 0) minFPS = currentFPS
		}

	def updateMaxValues =
		if (currentFPS > windowMaxFPS) {
			windowMaxFPS = currentFPS
			if (currentFPS > maxFPS) maxFPS = currentFPS
		}
}