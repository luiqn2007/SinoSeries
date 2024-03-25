package games.moegirl.sinocraft.sinocore.registry.fabric;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegister;
import games.moegirl.sinocraft.sinocore.datagen.IDataGenContext;
import games.moegirl.sinocraft.sinocore.registry.IDataProviderRegister;
import games.moegirl.sinocraft.sinocore.registry.IRegistry;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegister;
import games.moegirl.sinocraft.sinocore.registry.ITabRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

public class RegistryManagerImpl {

    public static <T> IRegistry<T> _create(String modId, ResourceKey<Registry<T>> key) {
        return new FabricRegistryImpl<>(modId, key);
    }

    public static ITabRegistry _createTab(String modId) {
        return new FabricTabRegistryImpl(modId);
    }

    public static IMenuRegister _createMenu(String modId) {
        return new FabricMenuRegister(modId);
    }

    public static IScreenRegister _createScreen(String modId) {
        return new FabricScreenRegister();
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
