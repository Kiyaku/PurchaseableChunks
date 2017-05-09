package com.seriouscreeper.purchaseablechunks.proxy;


import com.seriouscreeper.purchaseablechunks.init.ModItems;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        ModItems.init();
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
}
