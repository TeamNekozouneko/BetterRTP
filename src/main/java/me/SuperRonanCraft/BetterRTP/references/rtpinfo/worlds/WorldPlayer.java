package me.SuperRonanCraft.BetterRTP.references.rtpinfo.worlds;

import lombok.Getter;
import me.SuperRonanCraft.BetterRTP.player.commands.RTP_SETUP_TYPE;
import me.SuperRonanCraft.BetterRTP.BetterRTP;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTPSetupInformation;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_PlayerInfo;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_SHAPE;
import me.SuperRonanCraft.BetterRTP.player.rtp.RTP_TYPE;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WorldPlayer implements RTPWorld, RTPWorld_Defaulted {
    private boolean useWorldborder;
    private boolean RTPOnDeath;
    private int CenterX, CenterZ, maxXRad, minXRad, maxZRad, minZRad, price, min_y, max_y;
    private long cooldown;
    private List<String> Biomes;
    @Getter private final Player player;
    @Getter private final CommandSender sendi;
    @Getter private final RTP_PlayerInfo playerInfo;
    @Getter private final RTP_TYPE rtp_type;
    private final World world;
    private WORLD_TYPE world_type;
    public WorldPermissionGroup config = null;
    private RTP_SHAPE shape;
    public RTP_SETUP_TYPE setup_type = RTP_SETUP_TYPE.DEFAULT;
    public String setup_name;

    @Getter private boolean setup = false;

    public WorldPlayer(RTPSetupInformation setup_info) {
        this.sendi = setup_info.getSender();
        this.player = setup_info.getPlayer();
        this.world = setup_info.getWorld();
        this.rtp_type = setup_info.getRtp_type();
        this.playerInfo = setup_info.getPlayerInfo();
    }

    public void setup(String setup_name, RTPWorld world, List<String> biomes) {
        if (world instanceof WorldLocation) {
            setup_type = RTP_SETUP_TYPE.LOCATION;
        } else if (world instanceof WorldCustom) {
            setup_type = RTP_SETUP_TYPE.CUSTOM_WORLD;
        } else if (world instanceof WorldPermissionGroup)
            setup_type = RTP_SETUP_TYPE.PERMISSIONGROUP;
        this.setup_name = setup_name;
        setUseWorldBorder(world.getUseWorldborder());
        setRTPOnDeath(world.getRTPOnDeath());

        //BetterRTP.getInstance().getLogger().info("WorldPlayer Center x: " + CenterX);
        setCenterX(world.getCenterX());
        //BetterRTP.getInstance().getLogger().info("set to " + world.getCenterX());
        //BetterRTP.getInstance().getLogger().info("is now " + CenterX);
        setCenterZ(world.getCenterZ());
        setMaxXRadius(world.getMaxXRadius());
        setMinXRadius(world.getMinXRadius());
        setMaxZRadius(world.getMaxZRadius());
        setMinZRadius(world.getMinZRadius());
        setShape(world.getShape());
        if (world instanceof WorldDefault)
            setPrice(((WorldDefault) world).getPrice(getWorld().getName()));
        else
            setPrice(world.getPrice());
        List<String> list = new ArrayList<>(world.getBiomes());
        if (biomes != null) {
            list.clear();
            list.addAll(biomes);
        }
        setBiomes(list);
        //World border protection
        if (getUseWorldborder()) {
            WorldBorder border = getWorld().getWorldBorder();
            int _borderRad = (int) border.getSize() / 2;
            if (getMaxXRadius() > _borderRad)
                setMaxXRadius(_borderRad);
            if (getMaxZRadius() > _borderRad)
                setMaxZRadius(_borderRad);
            setCenterX(border.getCenter().getBlockX());
            setCenterZ(border.getCenter().getBlockZ());
        }
        //Make sure our borders will not cause an invalid integer
        if (getMaxXRadius() <= getMinXRadius()) {
            setMinXRadius(BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinXRadius());
            if (getMaxXRadius() <= getMinXRadius())
                setMinXRadius(0);
        }
        if (getMaxZRadius() <= getMinZRadius()) {
            setMinZRadius(BetterRTP.getInstance().getRTP().getRTPdefaultWorld().getMinZRadius());
            if (getMaxZRadius() <= getMinZRadius())
                setMinZRadius(0);
        }

        //MinY
        setMinY(world.getMinY());
        setMaxY(world.getMaxY());
        //Cooldown
        setCooldown(world.getCooldown());
        setup = true;

        //BetterRTP.getInstance().getLogger().info("WorldPlayer Center x: " + CenterX);
    }

    public static boolean checkIsValid(Location loc, RTPWorld rtpWorld) { //Will check if a previously given location is valid
        if (loc.getWorld() != rtpWorld.getWorld())
            return false;
        int _xLMax = rtpWorld.getCenterX() - rtpWorld.getMaxXRadius(); //I|-||
        int _xLMin = rtpWorld.getCenterX() - rtpWorld.getMinXRadius(); //|I-||
        int _xRMax = rtpWorld.getCenterX() + rtpWorld.getMaxXRadius(); //||-|I
        int _xRMin = rtpWorld.getCenterX() + rtpWorld.getMinXRadius(); //||-I|
        int _xLoc = loc.getBlockX();
        if (_xLoc < _xLMax || (_xLoc > _xLMin && _xLoc < _xRMin) || _xLoc > _xRMax)
            return false;
        int _zLMax = rtpWorld.getCenterZ() - rtpWorld.getMaxZRadius(); //I|-||
        int _zLMin = rtpWorld.getCenterZ() - rtpWorld.getMinZRadius(); //|I-||
        int _zRMax = rtpWorld.getCenterZ() + rtpWorld.getMaxZRadius(); //||-|I
        int _zRMin = rtpWorld.getCenterZ() + rtpWorld.getMinZRadius(); //||-I|
        int _zLoc = loc.getBlockX();
        return _zLoc >= _zLMax && (_zLoc <= _zLMin || _zLoc >= _zRMin) && _zLoc <= _zRMax;
    }

    @NotNull
    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean getUseWorldborder() {
        return useWorldborder;
    }

    @Override
    public int getCenterX() {
        return CenterX;
    }

    @Override
    public int getCenterZ() {
        return CenterZ;
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
        return Biomes;
    }

    @Override
    public RTP_SHAPE getShape() {
        return shape;
    }

    @Override
    public void setUseWorldBorder(boolean bool) {
        useWorldborder = bool;
    }

    @Override public void setRTPOnDeath(boolean bool) {
        RTPOnDeath = bool;
    }

    @Override
    public void setCenterX(int x) {
        CenterX = x;
    }

    @Override
    public void setCenterZ(int z) {
        CenterZ = z;
    }

    //Modifiable
    public void setMaxXRadius(int max) {
        maxXRad = max;
    }

    public void setMinXRadius(int min) {
        minXRad = min;
    }

    public void setMaxZRadius(int max) {
        maxZRad = max;
    }

    public void setMinZRadius(int min) {
        minZRad = min;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    //
    public void setBiomes(List<String> biomes) {
        this.Biomes = biomes;
    }

    @Override
    public void setWorld(World value) {
        //Can't override this one buddy
    }

    //Custom World type
    public void setWorldtype(WORLD_TYPE type) {
        this.world_type = type;
    }

    public void setShape(RTP_SHAPE shape) {
        this.shape = shape;
    }

    public void setMinY(int value) {
        this.min_y = value;
    }

    public void setMaxY(int value) {
        this.max_y = value;
    }

    @Override
    public void setCooldown(long value) {
        this.cooldown = value;
    }

    public WorldPermissionGroup getConfig() {
        return this.config;
    }

    public WORLD_TYPE getWorldtype() {
        return this.world_type;
    }

    public int getMinY() {
        return min_y;
    }

    @Override
    public int getMaxY() {
        return max_y;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override public boolean getRTPOnDeath() {
        return RTPOnDeath;
    }

}
