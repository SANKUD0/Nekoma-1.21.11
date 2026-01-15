package com.sankudo.nekomamod.item;

import com.sankudo.nekomamod.NekomaMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    // water-torch
    public static final Identifier WATER_TORCH_ID = Identifier.of(NekomaMod.MOD_ID, "water_torch");
    public static final RegistryKey<Item> WATER_TORCH_KEY = RegistryKey.of(RegistryKeys.ITEM, WATER_TORCH_ID);

    public static final Item WATER_TORCH = registerItem(WATER_TORCH_ID, new Item(new Item.Settings().registryKey(WATER_TORCH_KEY)));

    private static Item registerItem(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void registerModItems() {
        NekomaMod.LOGGER.info("Registering Mod items for " + NekomaMod.MOD_ID);
        NekomaMod.LOGGER.info("WATER_TORCH real id = " + Registries.ITEM.getId(WATER_TORCH));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(WATER_TORCH);
        });
    }
}
