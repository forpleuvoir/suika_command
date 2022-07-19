package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import forpleuvoir.mc.suika_command.teleport.TpaHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * 玩家间传送指令
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 TpCommand
 * <p>
 * 创建时间 2022/7/19 12:45
 *
 * @author forpleuvoir
 */
public class TpCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("tpa")
				.then(Commands.argument("player", EntityArgument.player())
						.executes(TpCommand::tpa)));
		dispatcher.register(Commands.literal("tpahere")
				.then(Commands.argument("player", EntityArgument.player())
						.executes(TpCommand::tpahere)));
		dispatcher.register(Commands.literal("tpaccept").executes(TpCommand::tpaccept));
	}

	private static int tpa(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity sender = source.asPlayer();
		ServerPlayerEntity target = EntityArgument.getPlayer(context, "player");
		TpaHelper.getINSTANCE().sendTpa(sender, target, source.getServer().getTickCounter());
		return 1;
	}

	private static int tpahere(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity target = source.asPlayer();
		ServerPlayerEntity tpPlayer = EntityArgument.getPlayer(context, "player");
		TpaHelper.getINSTANCE().sendTpa(tpPlayer, target, source.getServer().getTickCounter());
		return 1;
	}

	private static int tpaccept(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity target = source.asPlayer();
		TpaHelper.getINSTANCE().tpaccept(target, source.getServer().getTickCounter());
		return 1;
	}

}
