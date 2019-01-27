package work.torp.donorrewards.classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import work.torp.donorrewards.alerts.Alert;
import work.torp.donorrewards.helper.Provide;

public class UnclaimedItem {
	private int itemID;
	private int donationID;
	private String uuid;
	private String name;
	private Material material;
	private int itemCnt;
	private boolean tagItem;
    public int getItemID() {
        return itemID;
    }
    public int getDonationID() {
        return donationID;
    }
    public String getUUID() {
    	return uuid;
    }
    public String getName() {
    	return this.name;
    }
    public Material getMaterial() {
        return material;
    }
    public int getItemCount() {
        return itemCnt;
    }
    public boolean getTagItem() {
    	return tagItem;
    }
    public void set(int itemID, int donationID, String uuid, String name, Material material, int itemCnt, boolean tagItem)
    {
    	this.itemID = itemID;
    	this.donationID = donationID;
    	this.uuid = uuid;
    	this.name = name;
    	this.material = material;
    	this.itemCnt = itemCnt;
    	this.tagItem = tagItem;
    }
    public ItemStack getItem(Player player) {
    	ItemStack istack = new ItemStack(this.material, itemCnt);
    	ItemMeta imeta = istack.getItemMeta();
		if (imeta != null)
		{
			if (this.name != null)
			{
				Alert.DebugLog("UnclaimedItem", "getItem", "this.name = " + this.name);
				if (!this.name.equals(""))
				{
					imeta.setDisplayName(this.name);
				}
			}
			if (tagItem)
			{
				Rank r = Provide.rankByDonationID(this.donationID);
				if (r != null)
				{
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // Create a DateTimeFormatter
					LocalDate localDate = LocalDate.now(); // Get the current date
					List<String> lore = new ArrayList<String>(); // Create a new list for our Lore (this will tag the items as donation items)
					lore.add("Rank: " + r.getDisplayName()); // Add our string to the Lore list
					lore.add("Owner: " + player.getDisplayName()); // Add the owner to the Lore list
					lore.add("Given: " + dtf.format(localDate)); // Add the current date (formatted yyyy/MM/dd) as the date given to the Lore list
					lore.add(ChatColor.BLACK + "Item: " + Integer.toString(this.itemID));
					imeta.setLore(lore);
					istack.setItemMeta(imeta);	
				} else {
					List<String> lore = new ArrayList<String>(); // Create a new list for our Lore (this will tag the items as donation items)
					lore.add(ChatColor.BLACK + "Item: " + Integer.toString(this.itemID));
					imeta.setLore(lore);
					istack.setItemMeta(imeta);	
				}
			} else {
				List<String> lore = new ArrayList<String>(); // Create a new list for our Lore (this will tag the items as donation items)
				lore.add(ChatColor.BLACK + "Item: " + Integer.toString(this.itemID));
				imeta.setLore(lore);
				istack.setItemMeta(imeta);	
			}
		}
    	return istack;
    }
}
