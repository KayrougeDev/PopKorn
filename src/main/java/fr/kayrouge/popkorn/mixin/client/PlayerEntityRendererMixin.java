package fr.kayrouge.popkorn.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

	@Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("TAIL"))
	public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int i, CallbackInfo ci) {
			//renderLine(matrices, vertexConsumers, renderLayer);
		}

	@Unique
	private void renderLine(MatrixStack matrices, VertexConsumerProvider vertexConsumers, RenderLayer renderLayer) {
		matrices.push();

		double x = 0d;
		double y = 0d;
		double z = 0d;

		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		MatrixStack.Entry entry = matrices.peek();

		VertexConsumer provider = vertexConsumers.getBuffer(renderLayer);

		float n1 = (float) (x - x);
		float n2 = (float) (y - y+2f);
		float n3 = (float) (z - z);

		float t = (float)Math.sqrt(n1 * n1 + n2 * n2 + n3 * n3);
		n1 /= t;
		n2 /= t;
		n3 /= t;

		provider.xyz(entry, (float) x, (float) y, (float) z)
			.color(1.0F, 0.0F, 0.0F, 1.0F)
			.normal(entry, n1, n2, n3);

		provider.xyz(entry, (float) x, (float) y+2f, (float) z)
			.color(1.0F, 0.0F, 0.0F, 1.0F)
			.normal(entry, n1, n2, n3);

		RenderSystem.enableDepthTest();
		RenderSystem.disableBlend();
		matrices.pop();
	}

	@Unique
	private void renderModelWireframe(MatrixStack matrices, VertexConsumerProvider vertexConsumers, LivingEntity entity, int light) {
		// Récupérer le modèle (EntityModel) lié à l'entité
		EntityRenderer<?> renderer = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity);
		if (!(renderer instanceof LivingEntityRenderer<?, ?> livingRenderer)) {
			return;
		}

		EntityModel<?> model = livingRenderer.getModel();

		// Utiliser un RenderLayer pour dessiner les lignes
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());

		// Dessiner le modèle en wireframe
		matrices.push();

		// Optionnel : ajuster les transformations si nécessaire
		model.method_2828(matrices, vertexConsumer, 1, 1, 654311423);

		matrices.pop();
	}

}
