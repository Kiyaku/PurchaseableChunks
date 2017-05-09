package com.seriouscreeper.purchaseablechunks.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.seriouscreeper.purchaseablechunks.Reference;
import com.seriouscreeper.purchaseablechunks.utils.OwnedChunkData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;


public class ItemChunkClaimer extends Item {
    public ItemChunkClaimer() {
        setRegistryName("chunkclaimer");
        setUnlocalizedName(Reference.MOD_ID + ".chunkclaimer");
        GameRegistry.register(this);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(ChatFormatting.GREEN + "To use, stand inside an unclaimed chunk and use the item.");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(worldIn.isRemote) {
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
        }

        ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(playerIn.getPosition()).getChunkCoordIntPair();

        if(OwnedChunkData.get(worldIn).ClaimChunk(chunkPos, playerIn)) {
            OnChunkClaimed(chunkPos, worldIn, playerIn);
            playerIn.addChatComponentMessage(new TextComponentString("Chunk claimed!"));

            playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
        } else {
            playerIn.addChatComponentMessage(new TextComponentString("Can't claim this chunk!"));
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }


    public void OnChunkClaimed(ChunkPos chunk, World world, EntityPlayer player) {
    }
}
