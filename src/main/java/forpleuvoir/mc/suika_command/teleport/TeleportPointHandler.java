package forpleuvoir.mc.suika_command.teleport;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import forpleuvoir.mc.suika_command.mixin.MinecraftServerMixin;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * 传送点处理器
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 TeleportPointHandler
 * <p>
 * 创建时间 2022/7/20 1:51
 *
 * @author forpleuvoir
 */
@Mod.EventBusSubscriber
public class TeleportPointHandler {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static final String FILE_NAME = "suika_command.json";

	private static Long tickCounter;

	private static String lastSaveContent = "";

	private static MinecraftServer currentServer;

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		tickCounter++;
		if (tickCounter % 30 * 20 == 0) {
			JsonObject saveObject = saveObject();
			if (!gson.toJson(saveObject).equals(lastSaveContent)) {
			CompletableFuture.runAsync(() -> saveFile(currentServer, saveObject));

			}
		}
	}

	@SubscribeEvent
	public static void onServerStart(FMLServerStartingEvent event) {
		final MinecraftServer server = event.getServer();
		TpaHelper.init();
		JsonObject json = loadFile(server);
		if (json != null) {
			try {
				WarpPoint.init(json.getAsJsonObject("warps"));
			} catch (Exception e) {
				WarpPoint.init(new JsonObject());
			}
			try {
				HomePoint.init(json.getAsJsonObject("homes"));
			} catch (Exception e) {
				HomePoint.init(new JsonObject());
			}
			try {
				BackPoint.init(json.getAsJsonObject("backs"));
			} catch (Exception e) {
				BackPoint.init(new JsonObject());
			}
		} else {
			WarpPoint.init(new JsonObject());
			HomePoint.init(new JsonObject());
			BackPoint.init(new JsonObject());
		}
		currentServer = server;
		tickCounter = 0L;
	}

	@SubscribeEvent
	public static void onServerStop(FMLServerStoppingEvent event) {
		final MinecraftServer server = event.getServer();
		saveFile(server, saveObject());
	}


	private static JsonObject saveObject() {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("warps", WarpPoint.getINSTANCE().toJson());
		jsonObject.add("homes", HomePoint.getINSTANCE().toJson());
		jsonObject.add("backs", BackPoint.getINSTANCE().toJson());
		return jsonObject;
	}

	private static void saveFile(MinecraftServer server, JsonObject object) {
		File file = getFile(server);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String s = gson.toJson(object);
			Files.write(s, file, StandardCharsets.UTF_8);
			lastSaveContent = s;
		} catch (Exception ignored) {
		}
	}

	private static JsonObject loadFile(MinecraftServer server) {
		File file = getFile(server);
		System.out.println(file.getAbsolutePath());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			return new JsonParser().parse(new FileReader(file)).getAsJsonObject();
		} catch (Exception e) {
			return null;
		}
	}

	private static File getFile(MinecraftServer server) {
		Path path = ((MinecraftServerMixin) server).getAnvilConverterForAnvilFile().getWorldDir();
		return new File(path.toFile(), FILE_NAME);
	}
}
