package com.sankudo.nekomamod.block;

import com.sankudo.nekomamod.NekomaMod;
import com.sankudo.nekomamod.block.custom.WaterTorchBlock;
import com.sankudo.nekomamod.block.custom.WaterTorchWallBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.TorchBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public class ModBlocks {

    // water_torch
    public static final Identifier WATER_TORCH_BLOCK_ID = Identifier.of(NekomaMod.MOD_ID, "water_torch_block");
    public static final RegistryKey<Block> WATER_TORCH_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, WATER_TORCH_BLOCK_ID);
    // water_torch_wall
    public static final Identifier WATER_TORCH_WALL_BLOCK_ID = Identifier.of(NekomaMod.MOD_ID, "water_torch_wall");
    public static final RegistryKey<Block> WATER_TORCH_WALL_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, WATER_TORCH_WALL_BLOCK_ID);

    public static final Block WATER_TORCH_WALL_BLOCK = Registry.register(
            Registries.BLOCK,
            WATER_TORCH_WALL_BLOCK_ID,
            new WaterTorchWallBlock(AbstractBlock.Settings.create()
                    .registryKey(WATER_TORCH_WALL_BLOCK_KEY)
                    .strength(0.0F)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.WOOD)
                    .luminance(state -> state.get(Properties.WATERLOGGED) ? 12 : 0)
            )
    );


    public static final Block WATER_TORCH_BLOCK = Registry.register(
            Registries.BLOCK,
            WATER_TORCH_BLOCK_ID,
            new WaterTorchBlock(AbstractBlock.Settings.create()
                    .registryKey(WATER_TORCH_BLOCK_KEY)
                    .strength(0.0F)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.WOOD)
                    .luminance(state -> state.get(Properties.WATERLOGGED) ? 12 : 0)
            )
    );

    public static void registerModBlocks() {
        NekomaMod.LOGGER.info("Registering Mod Blocks for " + NekomaMod.MOD_ID);
    }
}
