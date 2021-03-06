package org.uqbar.chocolate.core.loaders;

import org.uqbar.cacao.Renderer
import java.awt.GraphicsEnvironment
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.net.URL
import java.net.URLClassLoader
import javax.imageio.ImageIO
import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.appearances.Sprite
import org.uqbar.chocolate.core.exceptions.GameException
import collection.JavaConversions._
import org.uqbar.math.vectors.Vector
import org.uqbar.cacao._

class JarResourceLoader(jarPath: String) extends ResourceLoader {

	//TODO: DEPRECATED CACAO => Image(name) should read from inside jars without any other problems
	def font(name: Symbol, size: Int, modifiers: FontModifier*): Font = ???
	def image(fileName: String): Image = ???
	def renderer: Renderer = ???

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def loadGame(gameClassName: String) = {
		try {
			val url = new URL("file:" + jarPath)
			val loader = new URLClassLoader(Array(url))
			loader.loadClass(gameClassName).newInstance().asInstanceOf[Game]
		} catch {
			case e: ClassNotFoundException ⇒
				throw new GameException("The game class '" + gameClassName + "' was not found")
			case e: Exception ⇒
				throw new RuntimeException("The game class can't be instantiated", e)
		}
	}

	override def loadSprite(imageFileName: String) = ???
	//TODO: DEPRECATED CACAO => Image(name) should read from inside jars without any other problems
	//	override def loadSprite(imageFileName : String) = {
	//		val image = try { ImageIO.read(new URL("jar:file:" + jarPath + "!" + imageFileName)) }
	//		catch {
	//			case e : Exception ⇒
	//				throw new GameException("The resource '" + imageFileName + "' was not found")
	//		}
	//
	//		val transformed = GraphicsEnvironment
	//			.getLocalGraphicsEnvironment()
	//			.getDefaultScreenDevice()
	//			.getDefaultConfiguration()
	//			.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT)
	//
	//		val graphics = transformed.createGraphics()
	//		graphics.drawImage(image, 0, 0, null)
	//		graphics.dispose()
	//
	//		new Sprite(transformed)
	//	}
}