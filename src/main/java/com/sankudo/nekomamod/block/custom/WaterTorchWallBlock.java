package com.sankudo.nekomamod.block.custom;

import net.minecraft.block.*;
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
        super(ParticleTypes.FLAME, settings);
        this.setDefaultState(this.getDefaultState()
                .with(WATERLOGGED, false)
                .with(FACING, Direction.NORTH));
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

    // ✅ support “mur” : bloc derrière la torche
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos supportPos = pos.offset(facing.getOpposite());
        return Block.sideCoversSmallSquare(world, supportPos, facing);
    }

    // ✅ drop si le support casse
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, WireOrientation wireOrientation, boolean notify) {
        if (!this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
            return;
        }
        super.neighborUpdate(state, world, pos, block, wireOrientation, notify);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return super.getPickStack(world, pos, state, includeData);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    // mêmes particules glow squid “pur”
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WATERLOGGED)) return;
        if (random.nextInt(6) != 0) return;

        double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.25;
        double y = pos.getY() + 0.65 + (random.nextDouble() - 0.5) * 0.25;
        double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.25;

        world.addParticleClient(ParticleTypes.GLOW, x, y, z, 0.0, 0.01, 0.0);
    }
}
