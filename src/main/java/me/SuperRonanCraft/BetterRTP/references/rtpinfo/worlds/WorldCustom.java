package me.SuperRonanCraft.BetterRTP.references.rtpinfo.worlds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import me.SuperRonanCraft.BetterRTP.BetterRTP;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_SHAPE;
import me.SuperRonanCraft.BetterRTP.references.file.FileOther;
import me.SuperRonanCraft.BetterRTP.references.messages.Message_RTP;

public class WorldCustom implements RTPWorld, RTPWorld_Defaulted {
    public World world;
    private boolean useWorldborder, RTPOnDeath;
    private int centerX, centerZ, maxXRad, minXRad, maxZRad, minZRad, price, miny, maxy;
    private long cooldown;
    private List<String> biomes;
    private RTP_SHAPE shape;

    public WorldCustom(World world) {
        //String pre = "CustomWorlds.";
        FileOther.FILETYPE config = BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.CONFIG);
        List<Map<?, ?>> map = config.getMapList("CustomWorlds");
        this.world = world;

        //Set Defaults
        setupDefaults();

        //Find Custom World and cache values
        for (Map<?, ?> m : map) {
            for (Map.Entry<?, ?> entry : m.entrySet()) {
                String key = entry.getKey().toString();
                if (!key.equals(world.getName()))
                    continue;
                Map<?, ?> test = ((Map<?, ?>) m.get(key));
                if (test == null)
                    continue;
                if (test.get("UseWorldBorder") != null) {
                    if (test.get("UseWorldBorder").getClass() == Boolean.class) {
                        useWorldborder = Boolean.parseBoolean(test.get("UseWorldBorder").toString());
                        BetterRTP.debug("- UseWorldBorder: " + this.useWorldborder);
                    }
                }
                if (test.get("RTPOnDeath") != null) {
                    if (test.get("RTPOnDeath").getClass() == Boolean.class) {
                        RTPOnDeath = Boolean.parseBoolean(test.get("RTPOnDeath").toString());
                        BetterRTP.debug("- RTPOnDeath: " + this.RTPOnDeath);
                    }
                }
                if (test.get("CenterX") != null) {
                    if (test.get("CenterX").getClass() == Integer.class) {
                        centerX = Integer.parseInt((test.get("CenterX")).toString());
                        BetterRTP.debug("- CenterX: " + this.centerX);
                    }
                }
                if (test.get("CenterZ") != null) {
                    if (test.get("CenterZ").getClass() == Integer.class) {
                        centerZ = Integer.parseInt((test.get("CenterZ")).toString());
                        BetterRTP.debug("- CenterZ: " + this.centerZ);
                    }
                }
                if (test.get("MaxXRadius") != null) {
                    if (test.get("MaxXRadius").getClass() == Integer.class) {
                        maxXRad = Integer.parseInt((test.get("MaxXRadius")).toString());
                        BetterRTP.debug("- MaxXRadius: " + this.maxXRad);
                    }
                    if (maxXRad <= 0) {
                        Message_RTP.sms(Bukkit.getConsoleSender(),
                                "WARNING! Custom world '" + world + "' Maximum X radius of '" + maxXRad + "' is not allowed! Set to default value!");
                        maxXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxXRadius();
                    }
                }
                if (test.get("MinXRadius") != null) {
                    if (test.get("MinXRadius").getClass() == Integer.class) {
                        minXRad = Integer.parseInt((test.get("MinXRadius")).toString());
                        BetterRTP.debug("- MinXRadius: " + this.minXRad);
                    }
                    if (minXRad < 0 || minXRad >= maxXRad) {
                        Message_RTP.sms(Bukkit.getConsoleSender(),
                                "WARNING! Custom world '" + world + "' Minimum X radius of '" + minXRad + "' is not allowed! Set to default value!");
                        minXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinXRadius();
                        if (minXRad >= maxXRad)
                            maxXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxXRadius();
                    }
                }
                if (test.get("MaxZRadius") != null) {
                    if (test.get("MaxZRadius").getClass() == Integer.class) {
                        maxZRad = Integer.parseInt((test.get("MaxZRadius")).toString());
                        BetterRTP.debug("- MaxZRadius: " + this.maxZRad);
                    }
                    if (maxZRad <= 0) {
                        Message_RTP.sms(Bukkit.getConsoleSender(),
                                "WARNING! Custom world '" + world + "' Maximum Z radius of '" + maxXRad + "' is not allowed! Set to default value!");
                        maxZRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxZRadius();
                    }
                }
                if (test.get("MinXRadius") != null) {
                    if (test.get("MinXRadius").getClass() == Integer.class) {
                        minXRad = Integer.parseInt((test.get("MinXRadius")).toString());
                        BetterRTP.debug("- MinXRadius: " + this.minXRad);
                    }
                    if (minXRad < 0 || minXRad >= maxXRad) {
                        Message_RTP.sms(Bukkit.getConsoleSender(),
                                "WARNING! Custom world '" + world + "' Minimum X radius of '" + minXRad + "' is not allowed! Set to default value!");
                        minXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinXRadius();
                        if (minXRad >= maxXRad)
                            maxXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxXRadius();
                    }
                }
                if (test.get("MinZRadius") != null) {
                    if (test.get("MinZRadius").getClass() == Integer.class) {
                        minZRad = Integer.parseInt((test.get("MinZRadius")).toString());
                        BetterRTP.debug("- MinZRadius: " + this.minZRad);
                    }
                    if (minZRad < 0 || minZRad >= maxZRad) {
                        Message_RTP.sms(Bukkit.getConsoleSender(),
                                "WARNING! Custom world '" + world + "' Minimum Z radius of '" + minZRad + "' is not allowed! Set to default value!");
                        minXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinZRadius();
                        if (minZRad >= maxZRad)
                            maxZRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxZRadius();
                    }
                }
                if (test.get("Biomes") != null) {
                    if (test.get("Biomes").getClass() == ArrayList.class) {
                        this.biomes = new ArrayList<String>((ArrayList) test.get("Biomes"));
                        BetterRTP.debug("- Biomes: " + this.biomes);
                    }
                }
                if (BetterRTP.getInstance().getFiles().getType(FileOther.FILETYPE.ECO).getBoolean("Economy.Enabled"))
                    if (test.get("Price") != null) {
                        if (test.get("Price").getClass() == Integer.class)
                            this.price = Integer.parseInt(test.get("Price").toString());
                        BetterRTP.debug("- Price: " + this.price);
                    }
                if (test.get("Shape") != null) {
                    if (test.get("Shape").getClass() == String.class) {
                        try {
                            this.shape = RTP_SHAPE.valueOf(test.get("Shape").toString().toUpperCase());
                            BetterRTP.debug("- Shape: " + this.shape);
                        } catch (Exception e) {
                            //Invalid shape
                        }
                    }
                }
                if (test.get("MinY") != null) {
                    if (test.get("MinY").getClass() == Integer.class) {
                        this.miny = Integer.parseInt((test.get("MinY")).toString());
                        BetterRTP.debug("- MinY: " + this.miny);
                    }
                }
                if (test.get("MaxY") != null) {
                    if (test.get("MaxY").getClass() == Integer.class) {
                        this.maxy = Integer.parseInt((test.get("MaxY")).toString());
                        BetterRTP.debug("- MaxY: " + this.maxy);
                    }
                }
                if (test.get("Cooldown") != null) {
                    if (test.get("Cooldown").getClass() == Integer.class || test.get("Cooldown").getClass() == Long.class) {
                        this.cooldown = Long.parseLong((test.get("Cooldown")).toString());
                        BetterRTP.debug("- Cooldown: " + this.cooldown);
                    }
                }
            }
        }

