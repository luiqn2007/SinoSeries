package games.moegirl.sinocraft.sinocore.registry.neoforge;

import com.mojang.datafixers.util.Pair;
import games.moegirl.sinocraft.sinocore.registry.IRegRef;
import games.moegirl.sinocraft.sinocore.registry.IScreenRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeScreenRegister implements IScreenRegister {

    private final List<Pair<IRegRef<MenuType<?>, ?>, IScreenFactory<?>>> screens = new ArrayList<>();

    @Override
    public void register() {
        for (Pair<IRegRef<MenuType<?>, ?>, IScreenFactory<?>> screen : screens) {
            MenuScreens.register(screen.getFirst().get(), new ScreenFactoryWrapper(screen.getSecond()));
        }
    }

    @Override
    public <T extends AbstractContainerMenu> void register(IRegRef<MenuType<?>, ?> menuType, IScreenFactory<T> screenFactory) {
        screens.add(Pair.of(menuType, screenFactory));
    }

    static class ScreenFactoryWrapper implements MenuScreens.ScreenConstructor {

        private final IScreenFactory factory;

        public ScreenFactoryWrapper(IScreenFactory<?> factory) {
            this.factory = factory;
        }

        @Override
        public Screen create(AbstractContainerMenu menu, Inventory inventory, Component title) {
            return factory.create(menu, inventory, title);
        }
    }
}
