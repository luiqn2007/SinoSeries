package games.moegirl.sinocraft.sinofoundation.data.gen.tag;

import games.moegirl.sinocraft.sinocore.data.gen.AbstractItemTagsProvider;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlockItems;
import games.moegirl.sinocraft.sinofoundation.data.SFDItemTags;
import games.moegirl.sinocraft.sinofoundation.item.SFDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SFDItemTagsProvider extends AbstractItemTagsProvider {
    public SFDItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTagsProvider, String modId,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, modId, existingFileHelper);
    }

    @Override
    protected void addToTags() {
        tag(SFDItemTags.KNIVES).add(SFDItems.IRON_KNIFE.get(), SFDItems.GOLD_KNIFE.get(), SFDItems.DIAMOND_KNIFE.get());

        tag(SFDItemTags.FORGE_DUSTS_SULFUR).add(SFDItems.SULPHUR.get());
        tag(SFDItemTags.FORGE_DUSTS_NITER).add(SFDItems.NITER.get());

        chest(SFDBlockItems.JUJUBE_CHEST, false);
        chest(SFDBlockItems.JUJUBE_TRAPPED_CHEST, true);
    }
}
