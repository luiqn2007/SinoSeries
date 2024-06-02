package games.moegirl.sinocraft.sinocore.data.gen;

import games.moegirl.sinocraft.sinocore.mixin_interfaces.interfaces.IRenamedProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class AbstractBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> implements IRenamedProvider, ISinoDataProvider {

    protected static final TagKey<Block> FORGE_CHESTS_WOODEN = forgeTag("chests/wooden");
    protected static final TagKey<Block> FORGE_CHESTS_TRAPPED = forgeTag("chests/trapped");

    protected final String modId;

    public AbstractBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, Registries.BLOCK, lookupProvider, block -> block.builtInRegistryHolder().key());
        this.modId = modId;
    }

    public AbstractBlockTagsProvider(IDataGenContext context) {
        super(context.getOutput(), Registries.BLOCK, context.registriesFuture(), block -> block.builtInRegistryHolder().key());
        this.modId = context.getModId();
    }

    public void addPickaxe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_PICKAXE, blocks);
    }

    public void addAxe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_AXE, blocks);
    }

    public void addShovel(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_SHOVEL, blocks);
    }

    public void addHoe(Block... blocks) {
        add(BlockTags.MINEABLE_WITH_HOE, blocks);
    }

    public void addStoneTool(Block... blocks) {
        add(BlockTags.NEEDS_STONE_TOOL, blocks);
    }

    public void addIronTool(Block... blocks) {
        add(BlockTags.NEEDS_IRON_TOOL, blocks);
    }

    public void addDiamondTool(Block... blocks) {
        add(BlockTags.NEEDS_DIAMOND_TOOL, blocks);
    }

    public void add(TagKey<Block> tag, Block... blocks) {
        tag(tag).add(blocks);
    }

    public void add(TagKey<Block> tag, Collection<? extends Block> blocks) {
        tag(tag).add(blocks.toArray(Block[]::new));
    }

    public void add(TagKey<Block> tag, Stream<? extends Block> blocks) {
        tag(tag).add(blocks.toArray(Block[]::new));
    }

    @Override
    public String getNewName() {
        return "Tags for Block: " + modId;
    }

    @Override
    public String getModId() {
        return modId;
    }

    protected void chest(Supplier<? extends Block> block, TagKey<Block> material, boolean trapped) {
        Block b = block.get();
        tag(FORGE_CHESTS_WOODEN).add(b);
        tag(BlockTags.GUARDED_BY_PIGLINS).add(b);
        tag(BlockTags.MINEABLE_WITH_AXE).add(b);
        tag(BlockTags.FEATURES_CANNOT_REPLACE).add(b);
        tag(material).add(b);
        if (trapped) {
            tag(FORGE_CHESTS_TRAPPED).add(b);
        }
    }

    protected static TagKey<Block> forgeTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("forge", name));
    }
}
