package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

public class OreJade extends DropExperienceBlock implements ILootableBlock {

    public OreJade() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createOreDrops(this, SDItems.JADE.get(), 1, 2);
    }
}