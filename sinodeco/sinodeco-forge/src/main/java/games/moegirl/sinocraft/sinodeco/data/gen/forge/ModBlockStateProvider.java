package games.moegirl.sinocraft.sinodeco.data.gen.forge;

import games.moegirl.sinocraft.sinodeco.block.SDBlocks;
import games.moegirl.sinocraft.sinodeco.block.WoodDeskBlock;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        {
            var builder = getMultipartBuilder(SDBlocks.WOODEN_TABLE.get());

            for (var i = 0; i < WoodDeskBlock.PARTS.length; i++) {
                var north = (i & 0b1000) != 0;
                var east = (i & 0b0100) != 0;
                var south = (i & 0b0010) != 0;
                var west = (i & 0b0001) != 0;

                var extras = WoodDeskBlock.PARTS[i];
                for (var parts : extras) {
                    if (parts == null) {
                        continue;
                    }

                    for (var p : parts) {
                        if (p == null) {
                            continue;
                        }

                        var part = builder.part()
                                .modelFile(models().getExistingFile(modLoc("block/wood_desk_" + p.name())))
                                .rotationY(p.rotate())
                                .addModel()
                                .condition(WoodDeskBlock.NORTH, north)
                                .condition(WoodDeskBlock.EAST, east)
                                .condition(WoodDeskBlock.SOUTH, south)
                                .condition(WoodDeskBlock.WEST, west);

                        if (p.condition() != null) {
                            part.condition(p.condition(), p.conditionValue());
                        }
                    }
                }
            }
        }
    }
}
