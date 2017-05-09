package com.seriouscreeper.purchaseablechunks.events;

import com.seriouscreeper.purchaseablechunks.PurchaseableChunks;
import com.seriouscreeper.purchaseablechunks.utils.OwnedChunkData;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHandling {
    @SubscribeEvent
    public void onWordLoaded(WorldEvent.Load event) {
        OwnedChunkData.get(event.getWorld());
    }


    @SubscribeEvent
    public void onBlockPlace(BlockEvent.PlaceEvent event) {
        if(event.getPlayer() != null && !event.getPlayer().isCreative()) {
            Chunk c = event.getWorld().getChunkFromBlockCoords(event.getPos());

            if(!OwnedChunkData.get(event.getWorld()).OwnsChunk(c.getChunkCoordIntPair(), event.getPlayer())) {
                event.getPlayer().addChatComponentMessage(new TextComponentString("You can only break blocks in your claimed chunks!"));
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getPlayer() != null && !event.getPlayer().isCreative()) {
            Chunk c = event.getWorld().getChunkFromBlockCoords(event.getPos());

            if(!OwnedChunkData.get(event.getWorld()).OwnsChunk(c.getChunkCoordIntPair(), event.getPlayer())) {
                event.getPlayer().addChatComponentMessage(new TextComponentString("You can only break blocks in your claimed chunks!"));
                event.setCanceled(true);
            }
        }
    }
}
