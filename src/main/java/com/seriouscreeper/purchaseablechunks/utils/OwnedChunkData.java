package com.seriouscreeper.purchaseablechunks.utils;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OwnedChunkData extends WorldSavedData {
    private static final String DATA_NAME = "purchaseablechunks_data";
    public static Map<ChunkPos, UUID> ownedChunks = new HashMap<ChunkPos, UUID>();


    public OwnedChunkData() {
        super(DATA_NAME);
    }


    public OwnedChunkData(String s) {
        super(s);
    }


    public static OwnedChunkData get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        OwnedChunkData instance = (OwnedChunkData) storage.getOrLoadData(OwnedChunkData.class, DATA_NAME);

        if (instance == null) {
            instance = new OwnedChunkData();
            storage.setData(DATA_NAME, instance);
        }

        return instance;
    }


    public boolean OwnsChunk(ChunkPos pos, EntityPlayer player) {
        for(ChunkPos c : ownedChunks.keySet()) {
            if(c.equals(pos) && ownedChunks.get(c).equals(player.getUniqueID())) {
                return true;
            }
        }

        return false;
    }


    public boolean BelongsToSomeoneElse(ChunkPos pos, EntityPlayer player) {
        for(ChunkPos c : ownedChunks.keySet()) {
            if(c.equals(pos) && !ownedChunks.get(c).equals(player.getUniqueID())) {
                return true;
            }
        }

        return false;
    }


    public boolean ClaimChunk(ChunkPos pos, EntityPlayer player) {
        if(ownedChunks.containsKey(pos)) {
            return false;
        } else {
            ownedChunks.put(pos, player.getUniqueID());
            markDirty();
            return true;
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagCompound chunks = nbt.getCompoundTag("ownedChunks");

        for(String pos : chunks.getKeySet()) {
            ownedChunks.put(stringToChunkPos(pos), UUID.fromString(chunks.getString(pos)));
        }
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound chunks = new NBTTagCompound();

        for(ChunkPos pos : ownedChunks.keySet()) {
            chunks.setString(pos.toString(), ownedChunks.get(pos).toString());
        }

        nbt.setTag("ownedChunks", chunks);

        return nbt;
    }


    private ChunkPos stringToChunkPos(String pos) {
        pos = pos.substring(1, pos.length() - 1);
        String[] splitPos = pos.split(", ");

        if(splitPos.length == 2) {
            return new ChunkPos(Integer.parseInt(splitPos[0]), Integer.parseInt(splitPos[1]));
        }

        return null;
    }
}
