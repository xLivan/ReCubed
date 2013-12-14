/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 5:12:41 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.lib.LibObfuscation;
import cpw.mods.fml.relauncher.ReflectionHelper;

public final class PlayerLastTickData {

	int xp;
	int level;
	boolean riding;
	
	public void tickPlayer(EntityPlayer player) {
		if(!ReCubedAPI.validatePlayer(player))
			return;
		
		if(player.experienceTotal > xp) {
			int extra = player.experienceTotal - xp;
			ReCubedAPI.addValueToCategory(LibCategories.EXPERIENCE_GATHERED, player.username, "recubed.misc.experience", extra);
		}
		
		if(player.experienceLevel > level) {
			int extra = player.experienceLevel - level;
			ReCubedAPI.addValueToCategory(LibCategories.LEVELS_GAINED, player.username, "recubed.misc.level", extra);
		}
		
		if(!riding && player.ridingEntity != null) 
			ReCubedAPI.addValueToCategory(LibCategories.ENTITIES_RIDDEN, player.username, MiscHelper.getEntityString(player.ridingEntity), 1);

		ItemStack itemInUse = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE);
		int itemInUseCount = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE_COUNT);
		if(itemInUse != null && itemInUseCount == 1) {
			Item item = itemInUse.getItem();
			
			if(item instanceof ItemFood)
				ReCubedAPI.addValueToCategory(LibCategories.FOOD_EATEN, player.username, itemInUse.getUnlocalizedName() + ".name", 1);
		}
		
		setData(player);
	}
	
	public void setData(EntityPlayer player) {
		xp = player.experienceTotal;
		level = player.experienceLevel;
		riding = player.ridingEntity != null;
	}
	
}