package org.uqbar.chocolate.core.reactions.io

import java.awt.event.KeyEvent
import java.awt.event.KeyEvent._
import java.security.InvalidParameterException

trait KeyLocation
object KeyLocation {
	object Standard extends KeyLocation
	object Left extends KeyLocation
	object Right extends KeyLocation
	object Numpad extends KeyLocation
}
case class Key(code: Int, location: KeyLocation = KeyLocation.Standard, character: Option[Char] = None)
object Key {
	object Character {
		def apply(char: Char) = KeyEvent.getExtendedKeyCodeForChar(char) match {
			case VK_UNDEFINED => throw new InvalidParameterException
			case other => Key(other, KeyLocation.Standard, Some(char))
		}

		def unapply(key: Key) = key match {
			case Key(_, _, char) => char
		}
	}
	object Numpad {
		lazy val - = Key(VK_SUBTRACT, KeyLocation.Numpad)
		lazy val + = Key(VK_ADD, KeyLocation.Numpad)
		lazy val / = Key(VK_DIVIDE, KeyLocation.Numpad)
		lazy val * = Key(VK_MULTIPLY, KeyLocation.Numpad)
		lazy val Decimal = Key(VK_DECIMAL, KeyLocation.Numpad)
		lazy val Down = Key(VK_KP_DOWN, KeyLocation.Numpad)
		lazy val Left = Key(VK_KP_LEFT, KeyLocation.Numpad)
		lazy val Right = Key(VK_KP_RIGHT, KeyLocation.Numpad)
		lazy val Up = Key(VK_KP_UP, KeyLocation.Numpad)

		def unapply(key: Key) = key match {
			case Key(code, KeyLocation.Numpad, _) if VK_NUMPAD0 to VK_NUMPAD9 contains code => Some(code - VK_NUMPAD0)
			case _ => None
		}
	}
	object F {
		def apply(functionNumber: Int) = functionNumber match {
			case _ if 1 to 12 contains functionNumber => Key(VK_F1 + functionNumber - 1)
			case _ if 13 to 24 contains functionNumber => Key(VK_F13 + functionNumber - 1)
			case _ => throw new InvalidParameterException
		}

		def unapply(key: Key) = key match {
			case Key(code, _, _) if VK_F1 to VK_F12 contains code => Some(code - VK_F1)
			case Key(code, _, _) if VK_F13 to VK_F24 contains code => Some(code - VK_F13)
			case _ => None
		}
	}
	object Number {
		def apply(number: Int) = number match {
			case _ if 0 to 9 contains number => Key(VK_0 + number)
			case _ => throw new InvalidParameterException
		}

