package forpleuvoir.mc.suika_command.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.mixin
 * <p>
 * 文件名 MinecraftServerMixin
 * <p>
 * 创建时间 2022/7/20 17:48
 *
 * @author forpleuvoir
 */
@Mixin(MinecraftServer.class)
public interface MinecraftServerMixin {

	@Accessor("anvilConverterForAnvilFile")
	public SaveFormat.LevelSave getAnvilConverterForAnvilFile();

}
