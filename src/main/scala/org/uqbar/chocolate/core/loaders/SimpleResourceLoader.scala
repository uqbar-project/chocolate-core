package org.uqbar.chocolate.core.loaders;

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.appearances.Sprite
import org.uqbar.chocolate.core.exceptions.GameException
import scala.reflect._
import org.uqbar.chocolate.core.dimensions.Vector

class SimpleResourceLoader extends ResourceLoader {

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	override def loadGame(gameClassName : String) : Game = {
		try {
			val clazz = java.lang.Class.forName(gameClassName + "$")
			clazz.getField("MODULE$").get(clazz).asInstanceOf[Game]
		} catch {
			case e : ClassNotFoundException ⇒
				throw new GameException("The game class '" + gameClassName + "' was not found");
			case e : Exception ⇒
				throw new RuntimeException("The game class can't be instantiated", e);
		}
	}

	override def loadSprite(imageFileName : String) : Sprite = {
		try {
			val image = ImageIO.read(this.getClass().getResource(imageFileName));
			new Sprite(image);
		} catch {
			case _ : Exception ⇒ throw new GameException("The resource '" + imageFileName + "' was not found");
		}
	}

	def companion[T](implicit man : Manifest[T]) : T =
		man.runtimeClass.getField("MODULE$").get(man.runtimeClass).asInstanceOf[T]

	// companion[List$].make(3, "s")
}