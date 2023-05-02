package games.moegirl.sinocraft.sinocalligraphy.tree;

import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocore.tree.Tree;
import games.moegirl.sinocraft.sinocore.tree.TreeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class SCATrees {
    public static Tree GREEN_SANDALWOOD = Tree.builder(new ResourceLocation(SinoCalligraphy.MODID, "green_sandalwood"))
            .translate("zh_cn", "青檀木")
            .translate("zh_tw", "青檀木")
            .translate("en_us", "Green Sandalwood")
            .build();

    public static void register(IEventBus bus) {
        TreeRegistry.register(SinoCalligraphy.MODID, bus);
    }
}