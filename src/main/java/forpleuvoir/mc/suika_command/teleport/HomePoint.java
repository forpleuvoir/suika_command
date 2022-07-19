package forpleuvoir.mc.suika_command.teleport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import forpleuvoir.mc.suika_command.common.JsonData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Home点
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 HomePoint
 * <p>
 * 创建时间 2022/7/19 12:54
 *
 * @author forpleuvoir
 */
public class HomePoint implements JsonData {

	private static HomePoint INSTANCE;

	public static HomePoint init(JsonElement jsonElement) {
		INSTANCE = new HomePoint(jsonElement);
		return INSTANCE;
	}

	public static HomePoint getINSTANCE() {
		return INSTANCE;
	}

	private HomePoint(JsonElement jsonElement) {
		fromJson(jsonElement);
	}

	private final HashMap<String, LinkedHashMap<String, Point>> data = new HashMap<>();

	public Set<String> getHomes(ServerPlayerEntity player) {
		String uuid = player.getUniqueID().toString();
		if (data.containsKey(uuid)) {
			return data.get(uuid).keySet();
		}
		return new LinkedHashSet<>();
	}

	public void setHome(ServerPlayerEntity player, String homeName) {
		String uuid = player.getUniqueID().toString();
		if (homeName == null) homeName = "home";
		if (data.containsKey(uuid)) {
			if (data.get(uuid).size() >= 10) {
				player.sendMessage(new StringTextComponent("当前家数量达到上限").mergeStyle(TextFormatting.RED), player.getUniqueID());
			}
			data.get(uuid).put(homeName, new Point(player));
			player.sendMessage(new StringTextComponent("§6已设置名为 §b" + homeName + " §6的家"), player.getUniqueID());
		} else {
			LinkedHashMap<String, Point> homes = new LinkedHashMap<>();
			homes.put(homeName, new Point(player));
			data.put(uuid, homes);
			player.sendMessage(new StringTextComponent("§6已设置名为 §b" + homeName + " §6的家"), player.getUniqueID());
		}
	}

	public void setHome(ServerPlayerEntity player) {
		setHome(player, "home");
	}

	public void removeHome(ServerPlayerEntity player, String homeName) {
		String uuid = player.getUniqueID().toString();
		if (homeName == null) homeName = "home";
		if (data.containsKey(uuid)) {
			if (data.get(uuid).isEmpty()) {
				player.sendMessage(new StringTextComponent("你还没有设置家").mergeStyle(TextFormatting.RED), player.getUniqueID());
			}
			if (data.get(uuid).containsKey(homeName)) {
				data.get(uuid).remove(homeName);
				player.sendMessage(new StringTextComponent("§6已删除 §b" + homeName + " §6的家"), player.getUniqueID());
			} else {
				player.sendMessage(new StringTextComponent("§c没有找到名为 §b" + homeName + " §c的家"), player.getUniqueID());
			}
		} else {
			player.sendMessage(new StringTextComponent("§c没有找到名为 §b" + homeName + " §c的家").mergeStyle(TextFormatting.RED), player.getUniqueID());
		}
	}

	public void toHome(ServerPlayerEntity player, String homeName) {
		String uuid = player.getUniqueID().toString();
		if (homeName == null) homeName = "home";
		if (data.containsKey(uuid)) {
			LinkedHashMap<String, Point> homes = data.get(uuid);
			if (homes.containsKey(homeName)) {
				homes.get(homeName).teleport(player);
				player.sendMessage(new StringTextComponent("§6已传送到名为 §b" + homeName + " §6的家").mergeStyle(TextFormatting.GOLD), player.getUniqueID());
			} else {
				player.sendMessage(new StringTextComponent("§c没有找到名为 §b" + homeName + " §c的家").mergeStyle(TextFormatting.RED), player.getUniqueID());
			}
		} else {
			player.sendMessage(new StringTextComponent("§c你还没有设置家").mergeStyle(TextFormatting.RED), player.getUniqueID());
		}
	}

	public void toHome(ServerPlayerEntity player) {toHome(player, "home");}

	@Override
	public JsonElement toJson() {
		JsonObject object = new JsonObject();
		data.forEach((key, value) -> {
			JsonObject homes = new JsonObject();
			value.forEach((k, v) -> {
				homes.add(k, v.toJson());
			});
			object.add(key, homes);
		});
		return object;
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		data.clear();
		jsonElement.getAsJsonObject().entrySet().forEach(entry -> {
			LinkedHashMap<String, Point> homes = new LinkedHashMap<>();
			entry.getValue().getAsJsonObject().entrySet().forEach(e -> {
				homes.put(e.getKey(), new Point(e.getValue()));
			});
			data.put(entry.getKey(), homes);
		});
	}
}
