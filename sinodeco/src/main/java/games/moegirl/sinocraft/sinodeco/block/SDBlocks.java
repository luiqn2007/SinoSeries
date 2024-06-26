package games.moegirl.sinocraft.sinodeco.block;

import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.RegistryManager;
import games.moegirl.sinocraft.sinodeco.SinoDeco;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SDBlocks {
    public static final IRegistry<Block> BLOCKS = RegistryManager.obtain(SinoDeco.MODID, Registries.BLOCK);

    public static void register() {
        BLOCKS.register();
    }

    public static final Supplier<Block> WOODEN_TABLE = BLOCKS.register("wooden_table", WoodDeskBlock::new);
}
