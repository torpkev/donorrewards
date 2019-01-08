package work.torp.donorrewards.commands;

import java.util.Map;

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
import work.torp.donorrewards.classes.Donation;
import work.torp.donorrewards.classes.Rank;

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
			Alert.DebugLog("Domain", "onCommand", "/domain command run by " + sender.getName() + " with arguments: " + fullargs);
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
							// TODO: Claim donor items
						} else {
							Alert.Sender("You do not have permission to run this command", sender, true);
							Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
						}
						break;
					case "give" :
						if (iargs >= 2)
						{
							String playername = "";
							String rank = "";
							if (args[1] != null)
							{
								playername = args[1].toString();
							}
							if (args[2] != null)
							{
								rank = args[2].toString();
							}
							if (hasGive)
							{
								OfflinePlayer oplayer = Bukkit.getPlayer(playername);
								if (oplayer != null) {
									if (!rank.equals(""))
									{
										boolean rankExists = false;
										Rank r = new Rank();
										for (Map.Entry<String, Rank> entry : Main.hmRanks.entrySet()) {
											if (entry.getKey().equalsIgnoreCase(args[2].toString()))
											{
												rankExists = true;
												r = entry.getValue();
											}
										}
										if (rankExists)
										{
											if (r != null)
											{
												Donation d = CommandFunctions.saveDonation(oplayer.getUniqueId().toString(), rank);
												if (d != null)
												{
													boolean blnMessage = false;
													boolean blnGroups = false;
													boolean blnCash = false;
													boolean blnItems = false;
													boolean blnSpawners = false;
													blnMessage = CommandFunctions.saveMessage(d);
													if (Main.getInstance().HasVault())
													{
														blnGroups = CommandFunctions.saveGroups(d);
														blnCash = CommandFunctions.saveCash(d);
													} else {
														blnGroups = true;
														blnCash = true;
													}
													blnItems = CommandFunctions.saveItems(d);
													blnSpawners = CommandFunctions.saveSpawners(d);
													if (!blnMessage)
													{
														Alert.DebugLog("Donor", "Give", "Unable to save message for " + oplayer.getName());
													}
													if (!blnGroups)
													{
														Alert.DebugLog("Donor", "Give", "Unable to save groups for " + oplayer.getName());
													}
													if (!blnCash)
													{
														Alert.DebugLog("Donor", "Give", "Unable to save cash reward for " + oplayer.getName());
													}
													if (!blnItems)
													{
														Alert.DebugLog("Donor", "Give", "Unable to save reward items for " + oplayer.getName());
													}
													if (!blnSpawners)
													{
														Alert.DebugLog("Donor", "Give", "Unable to save reward spawners for " + oplayer.getName());
													}
													Player p = Bukkit.getPlayer(oplayer.getUniqueId());
													if (p != null)
													{
														Alert.Player(r.getPlayerMessage().replace("<%player%>", p.getName()), p, true);
														Bukkit.broadcastMessage(r.getBroadcastMessage().replaceAll("<%player%>", p.getName()));
													}
												} else {
													Alert.Log("Donor.Give", "Unable to save donation to " + rank + " for " + oplayer.getName());
												}
											} else {
												Alert.Log("Donor.Give", "Unable to save donation to " + rank + " for " + oplayer.getName() + " - Rank not found");
												Alert.Sender("Unable to save donation to " + rank + " for " + oplayer.getName() + " - Rank not found", sender, true);
											}
										} else {
											Alert.Sender("Invalid rank - Unable to process donor reward", sender, true);
											Alert.DebugLog("Donor", "onCommand", "Invalid rank - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
										}
									} else {
										Alert.Sender("Invalid rank - Unable to process donor reward", sender, true);
										Alert.DebugLog("Donor", "onCommand", "Invalid rank - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
									}
								} else {
									Alert.Sender("Invalid player - Unable to process donor reward", sender, true);
									Alert.DebugLog("Donor", "onCommand", "Invalid player - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
								}							
							} else {
								Alert.Sender("You do not have permission to run this command", sender, true);
								Alert.DebugLog("Donor", "onCommand", "Invalid permissions for /donor command run by " + sender.getName() + ": /donor " + fullargs);
							}
						} else {
							Alert.Sender("Usage: /donor give " + ChatColor.AQUA + "<player> <rank>", sender, true);
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
						Alert.Sender(ChatColor.BOLD + "Give donor items: " + ChatColor.RESET + "/donor give " + ChatColor.AQUA + " <player> <rank>", sender, true);
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
