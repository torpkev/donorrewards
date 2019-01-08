package work.torp.donorrewards.events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.classes.UnclaimedMessage;
import work.torp.donorrewards.helper.Check;
import work.torp.donorrewards.helper.Provide;


public class PlayerEvents implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		String uuid = evt.getPlayer().getUniqueId().toString();
		boolean hasUnclaimedGroup = false;
		boolean hasUnclaimedCash = false;
		boolean hasUnclaimedItem = false;
		boolean hasUnclaimedSpawner = false;
		
		if (Check.hasUnclaimedMessage(uuid))
		{
			List<UnclaimedMessage> lstUM = Provide.getUnclaimedMessages(uuid);
			if (lstUM != null)
			{
				for (UnclaimedMessage um : lstUM)
				{
					Alert.Player(um.getPlayerMessage().replace("<%player%>", evt.getPlayer().getName()), evt.getPlayer(), true);
					if (!Main.getInstance().getDatabase().claimMessage(um.getMessageID(), uuid))
					{
						Alert.Log("Player Join", "Unexpected error marking donation message as read");
					}
				}
			}
		}
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
			Alert.Player("You have unclaimed donor rewards.  Type " + ChatColor.AQUA + "/donor claim" + ChatColor.RESET + " to claim them!", evt.getPlayer(), true);
		}
	}
}
