package net.citizensnpcs.util.nms;

import java.lang.reflect.Field;

import net.citizensnpcs.util.NMS;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EntityTrackerEntry;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;

import org.bukkit.entity.Player;

public class PlayerlistTrackerEntry extends EntityTrackerEntry {
    public PlayerlistTrackerEntry(Entity entity, int i, int j, boolean flag) {
        super(entity, i, j, flag);
    }

    public PlayerlistTrackerEntry(EntityTrackerEntry entry) {
        this(entry.tracker, entry.b, entry.c, getU(entry));
    }

    @Override
    public void updatePlayer(EntityPlayer entityplayer) {
        if (entityplayer != this.tracker && c(entityplayer)) {
            if (!this.trackedPlayers.contains(entityplayer)
                    && ((entityplayer.u().getPlayerChunkMap().a(entityplayer, this.tracker.ae, this.tracker.ag)) || (this.tracker.attachedToPlayer))) {
                if ((this.tracker instanceof EntityPlayer)) {
                    Player player = ((EntityPlayer) this.tracker).getBukkitEntity();
                    if (!entityplayer.getBukkitEntity().canSee(player)) {
                        return;
                    }
                    entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(
                            EnumPlayerInfoAction.ADD_PLAYER, (EntityPlayer) this.tracker));
                }
            }
        }
        super.updatePlayer(entityplayer);
    }

    private static boolean getU(EntityTrackerEntry entry) {
        try {
            return (Boolean) U.get(entry);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Field U = NMS.getField(EntityTrackerEntry.class, "u");
}