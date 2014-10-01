package org.uqbar.chocolate.core.loaders;

import org.uqbar.cacao.{ ResourceLoader => CacaoResourceLoader }
import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.appearances.Sprite
import org.uqbar.cacao.FontModifier

object ResourceLoader extends ResourceLoader {
	protected var _instance: ResourceLoader = _

	def setUp(instance: ResourceLoader) = _instance = instance

	def loadGame(gameClassName: String) = _instance.loadGame(gameClassName)
	def loadSprite(imageFileName: String) = _instance.loadSprite(imageFileName)
	def font(name: Symbol, size: Int, modifiers: FontModifier*) = _instance.font(name, size, modifiers: _*)
	def image(fileName: String) = _instance.image(fileName)
}

trait ResourceLoader extends CacaoResourceLoader {
	def loadGame(gameClassName: String): Game
	def loadSprite(imageFileName: String): Sprite
}