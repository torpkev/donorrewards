package work.torp.donorrewards.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import work.torp.donorrewards.helper.Check;
import work.torp.donorrewards.helper.CommandFunctions;
import work.torp.donorrewards.helper.Debug;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.classes.ClaimGUI;

public class Donor implements CommandExecutor {
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Alert.DebugLog("Donor", "onCommand", "/donor command run");
		boolean playerSent = false;
		if (sender instanceof Player) {
			Alert.DebugLog("Donor", "onCommand", "Player sent");
			playerSent = true;
			if (Main.getInstance().getConsoleOnly())
			{
				Alert.Sender("This command can only be performed on Console", sender, true);
				return true;
			}
		} else {
			// Console sent
			Alert.DebugLog("Donor", "onCommand", "Console sent");
		}
		
		boolean hasGive = false;
		boolean hasClaim = false;
		boolean hasDebug = false;
		if (playerSent)
		{
			if (Check.hasPermission((Player) sender, "donorrewards.give"))
			{
				hasGive = true;
			}
			if (Check.hasPermission((Player) sender, "donorrewards.claim"))
			{
				hasClaim = true;
			}
			if (Check.hasPermission((Player) sender, "donorrewards.debug"))
			{
				hasDebug = true;
			}
		} else {
			hasGive = true;
			hasClaim = false; // Console shouldn't be able to claim
			hasDebug = true;
		}
		

		StringBuilder fullargs = new StringBuilder();
		int iargs = 0;
		if (args.length > 0) {
			for (Object o : args)
			{
				iargs++;
				fullargs.append(o.toString());
				fullargs.append(" ");
			}
			Alert.DebugLog("Donor", "onCommand", "/domain command run by " + sender.getName() + " with arguments: " + fullargs);
		}
		if (iargs >= 1)
		{
			if (args[0] != null)
			{
				switch (args[0])
				{			
					case "debug" :
						if (hasDebug)
						{
							// TODO: Toggle debug
						} else {
							Alert.Sender("You do not have permission to run this command", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
						}
						break;	
					case "debugconfig" :
						if (hasDebug)
						{
							Debug.AlertConfig();
						} else {
							Alert.Sender("You do not have permission to run this command", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
						}
						break;
					case "list" :
						if (hasGive)
						{
							// TODO: Print out list of ranks
						} else {
							Alert.Sender("You do not have permission to run this command", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
						}
						break;
					case "claim" :
						if (hasClaim)
						{
							if (playerSent)
							{
								Player player = (Player) sender;
								ClaimGUI gui = new ClaimGUI();
								player.openInventory(gui.getInventory()); // display the GUI
							} else {
								Alert.Sender("You must be a logged in player to send the claim command", sender, true);
								Alert.DebugLog("Donor", "onCommand", "Console tried to send /donor claim command: /donor " + fullargs);
							}
						} else {
							Alert.Sender("You do not have permission to run this command", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
						}
						break;
					case "give" :
						if (iargs >= 2)
						{
							UUID uuid = null;
							String rank = "";
							OfflinePlayer oplayer = null;
							if (args[1] != null)
							{
								try{
								    uuid = UUID.fromString(args[1].toString());
								    Alert.DebugLog("Command", "Give", "UUID: " + uuid.toString());
								    oplayer = Bukkit.getOfflinePlayer(uuid);
								    if (oplayer != null)
								    {
								    	Alert.DebugLog("Donor", "Give", "Found player: " + oplayer.getName());
								    } else {
								    	Alert.DebugLog("Donor", "Give", "Unable to find player: " + uuid.toString());
								    }
								} catch (IllegalArgumentException exception){
									Alert.Sender("Unable to give donor reward.  Invalid player UUID", sender, true);
									return false;
								}
							} else {
								Alert.DebugLog("Donor", "Give", "no arg 1");
							}
							if (args[2] != null)
							{
								rank = args[2].toString();
								Alert.DebugLog("Donor", "Give", "Rank found: " + rank);
							}
							if (hasGive)
							{
								CommandFunctions.giveOfflineReward(oplayer, rank, sender, fullargs.toString());
							} else {
								Alert.Sender("You do not have permission to run this command", sender, true);
								Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
							}
						} else {
							Alert.Sender("Usage: /donor give " + ChatColor.AQUA + "<uuid> <rank>", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Command sent by " + sender.getName() + " without all arguments: /donor " + fullargs);
						}
						break;
					default:
						break;
				}
			} else {
				if (hasGive || hasClaim || hasDebug)
				{
					Alert.Sender("Donor Rewards Usage:", sender, true);
					if (hasGive)
					{
						Alert.Sender(ChatColor.BOLD + "Give donor items: " + ChatColor.RESET + "/donor give " + ChatColor.AQUA + " <uuid> <rank>", sender, true);
						Alert.Sender(ChatColor.BOLD + "List donor ranks: " + ChatColor.RESET + "/donor list", sender, true);
					}
					if (hasClaim)
					{
						Alert.Sender(ChatColor.BOLD + "Claim donor items: " + ChatColor.RESET + "/donor claim", sender, true);
					}
					if (hasDebug)
					{
						Alert.Sender(ChatColor.BOLD + "Toggle debug mode:   " + ChatColor.RESET + "/donor debug", sender, true);
						Alert.Sender(ChatColor.BOLD + "View config options: " + ChatColor.RESET + "/donor debugconfig", sender, true);
					}	
				}
				Alert.DebugLog("Donor", "onCommand", "Command sent by " + sender.getName() + " without all arguments: /donor " + fullargs);
			}
		} else {
			Alert.Sender("Usage: /donor " + ChatColor.AQUA + "<player> <rank>", sender, true);
			Alert.DebugLog("Donor", "onCommand", "Command sent by " + sender.getName() + " without all arguments: /donor " + fullargs);
		}
		return true;
	}

}
