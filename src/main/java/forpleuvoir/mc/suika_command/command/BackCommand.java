package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import forpleuvoir.mc.suika_command.teleport.BackPoint;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * 返回指令
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 BackCommand
 * <p>
 * 创建时间 2022/7/19 12:44
 *
 * @author forpleuvoir
 */
public class BackCommand {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("back").executes(BackCommand::back));
	}

	private static int back(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.asPlayer();
		BackPoint.getINSTANCE().toBack(player);
		return 1;
	}
}
