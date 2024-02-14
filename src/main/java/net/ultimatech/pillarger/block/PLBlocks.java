package net.ultimatech.pillarger.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ultimatech.pillarger.Pillarger;
import net.ultimatech.pillarger.api.block.ConnectedLargePillarBlock;

public class PLBlocks {

    /*
    public static final Block PL_PURPUR_PILLAR = overrideBlock(Blocks.PURPUR_PILLAR, new ConnectedLargePillarBlock(FabricBlockSettings.copyOf(Blocks.PURPUR_PILLAR)));
    public static final Block PL_QUARTZ_PILLAR = overrideBlock(Blocks.QUARTZ_PILLAR, new ConnectedLargePillarBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_PILLAR)));
     */

    /*
    private static Item overrideBlockItem(BlockItem toOverride, BlockItem newItem){
        return Registry.register(Registries.ITEM, Registries.ITEM.getId(toOverride).getPath() ,newItem);
    }

    private static Block overrideBlock(Block toOverride, Block newBlock){
        BlockItem newBlockItem = new BlockItem(newBlock, new FabricItemSettings());
        overrideBlockItem((BlockItem) toOverride.asItem(), newBlockItem);

        return Registry.register(Registries.BLOCK, Registries.BLOCK.getId(toOverride).getPath(),newBlock);
    }
    */
    public static void registerModBlocks() {
        Pillarger.LOGGER.info("Registering mod blocks for " + Pillarger.MOD_ID);
    }
}
