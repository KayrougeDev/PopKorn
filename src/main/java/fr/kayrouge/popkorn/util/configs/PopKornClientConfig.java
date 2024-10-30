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

public class PopKornClientConfig extends ReflectiveConfig {

	public static final PopKornClientConfig INSTANCE = QuiltConfig.create(PopKorn.MODID, "client", PopKornClientConfig.class);

	@SerializedName("draw_unable_to_recover_abilities_data_string")
	public final TrackedValue<Boolean> drawUnableToRecoverAbilitiesDataString = this.value(true);

	public Screen init(Screen parent) {
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Text.translatable("title.popkorn.config"));

		ConfigCategory hud = builder.getOrCreateCategory(Text.translatable("category.popkorn.hud"));
		ConfigEntryBuilder entryBuilder = builder.entryBuilder();

		hud.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.popkorn.warncantquerryabilitiesdata"), drawUnableToRecoverAbilitiesDataString.value())
			.setDefaultValue(this.drawUnableToRecoverAbilitiesDataString.getDefaultValue())
			.setTooltip(Text.translatable("option.popkorn.warncantquerryabilitiesdata.tooltip"))
			.setSaveConsumer(drawUnableToRecoverAbilitiesDataString::setValue)
			.build());

		return builder.build();
	}
}
