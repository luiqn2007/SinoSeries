package games.moegirl.sinocraft.sinocore.registry.forge;

import games.moegirl.sinocraft.sinocore.registry.IMenuRegister;
import games.moegirl.sinocraft.sinocore.registry.IRef;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;

public class ForgeMenuRegister extends ForgeRegistryImpl<MenuType<?>> implements IMenuRegister {

    ForgeMenuRegister(String modId) {
        super(modId, Registries.MENU);
    }

    @Override
    public <T extends AbstractContainerMenu> IRef<MenuType<?>, ?> register(String name, MenuFactory<T> factory) {
        return register(name, () -> new MenuType<>((IContainerFactory<T>) factory::create, FeatureFlagSet.of()));
    }
}
