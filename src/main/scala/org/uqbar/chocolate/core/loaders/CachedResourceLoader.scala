package org.uqbar.chocolate.core.loaders;

import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.appearances.Sprite
import scala.collection.mutable.HashMap
import org.uqbar.cacao._

class CachedResourceLoader(innerLoader: ResourceLoader) extends ResourceLoader {
	var cache = new HashMap[String, Object]()

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def font(name: Symbol, size: Int, modifiers: FontModifier*) = innerLoader.font(name, size, modifiers: _*)
	def image(fileName: String) = innerLoader.image(fileName)

	// TODO: Cachear el juego
	override def loadGame(gameClassName: String) = innerLoader loadGame gameClassName

	override def loadSprite(imageFileName: String) =
		cache.getOrElseUpdate(imageFileName, innerLoader loadSprite imageFileName).asInstanceOf[Sprite].clone

}