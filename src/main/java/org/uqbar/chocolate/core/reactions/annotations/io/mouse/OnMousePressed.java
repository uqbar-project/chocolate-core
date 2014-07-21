package org.uqbar.chocolate.core.reactions.annotations.io.mouse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.uqbar.chocolate.core.reactions.annotations.GameTrigger;
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton;
import org.uqbar.chocolate.core.reactions.events.MousePressed;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@GameTrigger(MousePressed.class)
public @interface OnMousePressed {
	public MouseButton button() default MouseButton.LEFT;
}