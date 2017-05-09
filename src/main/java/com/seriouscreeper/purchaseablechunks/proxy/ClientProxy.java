package com.seriouscreeper.purchaseablechunks.proxy;

import com.seriouscreeper.purchaseablechunks.init.ModItems;
import com.seriouscreeper.purchaseablechunks.utils.OwnedChunkData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        ModItems.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent e) {
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
