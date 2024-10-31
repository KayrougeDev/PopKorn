package fr.kayrouge.popkorn.mixin.client;

import fr.kayrouge.popkorn.client.manager.ClientPlayerManager;
import fr.kayrouge.popkorn.debug.AnimationTest;
import fr.kayrouge.popkorn.items.PKItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {

	@Shadow
	public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformationMode modelTransformationMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

	@Shadow
	protected abstract void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress);

	@Unique
	private int progress = 0;

	// Method use to render first person held item (or hand)
	@Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
	public void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if(ClientPlayerManager.getAbility("chainsaw").isUsed()) {
			progress++;
			ClientPlayerManager.getAbility("chainsaw").setUsed(false);
		}
		if(progress > 0) {
			matrices.push();

			Arm arm = player.getMainArm();
			boolean bl = arm == Arm.RIGHT;

			applyEquipOffset(matrices, arm, 0F);
			AnimationTest.test(matrices);
			renderItem(player, new ItemStack(() -> PKItems.CHAINSAW), bl ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND, !bl, matrices, vertexConsumers, light);
			matrices.pop();

			ci.cancel();
			progress++;
			if(progress == 101) {
				progress = 0;
			}
		}
	}

}
