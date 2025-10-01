package me.SuperRonanCraft.BetterRTP.player.events;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import me.SuperRonanCraft.BetterRTP.BetterRTP;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTPSetupInformation;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_TYPE;
import me.SuperRonanCraft.BetterRTP.references.helpers.HelperRTP;
import me.SuperRonanCraft.BetterRTP.references.rtpinfo.worlds.WorldPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Death {

    static void respawnEvent(PlayerRespawnEvent e) {
        // It's not need to fire respawnEvent when running on folia
        if (BetterRTP.isFolia) return;

        Player p = e.getPlayer();
        WorldPlayer worldPlayer = HelperRTP.getPlayerWorld(new RTPSetupInformation(
                p.getWorld(),
                p, p, false
        ));
        if (worldPlayer.getRTPOnDeath()) {
            HelperRTP.tp(p, p, p.getWorld(), null, RTP_TYPE.FORCED, true, true);
        }
    }

    static void deathEvent(PlayerDeathEvent e) {
        // It must only must fire when running on folia.
        if (!BetterRTP.isFolia) return;

        Player p = e.getPlayer();
        WorldPlayer worldPlayer = HelperRTP.getPlayerWorld(new RTPSetupInformation(
                p.getWorld(),
                p, p, false
        ));

        // Detection respawn
        if (worldPlayer.getRTPOnDeath()) Bukkit.getGlobalRegionScheduler().runAtFixedRate(BetterRTP.getInstance(), (task)->{
            if(!p.isOnline()) {
                if(!task.isCancelled()) task.cancel();
                return;
            }
            if(!p.isDead()){
                HelperRTP.tp(p, p, p.getWorld(), null, RTP_TYPE.FORCED, true, true);
                if(!task.isCancelled()) task.cancel();
            }
        }, 1L, 1L);

    }
}
