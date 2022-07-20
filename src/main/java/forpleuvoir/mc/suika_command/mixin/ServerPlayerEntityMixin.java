package forpleuvoir.mc.suika_command.mixin;

import forpleuvoir.mc.suika_command.teleport.BackPoint;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

/**
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.mixin
 * <p>
 * 文件名 ServerPlayerEntityMixin
 * <p>
 * 创建时间 2022/7/20 13:29
 *
 * @author forpleuvoir
 */
@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {


	@Shadow
	public abstract void sendMessage(ITextComponent component, UUID senderUUID);

	@Inject(method = "onDeath", at = @At("HEAD"))
	public void onDeath(DamageSource cause, CallbackInfo ci) {
		BackPoint.getINSTANCE().setBack((ServerPlayerEntity) (Object) this);
		sendMessage(new StringTextComponent("§6输入 ")
						.appendString("§b/back").modifyStyle(style ->
								style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/back"))
										.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new StringTextComponent("§b点击此处传送")))
						)
						.appendString(" §6返回死亡点"),
				((ServerPlayerEntity) (Object) this).getUniqueID()
		);
	}

	@Inject(method = "teleport", at = @At("HEAD"))
	public void teleport(ServerWorld newWorld, double x, double y, double z, float yaw, float pitch, CallbackInfo ci) {
		BackPoint.getINSTANCE().setBack((ServerPlayerEntity) (Object) this);
	}

}
