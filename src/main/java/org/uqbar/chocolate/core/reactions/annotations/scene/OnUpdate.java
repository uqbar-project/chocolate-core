package org.uqbar.chocolate.core.reactions.annotations.scene;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.uqbar.chocolate.core.reactions.annotations.GameTrigger;
import org.uqbar.chocolate.core.reactions.events.Update;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@GameTrigger(Update.class)
public @interface OnUpdate {
}