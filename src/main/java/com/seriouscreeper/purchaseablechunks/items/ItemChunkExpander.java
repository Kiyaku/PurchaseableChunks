package com.seriouscreeper.purchaseablechunks.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.seriouscreeper.purchaseablechunks.Reference;
import com.seriouscreeper.purchaseablechunks.events.custom.ChunkExpandedEvent;
import com.seriouscreeper.purchaseablechunks.utils.OwnedChunkData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;


public class ItemChunkExpander extends Item {
    public ItemChunkExpander() {
        setRegistryName("chunkexpander");
        setUnlocalizedName(Reference.MOD_ID + ".chunkexpander");
        GameRegistry.register(this);
        setCreativeTab(CreativeTabs.MISC);
    }


    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(ChatFormatting.GREEN + "To use, stand inside a claimed chunk and face the chunk you want to claim next.");
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(worldIn.isRemote) {
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
        }

        if(hand != EnumHand.MAIN_HAND)
            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);

        EnumFacing direction = playerIn.getHorizontalFacing();
        ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(playerIn.getPosition()).getChunkCoordIntPair();

        if(OwnedChunkData.get(worldIn).OwnsChunk(chunkPos, playerIn)) {
            ChunkPos newPos = new ChunkPos(chunkPos.chunkXPos + direction.getFrontOffsetX(), chunkPos.chunkZPos + direction.getFrontOffsetZ());

            if(OwnedChunkData.get(worldIn).ClaimChunk(newPos, playerIn)) {
                playerIn.addChatComponentMessage(new TextComponentString("Chunk claimed!"));
                OnChunkClaimed(newPos, worldIn, playerIn, itemStackIn.getTagCompound());

                playerIn.inventory.decrStackSize(playerIn.inventory.currentItem, 1);
            } else {
                playerIn.addChatComponentMessage(new TextComponentString("Can't claim this chunk!"));
            }
        } else {
            playerIn.addChatComponentMessage(new TextComponentString("You already own this chunk!"));
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }


    public void OnChunkClaimed(ChunkPos chunk, World world, EntityPlayer player, NBTTagCompound nbt) {
        ChunkExpandedEvent event = new ChunkExpandedEvent(world, chunk, player, nbt);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
