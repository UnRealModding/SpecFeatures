package com.unrealdinnerbone.specfeatures.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.unrealdinnerbone.specfeatures.features.Features;
import com.unrealdinnerbone.specfeatures.util.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity> {
    public LivingEntityRendererMixin(EntityRendererProvider.Context ctx,EntityModel<LivingEntity> model, float shadowRadius) {
        super(ctx);
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("RETURN"))
    public void renderHealth(LivingEntity livingEntity, float f, float g, PoseStack matrixStack, MultiBufferSource multiBufferSource, int light, CallbackInfo ci) {
        if (shouldRenderForEntity(livingEntity)) {
            matrixStack.pushPose();

            double d = this.entityRenderDispatcher.distanceToSqr(livingEntity);

            matrixStack.translate(0, livingEntity.getBbHeight() + 0.5f, 0);
            if (this.shouldShowName(livingEntity) && d <= 4096.0) {
                matrixStack.translate(0.0D, 9.0F * 1.15F * 0.025F, 0.0D);
                if (d < 100.0 && livingEntity.getLevel().getScoreboard().getDisplayObjective(2) != null) {
                    matrixStack.translate(0.0D, 9.0F * 1.15F * 0.025F, 0.0D);
                }
            }

            matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

            float pixelSize = 0.025F;
            matrixStack.scale(pixelSize, pixelSize, pixelSize);

            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder vertexConsumer = tessellator.getBuilder();

            vertexConsumer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
            RenderSystem.enableDepthTest();

            Matrix4f model = matrixStack.last().pose();

            int healthRed = Mth.ceil(livingEntity.getHealth());
            int maxHealth = Mth.ceil(livingEntity.getMaxHealth());
            int healthYellow = Mth.ceil(livingEntity.getAbsorptionAmount());


            int heartsRed = Mth.ceil(healthRed / 2.0f);
            boolean lastRedHalf = (healthRed & 1) == 1;
            int heartsNormal = Mth.ceil(maxHealth / 2.0f);
            int heartsYellow = Mth.ceil(healthYellow / 2.0f);
            boolean lastYellowHalf = (healthYellow & 1) == 1;
            int heartsTotal = heartsNormal + heartsYellow;

            int pixelsTotal = heartsTotal * 8 + 1;
            float maxX = pixelsTotal / 2.0f;
            for (int heart = 0; heart < heartsTotal; heart++){
                float x = maxX - heart * 8;
                drawHeart(model, vertexConsumer, x, 0);
                // Offset in the gui icons texture in hearts
                // 0 - empty, 2 - red, 8 - yellow, +1 for half
                int type;
                if (heart < heartsRed) {
                    type = 2 * 2;
                    if (heart == heartsRed - 1 && lastRedHalf) type += 1;
                } else if (heart < heartsNormal) {
                    type = 0;
                } else {
                    type = 8 * 2;
                    if(heart == heartsTotal - 1 && lastYellowHalf) type += 1;
                }
                if (type != 0) {
                    drawHeart(model, vertexConsumer, x, type);
                }
            }

            tessellator.end();

            matrixStack.popPose();
        }
    }

    private static boolean shouldRenderForEntity(Entity entity) {
        return Features.RENDER_HEALTH.isAllowed() && Features.RENDER_HEALTH.getList().contains(entity.getType()) && (!(entity instanceof AbstractClientPlayer player) || PlayerUtil.getGameMode(player).isSurvival());
    }

    private static void drawHeart(Matrix4f model, BufferBuilder vertexConsumer, float x, int type){
        float minU = 16F / 256F + type * 9F / 256F;
        float maxU = minU + 9F / 256F;
        float minV = 0;
        float maxV = minV + 9F / 256F;

        float heartSize = 9F;

        drawVertex(model, vertexConsumer, x, 0F - heartSize, 0F, minU, maxV);
        drawVertex(model, vertexConsumer, x - heartSize, 0F - heartSize, 0F, maxU, maxV);
        drawVertex(model, vertexConsumer, x - heartSize, 0F, 0F, maxU, minV);
        drawVertex(model, vertexConsumer, x, 0F, 0F, minU, minV);
    }

    private static void drawVertex(Matrix4f model, BufferBuilder vertices, float x, float y, float z, float u, float v) {
        vertices.vertex(model, x, y, z).uv(u, v).endVertex();
    }
}
