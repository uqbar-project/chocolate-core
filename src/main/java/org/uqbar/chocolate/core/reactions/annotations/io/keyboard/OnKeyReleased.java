package org.uqbar.chocolate.core.reactions.annotations.io.keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.uqbar.chocolate.core.reactions.annotations.GameTrigger;
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key;
import org.uqbar.chocolate.core.reactions.events.KeyPressed;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@GameTrigger(KeyPressed.class)
public @interface OnKeyReleased {
	public Key value() default Key.ANY;
}
