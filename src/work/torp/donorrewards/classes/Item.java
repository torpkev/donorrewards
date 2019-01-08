package work.torp.donorrewards.classes;

import org.bukkit.Material;

public class Item {
	Material material;
	int itemCnt;
	boolean tagItem;
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
    	this.material = material;
    }
    public int getItemCount() {
        return itemCnt;
    }
    public void setItemCount(int itemCnt) {
    	this.itemCnt = itemCnt;
    }
    public boolean getTagItem() {
    	return tagItem;
    }
    public void setTagItem(boolean tagItem) {
    	this.tagItem = tagItem;
    }
}
