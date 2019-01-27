package work.torp.donorrewards.helper;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.classes.Donation;
import work.torp.donorrewards.classes.Item;
import work.torp.donorrewards.classes.Rank;
import work.torp.donorrewards.classes.Spawner;
import work.torp.donorrewards.classes.UnclaimedCash;
import work.torp.donorrewards.classes.UnclaimedGroup;
import work.torp.donorrewards.classes.UnclaimedItem;
import work.torp.donorrewards.classes.UnclaimedMessage;
import work.torp.donorrewards.classes.UnclaimedSpawner;

public class CommandFunctions {
	public static void giveOfflineReward(OfflinePlayer oplayer, String rank, CommandSender sender, String fullargs)
	{
		if (oplayer != null) 
		{
			if (!rank.equals(""))
			{
				boolean rankExists = false;
				Rank r = new Rank();
				for (Map.Entry<String, Rank> entry : Main.hmRanks.entrySet()) {
					if (entry.getKey().equalsIgnoreCase(rank))
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
								Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Unable to save message for " + oplayer.getName());
							}
							if (!blnGroups)
							{
								Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Unable to save groups for " + oplayer.getName());
							}
							if (!blnCash)
							{
								Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Unable to save cash reward for " + oplayer.getName());
							}
							if (!blnItems)
							{
								Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Unable to save reward items for " + oplayer.getName());
							}
							if (!blnSpawners)
							{
								Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Unable to save reward spawners for " + oplayer.getName());
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
					Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Invalid rank - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
				}
			} else {
				Alert.Sender("Invalid rank - Unable to process donor reward", sender, true);
				Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Invalid rank - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
			}
		} else {
			Alert.Sender("Invalid player - Unable to process donor reward", sender, true);
			Alert.DebugLog("CommandFunctions", "giveOfflineReward", "Invalid player - Unable to process.  Command run by " + sender.getName() + ": /donor " + fullargs);
		}	
	}
	public static Donation saveDonation(String uuid, String rank) {
		int donation_id = -1;
		donation_id = Main.getInstance().getDatabase().saveDonation(uuid, rank);
		
		Donation d = new Donation();
		d.setDonationID(donation_id);
		d.setUUID(uuid);
		d.setRank(rank);
		d.setDonationDateTime(new Timestamp(System.currentTimeMillis()));
		
		Main.getInstance().AddDonation(d);
		return d;
	}
	public static boolean saveGroups(Donation d) {
		boolean returnVal = false;
		
		if (d != null)
		{
			int group_id = Main.getInstance().getDatabase().saveGroup(d.getDonationID(), d.getUUID());
			UnclaimedGroup ug = new UnclaimedGroup();
			ug.setGroupID(group_id);
			ug.setDonationID(d.getDonationID());
			ug.setUUID(d.getUUID());
			Rank r = Provide.rankByDonationID(ug.getDonationID());
    		if (r != null)
    		{
        		ug.setGroupName(r.getAddGroups());
        		ug.setKeepGroups(r.getKeepGroups());
        		ug.setRemoveOtherGroups(r.getRemoveOtherGroups());
    		}
    		Main.getInstance().AddUnclaimedGroup(ug);
    		returnVal = true;
		}
		
		return returnVal;
	}
	public static boolean saveCash(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			int amt = 0;
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			amt = r.getCashAmount();
    			int cash_id = Main.getInstance().getDatabase().saveCash(d.getDonationID(), d.getUUID(), amt);
    			UnclaimedCash uc = new UnclaimedCash();
    			uc.setCashID(cash_id);
    			uc.setDonationID(d.getDonationID());
    			uc.setUUID(d.getUUID());
    			uc.setCashAmount(amt);
    			Main.getInstance().AddUnclaimedCash(uc);
    			returnVal = true;
    		}
		}
		
		return returnVal;
	}
	public static boolean saveItems(Donation d) {
		Alert.DebugLog("CommandFunctions", "saveItems", "saveItems run with: ID = " + Integer.toString(d.getDonationID()) + " Rank = " + d.getRank() + " UUID = " + d.getUUID());
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			Alert.DebugLog("CommandFunctions", "saveItems", "Rank found: " + r.getDisplayName());
    			List<Item> lstI = r.getItems();
    			if (lstI != null)
    			{
    				if (lstI.size() > 0)
    				{
    					for (Item i : lstI)
    					{
    						UnclaimedItem ui = new UnclaimedItem();
    						int item_id = Main.getInstance().getDatabase().saveItem(d.getDonationID(), d.getUUID(), i.getName(), i.getMaterial().name(), i.getItemCount(), i.getTagItem());
    						ui.set(
    								item_id, 
    								d.getDonationID(), 
    								d.getUUID(), 
                					i.getName(), 
                					i.getMaterial(), 
                					i.getItemCount(), 
                					i.getTagItem()
                			);

    						Main.getInstance().AddUnclaimedItem(ui);
    						returnVal = true;
    					}
    				}
    			}
    		}
		}
		
		return returnVal;
	}
	public static boolean saveMessage(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			UnclaimedMessage um = new UnclaimedMessage();
				int player_message_id = Main.getInstance().getDatabase().saveMessage(d.getDonationID(), d.getUUID(), r.getPlayerMessage());
				um.setMessageID(player_message_id);
				um.setDonationID(d.getDonationID());
				um.setPlayerMessage(r.getPlayerMessage());
				
				Main.getInstance().AddUnclaimedMessage(um);
				returnVal = true;
    		}
		}
		
		return returnVal;
	}
	public static boolean saveSpawners(Donation d) {
		
		boolean returnVal = false;
		
		if (d != null)
		{
			Rank r = Provide.rankByDonationID(d.getDonationID());
    		if (r != null)
    		{
    			List<Spawner> lstS = r.getSpawners();
    			if (lstS != null)
    			{
    				if (lstS.size() > 0)
    				{
    					for (Spawner s : lstS)
    					{
    						UnclaimedSpawner us = new UnclaimedSpawner();
    						int spawner_id = Main.getInstance().getDatabase().saveSpawner(d.getDonationID(), d.getUUID(), s.getEntityType().name(), s.getSpawnerCount());
    						
    						us.set(spawner_id, d.getDonationID(), d.getUUID(), s.getEntityType(), s.getSpawnerCount());

    						Main.getInstance().AddUnclaimedSpawner(us);
    						returnVal = true;
    					}
    				}
    			}
    		}
		}
		
		return returnVal;
	}
	public static void setGroups(String uuid)
	{
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if (p != null)
		{
			List<UnclaimedGroup> lstUG = Provide.getUnclaimedGroups(uuid);
			if (lstUG != null)
			{
				for (UnclaimedGroup ug : lstUG)
				{
					
					if (ug.getRemoveOtherGroups())
					{
						Functions.removeGroups(p, ug.getKeepGroups());
					}
					if (ug.getGroupNames() != null)
					{
						for (String group : ug.getGroupNames())
						{
							Functions.addToGroup(p, group);
						}
					}
					Main.getInstance().getDatabase().claimGroups(ug.getGroupID(), uuid);
					Main.getInstance().RemoveUnclaimedGroup(ug);
				}
				Alert.Player("Your groups have been updated", p, true);
			}
		} else {
			Alert.Log("CommandFunctions.setGroups", "Unable to set groups for " + uuid + " - Player is offline or does not exist");
		}
	}
	public static void payPlayer(String uuid)
	{
		int totalPay = 0;
		Player p = Bukkit.getPlayer(UUID.fromString(uuid));
		if (p != null)
		{
			List<UnclaimedCash> lstUC = Provide.getUnclaimedCash(uuid);
			if (lstUC != null)
			{
				for (UnclaimedCash uc : lstUC)
				{
					if (uc.getCashAmount() > 0)
					{
						totalPay = totalPay + uc.getCashAmount();
						Functions.payPlayer(p, uc.getCashAmount());
						Main.getInstance().getDatabase().claimCash(uc.getCashID(), uuid);
						Main.getInstance().RemoveUnclaimedCash(uc);
					}
				}
				double newbal = Provide.getBalance(uuid);
				DecimalFormat formatter = new DecimalFormat("#0.00");        
				Alert.Player("You have been paid " + ChatColor.GREEN + "$" + formatter.format(totalPay), p, true);
				Alert.Player("New balance " + ChatColor.GREEN + "$" + formatter.format(newbal), p, true);
			}
		} else {
			Alert.Log("CommandFunctions.payPlayer", "Unable to deposit funds for " + uuid + " - Player is offline or does not exist");
		}
	}
}
