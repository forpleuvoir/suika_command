package forpleuvoir.mc.suika_command.teleport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import forpleuvoir.mc.suika_command.common.JsonData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.Set;

/**
 * 传送点
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 WarpPoint
 * <p>
 * 创建时间 2022/7/19 23:24
 *
 * @author forpleuvoir
 */
public class WarpPoint implements JsonData {

	private static WarpPoint INSTANCE;

	public static WarpPoint init(JsonElement jsonElement) {
		INSTANCE = new WarpPoint(jsonElement);
		return INSTANCE;
	}

	public static WarpPoint getINSTANCE() {
		return INSTANCE;
	}

	private WarpPoint(JsonElement jsonElement) {
		fromJson(jsonElement);
	}

	private final HashMap<String, Point> data = new HashMap<>();


	public Set<String> getWarps() {
		return data.keySet();
	}

	public void setWarp(ServerPlayerEntity player, String name) {
		data.put(name, new Point(player));
		player.sendMessage(new StringTextComponent("§6已设置名为 §b" + name + "§6 的传送点"), player.getUniqueID());
	}

	public void removeWarp(ServerPlayerEntity player, String name) {
		if (data.containsKey(name)) {
			data.remove(name);
			player.sendMessage(new StringTextComponent("§6已经删除名为 §b" + name + "§6 的传送点"), player.getUniqueID());
			return;
		}
		player.sendMessage(new StringTextComponent("§c没有找到 §b" + name + "§c 的传送点"), player.getUniqueID());
	}

	public void toWarp(ServerPlayerEntity player, String name) {
		if (data.containsKey(name)) {
			data.get(name).teleport(player);
			player.sendMessage(new StringTextComponent("§6已将你传送到 §b" + name), player.getUniqueID());
		} else {
			player.sendMessage(new StringTextComponent("§c没有找到 §b" + name + "§c 的传送点"), player.getUniqueID());
		}
	}

	@Override
	public JsonElement toJson() {
		JsonObject object = new JsonObject();
		data.forEach((key, value) -> {
			object.add(key, value.toJson());
		});
		return object;
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		data.clear();
		jsonElement.getAsJsonObject().entrySet().forEach(entry -> {
			data.put(entry.getKey(), new Point(entry.getValue()));
		});
	}
}
