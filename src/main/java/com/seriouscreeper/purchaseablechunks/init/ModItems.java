package com.seriouscreeper.purchaseablechunks.init;


import com.seriouscreeper.purchaseablechunks.items.ItemChunkClaimer;
import com.seriouscreeper.purchaseablechunks.items.ItemChunkExpander;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    public static ItemChunkExpander chunkExpander;
    public static ItemChunkClaimer chunkClaimer;

    public static void init() {
        chunkExpander = new ItemChunkExpander();
        chunkClaimer = new ItemChunkClaimer();
    }


    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(chunkExpander, 0);
        registerRender(chunkClaimer, 0);
    }


    private static void registerRender(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
