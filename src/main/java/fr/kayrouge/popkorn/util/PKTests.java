package fr.kayrouge.popkorn.util;

import net.minecraft.block.Blocks;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class PKTests {

	@GameTest(structureName = "popkorn:powder_snow_dispenser")
	public void powderSnowDispenserTest(TestContext context) {
		context.pushButton(1, 2, 0);
		context.succeedWhenBlockExists(Blocks.POWDER_SNOW, 1, 3, 1);
	}

}
