/*
 * Copyright (c) 2020-2020 ShadowVappy
 *
 * This file is part of Harvest Core, a mod made for Minecraft.
 *
 * Harvest Core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Harvest Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Harvest Core.  If not, see <https://www.gnu.org/licenses/>.
 */

package shadowvappy.harvestcore.common.harvestlevels.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import org.apache.logging.log4j.Logger;
import shadowvappy.harvestcore.common.harvestlevels.Levels;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.ModChecker;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MineLevelHandler 
{
	private static final Logger LOG = LogHelper.getLogger(null, "LevelHandler");
	
	public static void postInit() {
		addMineLevels();
	}
	
	private static void addMineLevels() {
		Iterator<Map.Entry<Integer, String>> originalLevels =
				ModChecker.isTinkersConstructLoaded ? Levels.originalTinkerLevelList.entrySet().iterator() : Levels.originalLevelList.entrySet().iterator(); //Get iterator for original level list
		Map.Entry<Integer, String> originalLevel = originalLevels.next(); //Get first original level from iterator
		for(Map.Entry<Integer, String> level : (ModChecker.isTinkersConstructLoaded ? Levels.tinkerLevelList.entrySet():Levels.levelList.entrySet())) {
			if(level.getValue().equalsIgnoreCase(originalLevel.getValue())) {
				if(originalLevels.hasNext()) {
					originalLevel = originalLevels.next();
				}
			}else {
				for(int i=0; i<4096; i++) {
					Block block = Block.getBlockById(i);
					if(block != null) {
						String harvestTool = block.getHarvestTool(block.getDefaultState());
						int harvestLevel = block.getHarvestLevel(block.getDefaultState());
						if(block.getUnlocalizedName().contains(Levels.modidList.get(level.getValue()).toLowerCase()))
							continue;
						if(block.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
							block.setHarvestLevel(harvestTool, level.getKey()-1);
							continue;
						}
						if(harvestLevel >= level.getKey()
								&& block != Block.getBlockFromName("minecraft:lapis_ore")
								&& block != Block.getBlockFromName("minecraft:lapis_block")) {
							block.setHarvestLevel(harvestTool, harvestLevel+1);
						}
					}
				}
				for(int i=0; i<32768; i++) {
					Item item = ItemTool.getItemById(i);
					if(item != null) {
						if(item.getUnlocalizedName().contains(Levels.modidList.get(level.getValue()).toLowerCase()))
							continue;
						Set<String> toolClasses = item.getToolClasses(new ItemStack(item));
						if(toolClasses.contains("sword")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "sword", null, null);
							if(item.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
								item.setHarvestLevel("sword", level.getKey());
							}else if(harvestLevel > level.getKey()) {
								item.setHarvestLevel("sword", harvestLevel+1);
							}
						}
						if(toolClasses.contains("pickaxe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "pickaxe", null, null);
							if(item.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
								item.setHarvestLevel("pickaxe", level.getKey());
							}else if(harvestLevel > level.getKey()) {
								item.setHarvestLevel("pickaxe", harvestLevel+1);
							}
						}
						if(toolClasses.contains("axe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "axe", null, null);
							if(item.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
								item.setHarvestLevel("axe", level.getKey());
							}else if(harvestLevel > level.getKey()) {
								item.setHarvestLevel("axe", harvestLevel+1);
							}
						}
						if(toolClasses.contains("spade")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "spade", null, null);
							if(item.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
								item.setHarvestLevel("spade", level.getKey());
							}else if(harvestLevel > level.getKey()) {
								item.setHarvestLevel("spade", harvestLevel+1);
							}
						}
						if(toolClasses.contains("hoe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "hoe", null, null);
							if(item.getUnlocalizedName().contains(level.getValue().toLowerCase())) {
								item.setHarvestLevel("hoe", level.getKey());
							}else if(harvestLevel > level.getKey()) {
								item.setHarvestLevel("hoe", harvestLevel+1);
							}
						}
					}
				}
			}
		}
		LOG.info("Harvest levels have been changed!");
	}
}
