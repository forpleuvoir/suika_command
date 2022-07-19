package forpleuvoir.mc.suika_command.teleport;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import forpleuvoir.mc.suika_command.common.JsonData;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 传送点
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 Point
 * <p>
 * 创建时间 2022/7/19 12:57
 *
 * @author forpleuvoir
 */
public class Point implements JsonData {

	private Vector3d position;

	private String dimension;

	private float yaw;

	private float pitch;

	public Point(Vector3d position, String dimension, float yaw, float pitch) {
		this.position = position;
		this.dimension = dimension;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	public Point(JsonElement jsonElement) {
		this.fromJson(jsonElement);
	}

	public Point(@Nonnull ServerPlayerEntity player) {
		position = player.getPositionVec();
		dimension = player.getServerWorld().getDimensionKey().getLocation().toString();
		yaw = player.rotationYaw;
		pitch = player.rotationPitch;

	}

	@Override
	public JsonElement toJson() {
		JsonObject object = new JsonObject();
		{
			JsonObject position1 = new JsonObject();
			position1.addProperty("x", position.x);
			position1.addProperty("y", position.y);
			position1.addProperty("z", position.z);
			object.add("position", position1);
		}
		object.addProperty("dimension", dimension);
		object.addProperty("yaw", yaw);
		object.addProperty("pitch", pitch);
		return object;
	}

	@Override
	public void fromJson(JsonElement jsonElement) {
		JsonObject object = jsonElement.getAsJsonObject();
		{
			JsonObject position1 = object.get("position").getAsJsonObject();
			double x = position1.get("x").getAsDouble();
			double y = position1.get("y").getAsDouble();
			double z = position1.get("z").getAsDouble();
			position = new Vector3d(x, y, z);
		}
		dimension = object.get("dimension").getAsString();
		yaw = object.get("yaw").getAsFloat();
		pitch = object.get("pitch").getAsFloat();
	}

	public void teleport(@Nonnull ServerPlayerEntity player) {
		ResourceLocation dim = new ResourceLocation(dimension);
		RegistryKey<World> world = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, dim);
		player.teleport(Objects.requireNonNull(Objects.requireNonNull(player.getServer()).getWorld(world)), position.x, position.y, position.z, yaw, pitch);
	}


}
