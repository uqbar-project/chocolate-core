package org.uqbar.chocolate.core.reactions.io

import org.uqbar.chocolate.core.reactions.events.Pressable

trait RepeatedKey {
	def location: KeyLocation
}

trait Key extends Pressable
object Key {

	//*********************************************************************************************
	// KEYS
	//*********************************************************************************************

	trait LetterKey extends Key
	object Letter {
		case object A extends LetterKey
		case object B extends LetterKey
		case object C extends LetterKey
		case object D extends LetterKey
		case object E extends LetterKey
		case object F extends LetterKey
		case object G extends LetterKey
		case object H extends LetterKey
		case object I extends LetterKey
		case object J extends LetterKey
		case object K extends LetterKey
		case object L extends LetterKey
		case object M extends LetterKey
		case object N extends LetterKey
		case object O extends LetterKey
		case object P extends LetterKey
		case object Q extends LetterKey
		case object R extends LetterKey
		case object S extends LetterKey
		case object T extends LetterKey
		case object U extends LetterKey
		case object V extends LetterKey
		case object W extends LetterKey
		case object X extends LetterKey
		case object Y extends LetterKey
		case object Z extends LetterKey
	}

	case class Number(n: Int, location: KeyLocation = KeyLocation.Standard) extends Key with RepeatedKey { require (0 to 9 contains n) }

	trait SymbolKey extends Key
	object Symbol {
		case object & extends SymbolKey
		case object \ extends SymbolKey
		case object ^ extends SymbolKey
		case object $ extends SymbolKey
		case object > extends SymbolKey
		case object < extends SymbolKey
		case class *(location: KeyLocation = KeyLocation.Standard) extends RepeatedKey with SymbolKey
		case class /(location: KeyLocation = KeyLocation.Standard) extends RepeatedKey with SymbolKey
		case class +(location: KeyLocation = KeyLocation.Standard) extends RepeatedKey with SymbolKey
		case class -(location: KeyLocation = KeyLocation.Standard) extends RepeatedKey with SymbolKey
		case class Period(location: KeyLocation = KeyLocation.Standard) extends RepeatedKey with SymbolKey //.
		case object Underscore extends SymbolKey // _
		case object At extends SymbolKey //@
		case object Colon extends SymbolKey // :
		case object Comma extends SymbolKey // ,
		case object Equals extends SymbolKey // =
		case object BackQuote extends SymbolKey // `
		case object Euro extends SymbolKey // €
		case object Numeral extends SymbolKey // #
		case object Period extends SymbolKey // .
		case object Quote extends SymbolKey // '
		case object DoubleQuote extends SymbolKey // "
		case object Semicolon extends SymbolKey // ;
		case object OpenExclamation extends SymbolKey // ¡
		case object CloseExclamation extends SymbolKey // !
		case object CloseBrace extends SymbolKey // }
		case object OpenBrace extends SymbolKey // {
		case object OpenBracket extends SymbolKey // [
		case object CloseBracket extends SymbolKey // ]
		case object OpenParenthesis extends SymbolKey // (
		case object CloseParenthesis extends SymbolKey // )
	}

	case class Function(n: Int) extends Key { require (1 to 24 contains n) }

	trait ActionKey extends Key
	object Action {
		case object Undo extends ActionKey
		case object Redo extends ActionKey
		case object Cut extends ActionKey
		case object Copy extends ActionKey
		case object Paste extends ActionKey
		case object Find extends ActionKey
		case object Accept extends ActionKey
		case object Cancel extends ActionKey
		case object Clear extends ActionKey
		case object Pause extends ActionKey
		case object Stop extends ActionKey
		case object PrintScreen extends ActionKey
		case object Help extends ActionKey
		case class Insert(location: KeyLocation = KeyLocation.Standard) extends ActionKey with RepeatedKey
		case class Delete(location: KeyLocation = KeyLocation.Standard) extends ActionKey with RepeatedKey
	}

	trait NavigationKey extends Key
	object Navigation {
		trait ArrowKey extends RepeatedKey with NavigationKey
		object Arrow {
			case class Up(location: KeyLocation = KeyLocation.Standard) extends ArrowKey
			case class Down(location: KeyLocation = KeyLocation.Standard) extends ArrowKey
			case class Left(location: KeyLocation = KeyLocation.Standard) extends ArrowKey
			case class Right(location: KeyLocation = KeyLocation.Standard) extends ArrowKey
		}
		case object PageDown extends NavigationKey
		case object PageUp extends NavigationKey
		case object Begin extends NavigationKey
		case object End extends NavigationKey
		case object Home extends NavigationKey
	}

	trait LockKey extends Key
	object Lock {
		case object ScrollLock extends LockKey
		case object CapsLock extends LockKey
		case object NumLock extends LockKey
	}

	trait SpecialKey extends Key
	object Special {
		case object Space extends SpecialKey
		case object BackSpace extends SpecialKey
		case object Tab extends SpecialKey
		case object Esc extends SpecialKey
		case object ContextMenu extends SpecialKey
		case object Alt extends SpecialKey
		case object AltGr extends SpecialKey

		case class Enter(location: KeyLocation = KeyLocation.Anywhere) extends SpecialKey with RepeatedKey
		case class Ctrl(location: KeyLocation = KeyLocation.Anywhere) extends SpecialKey with RepeatedKey
		case class Shift(location: KeyLocation = KeyLocation.Anywhere) extends SpecialKey with RepeatedKey
		case class Meta(location: KeyLocation = KeyLocation.Anywhere) extends SpecialKey with RepeatedKey
		case class Windows(location: KeyLocation = KeyLocation.Anywhere) extends SpecialKey with RepeatedKey
	}

	case class Unknown(value: Any) extends Key
}

trait KeyLocation {
	override def equals(o: Any) = o match {
		case x: KeyLocation => x.eq(this) || x.eq(KeyLocation.Anywhere)
		case _ => false
	}
	override def hashCode = KeyLocation.Anywhere.hashCode
}
object KeyLocation {
	case object Standard extends KeyLocation
	case object Left extends KeyLocation
	case object Right extends KeyLocation
	case object Numpad extends KeyLocation
	case object Unknown extends KeyLocation
	case object Anywhere extends KeyLocation {
		override def equals(o: Any) = o.isInstanceOf[KeyLocation]
		override def hashCode = 47
	}
}