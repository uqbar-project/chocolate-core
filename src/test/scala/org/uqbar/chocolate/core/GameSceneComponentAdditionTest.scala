package org.uqbar.chocolate.core

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

import org.uqbar.chocolate.core.components.GameComponent

@SuppressWarnings(Array("unqualified-field-access"))
class GameSceneComponentAdditionTest extends FunSuite with BeforeAndAfter {
	var scene : GameScene = null

	// ****************************************************************
	// ** INITIALIZATIONS
	// ****************************************************************

	before{
		scene = new GameScene()

		addNewComponent(-1)
		addNewComponent(4)
		addNewComponent(4)
		addNewComponent(4)
		addNewComponent(10)
	}

	// ****************************************************************
	// ** TESTS
	// ****************************************************************

	test("initialOrderIsConsistent"){
		assert(-1 === scene.components(0).z.toInt)
		assert(4 === scene.components(1).z.toInt)
		assert(4 === scene.components(2).z.toInt)
		assert(4 === scene.components(3).z.toInt)
		assert(10 === scene.components(4).z.toInt)
	}

	test("nonIncludedMinZInsertsFirst"){assertComponentGetsAddedAtIndex(-10, 0)}

	test("includedMinZInsertsAfterEquals"){assertComponentGetsAddedAtIndex(-1, 1)}

	test("nonIncludedMediumZInsertsBetweenLowersAndHighers"){assertComponentGetsAddedAtIndex(2, 1)}

	test("includedMediumZInsertsAfterEquals"){assertComponentGetsAddedAtIndex(4, 4)}

	test("nonIncludedMaxZInsertsLast"){assertComponentGetsAddedAtIndex(15, 5)}

	test("includedMaxZInsertsLast"){assertComponentGetsAddedAtIndex(10, 5)}

	// ****************************************************************
	// ** TEST HELPERS
	// ****************************************************************

	protected def addNewComponent(z : Int) = {
		val component = new GameComponent() {};
		component.z = z
		scene addComponent component
		component
	}

	protected def assertComponentGetsAddedAtIndex(z : Int, index : Int) =
		assert(index === scene.components.indexOf(addNewComponent(z)))
}