package forpleuvoir.mc.suika_command.teleport;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import java.util.HashMap;

/**
 * tp助手
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 TpaHelper
 * <p>
 * 创建时间 2022/7/19 23:40
 *
 * @author forpleuvoir
 */
public class TpaHelper {

	private static TpaHelper INSTANCE;

	public static TpaHelper init() {
		INSTANCE = new TpaHelper();
		return INSTANCE;
	}

	public static TpaHelper getINSTANCE() {
		return INSTANCE;
	}

	private final HashMap<ServerPlayerEntity, Tpa> data = new HashMap<>();

	public void tpaccept(ServerPlayerEntity player, long nowTime) {
		if (data.containsKey(player)) {
			if (data.get(player).tp(nowTime)) {
				player.sendMessage(new StringTextComponent("已传送到目标点").mergeStyle(TextFormatting.GOLD)
						, player.getUniqueID()
				);
				data.remove(player);
				return;
			}
		}
		player.sendMessage(new StringTextComponent("没有待接受的传送").mergeStyle(TextFormatting.RED)
				, player.getUniqueID()
		);
	}

	public void sendTpa(ServerPlayerEntity sender, ServerPlayerEntity target, long nowTime) {
		data.put(target, new Tpa(sender, target, nowTime));
		target.sendMessage(new StringTextComponent("§6玩家 ")
						.appendSibling(sender.getName()).mergeStyle(TextFormatting.AQUA)
						.appendString(" §6请求传送到你身边")
				, sender.getUniqueID()
		);
		target.sendMessage(new StringTextComponent("§6120秒内点击 ")
						.appendSibling(new StringTextComponent(" 接受传送").modifyStyle(style -> style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")))
								.mergeStyle(TextFormatting.GREEN)
						)
				, sender.getUniqueID());

		sender.sendMessage(new StringTextComponent("传送请求已发送").mergeStyle(TextFormatting.GOLD), sender.getUniqueID());
	}

	public void sendTpahere(ServerPlayerEntity sender, ServerPlayerEntity target, long nowTime) {
		data.put(target, new Tpa(target, sender, nowTime));
		target.sendMessage(new StringTextComponent("§6玩家 ")
						.appendSibling(sender.getName()).mergeStyle(TextFormatting.AQUA)
						.appendString(" §6请求请将传送到TA身边")
				, sender.getUniqueID()
		);
		target.sendMessage(new StringTextComponent("§6120秒内点击 ")
						.appendSibling(new StringTextComponent(" 接受传送").modifyStyle(style -> style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept")))
								.mergeStyle(TextFormatting.GREEN)
						)
				, sender.getUniqueID());

		sender.sendMessage(new StringTextComponent("传送请求已发送").mergeStyle(TextFormatting.GOLD), sender.getUniqueID());
	}

}
