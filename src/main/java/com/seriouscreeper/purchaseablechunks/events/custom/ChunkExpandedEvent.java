package com.seriouscreeper.purchaseablechunks.events.custom;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;


public class ChunkExpandedEvent extends Event {
    private final World world;
    private final ChunkPos pos;
    private final EntityPlayer claimer;
    private final NBTTagCompound nbt;

    public ChunkExpandedEvent(World world, ChunkPos pos, EntityPlayer claimer, NBTTagCompound nbt) {
        this.world = world;
        this.pos = pos;
        this.claimer = claimer;
        this.nbt = nbt;
    }


    public World getWorld() { return world; }
    public ChunkPos getChunkPos() {
        return pos;
    }
    public EntityPlayer getClaimer(){
        return claimer;
    }
    public NBTTagCompound getNBT() { return nbt; }
}