        if (maxXRad <= 0) {
            Message_RTP.sms(Bukkit.getConsoleSender(),
                    "WARNING! Custom world '" + world + "' Maximum radius of '" + maxXRad + "' is not allowed! Set to default value!");
            maxXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMaxXRadius();
        }

        if (minXRad < 0 || minXRad >= maxXRad) {
            Message_RTP.sms(Bukkit.getConsoleSender(),
                    "WARNING! Custom world '" + world + "' Minimum radius of '" + minXRad + "' is not allowed! Set to default value!");
            minXRad = BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinXRadius();
        }
    }

    public WorldCustom(World world, RTPWorld rtpWorld) {
        setAllFrom(rtpWorld);
        this.world = world;
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

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public List<String> getBiomes() {
        return biomes;
    }

    @NotNull
    @Override
    public World getWorld() {
        return world;
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
        return cooldown;
    }

    @Override
    public boolean getRTPOnDeath() {
        return RTPOnDeath;
    }

    //Setters
    @Override
    public void setUseWorldBorder(boolean value) {
        this.useWorldborder = value;
    }

    @Override
    public void setCenterX(int value) {
        this.centerX = value;
    }

    @Override
    public void setCenterZ(int value) {
        this.centerZ = value;
    }

    @Override
    public void setMaxXRadius(int value) {
        this.maxXRad = value;
    }

    @Override
    public void setMinXRadius(int value) {
        this.minXRad = value;
    }

    @Override
    public void setMaxZRadius(int value) {
        this.maxZRad = value;
    }

    @Override
    public void setMinZRadius(int value) {
        this.minZRad = value;
    }

    @Override
    public void setPrice(int value) {
        this.price = value;
    }

    @Override
    public void setBiomes(List<String> value) {
        this.biomes = value;
    }

    @Override
    public void setWorld(World value) {
        this.world = value;
    }

    @Override
    public void setShape(RTP_SHAPE value) {
        this.shape = value;
    }

    @Override
    public void setMinY(int value) {
        this.miny = value;
    }

    @Override
    public void setMaxY(int value) {
        this.maxy = value;
    }

    @Override
    public void setCooldown(long value) {
        this.cooldown = value;
    }

    @Override
    public void setRTPOnDeath(boolean value) {
        this.RTPOnDeath = value;
    }
}
