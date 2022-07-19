package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import forpleuvoir.mc.suika_command.teleport.TpaHelper;
import forpleuvoir.mc.suika_command.teleport.WarpPoint;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * 传送点指令
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 WarpCommand
 * <p>
 * 创建时间 2022/7/19 12:48
 *
 * @author forpleuvoir
 */
public class WarpCommand {

	private static final SuggestionProvider<CommandSource> warpSuggest = (context, builder) -> {
		Set<String> keys = new LinkedHashSet<>();
		Set<String> strings = WarpPoint.getINSTANCE().getWarps();
		strings.forEach(s -> {
			keys.add("\"" + s + "\"");
		});
		return ISuggestionProvider.suggest(keys, builder);
	};

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("warp")
				.executes(WarpCommand::warps)
				.then(Commands.argument("warp", StringArgumentType.string())
						.suggests(warpSuggest)
						.executes(WarpCommand::warp)
				).then(Commands.literal("set")
						.then(Commands.argument("warp", StringArgumentType.string())
								.executes(WarpCommand::setWarp)
								.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
						)
				)
				.then(Commands.literal("remove")
						.then(Commands.argument("warp", StringArgumentType.string())
								.suggests(warpSuggest)
								.executes(WarpCommand::removeWarp)
								.requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
						)
				)
		);
	}

	private static int warp(CommandContext<CommandSource> context) throws CommandSyntaxException {
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.asPlayer();
		String warp = StringArgumentType.getString(context, "warp");
		WarpPoint.getINSTANCE().toWarp(player, warp);
		return 1;
	}

	private static int setWarp(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().asPlayer();
		String arg = StringArgumentType.getString(context, "warp");
		WarpPoint.getINSTANCE().setWarp(player, arg);
		return 1;
	}

	private static int removeWarp(CommandContext<CommandSource> context) throws CommandSyntaxException {
		ServerPlayerEntity player = context.getSource().asPlayer();
		String arg = StringArgumentType.getString(context, "warp");
		WarpPoint.getINSTANCE().removeWarp(player, arg);
		return 1;
	}

	private static int warps(CommandContext<CommandSource> context) {
		CommandSource source = context.getSource();
		StringTextComponent text = new StringTextComponent("§6世界传送点 : ");
		if (!WarpPoint.getINSTANCE().getWarps().isEmpty()) {
			WarpPoint.getINSTANCE().getWarps().forEach(e ->
					text.appendSibling(new StringTextComponent("§b" + e)
							.modifyStyle(style -> {
										style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warp " + e));
										style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent("§b点击传送")));
										return style;
									}
							)
					).appendString("  ")
			);
			source.sendFeedback(text, false);
		} else
			source.sendFeedback(new StringTextComponent("§c当前世界没有传送点"), false);
		return 1;
	}

}
