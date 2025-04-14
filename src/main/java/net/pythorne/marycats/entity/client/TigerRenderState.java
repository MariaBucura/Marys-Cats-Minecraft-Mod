package net.pythorne.marycats.entity.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.pythorne.marycats.entity.custom.TigerVariant;

@OnlyIn(Dist.CLIENT)
public class TigerRenderState extends LivingEntityRenderState {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/tiger/tiger_orange.png");
    public ResourceLocation texture = DEFAULT_TEXTURE;
    public final AnimationState idleAnimationState = new AnimationState();
    public boolean isSitting;
    public final AnimationState sittingAnimationState = new AnimationState();
    public TigerVariant variant = TigerVariant.ORANGE;}
