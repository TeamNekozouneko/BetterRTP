package me.SuperRonanCraft.BetterRTP.references.rtpinfo.worlds;


import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_SHAPE;
import me.SuperRonanCraft.BetterRTP.BetterRTP;
import me.SuperRonanCraft.BetterRTP.references.file.FileOther;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class WorldDefault implements RTPWorld {
    private boolean useWorldborder, RTPOnDeath;
    private int centerX, centerZ, maxXRad, minXRad, maxZRad, minZRad, price, miny, maxy;
    private List<String> Biomes;
    private final HashMap<String, Integer> prices = new HashMap<>();
    private RTP_SHAPE shape;

    public void load() {
        BetterRTP.debug("Loading Defaults...");
        //Setups
        String pre = "Default";
        FileOther.FILETYPE config = BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.CONFIG);
        //Booleans
        useWorldborder = config.getBoolean(pre + ".UseWorldBorder");
        RTPOnDeath = config.getBoolean(pre + ".RTPOnDeath");
        //Integers
        centerX = config.getInt(pre + ".CenterX");
        centerZ = config.getInt(pre + ".CenterZ");
        maxXRad = config.getInt(pre + ".MaxXRadius");
        maxZRad = config.getInt(pre + ".MaxZRadius");
        try {
            shape = RTP_SHAPE.valueOf(config.getString(pre + ".Shape").toUpperCase());
        } catch (Exception e) {
            shape = RTP_SHAPE.SQUARE;
        }
        if (maxXRad <= 0) {
            BetterRTP.getInstance().getLogger().warning("WARNING! Default Maximum X radius of '" + maxXRad + "' is not allowed! Value set to '1000'");
            maxXRad = 1000;
        }
        if (maxZRad <= 0) {
            BetterRTP.getInstance().getLogger().warning("WARNING! Default Maximum X radius of '" + maxXRad + "' is not allowed! Value set to '1000'");
            maxXRad = 1000;
        }
        minXRad = config.getInt(pre + ".MinXRadius");
        minZRad = config.getInt(pre + ".MinZRadius");
        if (minXRad < 0 || minXRad >= maxXRad) {
            BetterRTP.getInstance().getLogger().warning("The Default MinXRadius of '" + minXRad + "' is not allowed! Value set to '0'");
            minXRad = 0;
        }
        if (minZRad < 0 || minZRad >= maxZRad) {
            BetterRTP.getInstance().getLogger().warning("The Default MinXRadius of '" + minXRad + "' is not allowed! Value set to '0'");
            minXRad = 0;
        }
        prices.clear();
        if (BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.ECO).getBoolean("Economy.Enabled")) {
            price = BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.ECO).getInt("Economy.Price");
            if (BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.ECO).getBoolean("CustomWorlds.Enabled")) {
                List<Map<?, ?>> world_map = BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.ECO).getMapList("CustomWorlds.Prices");
                for (Map<?, ?> m : world_map)
                    for (Map.Entry<?, ?> entry : m.entrySet()) {
                        String _world = entry.getKey().toString();
                        //System.out.println("Custom World Price " + _world + ":" + entry.getValue().toString());
                        if (entry.getValue().getClass() == Integer.class)
                            prices.put(_world, Integer.parseInt((entry.getValue().toString())));
                    }
            }
        } else
            price = 0;
        //Other
        this.Biomes = config.getStringList(pre + ".Biomes");
        this.miny = config.getInt(pre + ".MinY");
        if (miny > 0) {
            miny = 0;
            BetterRTP.getInstance().getLogger().warning("Warning! Default MinY value is solely for 1.17+ support, and can only be negative!");
        }
        this.maxy = config.getInt(pre + ".MaxY");
        if (maxy < 64) {
            maxy = 320;
            BetterRTP.getInstance().getLogger().warning("Warning! Default MaxY value is below water level (64)! Reset to default 320!");
        }
        //Debugger
        if (BetterRTP.getInstance().getSettings().isDebug()) {
            Logger log = BetterRTP.getInstance().getLogger();
            log.info("- UseWorldBorder: " + this.useWorldborder);
            log.info("- RTPOnDeath: " + this.RTPOnDeath);
            log.info("- CenterX: " + this.centerX);
            log.info("- CenterZ: " + this.centerZ);
            log.info("- MaxXRadius: " + this.maxXRad);
            log.info("- MinXRadius: " + this.minXRad);
            log.info("- MaxZRadius: " + this.maxZRad);
            log.info("- MinZRadius: " + this.minZRad);
            log.info("- Price: " + this.price);
            log.info("- MinY: " + this.miny);
            log.info("- MaxY: " + this.maxy);
            log.info("- Cooldown (default): " + getCooldown());
        }
    }

    @Override
    public boolean getUseWorldborder() {
        return useWorldborder;
    }

    @Override
    public int getCenterX() {
        return centerX;
    }

    @Override
    public int getCenterZ() {
        return centerZ;
    }

    @Override
    public int getMaxXRadius() {
        return maxXRad;
    }

    @Override
    public int getMinXRadius() {
        return minXRad;
    }

    @Override
    public int getMaxZRadius() {
        return maxZRad;
    }

    @Override
    public int getMinZRadius() {
        return minZRad;
    }

    public int getPrice(String world) {
        return prices.getOrDefault(world, getPrice());
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public List<String> getBiomes() {
        return Biomes;
    }

    @NotNull @Override
    public World getWorld() {
        return null;
    }

    @Override
    public RTP_SHAPE getShape() {
        return shape;
    }

    @Override
    public int getMinY() {
        return miny;
    }

    @Override
    public int getMaxY() {
        return maxy;
    }

    @Override
    public long getCooldown() {
        return BetterRTP.getInstance().getCooldowns().getDefaultCooldownTime();
    }

    @Override
    public boolean getRTPOnDeath() {
        return RTPOnDeath;
    }
}
