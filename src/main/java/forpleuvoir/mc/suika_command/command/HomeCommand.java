package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import forpleuvoir.mc.suika_command.teleport.HomePoint;
import forpleuvoir.mc.suika_command.teleport.WarpPoint;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Home指令
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 HomeCommand
 * <p>
 * 创建时间 2022/7/19 12:45
 *
 * @author forpleuvoir
 */
public class HomeCommand {

	private static final SuggestionProvider<CommandSource> homeSuggest = (context, builder) -> {
		ServerPlayerEntity player = context.getSource().asPlayer();
		Set<String> homes = HomePoint.getINSTANCE().getHomes(player);
		return ISuggestionProvider.suggest(homes, builder);
	};

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(
				Commands.literal("home")
						.executes(HomeCommand::toHome)
						.then(Commands.argument("home name", StringArgumentType.string())
								.suggests(homeSuggest)
								.executes(HomeCommand::toHome)
						)
						.then(Commands.literal("set")
								.executes(HomeCommand::setHome)
								.then(Commands.argument("home name", StringArgumentType.string())
										.executes(HomeCommand::setHome)
								)
						)
						.then(Commands.literal("remove")
								.executes(HomeCommand::removeHome)
								.then(Commands.argument("home name", StringArgumentType.string())
										.suggests(homeSuggest)
										.executes(HomeCommand::removeHome)
								)
						)
		);

	}

	private static int toHome(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.asPlayer();
		String home = null;
		try {
			home = StringArgumentType.getString(context, "home name");
		} catch (Exception ignored) {
		}
		HomePoint.getINSTANCE().toHome(player, home);
		return 1;
	}

	private static int setHome(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.asPlayer();
		String home = null;
		try {
			home = StringArgumentType.getString(context, "home name");
		} catch (Exception ignored) {
		}
		HomePoint.getINSTANCE().setHome(player, home);
		return 1;
	}

	private static int removeHome(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.asPlayer();
		String home = null;
		try {
			home = StringArgumentType.getString(context, "home name");
		} catch (Exception ignored) {
		}
		HomePoint.getINSTANCE().removeHome(player, home);
		return 1;
	}
}
