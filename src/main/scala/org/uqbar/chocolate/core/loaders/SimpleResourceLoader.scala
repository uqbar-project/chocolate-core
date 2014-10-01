package org.uqbar.chocolate.core.loaders;

import org.uqbar.cacao._
import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.appearances.Sprite
import org.uqbar.chocolate.core.exceptions.GameException
import org.uqbar.cacao.{ ResourceLoader => CacaoResourceLoader }

//TODO: DEPRECATED CACAO: REFACTOR AND RE-THINK INTERFACE
class SimpleResourceLoader(cacaoResourceLoader: CacaoResourceLoader) extends ResourceLoader {

	def font(name: Symbol, size: Int, modifiers: FontModifier*) = cacaoResourceLoader.font(name, size, modifiers: _*)
	def image(fileName: String) = cacaoResourceLoader.image(fileName)

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	override def loadGame(gameClassName: String): Game = {
		try {
			val clazz = java.lang.Class.forName(gameClassName + "$")
			clazz.getField("MODULE$").get(clazz).asInstanceOf[Game]
		} catch {
			case e: ClassNotFoundException ⇒
				throw new GameException("The game class '" + gameClassName + "' was not found");
			case e: Exception ⇒
				throw new RuntimeException("The game class can't be instantiated", e);
		}
	}

	override def loadSprite(imageFileName: String) = new Sprite(cacaoResourceLoader.image(imageFileName))

	def companion[T](implicit man: Manifest[T]): T =
		man.runtimeClass.getField("MODULE$").get(man.runtimeClass).asInstanceOf[T]

	// companion[List$].make(3, "s")
}