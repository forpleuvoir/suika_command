package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 命令处理器
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 CommandEventHandler
 * <p>
 * 创建时间 2022/7/19 12:40
 *
 * @author forpleuvoir
 */
@Mod.EventBusSubscriber
public class CommandEventHandler {

	@SubscribeEvent
	public static void onServerStarting(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
	}


}
