package games.moegirl.sinocraft.sinodivination.block;

import games.moegirl.sinocraft.sinocore.block.TrappedChestBlockBase;
import games.moegirl.sinocraft.sinodivination.SDTrees;
import games.moegirl.sinocraft.sinodivination.blockentity.SDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SophoraTrappedChest extends TrappedChestBlockBase implements SophoraBlock {

    public SophoraTrappedChest() {
        super(SDBlockEntities.SOPHORA_TRAPPED_CHEST, SDTrees.SOPHORA.name);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState blockState, LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, blockState, placer, itemStack);
        placedBy(level, pos, placer);
    }
}
