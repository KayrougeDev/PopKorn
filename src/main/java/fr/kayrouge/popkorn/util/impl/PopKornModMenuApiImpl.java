package fr.kayrouge.popkorn.util.impl;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import fr.kayrouge.popkorn.client.screen.ConfigScreen;
import fr.kayrouge.popkorn.util.configs.PopKornClientConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class PopKornModMenuApiImpl implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return ConfigScreen::new;
	}
}
