package forpleuvoir.mc.suika_command.teleport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import forpleuvoir.mc.suika_command.common.JsonData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;

/**
 * 返回点
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 BackPoint
 * <p>
 * 创建时间 2022/7/19 23:09
 *
 * @author forpleuvoir
 */
public class BackPoint implements JsonData {
	private static BackPoint INSTANCE;

	public static BackPoint init(JsonElement jsonElement) {
		INSTANCE = new BackPoint(jsonElement);
		return INSTANCE;
	}

	public static BackPoint getINSTANCE() {
		return INSTANCE;
	}

	private BackPoint(JsonElement jsonElement) {
		fromJson(jsonElement);
	}

	private final HashMap<String, Point> data = new HashMap<>();

	public boolean setBack(ServerPlayerEntity player) {
		data.put(player.getUniqueID().toString(), new Point(player));
		return true;
	}

	public void toBack(ServerPlayerEntity player) {
		String uuid = player.getUniqueID().toString();
		if (data.containsKey(uuid)) {
			data.get(uuid).teleport(player);
			player.sendMessage(new StringTextComponent("已传送到上一个标记点").mergeStyle(TextFormatting.GOLD),player.getUniqueID());
		}
		player.sendMessage(new StringTextComponent("没有找到上一个标记点").mergeStyle(TextFormatting.RED),player.getUniqueID());
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
