package games.moegirl.sinocraft.sinocore.data.gen.loottable;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Map;

public interface IEntityLootTableSubProvider {

    EntityPredicate.Builder isEntityOnFire();


    // rename form createSheepTable
    LootTable.Builder createTableLikeSheep(ItemLike item);

    boolean canHaveLootTable(EntityType<?> entity);

    LootItemCondition.Builder killedByFrog();

    LootItemCondition.Builder killedByFrogVariant(FrogVariant frogVariant);

    void add(EntityType<?> entity, ResourceLocation lootTableLocation, LootTable.Builder builder);

    default void add(EntityType<?> entity, LootTable.Builder builder) {
        add(entity, entity.getDefaultLootTable(), builder);
    }

    default void addAll(Map<EntityType<?>, LootTable.Builder> tables) {
        tables.forEach(this::add);
    }

    boolean isEmpty();

    default EntityLootSubProvider asProvider() {
        return (EntityLootSubProvider) this;
    }
}
