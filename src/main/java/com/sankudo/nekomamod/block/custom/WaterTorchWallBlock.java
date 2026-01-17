package com.sankudo.nekomamod.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;

public class WaterTorchWallBlock extends WallTorchBlock implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WaterTorchWallBlock(Settings settings) {
        super(ParticleTypes.GLOW, settings.pistonBehavior(PistonBehavior.DESTROY));
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState base = super.getPlacementState(ctx);
        if (base == null) return null;

        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return base.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return super.getPickStack(world, pos, state, includeData);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    // particle
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WATERLOGGED)) return;

        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.75;
        double z = pos.getZ() + 0.5;

        x += (random.nextDouble() - 0.5) * 0.05;
        z += (random.nextDouble() - 0.5) * 0.05;

        if (random.nextInt(2) == 0) {
            world.addParticleClient(ParticleTypes.GLOW, x, y, z, 0.0, 0.01, 0.0);
        }

        if (random.nextInt(20) == 0) {
            world.addParticleClient(ParticleTypes.BUBBLE, x, y - 0.05, z, 0.0, 0.02, 0.0);
        }
    }
}
