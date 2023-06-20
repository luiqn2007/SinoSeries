package games.moegirl.sinocraft.sinofoundation.data;

import games.moegirl.sinocraft.sinofoundation.SinoFoundation;
import games.moegirl.sinocraft.sinofoundation.block.SFDBlocks;
import games.moegirl.sinocraft.sinofoundation.SFDDamages;
import games.moegirl.sinocraft.sinofoundation.world.SFDFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author luqin2007
 */
@SuppressWarnings("NotNullFieldNotInitialized")
public class SFDDatapackProvider extends DatapackBuiltinEntriesProvider {

    private static final RuleTest ORE_REPLACEABLE = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

    static Holder.Reference<ConfiguredFeature<?, ?>> confJade;
    static Holder.Reference<ConfiguredFeature<?, ?>> confSulphur;
    static Holder.Reference<ConfiguredFeature<?, ?>> confNiter;
    static Holder.Reference<PlacedFeature> placedJade;
    static Holder.Reference<PlacedFeature> placedSulphur;
    static Holder.Reference<PlacedFeature> placedNiter;

    public SFDDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, SFDDatapackProvider::addDamageTypes) // 伤害类型
                        .add(Registries.CONFIGURED_FEATURE, SFDDatapackProvider::addConfiguredFeatures) // 世界生成（局部）
                        .add(Registries.PLACED_FEATURE, SFDDatapackProvider::addPlacedFeatures), // 世界生成
                Set.of(SinoFoundation.MODID));
    }

    private static void addDamageTypes(BootstapContext<DamageType> context) {
        context.register(SFDDamages.SOPHORA_DOOR, new DamageType(SinoFoundation.MODID + ".damage.sophora_door", DamageScaling.ALWAYS, 0));
    }

    private static void addConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        confJade = context.register(SFDFeatures.Features.JADE, oreConfiguration(SFDBlocks.JADE_ORE, 16));
        confSulphur = context.register(SFDFeatures.Features.SULPHUR, oreConfiguration(SFDBlocks.SULPHUR_ORE, 20));
        confNiter = context.register(SFDFeatures.Features.NITER, oreConfiguration(SFDBlocks.NITER_ORE, 20));
    }

    private static void addPlacedFeatures(BootstapContext<PlacedFeature> context) {
        placedJade = context.register(SFDFeatures.Placements.JADE, orePlaced(confJade, 0, 64));
        placedSulphur = context.register(SFDFeatures.Placements.SULPHUR, orePlaced(confSulphur, -32, 8));
        placedNiter = context.register(SFDFeatures.Placements.NITER, orePlaced(confNiter, 8, 64));
    }

    /**
     * 矿石每次生成的配置
     *
     * @param ore  矿物方块
     * @param size 每一组生成的矿石平均个数
     * @return 生成配置
     */
    private static ConfiguredFeature<OreConfiguration, OreFeature> oreConfiguration(RegistryObject<? extends Block> ore, int size) {
        OreConfiguration configuration = new OreConfiguration(ORE_REPLACEABLE, ore.get().defaultBlockState(), size);
        return new ConfiguredFeature<>((OreFeature) Feature.ORE, configuration);
    }

    /**
     * 矿石的生成配置
     *
     * @param hMin 最低高度
     * @param hMax 最高高度
     * @return 配置
     */
    private static PlacedFeature orePlaced(Holder.Reference<ConfiguredFeature<?, ?>> configuration, int hMin, int hMax) {
        return new PlacedFeature(configuration, List.of(
                CountPlacement.of(9), // 生成次数
                InSquarePlacement.spread(), // ??
                HeightRangePlacement.uniform(VerticalAnchor.absolute(hMin), VerticalAnchor.absolute(hMax)), // 高度区间
                BiomeFilter.biome() // 根据生物群系过滤
        ));
    }

    @Override
    public String getName() {
        return super.getName() + ": " + SinoFoundation.MODID;
    }
}