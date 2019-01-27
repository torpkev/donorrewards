package work.torp.donorrewards.helper;

import java.util.List;
import org.bukkit.entity.Player;

import net.milkbowl.vault.permission.Permission;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.classes.UnclaimedCash;
import work.torp.donorrewards.classes.UnclaimedGroup;
import work.torp.donorrewards.classes.UnclaimedItem;
import work.torp.donorrewards.classes.UnclaimedMessage;
import work.torp.donorrewards.classes.UnclaimedSpawner;

public class Check {
	public static boolean hasPermission(Player player, String permission)
	{
		if (player.isOp() || player.hasPermission(permission))
		{
			return true;
		} else {
			return false;
		}
	}
	public static boolean hasUnclaimedMessage(String uuid)
	{
		boolean returnVal = false;
		List<UnclaimedMessage> lstM = Main.getInstance().GetUnclaimedMessage();
		if (lstM != null)
		{
			if (lstM.size() > 0)
			{
				for (UnclaimedMessage um : lstM)
				{
					if (um.getUUID().equalsIgnoreCase(uuid)) {
						returnVal = true;
					}
				}
			}
		}
		return returnVal;
	}
	public static boolean hasUnclaimedGroup(String uuid)
	{
		boolean returnVal = false;
		List<UnclaimedGroup> lstG = Main.getInstance().GetUnclaimedGroup();
		if (lstG != null)
		{
			if (lstG.size() > 0)
			{
				for (UnclaimedGroup ug : lstG)
				{
					if (ug.getUUID().equalsIgnoreCase(uuid)) {
						returnVal = true;
					}
				}
			}
		}
		return returnVal;
	}
	public static boolean hasUnclaimedCash(String uuid)
	{
		boolean returnVal = false;
		List<UnclaimedCash> lstC = Main.getInstance().GetUnclaimedCash();
		if (lstC != null)
		{
			if (lstC.size() > 0)
			{
				for (UnclaimedCash uc : lstC)
				{
					if (uc.getUUID().equalsIgnoreCase(uuid)) {
						returnVal = true;
					}
				}
			}
		}
		return returnVal;
	}
	public static boolean hasUnclaimedItem(String uuid)
	{
		boolean returnVal = false;
		List<UnclaimedItem> lstI = Main.getInstance().GetUnclaimedItem();
		if (lstI != null)
		{
			if (lstI.size() > 0)
			{
				for (UnclaimedItem ui : lstI)
				{
					if (ui.getUUID().equalsIgnoreCase(uuid)) {
						returnVal = true;
					}
				}
			}
		}
		return returnVal;
	}
	public static boolean hasUnclaimedSpawner(String uuid)
	{
		boolean returnVal = false;
		List<UnclaimedSpawner> lstS = Main.getInstance().GetUnclaimedSpawner();
		if (lstS != null)
		{
			if (lstS.size() > 0)
			{
				for (UnclaimedSpawner us : lstS)
				{
					if (us.getUUID().equalsIgnoreCase(uuid)) {
						returnVal = true;
					}
				}
			}
		}
		return returnVal;
	}
	public static boolean inGroup(Player player, String group)
	{
		boolean ret = false;
		Alert.DebugLog("Check", "inGroup", "Checking " + player.getDisplayName() + " - " + group);
		Permission perm = Main.getInstance().getPermission(); // Create a new Permission object
		String[] groups = perm.getPlayerGroups(null, player);
		for (String g : groups)
		{
			if (g.equalsIgnoreCase(group))
			{
				Alert.DebugLog("Check", "inGroup", "User is in group");
				ret = true;
			}
		}
		Alert.DebugLog("Check", "inGroup", "Check complete.  Result = " + Boolean.toString(ret));
		return ret;
	}
}
