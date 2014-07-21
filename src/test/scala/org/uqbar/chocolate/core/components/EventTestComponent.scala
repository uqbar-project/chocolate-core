package org.uqbar.chocolate.core.components;

import org.uqbar.chocolate.core.reactions.annotations.io.keyboard.OnKeyPressed
import org.uqbar.chocolate.core.reactions.annotations.io.mouse.OnMouseMoved
import org.uqbar.chocolate.core.reactions.annotations.io.mouse.OnMousePressed
import org.uqbar.chocolate.core.reactions.annotations.scene.OnUpdate
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton

class EventTestComponent extends GameComponent {
	var triggerCount = 0

	// ****************************************************************
	// ** TRIGGERS
	// ****************************************************************

	@OnUpdate
	def triggerOnUpdate = increaseTriggerCount

	@OnMouseMoved
	def triggerOnMouseMoved = increaseTriggerCount

	@OnMousePressed(button = MouseButton.ANY)
	def triggerOnAnyMousePressed = increaseTriggerCount

	@OnMousePressed
	def triggerOnLeftMousePressed = increaseTriggerCount

	@OnKeyPressed
	def triggerOnAnyKeyPressed = increaseTriggerCount

	@OnKeyPressed(Key.M)
	def triggerOnMKeyPressed = increaseTriggerCount

	@OnKeyPressed(Key.SIX)
	def triggerOn6KeyPressed = increaseTriggerCount

	@OnKeyPressed(Key.ENTER)
	def triggerOnEnterKeyPressed = increaseTriggerCount

	// @OnKeyPressed(Key.LEFT_SHIFT)
	def triggerOnLeftShiftKeyPressed = increaseTriggerCount

	@OnKeyPressed(Key.UP)
	def triggerOnUpArrowKeyPressed = increaseTriggerCount

	// @OnKeyPressed(Key.TAB)
	def triggerOnTabKeyPressed = increaseTriggerCount

	// ****************************************************************
	// ** OPERATIONS
	// ****************************************************************

	protected def increaseTriggerCount = triggerCount += 1

}