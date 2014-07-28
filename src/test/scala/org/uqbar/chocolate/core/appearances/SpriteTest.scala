package org.uqbar.chocolate.core.appearances

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import javax.imageio.ImageIO
import org.uqbar.chocolate.core.loaders.ResourceLoader
import org.uqbar.chocolate.core.loaders.SimpleResourceLoader
import org.uqbar.chocolate.core.utils.Implicits._

import SpriteTest._
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

object SpriteTest {
	protected def dumpAllNeededSpriteVersions(Original: Sprite) = {
		val route = "./src/test/resources/images/"

		SpriteTest.dumpScaledSprite(Original, 0.25, 0.25, route)
		SpriteTest.dumpScaledSprite(Original, 0.33, 0.33, route)
		SpriteTest.dumpScaledSprite(Original, 1.25, 1.25, route)
		SpriteTest.dumpScaledSprite(Original, 5, 5, route)
		SpriteTest.dumpScaledSprite(Original, 1.25, 1, route)
		SpriteTest.dumpScaledSprite(Original, 1, 1.25, route)
		SpriteTest.dumpScaledSprite(Original, 2, 0.5, route)
		SpriteTest.dumpScaledSprite(Original, 0.5, 2, route)
	}

	protected def dumpScaledSprite(sprite: Sprite, scaleX: Double, scaleY: Double, route: String) = {
		val format = new DecimalFormat("000")
		val fileName = "scaled_" + format.format(scaleX * 100) + "_" + format.format(scaleY * 100) + ".png"

		dumpSprite(sprite.scale(scaleX, scaleY), route + fileName)
	}

	protected def dumpSprite(sprite: Sprite, filePath: String) = ImageIO.write(sprite.image, "png", new File(filePath))
}

class SpriteTest extends FunSuite with BeforeAndAfter {

	var Original: Sprite = null
	var scaled_025_025: Sprite = null
	var scaled_033_033: Sprite = null
	var scaled_125_125: Sprite = null
	var scaled_500_500: Sprite = null
	var scaled_125_100: Sprite = null
	var scaled_100_125: Sprite = null
	var scaled_200_050: Sprite = null
	var scaled_050_200: Sprite = null

	// ****************************************************************
	// ** INITIALIZATION
	// ****************************************************************

	before {
		val loader = new SimpleResourceLoader()

		Original = loader.loadSprite("/images/original.png")

		// dumpAllNeededSpriteVersions(Original)

		scaled_025_025 = loader.loadSprite("/images/scaled_025_025.png")
		scaled_033_033 = loader.loadSprite("/images/scaled_033_033.png")
		scaled_125_125 = loader.loadSprite("/images/scaled_125_125.png")
		scaled_500_500 = loader.loadSprite("/images/scaled_500_500.png")
		scaled_125_100 = loader.loadSprite("/images/scaled_125_100.png")
		scaled_100_125 = loader.loadSprite("/images/scaled_100_125.png")
		scaled_200_050 = loader.loadSprite("/images/scaled_200_050.png")
		scaled_050_200 = loader.loadSprite("/images/scaled_050_200.png")
	}

	// ****************************************************************
	// ** TEST CASES
	// ****************************************************************

	test("synonims 1") { assertSpriteMatches(Original, Original.clone.scaleBy(1)) }
	test("synonims 2") { assertSpriteMatches(Original.clone.scaleBy(2), Original.clone.scale(2, 2)) }
	test("synonims 3") { assertSpriteMatches(Original.clone.scale(2)(), Original.clone.scale(2, 1)) }
	test("synonims 4") { assertSpriteMatches(Original.clone.scale()(2), Original.clone.scale(1, 2)) }

	test("scale is right 1") { assertSpriteMatches(scaled_025_025, Original.scaleBy(0.25)) }
	test("scale is right 2") { assertSpriteMatches(scaled_033_033, Original.scaleBy(0.33)) }
	test("scale is right 3") { assertSpriteMatches(scaled_500_500, Original.scaleBy(5)) }
	test("scale is right 4") { assertSpriteMatches(scaled_125_100, Original.scaleHorizontallyTo(320, false)) }
	test("scale is right 5") { assertSpriteMatches(scaled_125_125, Original.scaleHorizontallyTo(320, true)) }
	test("scale is right 6") { assertSpriteMatches(scaled_100_125, Original.scaleVerticallyTo(320, false)) }
	test("scale is right 7") { assertSpriteMatches(scaled_125_125, Original.scaleVerticallyTo(320, true)) }
	test("scale is right 8") { assertSpriteMatches(scaled_200_050, Original.scale(2, 0.5)) }
	test("scale is right 9") { assertSpriteMatches(scaled_050_200, Original.scale(0.5, 2)) }

	// ****************************************************************
	// ** ASSERTS
	// ****************************************************************

	protected def assertSpriteMatches(expected: Sprite, actual: Sprite) = {
		if (expected.width != actual.width || expected.height != actual.height)
			fail(s"dimensions doesn't match: ${(expected.width, expected.height)} != ${(actual.width, actual.height)}")

		val expectedImage = expected.image
		val actualImage = actual.image
		for {
			x ← 0 until expected.width
			y ← 0 until expected.height
		} assert(expectedImage.getRGB(x, y) === actualImage.getRGB(x, y))
	}
}