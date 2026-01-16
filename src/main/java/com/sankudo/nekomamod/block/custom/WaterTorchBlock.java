package com.sankudo.nekomamod.block.custom;

import com.sankudo.nekomamod.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import org.jetbrains.annotations.Nullable;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.random.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;



public class WaterTorchBlock extends TorchBlock implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WaterTorchBlock(Settings settings) {
        super(ParticleTypes.GLOW, settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    // Torche vanilla-like (centre), unités sur 16
    private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(
            6.0, 0.0, 6.0,
            10.0, 10.0, 10.0
    );

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

    // Collision vide
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty(); // comme une torche: pas de collision
    }

    // particules
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(WATERLOGGED)) return;

        // position de la "flamme" (sommet de la torche)
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.75;
        double z = pos.getZ() + 0.5;

        // petit jitter comme vanilla (très léger)
        x += (random.nextDouble() - 0.5) * 0.05;
        z += (random.nextDouble() - 0.5) * 0.05;

        // Glow plus rare (pour éviter le spam)
        if (random.nextInt(2) == 0) {
            world.addParticleClient(ParticleTypes.GLOW, x, y, z, 0.0, 0.01, 0.0);
        }

        // (optionnel) mini bulles très rares
        if (random.nextInt(20) == 0) {
            world.addParticleClient(ParticleTypes.BUBBLE, x, y - 0.05, z, 0.0, 0.02, 0.0);
        }
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx){
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    // Interdit de placer une water torch par-dessus une autre
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.down());
        // Si dessous = la même water torch => interdit
        if (below.isOf(this)) return false;

        // Sinon: comportement standard "doit être supporté"
        return Block.sideCoversSmallSquare(world, pos.down(), Direction.UP);
    }

    // Drop si le support case
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, WireOrientation wireOrientation, boolean notify) {
        if (!this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true); // ✅ true = drop
            return;
        }
        super.neighborUpdate(state, world, pos, block, wireOrientation, notify);
    }

    @Override
    protected ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
        return super.getPickStack(world, pos, state, includeData);
    }
}
