package forpleuvoir.mc.suika_command.teleport;

import com.google.gson.JsonElement;
import forpleuvoir.mc.suika_command.common.JsonData;
import net.minecraft.util.math.vector.Vector3d;

import java.util.HashMap;
import java.util.LinkedHashMap;

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

	public static HomePoint init() {
		return INSTANCE;
	}

	public static HomePoint getINSTANCE() {
		return INSTANCE;
	}

	private final HashMap<String, LinkedHashMap<String, Point>> data = new HashMap<>();


	@Override
	public JsonElement toJson() {
		return null;
	}

	@Override
	public void fromJson(JsonElement jsonElement) {

	}
}
