package net.pythorne.marycats.entity.custom;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.pythorne.marycats.entity.ModEntities;
import net.pythorne.marycats.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class TigerEntity extends TamableAnimal implements NeutralMob, VariantHolder<TigerVariant> {

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(TigerEntity.class, EntityDataSerializers.INT);
    public static final float BABY_SCALE = 0.45F;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState sittingAnimationState = new AnimationState();
    private int sittingAnimationTimeout = 0;
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;

    public TigerEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setTame(false, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.5));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.7D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 2.0, true));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Sheep.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Cow.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Pig.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Chicken.class, false));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Rabbit.class, false));


    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_333447_) {
        super.defineSynchedData(p_333447_);
        p_333447_.define(VARIANT, 0);
        p_333447_.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    private int getTypeVariant(){
        return this.entityData.get(VARIANT);
    }

    private void setTypeVariant(int pTypeVariant) {
        this.entityData.set(VARIANT, pTypeVariant);
    }

    public TigerVariant tigerVariant() {
        return TigerVariant.byId(this.getTypeVariant() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT, pCompound.getInt("Variant"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, EntitySpawnReason p_366700_, @Nullable SpawnGroupData p_146749_) {
        //TigerVariant variant = Util.getRandom(TigerVariant.values(), this.random);
        this.setVariant(TigerVariant.ORANGE);
        return super.finalizeSpawn(p_146746_, p_146747_, p_366700_, p_146749_);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel pLevel, Animal pAnimal, @Nullable AgeableMob pBaby) {
        TigerVariant variant;

        if(this.random.nextFloat() < 0.00083f){
            variant = TigerVariant.WHITE;
        }else{
            variant = TigerVariant.ORANGE;
        }
        ((TigerEntity) pBaby).setVariant(variant);
        super.finalizeSpawnChildFromBreeding(pLevel, pAnimal, pBaby);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_DAMAGE, 7.0);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.TIGER_AMBIENT.get();
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.BEEF);
    }

    public static boolean checkTigerSpawnRules(
            EntityType<? extends Animal> pEntityType, LevelAccessor pLevel, EntitySpawnReason pSpawnReason, BlockPos pPos, RandomSource pRandom
    ) {
        return pLevel.getBlockState(pPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(pLevel, pPos);
    }

    @Override
    public @Nullable TigerEntity getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        TigerEntity tiger = ModEntities.TIGER.get().create(pLevel, EntitySpawnReason.BREEDING);
        return tiger;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Item item = itemstack.getItem();
        if(this.isTame()){
            if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
                this.usePlayerItem(pPlayer, pHand, itemstack);
                FoodProperties foodproperties = itemstack.get(DataComponents.FOOD);
                float f = foodproperties != null ? (float)foodproperties.nutrition() : 1.0F;
                this.heal(2.0F * f);
                return InteractionResult.SUCCESS;
            } else{
                InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                if (!interactionresult.consumesAction() && this.isOwnedBy(pPlayer)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS.withoutItem();
                } else {
                    return interactionresult;
                }
            }
        }else if(this.isBaby()){
            if (!this.level().isClientSide && (itemstack.is(Items.PORKCHOP) || itemstack.is(Items.MUTTON) || itemstack.is(Items.RABBIT)) && !this.isAngry()){
                itemstack.consume(1, pPlayer);
                this.tryToTame(pPlayer);
                return InteractionResult.SUCCESS_SERVER;
            }else{
                return super.mobInteract(pPlayer, pHand);
            }
        } else{
            return super.mobInteract(pPlayer, pHand);
        }
    }

    private void tryToTame(Player pPlayer) {
        if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
            this.tame(pPlayer);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    private void setupAnimationStates(){
        if(this.isInSittingPose()){
            this.idleAnimationState.stop();
                    if(!this.sittingAnimationState.isStarted()){
                        this.sittingAnimationState.start(this.tickCount);
                    }
        }else{
            this.sittingAnimationState.stop();
                if(!this.idleAnimationState.isStarted()){
                    this.idleAnimationState.start(this.tickCount);
                }
        }
    }

    public void tick(){
        super.tick();

        if(this.level().isClientSide()){
            setupAnimationStates();
        }

    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pRemainingPersistentAngerTime);
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {
        this.persistentAngerTarget = pPersistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public void setVariant(TigerVariant pVariant) {
        this.setTypeVariant(pVariant.getId() & 0xFF | this.getTypeVariant() & -256);
    }

    @Override
    public TigerVariant getVariant() {
        return TigerVariant.byId(this.getTypeVariant() & 0xFF);
    }
}
