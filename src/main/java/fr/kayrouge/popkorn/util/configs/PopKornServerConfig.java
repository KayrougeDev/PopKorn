package fr.kayrouge.popkorn.util.configs;

import fr.kayrouge.popkorn.PopKorn;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.quiltmc.config.api.ReflectiveConfig;
import org.quiltmc.config.api.annotations.SerializedName;
import org.quiltmc.config.api.values.TrackedValue;
import org.quiltmc.loader.api.config.v2.QuiltConfig;

public class PopKornServerConfig extends ReflectiveConfig {

	public static final PopKornServerConfig INSTANCE = QuiltConfig.create(PopKorn.MODID, "server", PopKornServerConfig.class);

	@SerializedName("elevator_max_distance")
	public final TrackedValue<Integer> elevatorMaxDistance = this.value(0);

	public Screen init(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Text.translatable("title.popkorn.config.server"));

		ConfigCategory block = builder.getOrCreateCategory(Text.translatable("category.popkorn.block"));
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		block.addEntry(entryBuilder.startIntField(Text.translatable("option.popkorn.elevatormaxdistance"), elevatorMaxDistance.value())
			.setDefaultValue(this.elevatorMaxDistance.getDefaultValue())
			.setTooltip(Text.translatable("option.popkorn.elevatormaxdistance.tooltip"))
			.setSaveConsumer(elevatorMaxDistance::setValue)
			.build());

		return builder.build();
	}
}
