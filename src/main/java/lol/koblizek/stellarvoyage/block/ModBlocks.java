package lol.koblizek.stellarvoyage.block;

import lol.koblizek.stellarvoyage.StellarVoyage;
import lol.koblizek.stellarvoyage.item.ModItems;
import lol.koblizek.stellarvoyage.util.References;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks implements References {

    public static final Block BAUXITE_ORE = registerBlock("bauxite",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE)));
    public static final Block BLOOMERY_UNFIRED = registerBlock("bloomery_unfired",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block BLOOMERY_FIRED = registerBlock("bloomery_fired",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    private static Block registerBlock(String name, Block block ) {
        ModItems.ITEMS.add(registerBlockItem(name, block));
        return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name),
            new BlockItem(block,new FabricItemSettings()));
    }
    public static void registerModBlock() {

    }
}
