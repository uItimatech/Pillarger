package net.ultimatech.pillarger.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;

import net.ultimatech.pillarger.api.block.ConnectedLargePillarBlock;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;


//@Debug(export = true)
@Mixin(Blocks.class)
public class BlocksMixin {

    /*Inject(at = @At("HEAD"), method = "register(Ljava/lang/String;Lnet/minecraft/block/Block;)Lnet/minecraft/block/Block;", cancellable = true)
    private static void register(String id, Block block, CallbackInfoReturnable<Block> cir) {
        if (Objects.equals(id, "purpur_pillar")) {
            cir.setReturnValue(Registry.register(Registries.BLOCK, id, new ConnectedLargePillarBlock((AbstractBlock.Settings.create().mapColor(MapColor.MAGENTA).instrument(Instrument.BASEDRUM).requiresTool().strength(1.5f, 6.0f)))));
        } else if (Objects.equals(id, "quartz_pillar")) {
            cir.setReturnValue(Registry.register(Registries.BLOCK, id, new ConnectedLargePillarBlock((AbstractBlock.Settings.create().mapColor(MapColor.OFF_WHITE).instrument(Instrument.BASEDRUM).requiresTool().strength(0.8f)))));
        }
        cir.setReturnValue(Registry.register(Registries.BLOCK, id, block));
    }*/


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