		def unapply(key: Key) = key match {
			case Key(code, _, _) if VK_0 to VK_9 contains code => Some(code - VK_0)
			case _ => None
		}
	}
	object Letter {
		lazy val A = Key(VK_A)
		lazy val B = Key(VK_B)
		lazy val C = Key(VK_C)
		lazy val D = Key(VK_D)
		lazy val E = Key(VK_E)
		lazy val F = Key(VK_F)
		lazy val G = Key(VK_G)
		lazy val H = Key(VK_H)
		lazy val I = Key(VK_I)
		lazy val J = Key(VK_J)
		lazy val K = Key(VK_K)
		lazy val L = Key(VK_L)
		lazy val M = Key(VK_M)
		lazy val N = Key(VK_N)
		lazy val O = Key(VK_O)
		lazy val P = Key(VK_P)
		lazy val Q = Key(VK_Q)
		lazy val R = Key(VK_R)
		lazy val S = Key(VK_S)
		lazy val T = Key(VK_T)
		lazy val U = Key(VK_U)
		lazy val V = Key(VK_V)
		lazy val W = Key(VK_W)
		lazy val X = Key(VK_X)
		lazy val Y = Key(VK_Y)
		lazy val Z = Key(VK_Z)
	}
	lazy val & = Key(VK_AMPERSAND)
	lazy val * = Key(VK_ASTERISK)
	lazy val \ = Key(VK_BACK_SLASH)
	lazy val ^ = Key(VK_CIRCUMFLEX)
	lazy val $ = Key(VK_DOLLAR)
	lazy val > = Key(VK_GREATER)
	lazy val < = Key(VK_LESS)
	lazy val / = Key(VK_SLASH)
	lazy val + = Key(VK_PLUS)
	lazy val - = Key(VK_MINUS)
	lazy val Space = Key(VK_SPACE)
	// _
	lazy val Underscore = Key(VK_UNDERSCORE)
	//@
	lazy val At = Key(VK_AT)
	// :
	lazy val Colon = Key(VK_COLON)
	// ,
	lazy val Comma = Key(VK_COMMA)
	// =
	lazy val Equals = Key(VK_EQUALS)
	// `
	lazy val BackQuote = Key(VK_BACK_QUOTE)
	// €
	lazy val Euro = Key(VK_EURO_SIGN)
	// #
	lazy val Numeral = Key(VK_NUMBER_SIGN)
	// .
	lazy val Period = Key(VK_PERIOD)
	// '
	lazy val Quote = Key(VK_QUOTE)
	// "
	lazy val DoubleQuote = Key(VK_QUOTEDBL)
	// ;
	lazy val Semicolon = Key(VK_SEMICOLON)
	// ¡
	lazy val OpenExclamation = Key(VK_EXCLAMATION_MARK)
	// !
	lazy val CloseExclamation = Key(VK_INVERTED_EXCLAMATION_MARK)
	// }
	lazy val CloseBrace = Key(VK_BRACELEFT)
	// {
	lazy val OpenBrace = Key(VK_BRACELEFT)
	// [
	lazy val OpenBracket = Key(VK_OPEN_BRACKET)
	// ]
	lazy val CloseBracket = Key(VK_CLOSE_BRACKET)
	// (
	lazy val OpenParenthesis = Key(VK_LEFT_PARENTHESIS)
	// )
	lazy val CloseParenthesis = Key(VK_RIGHT_PARENTHESIS)
	// ↓
	lazy val Up = Key(VK_UP)
	// ↑
	lazy val Down = Key(VK_DOWN)
	// ←
	lazy val Left = Key(VK_LEFT)
	// →
	lazy val Right = Key(VK_RIGHT)
	lazy val PageDown = Key(VK_PAGE_DOWN)
	lazy val PageUp = Key(VK_PAGE_UP)
	lazy val PrintScreen = Key(VK_PRINTSCREEN)
	lazy val Help = Key(VK_HELP)
	lazy val Begin = Key(VK_BEGIN)
	lazy val End = Key(VK_END)
	lazy val Home = Key(VK_HOME)
	lazy val Insert = Key(VK_INSERT)
	lazy val Delete = Key(VK_DELETE)
	lazy val ScrollLock = Key(VK_SCROLL_LOCK)
	lazy val CapsLock = Key(VK_CAPS_LOCK)
	lazy val NumLock = Key(VK_NUM_LOCK)
	lazy val BackSpace = Key(VK_BACK_SPACE)
	lazy val Enter = Key(VK_ENTER)
	lazy val Tab = Key(VK_TAB)
	lazy val Escape = Key(VK_ESCAPE)
	lazy val ContextMenu = Key(VK_CONTEXT_MENU)
	lazy val Alt = Key(VK_ALT)
	lazy val AltGr = Key(VK_ALT_GRAPH)
	object Action {
		lazy val Undo = Key(VK_UNDO)
		lazy val Redo = Key(VK_AGAIN)
		lazy val Cut = Key(VK_CUT)
		lazy val Copy = Key(VK_COPY)
		lazy val Paste = Key(VK_PASTE)
		lazy val Find = Key(VK_FIND)
		lazy val Accept = Key(VK_ACCEPT)
		lazy val Cancel = Key(VK_CANCEL)
		lazy val Clear = Key(VK_CLEAR)
		lazy val Pause = Key(VK_PAUSE)
		lazy val Stop = Key(VK_STOP)
	}
	protected class DualKey(code: Int) {
		def apply(location: KeyLocation) = Key(code, location)

		def unapply(key: Key) = key match {
			case Key(`code`, location, _) => Some(location)
		}
	}
	object Ctrl extends DualKey(VK_CONTROL)
	object Shift extends DualKey(VK_SHIFT)
	object Meta extends DualKey(VK_META)
	object Windows extends DualKey(VK_WINDOWS)
}
