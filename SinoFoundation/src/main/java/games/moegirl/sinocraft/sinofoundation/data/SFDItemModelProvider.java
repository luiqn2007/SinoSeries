package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinocore.data.ItemModelProviderBase;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;

public class SFDItemModelProvider extends ItemModelProviderBase {
    public SFDItemModelProvider(PackOutput output, String modId, ExistingFileHelper exHelper,
                                DeferredRegister<? extends ItemLike>... deferredRegisters) {
        super(output, modId, exHelper, deferredRegisters);
    }

    @Override
    protected void registerItemModels() {
        skipItem(SFDBlockItems.STOVE.get());

        getBuilder(SFDBlockItems.STOVE.getKey().location().getPath()).parent(weakCheckModel(modLoc("block/stove_off")));
    }
}