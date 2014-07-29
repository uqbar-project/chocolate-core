package org.uqbar.chocolate.core.reactions.events.adapters

import java.awt.event.{ KeyEvent => JavaKeyEvent }
import java.awt.event.KeyEvent._
import java.awt.event.KeyListener
import org.uqbar.chocolate.core.reactions.events.Typed
import java.awt.event.{ KeyEvent => JavaKeyEvent }
import org.uqbar.chocolate.core.reactions.io.Key
import org.uqbar.chocolate.core.reactions.io.KeyLocation
import scala.language.implicitConversions
import org.uqbar.chocolate.core.reactions.events.Pressed
import org.uqbar.chocolate.core.reactions.events.Hold
import org.uqbar.chocolate.core.reactions.events.Released
import org.uqbar.chocolate.core.reactions.events.EventDispatcher

trait KeyboardAdapter extends KeyListener {
	def target: EventDispatcher

	val RELEASE_KEY_WINDOW_SIZE = 5

	@volatile var _retainedKeys = Set[Key]()
	@volatile var _keysToIgnoreRelease = Set[Key]()

	protected implicit def JavaKeyEvent_to_Key(event: JavaKeyEvent) = {
		val code = if (event.getKeyCode != JavaKeyEvent.VK_UNDEFINED) event.getKeyCode else event.getKeyChar
		val location = event.getKeyLocation match {
			case KEY_LOCATION_STANDARD => KeyLocation.Standard
			case KEY_LOCATION_LEFT => KeyLocation.Left
			case KEY_LOCATION_RIGHT => KeyLocation.Right
			case KEY_LOCATION_NUMPAD => KeyLocation.Numpad
			case KEY_LOCATION_UNKNOWN => KeyLocation.Unknown
		}

		code match {
			case VK_A => Key.Letter.A
			case VK_B => Key.Letter.B
			case VK_C => Key.Letter.C
			case VK_D => Key.Letter.D
			case VK_E => Key.Letter.E
			case VK_F => Key.Letter.F
			case VK_G => Key.Letter.G
			case VK_H => Key.Letter.H
			case VK_I => Key.Letter.I
			case VK_J => Key.Letter.J
			case VK_K => Key.Letter.K
			case VK_L => Key.Letter.L
			case VK_M => Key.Letter.M
			case VK_N => Key.Letter.N
			case VK_O => Key.Letter.O
			case VK_P => Key.Letter.P
			case VK_Q => Key.Letter.Q
			case VK_R => Key.Letter.R
			case VK_S => Key.Letter.S
			case VK_T => Key.Letter.T
			case VK_U => Key.Letter.U
			case VK_V => Key.Letter.V
			case VK_W => Key.Letter.W
			case VK_X => Key.Letter.X
			case VK_Y => Key.Letter.Y
			case VK_Z => Key.Letter.Z

			case VK_UNDO => Key.Action.Undo
			case VK_AGAIN => Key.Action.Redo
			case VK_CUT => Key.Action.Cut
			case VK_COPY => Key.Action.Copy
			case VK_PASTE => Key.Action.Paste
			case VK_FIND => Key.Action.Find
			case VK_ACCEPT => Key.Action.Accept
			case VK_CANCEL => Key.Action.Cancel
			case VK_CLEAR => Key.Action.Clear
			case VK_PAUSE => Key.Action.Pause
			case VK_STOP => Key.Action.Stop
			case VK_PRINTSCREEN => Key.Action.PrintScreen
			case VK_HELP => Key.Action.Help
			case VK_INSERT => Key.Action.Insert(location)
			case VK_DELETE => Key.Action.Delete(location)

			case VK_AMPERSAND => Key.Symbol.&
			case VK_ASTERISK => Key.Symbol.*(KeyLocation.Standard)
			case VK_BACK_SLASH => Key.Symbol.\
			case VK_CIRCUMFLEX => Key.Symbol.^
			case VK_DOLLAR => Key.Symbol.$
			case VK_GREATER => Key.Symbol.>
			case VK_LESS => Key.Symbol.<
			case VK_SLASH => Key.Symbol./(KeyLocation.Standard)
			case VK_PLUS => Key.Symbol.+(KeyLocation.Standard)
			case VK_MINUS => Key.Symbol.-(KeyLocation.Standard)
			case VK_UNDERSCORE => Key.Symbol.Underscore
			case VK_AT => Key.Symbol.At
			case VK_COLON => Key.Symbol.Colon
			case VK_COMMA => Key.Symbol.Comma
			case VK_EQUALS => Key.Symbol.Equals
			case VK_BACK_QUOTE => Key.Symbol.BackQuote
			case VK_EURO_SIGN => Key.Symbol.Euro
			case VK_NUMBER_SIGN => Key.Symbol.Numeral
			case VK_PERIOD => Key.Symbol.Period
			case VK_QUOTE => Key.Symbol.Quote
			case VK_QUOTEDBL => Key.Symbol.DoubleQuote
			case VK_SEMICOLON => Key.Symbol.Semicolon
			case VK_EXCLAMATION_MARK => Key.Symbol.OpenExclamation
			case VK_INVERTED_EXCLAMATION_MARK => Key.Symbol.CloseExclamation
			case VK_BRACELEFT => Key.Symbol.OpenBrace
			case VK_BRACERIGHT => Key.Symbol.CloseBrace
			case VK_OPEN_BRACKET => Key.Symbol.OpenBracket
			case VK_CLOSE_BRACKET => Key.Symbol.CloseBracket
			case VK_LEFT_PARENTHESIS => Key.Symbol.OpenParenthesis
			case VK_RIGHT_PARENTHESIS => Key.Symbol.CloseParenthesis

			case VK_PAGE_DOWN => Key.Navigation.PageDown
			case VK_PAGE_UP => Key.Navigation.PageUp
			case VK_BEGIN => Key.Navigation.Begin
			case VK_END => Key.Navigation.End
			case VK_HOME => Key.Navigation.Home
			case VK_UP => Key.Navigation.Arrow.Up(KeyLocation.Standard)
			case VK_DOWN => Key.Navigation.Arrow.Down(KeyLocation.Standard)
			case VK_LEFT => Key.Navigation.Arrow.Left(KeyLocation.Standard)
			case VK_RIGHT => Key.Navigation.Arrow.Right(KeyLocation.Standard)

			case VK_SCROLL_LOCK => Key.Lock.ScrollLock
			case VK_CAPS_LOCK => Key.Lock.CapsLock
			case VK_NUM_LOCK => Key.Lock.NumLock

			case VK_SPACE => Key.Special.Space
			case VK_BACK_SPACE => Key.Special.BackSpace
			case VK_TAB => Key.Special.Tab
			case VK_ESCAPE => Key.Special.Esc
			case VK_CONTEXT_MENU => Key.Special.ContextMenu
			case VK_ALT => Key.Special.Alt
			case VK_ALT_GRAPH => Key.Special.AltGr

			case VK_ENTER => Key.Special.Enter(location)
			case VK_CONTROL => Key.Special.Ctrl(location)
			case VK_SHIFT => Key.Special.Shift(location)
			case VK_META => Key.Special.Meta(location)
			case VK_WINDOWS => Key.Special.Windows(location)

			case VK_SUBTRACT => Key.Symbol.-(KeyLocation.Numpad)
			case VK_ADD => Key.Symbol.+(KeyLocation.Numpad)
			case VK_DIVIDE => Key.Symbol./(KeyLocation.Numpad)
			case VK_MULTIPLY => Key.Symbol.*(KeyLocation.Numpad)
			case VK_DECIMAL => Key.Symbol.Period(KeyLocation.Numpad)
			case VK_KP_UP => Key.Navigation.Arrow.Up(KeyLocation.Numpad)
			case VK_KP_DOWN => Key.Navigation.Arrow.Down(KeyLocation.Numpad)
			case VK_KP_LEFT => Key.Navigation.Arrow.Left(KeyLocation.Numpad)
			case VK_KP_RIGHT => Key.Navigation.Arrow.Right(KeyLocation.Numpad)
			case code if VK_NUMPAD0 to VK_NUMPAD9 contains code => Key.Number(code - VK_NUMPAD0, KeyLocation.Numpad)

			case code if VK_0 to VK_9 contains code => Key.Number(code - VK_0, KeyLocation.Standard)

			case code if VK_F1 to VK_F12 contains code => Key.Function(code - VK_F1 + 1)
			case code if VK_F13 to VK_F24 contains code => Key.Function(code - VK_F13 + 13)

			case other => Key.Unknown((code, location, event.getKeyChar))
		}
	}

