package Fishrock123.NoEntityGrief;
import java.util.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
public class NoEntityGrief extends JavaPlugin implements Listener {
	private Map<Class<? extends Entity>, List<String>> EWs = new HashMap<Class<? extends Entity>, List<String>>();
	public void onEnable() {
		if (getConfig().options().header() == null) {
			getConfig().options().copyHeader();
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
		EWs.put(Enderman.class, getConfig().getStringList("Endermen"));
		EWs.put(Sheep.class, getConfig().getStringList("Sheep"));
		EWs.put(Creeper.class, getConfig().getStringList("Explosion"));
		EWs.put(Snowman.class, getConfig().getStringList("Snowman"));
		getServer().getPluginManager().registerEvents(this, this);
	}
	@EventHandler public void onBlockChange(EntityChangeBlockEvent e) {
		e.setCancelled(e.getEntity() instanceof Enderman && (EWs == null || !EWs.containsKey(Enderman.class) || !EWs.get(Enderman.class).contains(e.getBlock().getLocation().getWorld().getName())) ? true : e.isCancelled());
		if (e.getEntity() instanceof Sheep && (EWs == null || !EWs.containsKey(Sheep.class) || !EWs.get(Sheep.class).contains(e.getBlock().getLocation().getWorld().getName()))) {
			e.setCancelled(true);
			((Sheep)e.getEntity()).setSheared(false);
		}
	}
	@EventHandler public void onExplosion(EntityExplodeEvent e) {
		if (EWs == null || !EWs.containsKey(Creeper.class) || !EWs.get(Creeper.class).contains(e.getLocation().getWorld().getName())) e.blockList().clear();
	}
	@EventHandler public void onEntityForm(EntityBlockFormEvent e) {
		e.setCancelled(e.getEntity() instanceof Snowman && (EWs == null || !EWs.containsKey(Snowman.class) || !EWs.get(Snowman.class).contains(e.getBlock().getLocation().getWorld().getName())) ? true : e.isCancelled());
	}
}