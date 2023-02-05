package com.unrealdinnerbone.specfeatures;

import com.mojang.text2speech.Narrator;
import com.unrealdinnerbone.specfeatures.commands.NarrationCommand;
import com.unrealdinnerbone.specfeatures.commands.SpecFeaturesCommand;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.MessageSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SpecFeatures implements ClientModInitializer {

	public static final Narrator SPEC_NARRATOR = Narrator.getNarrator();
	public static final String MOD_ID = "testy";

	@Override
	public void onInitializeClient() {
		SpecFeaturesCommand.init();
		NarrationCommand.init();
		MessageSystem.ranSchedule();
		Features.FEATURES.stream().<ClientTickEvents.EndTick>map(feature -> client -> {
			while (feature.getKeyBinding().consumeClick()) {
				feature.toggle();
			}
		}).forEach(ClientTickEvents.END_CLIENT_TICK::register);
	}
}
