package net.ultimatech.pillarger.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Arrays;

public class ConnectedLargePillarBlock extends PillarBlock {

    public static final BooleanProperty UPDATED = BooleanProperty.of("updated");
    public static final IntProperty VARIANT = IntProperty.of("variant", 0, 4);
    // Variant is used to determine which texture to use
    // 0 -> default unconnected
    // 1-4 -> connected to the 2x2 pattern

    Vec3i[][][] offsets = {
            {
                    {new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(1, 0, 1)},
                    {new Vec3i(0, 0, 1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, 1)},
                    {new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(-1, 0, -1)},
                    {new Vec3i(0, 0, -1), new Vec3i(1, 0, 0), new Vec3i(1, 0, -1)},
            },
            {
                    {new Vec3i(0, 1, 0), new Vec3i(0, 0, 1), new Vec3i(0, 1, 1)},
                    {new Vec3i(0, 1, 0), new Vec3i(0, 0, -1), new Vec3i(0, 1, -1)},
                    {new Vec3i(0, -1, 0), new Vec3i(0, 0, -1), new Vec3i(0, -1, -1)},
                    {new Vec3i(0, -1, 0), new Vec3i(0, 0, 1), new Vec3i(0, -1, 1)},
            },
            {
                    {new Vec3i(0, 1, 0), new Vec3i(1, 0, 0), new Vec3i(1, 1, 0)},
                    {new Vec3i(0, 1, 0), new Vec3i(-1, 0, 0), new Vec3i(-1, 1, 0)},
                    {new Vec3i(0, -1, 0), new Vec3i(-1, 0, 0), new Vec3i(-1, -1, 0)},
                    {new Vec3i(0, -1, 0), new Vec3i(1, 0, 0), new Vec3i(1, -1, 0)},
            }
    };

    Direction.Axis[] axis = {
            Direction.Axis.Y,
            Direction.Axis.X,
            Direction.Axis.Z
    };

    public ConnectedLargePillarBlock(Settings properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState()
                .with(AXIS, Direction.Axis.Y)
                .with(VARIANT, 0)
                .with(UPDATED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS, VARIANT, UPDATED);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isClient || state.get(UPDATED)) return;
        world.setBlockState(pos, getStateAndUpdate(world, pos, state, null));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (world.isClient || state.get(UPDATED)) return;
        world.scheduleBlockTick(pos, this, 0);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        LivingEntity placer = context.getPlayer();

        BlockState state = this.getDefaultState()
                .with(AXIS, context.getSide().getAxis());

        return this.getStateAndUpdate(world, pos, state, placer);
    }

    public BlockState getStateAndUpdate(World world, BlockPos pos, BlockState state, LivingEntity placer) {

        if (world.isClient) {
            return state;
        }

        boolean hasSound;

        hasSound = placer != null;
        if (hasSound  && placer.isSneaking()) return state.with(UPDATED, true);


        for (int axis = 0; axis < 3; axis++) {
            if (state.get(AXIS) == this.axis[axis]) {
                for (int connectionDirection = 0; connectionDirection < 4; connectionDirection++) {
                    BlockPos[] poses = {
                            newBlockPose(pos, offsets[axis][connectionDirection][0]),
                            newBlockPose(pos, offsets[axis][connectionDirection][1]),
                            newBlockPose(pos, offsets[axis][connectionDirection][2]),
                    };

                    BlockState[] states = {
                            world.getBlockState(poses[0]),
                            world.getBlockState(poses[1]),
                            world.getBlockState(poses[2]),
                    };

                    int[] neighborVariants = {
                            4 - connectionDirection,
                            connectionDirection % 2 == 0 ? connectionDirection + 2 : connectionDirection,
                            connectionDirection + 3 > 4 ? connectionDirection - 1 : connectionDirection + 3,
                    };

                    int finalAxis = axis;
                    if (Arrays.stream(states).allMatch(s -> s.getBlock() == this && s.get(AXIS) == this.axis[finalAxis])) {
                        // Sets the neighboring blocks to their new variant
                        for (int j = 0; j < 3; j++) {
                            world.setBlockState(poses[j], states[j].with(VARIANT, neighborVariants[j]).with(UPDATED, true));
                        }
                        if (hasSound && state.get(VARIANT) != 0) world.playSound(null, pos, this.getSoundGroup(state).getBreakSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return state.with(VARIANT, connectionDirection + 1).with(UPDATED, true);
                    }
                }
            }
        }

        return state.with(UPDATED, true);
    }

    public BlockPos newBlockPose(BlockPos pos, Vec3i vector) {
        return new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY(), pos.getZ() + vector.getZ());
    }
}