package org.uqbar.chocolate.core.loaders;

import java.awt.Graphics2D
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
import org.uqbar.chocolate.core.dimensions.Vector

class JarResourceLoader(jarPath : String) extends ResourceLoader {

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	def loadGame(gameClassName : String) = {
		try {
			val url = new URL("file:" + jarPath)
			val loader = new URLClassLoader(Array(url))
			loader.loadClass(gameClassName).newInstance().asInstanceOf[Game]
		} catch {
			case e : ClassNotFoundException ⇒
				throw new GameException("The game class '" + gameClassName + "' was not found")
			case e : Exception ⇒
				throw new RuntimeException("The game class can't be instantiated", e)
		}
	}

	override def loadSprite(imageFileName : String) = {
		val image = try { ImageIO.read(new URL("jar:file:" + jarPath + "!" + imageFileName)) }
		catch {
			case e : Exception ⇒
				throw new GameException("The resource '" + imageFileName + "' was not found")
		}

		val transformed = GraphicsEnvironment
			.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice()
			.getDefaultConfiguration()
			.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT)

		val graphics = transformed.createGraphics()
		graphics.drawImage(image, 0, 0, null)
		graphics.dispose()

		new Sprite(transformed)
	}
}