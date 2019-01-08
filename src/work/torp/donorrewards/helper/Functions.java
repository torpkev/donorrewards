package work.torp.donorrewards.helper;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;

public class Functions {
	public static void removeGroups(Player player, List<String> exemptList)
	{
		List<String> groups = new ArrayList<String>();
		Permission perm = Main.getInstance().getPermission(); // Create a new Permission object
		for (String keep : exemptList) {
			if (perm.playerInGroup(player, keep)) {
				groups.add(keep);
			}
		}
		for (String group : perm.getPlayerGroups(null, player)) { // Loop through all groups the player is in
    		perm.playerRemoveGroup(null, player, group); // Remove the player from the group
	    }
		for (String g : groups)
		{
			perm.playerAddGroup(player, g);
		}
	}
	public static void addToGroup(Player player, String group)
	{
		Permission perm = Main.getInstance().getPermission(); // Create a new Permission object
		if (!perm.playerInGroup(player, group)) {
			perm.playerAddGroup(player, group);
		}
	}
	public static void payPlayer(Player player, int amt)
	{
		try
		{
			Economy econ = Main.getInstance().getEconomy();
			EconomyResponse r = econ.depositPlayer(player, amt);
			if (!r.transactionSuccess())
			{
				Alert.DebugLog("Functions", "payPlayer", "Unexpected error trying to deposit funds to player.  Error: " + r.errorMessage);
			}
		}
		catch (Exception ex) {
			Alert.DebugLog("Functions", "payPlayer", "Unexpected error trying to deposit funds to player.  Error: " + ex.getMessage());
		}
	}
}
