package io.github.tropheusj.its_as_shrimple_as_that.entity.render;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;

import io.github.tropheusj.its_as_shrimple_as_that.ItsAsShrimpleAsThat;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ShrimpModel<T extends LivingEntityRenderState> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ItsAsShrimpleAsThat.id("shrimp"), "main");

	private final KeyframeAnimation walkAnimation;

	public ShrimpModel(EntityRendererProvider.Context ctx) {
		super(ctx.bakeLayer(LAYER_LOCATION));
		this.walkAnimation = ShrimpMoveAnimation.WALK.bake(this.root());
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mid_segment = partdefinition.addOrReplaceChild("mid_segment", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -2.5F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 23.0F, -2.5F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition r3 = mid_segment.addOrReplaceChild("r3", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, -2.0F));

		PartDefinition r2 = mid_segment.addOrReplaceChild("r2", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, -0.5F));

		PartDefinition r1 = mid_segment.addOrReplaceChild("r1", CubeListBuilder.create().texOffs(0, 21).addBox(-0.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.0F, 1.0F));

		PartDefinition l1 = mid_segment.addOrReplaceChild("l1", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-2.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -1.0F, 1.0F));

		PartDefinition l2 = mid_segment.addOrReplaceChild("l2", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-2.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -1.0F, -0.5F));

		PartDefinition l3 = mid_segment.addOrReplaceChild("l3", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-2.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -1.0F, -2.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.6915F, -0.1392F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.05F))
				.texOffs(8, 0).addBox(-1.5F, 0.3085F, 3.8608F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(1.0F, -1.1915F, 3.3608F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(28, 0).addBox(-2.0F, -1.1915F, 3.3608F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.9403F, -4.0326F, -2.8362F, 0.0F, 3.1416F));

		PartDefinition left_antennae_r1 = head.addOrReplaceChild("left_antennae_r1", CubeListBuilder.create().texOffs(7, 10).mirror().addBox(-8.1225F, -2.1451F, -10.9603F, 7.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.7391F, 3.4097F, -0.1737F, 0.0076F, 0.1309F));

		PartDefinition right_antennae_r1 = head.addOrReplaceChild("right_antennae_r1", CubeListBuilder.create().texOffs(7, 10).addBox(1.1225F, -2.1451F, -10.9603F, 7.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.7391F, 3.4097F, -0.1737F, -0.0076F, -0.1309F));

		PartDefinition l_arm_1 = head.addOrReplaceChild("l_arm_1", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.3085F, 0.8608F));

		PartDefinition arm_r1 = l_arm_1.addOrReplaceChild("arm_r1", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-4.6682F, -1.4602F, 0.4221F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7586F, 0.8667F, -0.6986F));

		PartDefinition l_arm_2 = head.addOrReplaceChild("l_arm_2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 1.6092F, 1.8145F, -0.0183F, 0.3323F, -0.109F));

		PartDefinition arm_r2 = l_arm_2.addOrReplaceChild("arm_r2", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-3.6821F, -0.6312F, 0.3491F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.342F, -0.2826F, -0.8962F, -0.7586F, 0.8667F, -0.6986F));

		PartDefinition r_arm_2 = head.addOrReplaceChild("r_arm_2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 1.6092F, 1.8145F, -0.0183F, -0.3323F, 0.109F));

		PartDefinition arm_r3 = r_arm_2.addOrReplaceChild("arm_r3", CubeListBuilder.create().texOffs(0, 21).addBox(0.6821F, -0.6312F, 0.3491F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.342F, -0.2826F, -0.8962F, -0.7586F, -0.8667F, 0.6986F));

		PartDefinition r_arm_1 = head.addOrReplaceChild("r_arm_1", CubeListBuilder.create(), PartPose.offset(1.0F, 1.3085F, 0.8608F));

		PartDefinition arm_r4 = r_arm_1.addOrReplaceChild("arm_r4", CubeListBuilder.create().texOffs(0, 24).addBox(0.1639F, -1.5254F, -0.4439F, 5.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7586F, -0.8667F, 0.6986F));

		PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(16, 22).addBox(-2.0F, -5.5F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 1.0F));

		PartDefinition back_segment = partdefinition.addOrReplaceChild("back_segment", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.9962F, -3.9128F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 22.5F, 0.0F, -3.0543F, 0.0F, 3.1416F));

		PartDefinition tail = back_segment.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(12, 5).addBox(-1.0F, -0.5F, -3.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(17, 3).addBox(-2.5F, 0.0F, -5.5F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.9962F, -3.9128F, 0.1309F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T state) {
		super.setupAnim(state);
		this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, 2, 2);
	}
}
