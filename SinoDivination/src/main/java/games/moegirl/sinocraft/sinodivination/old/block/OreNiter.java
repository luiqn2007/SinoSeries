package games.moegirl.sinocraft.sinodivination.old.block;

import games.moegirl.sinocraft.sinocore.block.ILootableBlock;
import games.moegirl.sinocraft.sinocore.utility.BlockLootables;
import games.moegirl.sinocraft.sinodivination.old.item.SDItems;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;

public class OreNiter extends DropExperienceBlock implements ILootableBlock {

    public OreNiter() {
        super(Properties.of(Material.STONE)
                .requiresCorrectToolForDrops()
                .explosionResistance(3.0F));
    }

    @Override
    public LootTable.Builder createLootBuilder(BlockLootables helper) {
        return helper.createOreDrops(this, SDItems.NITER.get(), 1, 2);
    }
}