package games.moegirl.sinocraft.sinocore.registry.neoforge;

import games.moegirl.sinocraft.sinocore.registry.*;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import net.minecraft.core.Registry;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new NeoForgeRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new NeoForgeTabRegistryImpl(modId);
    }
    public static IMenuRegister _createMenu(String modId) {
        return new NeoForgeMenuRegister(modId);
    }

    public static IScreenRegister _createScreen(String modId) {
        return new NeoForgeScreenRegister();
    }

    public static ICommandRegistry _createCommand(String modId) {
        return new NeoForgeCommandRegister();
    }

    public static IDataProviderRegister _createDataProvider(String modId) {
        return new IDataProviderRegister() {

            @Override
            public <T extends DataProvider> Supplier<T> put(Function<IDataGenContext, ? extends T> builder, boolean run) {
                return () -> null;
            }
        };
    }
}
