package net.pythorne.marycats.entity;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.pythorne.marycats.MarysCats;
import net.pythorne.marycats.entity.custom.TigerEntity;
import net.pythorne.marycats.entity.custom.TigerVariant;

import java.util.Optional;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MarysCats.MOD_ID);

    public static final RegistryObject<EntityType<TigerEntity>> TIGER =
            ENTITY_TYPES.register("tiger", () -> EntityType.Builder.of(TigerEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f)
                    .build(ResourceKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), ResourceLocation.fromNamespaceAndPath("marycats", "tiger"))));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
