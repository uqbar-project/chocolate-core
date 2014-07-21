package org.uqbar.chocolate.core.components.debug

import org.uqbar.chocolate.core.components.Visible
import org.uqbar.chocolate.core.appearances.Label
import org.uqbar.chocolate.core.reactions.ReactionRegistry
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.events.KeyPressed
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.utils.Implicits._
import scala.reflect.runtime.universe._
import scala.reflect._
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.awt.Font
import java.awt.Color
import org.uqbar.chocolate.core.dimensions.Vector
import java.awt.Graphics2D

class LogPanel(val maxLineCount : Int) extends Visible {

	final val DATE_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS")
	final val FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14)
	final val FONT_COLOR = Color.BLACK
	final val BACKGROUND_COLOR = new Color(125, 125, 125, 125)

	z = 12000

	val appearance = new Label(FONT)(FONT_COLOR)() {
		override def renderAt(basePosition : Vector, graphics : Graphics2D) {
			graphics.setColor(BACKGROUND_COLOR)
			graphics.fillRect(left, top, width + 5, height + 5)

			super.renderAt(basePosition, graphics)
		}
	}

	def attendToEvent[T <: GameEvent](implicit t : TypeTag[T]) = t.tpe match {
		case TypeRef(_, symbol, _) ⇒ {
			val eventClass = runtimeMirror(getClass.getClassLoader).runtimeClass(symbol.asClass).asInstanceOf[Class[T]]

			ReactionRegistry.+=[LogPanel] { case (e, log) ⇒ if (eventClass.isInstance(e)) log.log(e) }
		}
	}

	protected def log(x : Any) {
		appearance.textLines = appearance.textLines.drop(appearance.textLines.size - maxLineCount) ++
			List(s"\n${DATE_FORMAT.format(Calendar.getInstance.getTime)} : $x")
	}

}