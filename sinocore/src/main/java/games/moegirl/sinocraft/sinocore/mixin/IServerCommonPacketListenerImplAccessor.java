package games.moegirl.sinocraft.sinocore.mixin;

import net.minecraft.network.Connection;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerGamePacketListenerImpl.class)
public interface IServerCommonPacketListenerImplAccessor {

    /**
     * 针对 fabric，获取 {@link Connection} 用于创建网络通信上下文
     */
    @Accessor
    Connection getConnection();
}
