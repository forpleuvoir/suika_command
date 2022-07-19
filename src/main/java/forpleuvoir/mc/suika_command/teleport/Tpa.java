package forpleuvoir.mc.suika_command.teleport;

import net.minecraft.entity.player.ServerPlayerEntity;

/**
 * 玩家传送
 * <p>
 * 项目名 suika_command
 * <p>
 * 包名 forpleuvoir.mc.suika_command.teleport
 * <p>
 * 文件名 Tpa
 * <p>
 * 创建时间 2022/7/19 23:30
 *
 * @author forpleuvoir
 */
public class Tpa {

	private final static long TIME_OUT = 120 * 20;

	/**
	 * 需要被TP的玩家
	 */
	private final ServerPlayerEntity tpPlayer;

	/**
	 * 目标玩家
	 */
	private final ServerPlayerEntity targetPlayer;

	/**
	 * 过期时间
	 */
	private final long expireTime;

	public Tpa(ServerPlayerEntity tpPlayer, ServerPlayerEntity targetPlayer, long nowTime) {
		this.tpPlayer = tpPlayer;
		this.targetPlayer = targetPlayer;
		this.expireTime = nowTime + TIME_OUT;
	}


	public boolean tp(long nowTime) {
		if (canTp(nowTime)) {
			new Point(targetPlayer).teleport(tpPlayer);
			return true;
		} else return false;
	}

	private boolean canTp(long nowTime) {
		return this.expireTime > nowTime;
	}


}
