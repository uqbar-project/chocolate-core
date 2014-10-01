package org.uqbar.chocolate.core.components.debug

import org.uqbar.chocolate.core.components.Visible
import org.uqbar.chocolate.core.appearances.Label
import org.uqbar.chocolate.core.reactions.ReactionRegistry
import org.uqbar.chocolate.core.components.GameComponent
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.utils.Implicits._
import scala.reflect.runtime.universe._
import scala.reflect._
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import org.uqbar.cacao.Color
import org.uqbar.math.vectors.Vector
import org.uqbar.cacao.Renderer
import org.uqbar.cacao.Rectangle
import org.uqbar.cacao._

class LogPanel(val font: Font, val maxLineCount: Int = 5) extends Visible {

	final val DATE_FORMAT = new SimpleDateFormat("HH:mm:ss:SSS")
	final val FONT_COLOR = Color.Black
	final val BACKGROUND_COLOR = Color(125, 125, 125, 125)

	z = 12000

	val appearance = new Label(font, FONT_COLOR)() {
		override def renderAt(basePosition: Vector)(renderer: Renderer) {
			renderer.color = BACKGROUND_COLOR
			renderer fill Rectangle((left, top), size + (5, 5))
			super.renderAt(basePosition)(renderer)
		}
	}

	def attendToEvent[T <: GameEvent](implicit t: TypeTag[T]) = t.tpe match {
		case TypeRef(_, symbol, _) ⇒ {
			val eventClass = runtimeMirror(getClass.getClassLoader).runtimeClass(symbol.asClass).asInstanceOf[Class[T]]

			ReactionRegistry.+=[LogPanel] { case (e, log) ⇒ if (eventClass.isInstance(e)) log.log(e) }
		}
	}

	protected def log(x: Any) {
		appearance.textLines = appearance.textLines.drop(appearance.textLines.size - maxLineCount) ++
			List(s"\n${DATE_FORMAT.format(Calendar.getInstance.getTime)} : $x")
	}

}