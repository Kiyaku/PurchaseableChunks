package com.seriouscreeper.purchaseablechunks;

import com.seriouscreeper.purchaseablechunks.events.BlockHandling;
import com.seriouscreeper.purchaseablechunks.events.DrawWorldHandler;
import com.seriouscreeper.purchaseablechunks.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.ArrayList;
import java.util.List;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class PurchaseableChunks {
    @Mod.Instance
    public static PurchaseableChunks instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        MinecraftForge.EVENT_BUS.register(new BlockHandling());
        MinecraftForge.EVENT_BUS.register(new DrawWorldHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
