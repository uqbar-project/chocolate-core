package org.uqbar.chocolate.core.reactions.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.uqbar.chocolate.core.reactions.events.GameEvent;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
// TODO: Mejorar el nombre de este atributo? Si le dejo value puedo mandarlo
// directamente, pero no me dice
// mucho... Tal vez si cambio el nombre de la anotation... TriggerFor?
public @interface GameTrigger {
	public Class<? extends GameEvent> value();
}