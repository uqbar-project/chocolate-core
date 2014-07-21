package org.uqbar.chocolate.core.reactions;

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import scala.collection.mutable.Set
import org.uqbar.chocolate.core.Game
import org.uqbar.chocolate.core.dimensions.Vector._
import org.uqbar.chocolate.core.reactions.events.KeyHold
import org.uqbar.chocolate.core.reactions.events.KeyPressed
import org.uqbar.chocolate.core.reactions.events.KeyReleased
import org.uqbar.chocolate.core.reactions.events.MouseMoved
import org.uqbar.chocolate.core.reactions.events.MousePressed
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton
import org.uqbar.chocolate.core.reactions.events.MouseReleased
import java.awt.Point
import org.uqbar.chocolate.core.dimensions.Vector
import scala.language.implicitConversions

class EventAdapter(game : Game) extends MouseMotionListener with MouseListener with KeyListener {

	final val RELEASE_KEY_WINDOW_SIZE = 5

	@volatile var _retainedKeys = Set[Key]()
	@volatile var _keysToIgnoreRelease = Set[Key]()

	def shouldRetain(k : Key) = synchronized { _retainedKeys(k) }
	def retain(k : Key) = synchronized { _retainedKeys += k }
	def release(k : Key) = synchronized { _retainedKeys -= k }

	def shouldIgnoreRelease(k : Key) = synchronized { _keysToIgnoreRelease(k) }
	def ignoreRelease(k : Key) = synchronized { _keysToIgnoreRelease += k }
	def enableRelease(k : Key) = synchronized { _keysToIgnoreRelease -= k }

	// ****************************************************************
	// ** IMPLICITS
	// ****************************************************************

	implicit def Point_to_Vector(p : Point) : Vector = (p.x, p.y)

	// ****************************************************************
	// ** EVENT HANDLING
	// ****************************************************************

	override def mouseDragged(e : MouseEvent) = {
		game pushEvent new MouseMoved(e.getPoint)
	}

	override def mouseMoved(e : MouseEvent) = {
		game pushEvent new MouseMoved(e.getPoint)
	}

	override def mouseClicked(e : MouseEvent) = {}

	override def mouseEntered(e : MouseEvent) = {}

	override def mouseExited(e : MouseEvent) = {}

	override def mousePressed(e : MouseEvent) = game pushEvent new MousePressed(MouseButton.fromMouseButtonCode(e.getButton), e.getPoint)

	override def mouseReleased(e : MouseEvent) = game pushEvent new MouseReleased(MouseButton.fromMouseButtonCode(e.getButton), e.getPoint)

	override def keyPressed(e : KeyEvent) {
	  println(s"[${e.getExtendedKeyCode}|${e.getKeyCode}|${e.getKeyChar}|${e.getModifiersEx}]")

		val key = Key.fromKeyCode(if (e.getKeyCode != KeyEvent.VK_UNDEFINED) e.getKeyCode else e.getKeyChar)

		if (!shouldRetain(key)) {
			game pushEvent KeyPressed(key)
			game startPushingEvent new KeyHold(key)
		} else ignoreRelease(key)
	}

	override def keyReleased(e : KeyEvent) {
		val key = Key.fromKeyCode(if (e.getKeyCode != KeyEvent.VK_UNDEFINED) e.getKeyCode else e.getKeyChar)

		retain(key)
		enableRelease(key)

		new Thread(new Runnable {
			def run {
				Thread.sleep(RELEASE_KEY_WINDOW_SIZE)
				EventAdapter.this.releaseRetained(key)
			}
		}).start
	}

	protected def releaseRetained(key : Key) {
		if (!shouldIgnoreRelease(key)) {
			game pushEvent KeyReleased(key)
			game stopPushingEvent new KeyHold(key)
			ignoreRelease(key)
		}

		release(key)
	}

	override def keyTyped(e : KeyEvent) = {}
}