package net.ultimatech.pillarger.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

import net.ultimatech.pillarger.api.block.ConnectedLargePillarBlock;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;


@Debug(export = true)
@Mixin(Blocks.class)
public class BlocksMixin {

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=quartz_pillar"}, ordinal = 0)), at = @At(value = "NEW", target = "net/minecraft/block/PillarBlock", ordinal = 0))
    private static PillarBlock QuartzPillarRedirect(AbstractBlock.Settings settings) {
        return new ConnectedLargePillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(Instrument.BASEDRUM).requiresTool().strength(0.8F));
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = {"stringValue=purpur_pillar"}, ordinal = 0)), at = @At(value = "NEW", target = "net/minecraft/block/PillarBlock", ordinal = 0))
    private static PillarBlock PurpurPillarRedirect(AbstractBlock.Settings settings) {
        return new ConnectedLargePillarBlock(AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(Instrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F));
    }

    /**
     * @author ultimatech - pillarger mod
     * @reason log blocks now allow for connected textures
     */
    @Overwrite
    public static Block createLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new ConnectedLargePillarBlock(
                AbstractBlock.Settings.create()
                        .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                        .instrument(Instrument.BASS)
                        .strength(2.0F)
                        .sounds(BlockSoundGroup.WOOD)
                        .burnable()
        );
    }

    /**
     * @author ultimatech - pillarger mod
     * @reason log blocks now allow for connected textures
     */
    @Overwrite
    public static Block createLogBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup) {
        return new ConnectedLargePillarBlock(
                AbstractBlock.Settings.create()
                        .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
                        .instrument(Instrument.BASS)
                        .strength(2.0F)
                        .sounds(soundGroup)
                        .burnable()
        );
    }

    /**
     * @author ultimatech - pillarger mod
     * @reason log blocks now allow for connected textures
     */
    @Overwrite
    public static Block createNetherStemBlock(MapColor mapColor) {
        return new ConnectedLargePillarBlock(
                AbstractBlock.Settings.create().mapColor(state -> mapColor).instrument(Instrument.BASS).strength(2.0F).sounds(BlockSoundGroup.NETHER_STEM)
        );
    }
}
