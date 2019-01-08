package work.torp.donorrewards.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import work.torp.donorrewards.Main;
import work.torp.donorrewards.classes.Donation;
import work.torp.donorrewards.classes.Rank;
import work.torp.donorrewards.classes.UnclaimedCash;
import work.torp.donorrewards.classes.UnclaimedGroup;
import work.torp.donorrewards.classes.UnclaimedItem;
import work.torp.donorrewards.classes.UnclaimedMessage;
import work.torp.donorrewards.classes.UnclaimedSpawner;

public class Provide {
	public static Donation donationByID(int donation_id) {
		Donation d = new Donation();
		List<Donation> lstD = Main.getInstance().GetDonations();
		if (lstD != null)
		{
			if (lstD.size() > 0)
			{
				for (Donation don : lstD)
				{
					if (don.getDonationID() == donation_id)
					{
						d = don;
					}
				}
			}
		}
		return d;
	}
	public static Rank rankByDonationID(int donation_id) {
		Rank r = new Rank();
		List<Donation> lstD = Main.getInstance().GetDonations();
		if (lstD != null)
		{
			if (lstD.size() > 0)
			{
				for (Donation don : lstD)
				{
					if (don.getDonationID() == donation_id)
					{
						for (Map.Entry<String, Rank> entry : Main.hmRanks.entrySet()) {
							if (entry.getKey().equalsIgnoreCase(don.getRank()))
							{
								r = entry.getValue();
							}
						}
					}
				}
			}
		}
		return r;
	}
	public static List<UnclaimedMessage> getUnclaimedMessages(String uuid)
	{
		List<UnclaimedMessage> lstRet = new ArrayList<UnclaimedMessage>();
		List<UnclaimedMessage> lstM = Main.getInstance().GetUnclaimedMessage();
		if (lstM != null)
		{
			if (lstM.size() > 0)
			{
				for (UnclaimedMessage um : lstM)
				{
					if (um.getUUID().equalsIgnoreCase(uuid)) {
						lstRet.add(um);
					}
				}
			}
		}
		return lstRet;
	}
	public static List<UnclaimedGroup> getUnclaimedGroups(String uuid)
	{
		List<UnclaimedGroup> lstRet = new ArrayList<UnclaimedGroup>();
		List<UnclaimedGroup> lstG = Main.getInstance().GetUnclaimedGroup();
		if (lstG != null)
		{
			if (lstG.size() > 0)
			{
				for (UnclaimedGroup ug : lstG)
				{
					if (ug.getUUID().equalsIgnoreCase(uuid)) {
						lstRet.add(ug);
					}
				}
			}
		}
		return lstRet;
	}
	public static List<UnclaimedCash> getUnclaimedCash(String uuid)
	{
		List<UnclaimedCash> lstRet = new ArrayList<UnclaimedCash>();
		List<UnclaimedCash> lstC = Main.getInstance().GetUnclaimedCash();
		if (lstC != null)
		{
			if (lstC.size() > 0)
			{
				for (UnclaimedCash uc : lstC)
				{
					if (uc.getUUID().equalsIgnoreCase(uuid)) {
						lstRet.add(uc);
					}
				}
			}
		}
		return lstRet;
	}
	public static List<UnclaimedItem> getUnclaimedItems(String uuid)
	{
		List<UnclaimedItem> lstRet = new ArrayList<UnclaimedItem>();
		List<UnclaimedItem> lstI = Main.getInstance().GetUnclaimedItem();
		if (lstI != null)
		{
			if (lstI.size() > 0)
			{
				for (UnclaimedItem ui : lstI)
				{
					if (ui.getUUID().equalsIgnoreCase(uuid)) {
						lstRet.add(ui);
					}
				}
			}
		}
		return lstRet;
	}
	public static List<UnclaimedSpawner> getUnclaimedSpawner(String uuid)
	{
		List<UnclaimedSpawner> lstRet = new ArrayList<UnclaimedSpawner>();
		List<UnclaimedSpawner> lstS = Main.getInstance().GetUnclaimedSpawner();
		if (lstS != null)
		{
			if (lstS.size() > 0)
			{
				for (UnclaimedSpawner us : lstS)
				{
					if (us.getUUID().equalsIgnoreCase(uuid)) {
						lstRet.add(us);
					}
				}
			}
		}
		return lstRet;
	}
	public static double getBalance(String uuid)
	{
		OfflinePlayer oplayer = Bukkit.getPlayer(UUID.fromString(uuid));
		Economy econ = Main.getInstance().getEconomy();
		double bal = econ.getBalance(oplayer);   
		return bal;
	}
}
