package eu.crushedpixel.sponge.masquerade.api;

import eu.crushedpixel.sponge.masquerade.impl.masquerades.AgeableMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.CreeperMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.FallingBlockMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.LivingMobMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.MinecartMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.ObjectMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.TameableMobMasquerade;
import eu.crushedpixel.sponge.masquerade.impl.masquerades.WolfMasquerade;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Masquerades {

    private static final Map<EntityType, Function<Player, Masquerade>> REGISTRY = new HashMap<>();

    static {
        // TODO: Create explicit masquerade for color
        registerDefault(EntityTypes.AREA_EFFECT_CLOUD, player ->
                new ObjectMasquerade(player, EntityTypes.AREA_EFFECT_CLOUD));

        // TODO: Explicit masquerade
        registerDefault(EntityTypes.ARMOR_STAND, player ->
                new LivingMobMasquerade(player, EntityTypes.ARMOR_STAND));

        // TODO: Explicit masquerade for hanging
        registerDefault(EntityTypes.BAT, player ->
                new LivingMobMasquerade(player, EntityTypes.BAT));

        // TODO: Explicit masquerade
        registerDefault(EntityTypes.BLAZE, player ->
                new LivingMobMasquerade(player, EntityTypes.BLAZE));

        // TODO: Explicit masquerade for boat type
        registerDefault(EntityTypes.BOAT, player ->
                new ObjectMasquerade(player, EntityTypes.BOAT));

        registerDefault(EntityTypes.CAVE_SPIDER, player ->
                new LivingMobMasquerade(player, EntityTypes.CAVE_SPIDER));

        registerDefault(EntityTypes.CHESTED_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.CHESTED_MINECART));

        registerDefault(EntityTypes.CHICKEN, player ->
                new AgeableMasquerade(player, EntityTypes.CHICKEN));

        registerDefault(EntityTypes.COMMANDBLOCK_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.COMMANDBLOCK_MINECART));

        registerDefault(EntityTypes.COW, player ->
                new AgeableMasquerade(player, EntityTypes.COW));

        registerDefault(EntityTypes.CREEPER, CreeperMasquerade::new);

        registerDefault(EntityTypes.DRAGON_FIREBALL, player ->
                new ObjectMasquerade(player, EntityTypes.DRAGON_FIREBALL));

        registerDefault(EntityTypes.EGG, player ->
                new ObjectMasquerade(player, EntityTypes.EGG));

        registerDefault(EntityTypes.ENDER_CRYSTAL, player ->
                new ObjectMasquerade(player, EntityTypes.ENDER_CRYSTAL));

        registerDefault(EntityTypes.ENDER_DRAGON, player ->
                new LivingMobMasquerade(player, EntityTypes.ENDER_DRAGON));

        registerDefault(EntityTypes.ENDER_PEARL, player ->
                new ObjectMasquerade(player, EntityTypes.ENDER_PEARL));

        // TODO: Explicit masquerade for held block
        registerDefault(EntityTypes.ENDERMAN, player ->
                new LivingMobMasquerade(player, EntityTypes.ENDERMAN));

        registerDefault(EntityTypes.ENDERMITE, player ->
                new LivingMobMasquerade(player, EntityTypes.ENDERMITE));

        // TODO: EXPERIENCE_ORB masquerade

        registerDefault(EntityTypes.EYE_OF_ENDER, player ->
                new ObjectMasquerade(player, EntityTypes.EYE_OF_ENDER));

        registerDefault(EntityTypes.FALLING_BLOCK, player ->
                new FallingBlockMasquerade(player, BlockState.builder().blockType(BlockTypes.SAND).build()));

        registerDefault(EntityTypes.FIREBALL, player ->
                new ObjectMasquerade(player, EntityTypes.FIREBALL));

        // TODO: is this a good idea?
        registerDefault(EntityTypes.FIREWORK, player ->
                new ObjectMasquerade(player, EntityTypes.FIREWORK));

        // TODO: is this a good idea as well?
        registerDefault(EntityTypes.FISHING_HOOK, player ->
                new ObjectMasquerade(player, EntityTypes.FISHING_HOOK));

        registerDefault(EntityTypes.FURNACE_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.FURNACE_MINECART));

        registerDefault(EntityTypes.GHAST, player ->
                new LivingMobMasquerade(player, EntityTypes.GHAST));

        registerDefault(EntityTypes.GIANT, player ->
                new LivingMobMasquerade(player, EntityTypes.GIANT));

        // TODO: Explicit masquerade for spikes
        registerDefault(EntityTypes.GUARDIAN, player ->
                new LivingMobMasquerade(player, EntityTypes.GUARDIAN));

        registerDefault(EntityTypes.HOPPER_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.HOPPER_MINECART));

        // TODO: Explicit masquerade
        registerDefault(EntityTypes.HORSE, player ->
                new AgeableMasquerade(player, EntityTypes.HORSE));

        // TODO: Explicit masquerade for player
        registerDefault(EntityTypes.HUMAN, player ->
                new LivingMobMasquerade(player, EntityTypes.PLAYER)); // this is not a mistake

        // TODO: Explicit masquerade for flower
        registerDefault(EntityTypes.IRON_GOLEM, player ->
                new LivingMobMasquerade(player, EntityTypes.IRON_GOLEM));

        // TODO: Explicit masquerade for item type
        registerDefault(EntityTypes.ITEM, player ->
                new ObjectMasquerade(player, EntityTypes.ITEM));

        // TODO: Explicit masquerade for item frame orientation and item
        registerDefault(EntityTypes.ITEM_FRAME, player ->
                new ObjectMasquerade(player, EntityTypes.ITEM_FRAME));

        registerDefault(EntityTypes.LEASH_HITCH, player ->
                new ObjectMasquerade(player, EntityTypes.LEASH_HITCH));

        // TODO: Slime masquerade for size
        registerDefault(EntityTypes.MAGMA_CUBE, player ->
                new LivingMobMasquerade(player, EntityTypes.MAGMA_CUBE));

        registerDefault(EntityTypes.MOB_SPAWNER_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.MOB_SPAWNER_MINECART));

        registerDefault(EntityTypes.MUSHROOM_COW, player ->
                new AgeableMasquerade(player, EntityTypes.MUSHROOM_COW));

        registerDefault(EntityTypes.OCELOT, player ->
                new TameableMobMasquerade(player, EntityTypes.OCELOT));

        // TODO: PAINTING masquerade

        registerDefault(EntityTypes.PIG, player ->
                new AgeableMasquerade(player, EntityTypes.PIG));

        // TODO: Explicit zombie masquerade for child, arms raised
        registerDefault(EntityTypes.PIG_ZOMBIE, player ->
                new LivingMobMasquerade(player, EntityTypes.PIG_ZOMBIE));

        // TODO: Explicit masquerade for player
        registerDefault(EntityTypes.PLAYER, player ->
                new LivingMobMasquerade(player, EntityTypes.PLAYER));

        // TODO: Explicit masquerade for standing up
        registerDefault(EntityTypes.POLAR_BEAR, player ->
                new AgeableMasquerade(player, EntityTypes.POLAR_BEAR));

        // TODO: is this a good idea as-is?
        registerDefault(EntityTypes.PRIMED_TNT, player ->
                new ObjectMasquerade(player, EntityTypes.PRIMED_TNT));

        registerDefault(EntityTypes.RABBIT, player ->
                new AgeableMasquerade(player, EntityTypes.RABBIT));

        registerDefault(EntityTypes.RIDEABLE_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.RIDEABLE_MINECART));

        // TODO: Explicit masquerade for color and shear status
        registerDefault(EntityTypes.SHEEP, player ->
                new AgeableMasquerade(player, EntityTypes.SHEEP));

        // TODO: Explicit masquerade for direction and color
        registerDefault(EntityTypes.SHULKER, player ->
                new LivingMobMasquerade(player, EntityTypes.SHULKER));

        registerDefault(EntityTypes.SHULKER_BULLET, player ->
                new ObjectMasquerade(player, EntityTypes.SHULKER_BULLET));

        registerDefault(EntityTypes.SILVERFISH, player ->
                new LivingMobMasquerade(player, EntityTypes.SILVERFISH));

        // TODO: Explicit masquerade for entity type
        registerDefault(EntityTypes.SKELETON, player ->
                new LivingMobMasquerade(player, EntityTypes.SKELETON));

        // TODO: Slime masquerade for size
        registerDefault(EntityTypes.SLIME, player ->
                new LivingMobMasquerade(player, EntityTypes.SLIME));

        registerDefault(EntityTypes.SMALL_FIREBALL, player ->
                new ObjectMasquerade(player, EntityTypes.SMALL_FIREBALL));

        registerDefault(EntityTypes.SNOWBALL, player ->
                new ObjectMasquerade(player, EntityTypes.SNOWBALL));

        // TODO: Explicit masquerade for pumpkin head
        registerDefault(EntityTypes.SNOWMAN, player ->
                new LivingMobMasquerade(player, EntityTypes.SNOWMAN));

        registerDefault(EntityTypes.SPECTRAL_ARROW, player ->
                new ObjectMasquerade(player, EntityTypes.SPECTRAL_ARROW));

        registerDefault(EntityTypes.SPIDER, player ->
                new LivingMobMasquerade(player, EntityTypes.SPIDER));

        // TODO: Explicit masquerade for color
        registerDefault(EntityTypes.SPLASH_POTION, player ->
                new ObjectMasquerade(player, EntityTypes.SPLASH_POTION));

        registerDefault(EntityTypes.SQUID, player ->
                new LivingMobMasquerade(player, EntityTypes.SQUID));

        registerDefault(EntityTypes.THROWN_EXP_BOTTLE, player ->
                new ObjectMasquerade(player, EntityTypes.THROWN_EXP_BOTTLE));

        registerDefault(EntityTypes.TIPPED_ARROW, player ->
                new ObjectMasquerade(player, EntityTypes.TIPPED_ARROW));

        registerDefault(EntityTypes.TNT_MINECART, player ->
                new MinecartMasquerade(player, EntityTypes.TNT_MINECART));

        // TODO: Explicit masquerade for profession
        registerDefault(EntityTypes.VILLAGER, player ->
                new LivingMobMasquerade(player, EntityTypes.VILLAGER));

        registerDefault(EntityTypes.WITCH, player ->
                new LivingMobMasquerade(player, EntityTypes.WITCH));

        // TODO: Explicit masquerade for charged status
        registerDefault(EntityTypes.WITHER, player ->
                new LivingMobMasquerade(player, EntityTypes.WITHER));

        registerDefault(EntityTypes.WITHER_SKULL, player ->
                new ObjectMasquerade(player, EntityTypes.WITHER_SKULL));

        registerDefault(EntityTypes.WOLF, WolfMasquerade::new);

        registerDefault(EntityTypes.ZOMBIE, player ->
                new LivingMobMasquerade(player, EntityTypes.ZOMBIE));
    }

    public static void registerDefault(EntityType entityType, Function<Player, Masquerade> function) {
        REGISTRY.put(entityType, function);
    }

    public static Masquerade fromType(EntityType entityType, Player player) {
        Function<Player, Masquerade> function = REGISTRY.get(entityType);

        if (function == null) throw new IllegalArgumentException("No Masquerade available for this EntityType");
        return function.apply(player);
    }

    public static Masquerade fallingBlock(Player player, BlockState blockState) {
        return new FallingBlockMasquerade(player, blockState);
    }

}
