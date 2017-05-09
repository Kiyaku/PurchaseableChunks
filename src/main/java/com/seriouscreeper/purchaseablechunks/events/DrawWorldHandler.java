package com.seriouscreeper.purchaseablechunks.events;

import com.seriouscreeper.purchaseablechunks.Reference;
import com.seriouscreeper.purchaseablechunks.items.ItemChunkClaimer;
import com.seriouscreeper.purchaseablechunks.items.ItemChunkExpander;
import com.seriouscreeper.purchaseablechunks.utils.OwnedChunkData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;


public class DrawWorldHandler {
    public static final ResourceLocation TEXTURE_RANGE = new ResourceLocation(Reference.MOD_ID, "textures/chunkregion.png");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDrawWorld(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();

        if(mc.thePlayer.getHeldItemMainhand() != null && (mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemChunkExpander || mc.thePlayer.getHeldItemMainhand().getItem() instanceof ItemChunkClaimer)) {
            float alpha = 0.4f;

            Entity renderEntity = mc.getRenderViewEntity();
            ChunkPos chunkPos = mc.theWorld.getChunkFromBlockCoords(renderEntity.getPosition()).getChunkCoordIntPair();
            double entityX = renderEntity.lastTickPosX + (renderEntity.posX - renderEntity.lastTickPosX) * (double) event.getPartialTicks();
            double entityY = renderEntity.lastTickPosY + (renderEntity.posY - renderEntity.lastTickPosY) * (double) event.getPartialTicks();
            double entityZ = renderEntity.lastTickPosZ + (renderEntity.posZ - renderEntity.lastTickPosZ) * (double) event.getPartialTicks();

            GlStateManager.alphaFunc(516, 0.01F);
            mc.renderEngine.bindTexture(TEXTURE_RANGE);

            for(int x = -2; x <= 2; x++) {
                for(int z = -2; z <= 2; z++) {
                    ChunkPos newPos = new ChunkPos(chunkPos.chunkXPos + x, chunkPos.chunkZPos + z);

                    GlStateManager.pushMatrix();
                    GlStateManager.translate(-entityX, -entityY, -entityZ);
                    GlStateManager.translate(newPos.chunkXPos * 16, 0, newPos.chunkZPos * 16);
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.disableLighting();
                    GlStateManager.disableCull();
                    GlStateManager.depthMask(true);
                    GlStateManager.enableTexture2D();

                    if(OwnedChunkData.get(mc.theWorld).BelongsToSomeoneElse(newPos, mc.thePlayer)) {
                        GlStateManager.color(1.0f, 0.0f, 0.0f, alpha);
                    } else if(OwnedChunkData.get(mc.theWorld).OwnsChunk(newPos, mc.thePlayer)) {
                        GlStateManager.color(0.0f, 1.0f, 0.0f, alpha);
                    } else {
                        GlStateManager.color(1.0f, 0.5f, 0.0f, alpha);
                    }

                    renderGrid(entityY);
                    GlStateManager.depthMask(false);

                    GlStateManager.popMatrix();
                }
            }
        }
    }


    public static void renderGrid(double height)
    {
        VertexBuffer renderer = Tessellator.getInstance().getBuffer();

        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        BlockPos pos1 = new BlockPos(16, height, 16);
        BlockPos pos2 = new BlockPos(0, height, 0);

        float offset = 0.02f;

        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0001f);

        renderer.pos(-pos2.getX(), pos1.getY() + offset, -pos2.getZ()).tex(0, 0).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY() + offset, pos1.getZ()).tex(0, 16).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + offset, pos1.getZ()).tex(16, 16).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + offset, -pos2.getZ()).tex(16, 0).endVertex();

        Tessellator.getInstance().draw();

        GlStateManager.disableTexture2D();
        renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_TEX);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1);

        renderer.pos(-pos2.getX(), pos1.getY() + offset, -pos2.getZ()).tex(0, 0).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY() + offset, pos1.getZ()).tex(0, 1).endVertex();

        renderer.pos(-pos2.getX(), pos1.getY() + offset, pos1.getZ()).tex(0, 1).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + offset, pos1.getZ()).tex(1, 1).endVertex();

        renderer.pos(pos1.getX(), pos1.getY() + offset, pos1.getZ()).tex(1, 1).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + offset, -pos2.getZ()).tex(1, 0).endVertex();

        renderer.pos(pos1.getX(), pos1.getY() + offset, -pos2.getZ()).tex(1, 0).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY() + offset, -pos2.getZ()).tex(0, 0).endVertex();

        Tessellator.getInstance().draw();

        renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_TEX);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1);

        renderer.pos(-pos2.getX(), pos1.getY(), -pos2.getZ()).tex(0, 0).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY() + 250, -pos2.getZ()).tex(0, 0).endVertex();

        renderer.pos(-pos2.getX(), pos1.getY(), pos1.getZ()).tex(0, 1).endVertex();
        renderer.pos(-pos2.getX(), pos1.getY() + 250 , pos1.getZ()).tex(0, 1).endVertex();

        renderer.pos(pos1.getX(), pos1.getY(), pos1.getZ()).tex(1, 1).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + 250, pos1.getZ()).tex(1, 1).endVertex();

        renderer.pos(pos1.getX(), pos1.getY(), -pos2.getZ()).tex(1, 0).endVertex();
        renderer.pos(pos1.getX(), pos1.getY() + 250, -pos2.getZ()).tex(1, 0).endVertex();

        Tessellator.getInstance().draw();

        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.002f);
        GlStateManager.disableBlend();
    }
}
