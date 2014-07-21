package org.uqbar.chocolate.core.reactions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.uqbar.chocolate.core.reactions.events.CustomEvent;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@GameTrigger(CustomEvent.class)
public @interface OnCustomEvent {
	public String value();
}
