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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.text.TextFormatting;
import shadowvappy.harvestcore.config.ModConfig;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.Reference;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class Levels 
{
	private static final Logger LOG = LogHelper.getLogger(null, "Levels");
	
	/** List in which to store vanilla harvest levels. */
	public static List<Level> levelList = new ArrayList<Level>();
	public static List<Level> originalLevelList = new ArrayList<Level>();
	/** List in which to store tinker harvest levels. */
	public static List<Level> tinkerLevelList = new ArrayList<Level>();
	public static List<Level> originalTinkerLevelList = new ArrayList<Level>();
	
	public static Level level;
	
	public static void preLoad() {
		setupBaseLevelLists();
	}
	
	private static void setupBaseLevelLists() {
		/* Add vanilla levels to vanilla level list */
		level = new Level("Stone", 0, TinkerMaterials.stone.getTextColor());
		levelList.add(level);
		level = new Level("Iron", 1, TinkerMaterials.iron.getTextColor());
		levelList.add(level);
		level = new Level("Diamond", 2, TextFormatting.DARK_AQUA.toString());
		levelList.add(level);
		level = new Level("Obsidian", 3, TinkerMaterials.obsidian.getTextColor());
		levelList.add(level);
		originalLevelList = (List<Level>)((ArrayList<Level>)levelList).clone();
		
		/* Add vanilla and tinker levels to tinker level list */
		level = new Level("Stone", 0, TinkerMaterials.stone.getTextColor());
		tinkerLevelList.add(level);
		level = new Level("Iron", 1, TinkerMaterials.iron.getTextColor());
		tinkerLevelList.add(level);
		level = new Level("Diamond", 2, TextFormatting.DARK_AQUA.toString());
		tinkerLevelList.add(level);
		level = new Level("Obsidian", 3, TinkerMaterials.obsidian.getTextColor());
		tinkerLevelList.add(level);
		level = new Level("Cobalt", 4, TinkerMaterials.cobalt.getTextColor());
		tinkerLevelList.add(level);
		originalTinkerLevelList = (List<Level>)((ArrayList<Level>)tinkerLevelList).clone();
	}
	
	/**
	 * Add a harvest level to the vanilla list. <br>
	 * Vanilla harvest levels: Stone (Wood/Gold Pick) = 0; Iron (Stone pick) = 1; Diamond (Iron pick) = 2; Obsidian (Diamond pick) = 3;
	 * 
	 * @param name The name for the harvest level to add
	 * @param modid The modid of the mod adding the harvest level
	 * @param level The harvest level to add, if you want to add before a particular level, give that level's harvest level
	 */
	public static void addLevel(String name, String modid, int level) {
		Level harvestLevel = new Level(name, modid, level);
		
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : levelList) {
				if(name.equalsIgnoreCase(existingLevel.getName())) {
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false) {
				if(level > levelList.size()) {
					levelList.add(harvestLevel);
				}else {
					if(ModConfig.addMineLevels) {
						levelList.add(level, harvestLevel);
						List<Level> newList = levelList.subList(level+1, levelList.size());
						if(newList.size() > 0) {
							for(Level newLevel : newList) {
								newLevel.setLevel(newLevel.getLevel()+1);
							}
						}
					}
				}
			}
		}else {
			if(modid != null) {
				LogManager.getFormatterLogger(modid + ": " + "Levels").warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}else {
				LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}
		}
	}
	/**
	 * Add a harvest level to the tinker list. <br>
	 * Must be placed before preInit. <br>
	 * Vanilla tconstruct harvest levels: Stone = 0; Iron = 1; Diamond = 2; Obsidian = 3; Cobalt = 4;
	 * 
	 * @param name The name for the harvest level to add
	 * @param modid The modid of the mod adding the harvest level
	 * @param level The harvest level to add, if you want to add before a particular level, give that level's harvest level
	 * @param color The color for the harvest level to be set in the tconstruct book, please use the tconstruct material color if possible
	 */
	public static void addTinkerLevel(String name, String modid, int level, String color) {
		/** The level form of the harvest level to add, to be added to the list. */
		Level harvestLevel = new Level(name, modid, level, color);
		
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : tinkerLevelList) {
				if(name.equalsIgnoreCase(existingLevel.getName())) {
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false) {
				if(level > tinkerLevelList.size()) {
					tinkerLevelList.add(harvestLevel);
				}else {
					if(ModConfig.TINKER_INTEGRATION.tinkerMineLevels) {
						tinkerLevelList.add(level, harvestLevel);
						List<Level> newList = tinkerLevelList.subList(level+1, tinkerLevelList.size());
						if(newList.size() > 0) {
							for(Level newLevel : newList) {
								newLevel.setLevel(newLevel.getLevel()+1);
							}
						}
					}
				}
			}
		}else {
			if(modid != null) {
				LogManager.getFormatterLogger(modid + "-" + "Levels").warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}else {
				LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}
		}
	}
}