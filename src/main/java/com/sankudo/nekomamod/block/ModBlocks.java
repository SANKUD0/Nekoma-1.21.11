package com.sankudo.nekomamod.block;

import com.sankudo.nekomamod.NekomaMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Identifier WATER_TORCH_BLOCK_ID = Identifier.of(NekomaMod.MOD_ID, "water_torch_block");
    public static final RegistryKey<Block> WATER_TORCH_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, WATER_TORCH_BLOCK_ID);

    public static final Block WATER_TORCH_BLOCK = Registry.register(
            Registries.BLOCK,
            WATER_TORCH_BLOCK_ID,
            new Block(AbstractBlock.Settings.create()
                    .registryKey(WATER_TORCH_BLOCK_KEY)
                    .strength(1.0F)
                    .sounds(BlockSoundGroup.LANTERN))
    );

    public static void registerModBlocks() {
        NekomaMod.LOGGER.info("Registering Mod Blocks for " + NekomaMod.MOD_ID);
    }
}
