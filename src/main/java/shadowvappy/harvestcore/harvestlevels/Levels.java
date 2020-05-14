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

package shadowvappy.harvestcore.harvestlevels;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.minecraft.util.text.TextFormatting;
import shadowvappy.harvestcore.util.LogHelper;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class Levels 
{
	private static final Logger LOG = LogHelper.getLogger("Levels");
	
	/** List in which to store vanilla harvest levels. */
	public static List<Level> levelList = new ArrayList<Level>();
	public static List<Level> originalLevelList = new ArrayList<Level>();
	/** List in which to store tinker harvest levels. */
	public static List<Level> tinkerLevelList = new ArrayList<Level>();
	public static List<Level> originalTinkerLevelList = new ArrayList<Level>();
	
	/* Vanilla Levels */
	public static final Level STONE = new Level("Stone", 0, TinkerMaterials.stone.getTextColor());
	public static final Level IRON = new Level("Iron", 1, TinkerMaterials.iron.getTextColor());
	public static final Level DIAMOND = new Level("Diamond", 2, TextFormatting.AQUA.toString());
	public static final Level OBSIDIAN = new Level("Obsidian", 3, TinkerMaterials.obsidian.getTextColor());
	/* Tinkers Levels */
	public static final Level COBALT = new Level("Cobalt", 4, TinkerMaterials.cobalt.getTextColor());
	
	public static void preInit() {
		setupBaseLevelLists();
	}
	
	private static void setupBaseLevelLists() {
		/* Add vanilla levels to vanilla level list */
		levelList.add(STONE);
		levelList.add(IRON);
		levelList.add(DIAMOND);
		levelList.add(OBSIDIAN);
		originalLevelList = levelList;
		
		/* Add vanilla and tinker levels to tinker level list */
		tinkerLevelList.add(STONE);
		tinkerLevelList.add(IRON);
		tinkerLevelList.add(DIAMOND);
		tinkerLevelList.add(OBSIDIAN);
		tinkerLevelList.add(COBALT);
		originalTinkerLevelList = tinkerLevelList;
	}
	
	/**
	 * Add a harvest level to the vanilla list.
	 * Vanilla harvest levels: Wood/Gold Pickaxe = 0; Stone Pickaxe = 1; Iron Pickaxe = 2; Diamond Pickaxe = 3;
	 * 
	 * @param The name for the harvest level to add
	 * @param The harvest level to add, if you want to add before a particular level, give that level's harvest level
	 * @param The modid of the mod adding the harvest level
	 */
	public static void addLevel(String name, int level, String modid) {
		Level harvestLevel = new Level(name, modid, level);
		
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : levelList) {
				if(existingLevel.getName().equalsIgnoreCase(name)) {
					hasLevel = true;
					break;
				}
			}
			if(!hasLevel) {
				levelList.add(level, harvestLevel);
			}
		}else {
			LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
		}
	}
	/**
	 * Add a harvest level to the tinker list.
	 * Vanilla tconstruct harvest levels: Stone = 0; Iron = 1; Diamond = 2; Obsidian = 3; Cobalt = 4;
	 * 
	 * @param The name for the harvest level to add
	 * @param The harvest level to add, if you want to add before a particular level, give that level's harvest level
	 * @param The modid of the mod adding the harvest level
	 * @param The color for the harvest level to be set in the tconstruct book, please use the tconstruct material color if possible
	 */
	public static void addTinkerLevel(String name, int level, String modid, String color) {
		Level harvestLevel = new Level(name, level, modid, color);
		
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : tinkerLevelList) {
				if(existingLevel.getName().equalsIgnoreCase(name)) {
					hasLevel = true;
					break;
				}
			}
			if(!hasLevel) {
				tinkerLevelList.add(level, harvestLevel);
			}
		}else {
			LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
		}
	}
}