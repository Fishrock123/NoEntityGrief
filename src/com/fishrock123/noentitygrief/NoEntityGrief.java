package com.fishrock123.noentitygrief;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.hanging.*;
import org.bukkit.plugin.java.JavaPlugin;
public class NoEntityGrief extends JavaPlugin implements Listener { // Licensed under GPLv3
	@SuppressWarnings("unchecked") private List<String>[] EWs = new ArrayList[6];
	@Override public void onEnable() {
		if (getConfig().options().header() == null) {
			getConfig().options().copyHeader();
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
		EWs[0] = getConfig().getStringList("Endermen");
		EWs[1] = getConfig().getStringList("Sheep");
		EWs[2] = getConfig().getStringList("MobExplosion");
		EWs[3] = getConfig().getStringList("TNT");
		EWs[4] = getConfig().getStringList("Snowman");
		EWs[5] = getConfig().getStringList("Silverfish");
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler public void onBlockChange(EntityChangeBlockEvent e) {
		if (e.getEntity() instanceof Enderman && (EWs[0] == null || !EWs[0].contains(e.getBlock().getLocation().getWorld().getName()))) e.setCancelled(true);
		else if (e.getEntity() instanceof Silverfish && (EWs[5] == null || !EWs[5].contains(e.getBlock().getLocation().getWorld().getName()))) e.setCancelled(true);
		else if (e.getEntity() instanceof Sheep && (EWs[1] == null || !EWs[1].contains(e.getBlock().getLocation().getWorld().getName()))) {
			e.setCancelled(true);
			((Sheep)e.getEntity()).setSheared(false);
		}
	}
	@EventHandler public void onExplosion(EntityExplodeEvent e) {
		if (e.getEntity() instanceof TNTPrimed) { 
			if (EWs[3] == null || !EWs[3].contains(e.getLocation().getWorld().getName())) e.blockList().clear();
		} else if (EWs[2] == null || !EWs[2].contains(e.getLocation().getWorld().getName())) e.blockList().clear();
	}
	@EventHandler public void onPaintingBreak(HangingBreakEvent e) {
		if (e.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION && e.getEntity() instanceof TNTPrimed) { 
			if (EWs[3] == null || !EWs[3].contains(e.getEntity().getWorld().getName())) e.setCancelled(true);
		} else if (e.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION && (EWs[2] == null || !EWs[2].contains(e.getEntity().getWorld().getName()))) e.setCancelled(true);
	}
	@EventHandler public void onEntityForm(EntityBlockFormEvent e) {
		if (EWs[4] == null || !EWs[4].contains(e.getBlock().getLocation().getWorld().getName())) e.setCancelled(true);
	}
}