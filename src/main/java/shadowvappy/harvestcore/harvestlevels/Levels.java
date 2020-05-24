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
	private static int added0 = 0, added1 = 0, added2 = 0, added3 = 0, added4 = 0, added5 = 0;
	
	/** List in which to store added harvest levels. */
	private static List<Level> addLevelList = new ArrayList<Level>();
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
	
	public static void preInit() {
		addVanillaLevels();
		addTinkerLevels();
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
	 * Add a harvest level to the list. <br>
	 * Must be placed before preInit. <br>
	 * TConstruct harvest levels: Stone = 0; Iron = 1; Diamond = 2; Obsidian = 3; Cobalt = 4;
	 * 
	 * @param name The name for the harvest level to add
	 * @param modid The modid of the mod adding the harvest level
	 * @param level The harvest level to add, <i>always</i> give the TConstruct level you wish to add your level before
	 * @param color The color for the harvest level to be set in the TConstruct book, please use the TConstruct material color if possible
	 */
	public static void addLevel(String name, String modid, int level, String color) {
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : originalTinkerLevelList) {
				if(name.equalsIgnoreCase(existingLevel.getName())) {
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false) {
				for(Level existingLevel : addLevelList) {
					if(name.equalsIgnoreCase(existingLevel.getName())) {
						hasLevel = true;
						break;
					}
				}
			}
			if(hasLevel == false) {
				switch(level) {
				case 0:
					level += added0;
					added0++;
					break;
				case 1:
					level += added0+added1;
					added1++;
					break;
				case 2:
					level += added0+added1+added2;
					added2++;
					break;
				case 3:
					level += added0+added1+added2+added3;
					added3++;
					break;
				case 4:
					level += added0+added1+added2+added3+added4;
					added4++;
					break;
				case 5:
					level += added0+added1+added2+added3+added4+added5;
					added5++;
					break;
				default:
					level = 5;
					level += added0+added1+added2+added3+added4+added5;
					added5++;
				}
				Level addLevel = new Level(name, modid, level, color);
				if(addLevelList.size() > 0) {
					for(Level existingLevel : addLevelList) {
						if(level <= existingLevel.getLevel()) {
							int index = addLevelList.indexOf(existingLevel);
							List<Level> shiftedLevelList = addLevelList.subList(index, addLevelList.size());
							for(Level shiftedLevel : shiftedLevelList) {
								shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
							}
							addLevelList.add(index, addLevel);
							hasLevel = true;
							break;
						}
					}
					if(hasLevel == false)
						addLevelList.add(addLevel);
				}else {
					addLevelList.add(addLevel);
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
	
	private static void addVanillaLevels() {
		for(Level level : addLevelList) {
			if(level.getLevel() < levelList.size()) {
				List<Level> shiftedLevelList = levelList.subList(level.getLevel(), levelList.size());
				for(Level shiftedLevel : shiftedLevelList) {
					shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
				}
				levelList.add(level.getLevel(), level);
			}else {
				if(level.getLevel() > levelList.get(levelList.size()-1).getLevel()+1)
					level.setLevel(levelList.get(levelList.size()-1).getLevel()+1);
				levelList.add(level);
			}
		}
	}
	private static void addTinkerLevels() {
		for(Level level : addLevelList) {
			if(level.getLevel() < tinkerLevelList.size()) {
				List<Level> shiftedLevelList = tinkerLevelList.subList(level.getLevel(), tinkerLevelList.size());
				for(Level shiftedLevel : shiftedLevelList) {
					shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
				}
				tinkerLevelList.add(level.getLevel(), level);
			}else {
				if(level.getLevel() > tinkerLevelList.get(tinkerLevelList.size()-1).getLevel()+1)
					level.setLevel(tinkerLevelList.get(tinkerLevelList.size()-1).getLevel()+1);
				tinkerLevelList.add(level);
			}
		}
	}
}