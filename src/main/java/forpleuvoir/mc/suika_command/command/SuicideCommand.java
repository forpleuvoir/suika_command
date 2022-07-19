package forpleuvoir.mc.suika_command.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;

import java.util.Objects;

/**
 * 自杀指令
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.command
 * <p>
 * 文件名 SuicideCommand
 * <p>
 * 创建时间 2022/7/19 12:44
 *
 * @author forpleuvoir
 */
public class SuicideCommand {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("suicide").executes(context -> {
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.asPlayer();
			player.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
			context.getSource().sendFeedback(new StringTextComponent("§6你结束了自己的生命"), false);
			Objects.requireNonNull(player.getServer()).getPlayerList()
					.func_232641_a_(new StringTextComponent("§b" + player.getName().getString() + " §c进入了幻想乡"), ChatType.CHAT, player.getUniqueID());
			return 1;
		}));
	}
}
