package org.uqbar.chocolate.core.loaders;

import org.uqbar.chocolate.core.Game;
import org.uqbar.chocolate.core.appearances.Sprite;

abstract class ResourceLoader {
	def loadGame(gameClassName: String): Game 
	def loadSprite(imageFileName: String): Sprite 
}