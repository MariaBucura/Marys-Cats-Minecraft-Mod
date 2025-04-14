package net.pythorne.marycats.entity.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;
import net.pythorne.marycats.MarysCats;
import net.pythorne.marycats.entity.custom.TigerEntity;
import net.pythorne.marycats.entity.custom.TigerVariant;

import java.util.Map;

public class TigerRenderer extends AgeableMobRenderer<TigerEntity, TigerRenderState, TigerModel> {

    private static final Map<TigerVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TigerVariant.class), map -> {
                map.put(TigerVariant.ORANGE,
                        ResourceLocation.fromNamespaceAndPath(MarysCats.MOD_ID, "textures/entity/tiger/tiger_orange.png"));
                map.put(TigerVariant.WHITE,
                        ResourceLocation.fromNamespaceAndPath(MarysCats.MOD_ID, "textures/entity/tiger/tiger_white.png"));
            });

    public TigerRenderer(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new TigerModel(p_174304_.bakeLayer(TigerModel.LAYER_LOCATION)), new TigerModel(p_174304_.bakeLayer(TigerModel.BABY_LAYER_LOCATION)), 0.85f);

    }

    @Override
    public TigerRenderState createRenderState() {
        return new TigerRenderState();
    }

    @Override
    public ResourceLocation getTextureLocation(TigerRenderState pRenderState) {
        return LOCATION_BY_VARIANT.get(pRenderState.variant);
    }

    @Override
    public void extractRenderState(TigerEntity p_368665_, TigerRenderState p_363057_, float p_364497_) {
        super.extractRenderState(p_368665_, p_363057_, p_364497_);
        p_363057_.variant = p_368665_.getVariant();
        p_363057_.sittingAnimationState.copyFrom(p_368665_.sittingAnimationState);
        p_363057_.idleAnimationState.copyFrom(p_368665_.idleAnimationState);
    }

    @Override
    public void render(TigerRenderState p_363116_, PoseStack p_363662_, MultiBufferSource p_366693_, int p_363199_) {
        if (p_363116_.isBaby) {
            p_363662_.scale(0.5f, 0.5f, 0.5f);
        } else {
            p_363662_.scale(1f, 1f, 1f);
        }
        super.render(p_363116_, p_363662_, p_366693_, p_363199_);
    }
}
