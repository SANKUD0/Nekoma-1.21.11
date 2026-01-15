package com.sankudo.nekomamod;

import com.sankudo.nekomamod.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NekomaMod implements ModInitializer {
    public static final String MOD_ID = "nekoma-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
	}
}