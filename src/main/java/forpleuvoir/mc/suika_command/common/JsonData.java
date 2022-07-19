package forpleuvoir.mc.suika_command.common;

import com.google.gson.JsonElement;

/**
 * json数据
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.common
 * <p>
 * 文件名 JsonData
 * <p>
 * 创建时间 2022/7/19 13:34
 *
 * @author forpleuvoir
 */
public interface JsonData {

	JsonElement toJson();

	void fromJson(JsonElement jsonElement);
}