	protected def shouldRetain(k: Key) = synchronized { _retainedKeys(k) }
	protected def retain(k: Key) = synchronized { _retainedKeys += k }
	protected def release(k: Key) = synchronized { _retainedKeys -= k }

	protected def shouldIgnoreRelease(k: Key) = synchronized { _keysToIgnoreRelease(k) }
	protected def ignoreRelease(k: Key) = synchronized { _keysToIgnoreRelease += k }
	protected def enableRelease(k: Key) = synchronized { _keysToIgnoreRelease -= k }

	protected def releaseRetained(key: Key) {
		if (!shouldIgnoreRelease(key)) {
			target pushEvent Released(key)
			target stopPushingEvent Hold(key)
			ignoreRelease(key)
		}

		release(key)
	}

	//*********************************************************************************************
	// KEY LISTENING
	//*********************************************************************************************

	def keyPressed(event: JavaKeyEvent) {
		val key: Key = event
		if (!shouldRetain(key)) {
			target pushEvent Pressed(key)
			target startPushingEvent Hold(key)
		} else ignoreRelease(key)
	}

	def keyReleased(event: JavaKeyEvent) {
		val key: Key = event
		retain(key)
		enableRelease(key)

		//TODO: Use future instead?
		new Thread(new Runnable {
			def run {
				Thread.sleep(RELEASE_KEY_WINDOW_SIZE)
				releaseRetained(key)
			}
		}).start
	}

	def keyTyped(event: JavaKeyEvent) = event.getKeyChar match {
		case VK_UNDEFINED | '\b' | '\u001b' | '\u007f' =>
		case char => target pushEvent Typed(char)
	}
}