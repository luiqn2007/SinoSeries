package games.moegirl.sinocraft.sinotest.sinocore.tree;

import games.moegirl.sinocraft.sinotest.SinoTest;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SinoTest.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TestData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();

        generator.addProvider(true, new TestZhCnLanguageProvider(output, SinoTest.MODID, "zh_cn"));
        generator.addProvider(true, new TestZhTwLanguageProvider(output, SinoTest.MODID, "zh_tw"));
        generator.addProvider(true, new TestEnUsLanguageProvider(output, SinoTest.MODID, "en_us"));
    }
}