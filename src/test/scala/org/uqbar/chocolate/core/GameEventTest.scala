package org.uqbar.chocolate.core

import java.awt.Canvas
import java.awt.Graphics2D
import java.awt.geom.Point2D
import org.uqbar.chocolate.core.components.EventTestComponent
import org.uqbar.chocolate.core.reactions.events.GameEvent
import org.uqbar.chocolate.core.reactions.events.KeyPressed
import org.uqbar.chocolate.core.reactions.events.MouseMoved
import org.uqbar.chocolate.core.reactions.events.MousePressed
import org.uqbar.chocolate.core.reactions.events.Update
import org.scalatest.FunSuite
import org.uqbar.chocolate.core.utils.Implicits._
import org.uqbar.math.vectors._
import org.uqbar.chocolate.core.components.CollisionTestComponent
import org.uqbar.chocolate.core.collisions.NoBoundingBox
import org.uqbar.chocolate.core.dimensions.Positioned
import org.uqbar.chocolate.core.collisions.RectangularBoundingBox
import org.uqbar.math.vectors.Vector
import org.uqbar.chocolate.core.components.GameComponent
import scala.collection.mutable.Seq
import org.uqbar.chocolate.core.reactions.annotations.io.enums.MouseButton
import org.uqbar.chocolate.core.reactions.annotations.io.enums.Key

class GameEventTest extends FunSuite {

	// ****************************************************************
	// ** TESTS
	// ****************************************************************

	test("Update event trigger onUpdate") {
		assertGameTriggerCountForEventEquals(1, new Update())
	}

	test("Mouse moved event triggers onMouseMoved") {
		assertGameTriggerCountForEventEquals(1, new MouseMoved(Origin))
	}

	test("Any MousePressed triggers onMousePressed for any button") {
		assertGameTriggerCountForEventEquals(1, new MousePressed(MouseButton.RIGHT, Origin))
	}

	test("Left MousePressed triggers OnMousePressed for left button") {
		assertGameTriggerCountForEventEquals(2, new MousePressed(MouseButton.LEFT, Origin))
	}

	test("Any KeyPressed triggers OnKeyPressed for any key") {
		assertGameTriggerCountForEventEquals(1, new KeyPressed(Key.G))
	}

	test("M KeyPressed triggers OnKeyPressed for M key") {
		assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.M))
	}

	test("6 KeyPressed triggers OnKeyPressed for 6 key") {
		assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.SIX))
	}

	test("Enter KeyPressed triggers OnKeyPressed for Enter key") {
		assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.ENTER))
	}

	test("Up KeyPressed triggers OnKeyPressed for Up key") {
		assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.UP))
	}

	test("Moving a CollisionableComponent updates it's collision zone coverage") {
		val component = new CollisionTestComponent(new RectangularBoundingBox(100, 100), (0, 0), 0)
		val scene = new GameScene(component)
		val zones = component.coveredZones.clone

		component.move(500, 500)

		val newZones = component.coveredZones

		assert(zones != newZones)
	}

	// TODO:
	// @Test
	// def leftShiftKeyPressedTriggersOnKeyPressedForLeftShiftKey = 
	// assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.LEFT_SHIFT))
	// fail()
	// }

	// @Test
	// def tabKeyPressedTriggersOnKeyPressedForTabKey = 
	// assertGameTriggerCountForEventEquals(2, new KeyPressed(Key.TAB))
	// fail()
	// }

	// ****************************************************************
	// ** TEST HELPERS
	// ****************************************************************

	//	def ptest(msg : String)(t : ⇒ Unit) = test(msg) {
	//		println("Start: " + msg)
	//		try {
	//			t
	//		} catch {
	//			case t : Throwable ⇒ {
	//				println("End: " + msg)
	//				throw t
	//			}
	//		}
	//	}

	protected def assertGameTriggerCountForEventEquals(triggerCount : Int, event : GameEvent) = {
		val component = new EventTestComponent
		val scene = new GameScene(component)
		scene.addCamera(new Camera {
			def screenPosition : Vector = (0, 0)
			def screenSize : Vector = (0, 0)
			def zoom : Vector = (1, 1)
			def z = 0
			override def shoot(components : Seq[GameComponent], graphics : Graphics2D) {}
		})

		scene.pushEvent(event)
		scene.takeStep(null)
		assert(triggerCount + 1 === component.triggerCount)
	}
}