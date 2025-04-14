package net.pythorne.marycats.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pythorne.marycats.MarysCats;
import net.pythorne.marycats.entity.custom.TigerEntity;

import javax.swing.text.html.parser.Entity;
import java.util.Set;

public class TigerModel extends EntityModel<TigerRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MarysCats.MOD_ID, "tigermodel"), "main");
    public static final ModelLayerLocation BABY_LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MarysCats.MOD_ID, "tigermodel"), "main");
    private final ModelPart full;
    private final ModelPart head;
    private final ModelPart myRoot;
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(Set.of("head"));

    public TigerModel(ModelPart root) {
        super(root);
        this.full = root.getChild("full");
        this.head = this.full.getChild("head");
        this.myRoot = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition full = partdefinition.addOrReplaceChild("full", CubeListBuilder.create(), PartPose.offset(8.0F, 24.0F, -13.0F));

        PartDefinition leftfrontleg = full.addOrReplaceChild("left front leg", CubeListBuilder.create().texOffs(36, 57).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -15.0F, 2.5F));

        PartDefinition rightfrontleg = full.addOrReplaceChild("right front leg", CubeListBuilder.create().texOffs(54, 57).addBox(-2.0F, -5.0F, -2.5F, 4.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -15.0F, 2.5F));

        PartDefinition leftbackleg = full.addOrReplaceChild("left back leg", CubeListBuilder.create().texOffs(0, 60).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -12.0F, 24.5F));

        PartDefinition rightbackleg = full.addOrReplaceChild("right back leg", CubeListBuilder.create().texOffs(18, 60).addBox(-2.0F, -2.0F, -2.5F, 4.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -12.0F, 24.5F));

        PartDefinition body = full.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -20.0F, -3.0F, 10.0F, 11.0F, 31.0F, new CubeDeformation(-0.2F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

        PartDefinition head = full.addOrReplaceChild("head", CubeListBuilder.create().texOffs(72, 58).addBox(-7.0F, -2.0F, -6.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(72, 64).addBox(2.0F, -2.0F, -6.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(36, 42).addBox(-4.0F, -4.0F, -7.8F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(72, 70).addBox(1.5F, -6.0F, -4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(72, 75).addBox(-3.5F, -6.0F, -4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -17.0F, -3.0F));

        PartDefinition muzzle_r1 = head.addOrReplaceChild("muzzle_r1", CubeListBuilder.create().texOffs(72, 51).addBox(-2.0F, -1.6F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -8.5F, 0.3927F, 0.0F, 0.0F));

        PartDefinition tail = full.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-8.0F, -19.0F, 27.0F));

        PartDefinition uppertail = tail.addOrReplaceChild("upper tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition uppertail_r1 = uppertail.addOrReplaceChild("uppertail_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -1.6F, -1.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition lowertail = tail.addOrReplaceChild("lower tail", CubeListBuilder.create().texOffs(68, 42).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 9.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public void setupAnim(TigerRenderState p_368486_) {
        super.setupAnim(p_368486_);
        this.applyHeadRotation(p_368486_, p_368486_.yRot, p_368486_.xRot );
        this.animateWalk(TigerAnimations.TIGER_WALK, p_368486_.walkAnimationPos, p_368486_.walkAnimationSpeed, 2.0f, 2.5f);
        this.animate(p_368486_.idleAnimationState, TigerAnimations.TIGER_IDLE, p_368486_.ageInTicks, 1.0f);
        this.animate(p_368486_.sittingAnimationState, TigerAnimations.TIGER_SIT, p_368486_.ageInTicks, 1.0f);
    }

    private void applyHeadRotation(TigerRenderState pRenderState, float pYRot, float pXRot) {
        pYRot = Mth.clamp(pYRot, -30.0F, 30.0F);
        pXRot = Mth.clamp(pXRot, -25.0F, 45.0F);

        this.head.yRot = pYRot * ((float)Math.PI / 180F);
        this.head.xRot = pXRot * ((float)Math.PI / 180F);
    }

}
