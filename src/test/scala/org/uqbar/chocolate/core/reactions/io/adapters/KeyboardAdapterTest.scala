package org.uqbar.chocolate.core.reactions.io.adapters

import java.awt.Component
import java.awt.Toolkit
import java.awt.event.InputEvent._
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent._
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import org.scalatest.BeforeAndAfter
import org.scalatest.BeforeAndAfterAll
import org.scalatest.FreeSpec
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.prop.TableDrivenPropertyChecks.Table
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.time.Day
import org.scalatest.time.Seconds
import org.scalatest.time.Span
import org.uqbar.chocolate.core.reactions.events.ContinuableEvent
import org.uqbar.chocolate.core.reactions.events.EventDispatcher
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.reactions.events.Hold
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Released
import org.uqbar.chocolate.core.reactions.events.Typed
import org.uqbar.chocolate.core.reactions.events.adapters.KeyboardAdapter
import org.uqbar.chocolate.core.reactions.io.Key
import org.uqbar.chocolate.core.reactions.io.KeyLocation

import javax.swing.JFrame

class KeyboardAdapterTest extends FreeSpec with Matchers with Eventually with ScalaFutures with BeforeAndAfter with BeforeAndAfterAll {

	class MockDispatcher extends EventDispatcher {
		var registeredEvents: List[GameEvent] = Nil
		var startedContinuableEvents: List[GameEvent] = Nil
		var finishedContinuableEvents: List[GameEvent] = Nil

		override def pushEvent(e: GameEvent) = registeredEvents = registeredEvents :+ e
		override def startPushingEvent(e: ContinuableEvent) = startedContinuableEvents = startedContinuableEvents :+ e
		override def stopPushingEvent(e: ContinuableEvent) = finishedContinuableEvents = finishedContinuableEvents :+ e
	}

	implicit val patience = PatienceConfig(timeout = Span(10, Seconds))

	val eventQueue = Toolkit.getDefaultToolkit.getSystemEventQueue
	var panel: JFrame = _
	var adapter: KeyboardAdapter = _
	var dispatcher: MockDispatcher = _

	//*********************************************************************************************
	// TEST CASES
	//*********************************************************************************************

	val keyMapping = Table(
		"Java Event" -> "Chocolate Event",
		javaKey(code = VK_Q, char = 'q') -> Key.Letter.Q,
		javaKey(code = VK_W, char = 'w') -> Key.Letter.W,
		javaKey(code = VK_4, char = '4') -> Key.Number(4),
		javaKey(char = 'ñ', location = KEY_LOCATION_UNKNOWN) -> Key.Unknown(('ñ'.toInt, KeyLocation.Unknown, 'ñ')),
		javaKey(char = '|', location = KEY_LOCATION_UNKNOWN) -> Key.Unknown(('|'.toInt, KeyLocation.Unknown, '|')),
		javaKey(code = VK_NUMPAD7, char = '7', location = KEY_LOCATION_NUMPAD) -> Key.Number(7, KeyLocation.Numpad),
		javaKey(code = VK_ENTER, char = '\n', location = KEY_LOCATION_NUMPAD) -> Key.Special.Enter(KeyLocation.Numpad),
		javaKey(code = VK_BACK_SPACE, char = '\b') -> Key.Special.BackSpace,
		javaKey(code = VK_TAB, char = '\t') -> Key.Special.Tab,
		javaKey(code = VK_ENTER, char = '\n') -> Key.Special.Enter(),
		javaKey(code = VK_ESCAPE, char = '\u001b') -> Key.Special.Esc,
		javaKey(code = VK_SPACE, char = ' ') -> Key.Special.Space,
		javaKey(code = VK_DELETE, char = '\u007f') -> Key.Action.Delete(KeyLocation.Standard),
		javaKey(code = VK_LESS, char = '<') -> Key.Symbol.<,
		javaKey(code = VK_BRACELEFT, char = '{') -> Key.Symbol.OpenBrace,
		javaKey(code = VK_COMMA, char = ',') -> Key.Symbol.Comma,
		javaKey(code = VK_F3) -> Key.Function(3),
		javaKey(code = VK_F22) -> Key.Function(22),
		javaKey(code = VK_CONTEXT_MENU) -> Key.Special.ContextMenu,
		javaKey(code = VK_INSERT) -> Key.Action.Insert(KeyLocation.Standard),
		javaKey(code = VK_WINDOWS) -> Key.Special.Windows(),
		javaKey(code = VK_PAGE_UP) -> Key.Navigation.PageUp,
		javaKey(code = VK_UP) -> Key.Navigation.Arrow.Up(KeyLocation.Standard),
		javaKey(code = VK_SHIFT, location = KEY_LOCATION_LEFT, mod = SHIFT_MASK) -> Key.Special.Shift(KeyLocation.Left),
		javaKey(code = VK_SHIFT, location = KEY_LOCATION_RIGHT, mod = SHIFT_MASK) -> Key.Special.Shift(KeyLocation.Right),
		javaKey(code = VK_CONTROL, location = KEY_LOCATION_LEFT, mod = CTRL_MASK) -> Key.Special.Ctrl(KeyLocation.Left),
		javaKey(code = VK_CONTROL, location = KEY_LOCATION_RIGHT, mod = CTRL_MASK) -> Key.Special.Ctrl(KeyLocation.Right),
		javaKey(code = VK_ALT, location = KEY_LOCATION_LEFT, mod = ALT_MASK) -> Key.Special.Alt,
		javaKey(code = VK_ALT_GRAPH, mod = ALT_GRAPH_MASK) -> Key.Special.AltGr,
		javaKey(code = VK_CAPS_LOCK) -> Key.Lock.CapsLock,
		javaKey(code = VK_NUM_LOCK, location = KEY_LOCATION_NUMPAD) -> Key.Lock.NumLock,
		javaKey(code = VK_KP_DOWN, location = KEY_LOCATION_NUMPAD) -> Key.Navigation.Arrow.Down(KeyLocation.Numpad),
		javaKey(code = VK_INSERT, location = KEY_LOCATION_NUMPAD) -> Key.Action.Insert(KeyLocation.Numpad)
	)

