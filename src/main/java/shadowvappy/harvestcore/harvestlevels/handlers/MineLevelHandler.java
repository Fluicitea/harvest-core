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

package shadowvappy.harvestcore.harvestlevels.handlers;

import java.util.Set;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import shadowvappy.harvestcore.harvestlevels.Level;
import shadowvappy.harvestcore.harvestlevels.Levels;
import shadowvappy.harvestcore.util.LogHelper;

public class MineLevelHandler 
{
	private static final Logger LOG = LogHelper.getLogger("MineLevel");
	
	public static void preInit() {
		addMineLevels();
	}
	
	private static void addMineLevels() {
		Level originalLevel = Levels.originalLevelList.get(0);
		for(Level level : Levels.levelList) {
			if(level.getName() == originalLevel.getName()) {
				originalLevel = Levels.originalLevelList.get(originalLevel.getLevel()+1);
			}else {
				outerloopblock:
				for(int i=0; i<4096; i++) {
					Block block = Block.getBlockById(i);
					if(block != null) {
						String harvestTool = block.getHarvestTool(block.getDefaultState());
						int harvestLevel = block.getHarvestLevel(block.getDefaultState());
						for(String ignoreMod : Levels.modIgnoreList) {
							if(block.getUnlocalizedName().contains(ignoreMod))
								continue outerloopblock;
						}
						if(harvestLevel > level.getLevel()-1
						   && block != block.getBlockFromName("minecraft:lapis_ore")
						   && block != block.getBlockFromName("minecraft:lapis_block")) {
							block.setHarvestLevel(harvestTool, harvestLevel+1);
						}
					}
				}
				outerloopitem:
				for(int i=0; i<32768; i++) {
					Item item = ItemTool.getItemById(i);
					if(item != null) {
						for(String ignoreMod : Levels.modIgnoreList) {
							if(item.getUnlocalizedName().contains(ignoreMod))
								continue outerloopitem;
						}
						Set<String> toolClasses = item.getToolClasses(new ItemStack(item));
						if(toolClasses.contains("sword")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "sword", null, null);
							if(harvestLevel > level.getLevel()) {
								item.setHarvestLevel("sword", harvestLevel+1);
							}
						}
						if(toolClasses.contains("pickaxe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "pickaxe", null, null);
							if(harvestLevel > level.getLevel()) {
								item.setHarvestLevel("pickaxe", harvestLevel+1);
							}
						}
						if(toolClasses.contains("axe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "axe", null, null);
							if(harvestLevel > level.getLevel()) {
								item.setHarvestLevel("axe", harvestLevel+1);
							}
						}
						if(toolClasses.contains("spade")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "spade", null, null);
							if(harvestLevel > level.getLevel()) {
								item.setHarvestLevel("spade", harvestLevel+1);
							}
						}
						if(toolClasses.contains("hoe")) {
							int harvestLevel = item.getHarvestLevel(new ItemStack(item), "hoe", null, null);
							if(harvestLevel > level.getLevel()) {
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
