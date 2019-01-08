package work.torp.donorrewards.scheduled;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.helper.Check;

public class PlayerNag {
	public static void Run() {
		if (Bukkit.getOnlinePlayers() != null)
		{
			for (Player p : Bukkit.getOnlinePlayers())
			{
				String uuid = p.getUniqueId().toString();
				boolean hasUnclaimedGroup = false;
				boolean hasUnclaimedCash = false;
				boolean hasUnclaimedItem = false;
				boolean hasUnclaimedSpawner = false;
				if (Main.getInstance().HasVault())
				{
					if (Check.hasUnclaimedGroup(uuid))
					{
						hasUnclaimedGroup = true;
					}
					if (Check.hasUnclaimedCash(uuid))
					{
						hasUnclaimedCash = true;
					}
				}
				if (Check.hasUnclaimedItem(uuid))
				{
					hasUnclaimedItem = true;
				}
				if (Check.hasUnclaimedSpawner(uuid))
				{
					hasUnclaimedSpawner = true;
				}
				
				if (hasUnclaimedGroup || hasUnclaimedCash || hasUnclaimedItem || hasUnclaimedSpawner)
				{
					Alert.Player("You have unclaimed donor rewards.  Type " + ChatColor.AQUA + "/donor claim" + ChatColor.RESET + " to claim them!", p, true);
				}
			}
		}
	}
}