	"The right Chocolate events should be dispatched when a Java key is pressed" in forAll(keyMapping) { (javaEvent, key) =>
		dispatch(javaEvent(KEY_PRESSED)) { dispatcher =>
			dispatcher.registeredEvents should be (List(Pressed(key)))
			dispatcher.startedContinuableEvents should be (List(Hold(key)))
			dispatcher.finishedContinuableEvents should be (Nil)
		}
	}

	"The right Chocolate events should be dispatched when a Java key is released" in forAll(keyMapping) { (javaEvent, key) =>
		dispatch(javaEvent(KEY_RELEASED)) { dispatcher =>
			dispatcher.registeredEvents should be (List(Released(key)))
			dispatcher.startedContinuableEvents should be (Nil)
			dispatcher.finishedContinuableEvents should be (List(Hold(key)))
		}
	}

	"Typing the L key" - {
		"should trigger Typed('l') if nothing else is being pressed" in {
			dispatch(javaKey(char = 'l', location = KEY_LOCATION_UNKNOWN)(KEY_TYPED)) { dispatcher =>
				dispatcher.registeredEvents should contain (Typed('l'))
			}
		}
		"should trigger Typed('ł') if AltGr is being pressed" in {
			dispatch(javaKey(char = 'ł', mod = ALT_GRAPH_MASK, location = KEY_LOCATION_UNKNOWN)(KEY_TYPED)) { dispatcher =>
				dispatcher.registeredEvents should contain (Typed('ł'))
			}
		}
		"should trigger Typed('L') if Shift is being pressed" in {
			dispatch(javaKey(char = 'L', mod = SHIFT_MASK, location = KEY_LOCATION_UNKNOWN)(KEY_TYPED)) { dispatcher =>
				dispatcher.registeredEvents should contain (Typed('L'))
			}
		}
		"should trigger Typed('Ł') if AltGr and Shift is being pressed" in {
			dispatch(javaKey(char = 'Ł', mod = SHIFT_MASK | ALT_GRAPH_MASK, location = KEY_LOCATION_UNKNOWN)(KEY_TYPED)) { dispatcher =>
				dispatcher.registeredEvents should contain (Typed('Ł'))
			}
		}
	}

	//*********************************************************************************************
	// SET UP & TEAR DOWN
	//*********************************************************************************************

	override def beforeAll {
		panel = new JFrame {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
			setFocusTraversalKeysEnabled(false)
			setBounds(0, 0, 0, 0)
			setUndecorated(true)
			setFocusable(true)
			setVisible(true)
		}
	}

	override def afterAll {
		panel.setVisible(false)
		panel.dispose
	}

	//*********************************************************************************************
	// AUXILIARS
	//*********************************************************************************************

	def javaKey(code: Int = VK_UNDEFINED, char: Char = VK_UNDEFINED, location: Int = KEY_LOCATION_STANDARD, mod: Int = 0) = {
		event: Int => { target: Component => new KeyEvent(target, event, System.nanoTime, mod, code, char, location) }
	}

	def dispatch(e: Component => KeyEvent)(promise: MockDispatcher => Unit) = {
		dispatcher = new MockDispatcher
		adapter = new KeyboardAdapter { val target = dispatcher }
		panel.addKeyListener(adapter)
		panel.requestFocus

		eventQueue.postEvent(e(panel))

		eventually { promise(dispatcher) }

		panel.removeKeyListener(adapter)
	}

	//*********************************************************************************************
	// SNIFFER
	//*********************************************************************************************

	def sniff = whenReady(Future {
		new JFrame {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
			setFocusTraversalKeysEnabled(false)
			setBounds(10, 10, 100, 100)

			def printEvent(what: String)(e: KeyEvent) = println(s"$what: $e")
			addKeyListener(new KeyListener {
				def keyPressed(e: KeyEvent) = println(s"Pressed:  ${e.paramString}")
				def keyReleased(e: KeyEvent) = println(s"Released: ${e.paramString}")
				def keyTyped(e: KeyEvent) = println(s"Typed:    ${e.paramString}")
			})

			addMouseListener(new MouseListener {
				override def mouseClicked(e: MouseEvent) = println(s"Clicked:  ${e}")
				override def mouseEntered(e: MouseEvent) = {}
				override def mouseExited(e: MouseEvent) = {}
				override def mousePressed(e: MouseEvent) = println(s"Pressed:  ${e}")
				override def mouseReleased(e: MouseEvent) = println(s"Pressed:  ${e}")
			})

			setVisible(true)
		}
		while (true) {}
	}, timeout(Span(1, Day))){ x => }

	//	sniff

}