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

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.text.TextFormatting;
import shadowvappy.harvestcore.config.ModConfig;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.ModChecker;
import slimeknights.tconstruct.tools.TinkerMaterials;

public class Levels 
{
	private static final Logger LOG = LogHelper.getLogger(null, "Levels");
	private static int added0 = 0, added1 = 0, added2 = 0, added3 = 0, added4 = 0, added5 = 0;
	
	/** List in which to store added harvest levels. */
	private static List<Level> addLevelListV = new ArrayList<Level>();
	private static List<Level> addLevelListT = new ArrayList<Level>();
	/** List in which to store vanilla harvest levels. */
	public static List<Level> levelList = new ArrayList<Level>();
	public static List<Level> originalLevelList = new ArrayList<Level>();
	/** List in which to store tinker harvest levels. */
	public static List<Level> tinkerLevelList = new ArrayList<Level>();
	public static List<Level> originalTinkerLevelList = new ArrayList<Level>();
	
	public static Level level;
	
	public static final void preLoad() {
		setupBaseLevelLists();
	}
	
	public static final void preInit() {
		if(ModChecker.isTinkersConstructLoaded) {
			addColorsToLevels();
			addTinkerLevels();
		}
		addVanillaLevels();
	}
	
	private static void setupBaseLevelLists() {
		/* Add vanilla levels to vanilla level list */
		level = new Level("Stone", 0);
		levelList.add(level);
		level = new Level("Iron", 1);
		levelList.add(level);
		level = new Level("Diamond", 2);
		levelList.add(level);
		level = new Level("Obsidian", 3);
		levelList.add(level);
		originalLevelList = (List<Level>)((ArrayList<Level>)levelList).clone();
		
		/* Add vanilla and tinker levels to tinker level list */
		level = new Level("Stone", 0);
		tinkerLevelList.add(level);
		level = new Level("Iron", 1);
		tinkerLevelList.add(level);
		level = new Level("Diamond", 2);
		tinkerLevelList.add(level);
		level = new Level("Obsidian", 3);
		tinkerLevelList.add(level);
		level = new Level("Cobalt", 4);
		tinkerLevelList.add(level);
		originalTinkerLevelList = (List<Level>)((ArrayList<Level>)tinkerLevelList).clone();
	}
	
	private static void addColorsToLevels() {
		String diamondColor = TextFormatting.AQUA.toString();
		for(Level level : addLevelListT) {
			if(level.getColor().equalsIgnoreCase(diamondColor))
				diamondColor = TextFormatting.DARK_AQUA.toString();
		}
		tinkerLevelList.get(0).setColor(TinkerMaterials.stone.getTextColor());
		tinkerLevelList.get(1).setColor(TinkerMaterials.iron.getTextColor());
		tinkerLevelList.get(2).setColor(diamondColor);
		tinkerLevelList.get(3).setColor(TinkerMaterials.obsidian.getTextColor());
		tinkerLevelList.get(4).setColor(TinkerMaterials.cobalt.getTextColor());
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
	public static void addLevel(String name, String modid, int level, @Nullable String color) {
		
		if(level >= 0) {
			boolean hasLevel = false;
			for(Level existingLevel : ModChecker.isTinkersConstructLoaded ? originalTinkerLevelList:originalLevelList) {
				if(name.equalsIgnoreCase(existingLevel.getName())) {
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false) {
				for(Level existingLevel : ModChecker.isTinkersConstructLoaded ? addLevelListT:addLevelListV) {
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
				addVanillaLevel(name, modid, level, color);
				if(ModChecker.isTinkersConstructLoaded)
					addTinkerLevel(name, modid, level, color);
			}
		}else {
			if(modid != null) {
				LogManager.getFormatterLogger(modid + ": " + "Levels").warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}else {
				LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
			}
		}
	}
	private static void addVanillaLevel(String name, String modid, int level, String color) {
		Level addLevel = new Level(name, modid, level, color);
		boolean hasLevel = false;
		if(addLevelListV.size() > 0) {
			for(Level existingLevel : addLevelListV) {
				if(level <= existingLevel.getLevel()) {
					int index = addLevelListV.indexOf(existingLevel);
					List<Level> shiftedLevelList = addLevelListV.subList(index, addLevelListV.size());
					for(Level shiftedLevel : shiftedLevelList) {
						shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
					}
					addLevelListV.add(index, addLevel);
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false)
				addLevelListV.add(addLevel);
		}else {
			addLevelListV.add(addLevel);
		}
	}
	private static void addTinkerLevel(String name, String modid, int level, String color) {
		Level addLevel = new Level(name, modid, level, color);
		boolean hasLevel = false;
		if(addLevelListT.size() > 0) {
			for(Level existingLevel : addLevelListT) {
				if(level <= existingLevel.getLevel()) {
					int index = addLevelListT.indexOf(existingLevel);
					List<Level> shiftedLevelList = addLevelListT.subList(index, addLevelListT.size());
					for(Level shiftedLevel : shiftedLevelList) {
						shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
					}
					addLevelListT.add(index, addLevel);
					hasLevel = true;
					break;
				}
			}
			if(hasLevel == false)
				addLevelListT.add(addLevel);
		}else {
			addLevelListT.add(addLevel);
		}
	}
	
	private static void addVanillaLevels() {
		List<Level> addList = (List<Level>)((ArrayList<Level>)addLevelListV).clone();
		for(Level level : addList) {
			if(level.getLevel() < levelList.size() && ModConfig.addMineLevels) {
				List<Level> shiftedLevelList = levelList.subList(level.getLevel(), levelList.size());
				for(Level shiftedLevel : shiftedLevelList) {
					shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
				}
				levelList.add(level.getLevel(), level);
			}else {
				if(level.getLevel() > (levelList.get(levelList.size()-1).getLevel()+1))
					level.setLevel((levelList.get(levelList.size()-1).getLevel()+1));
				levelList.add(level);
			}
		}
	}
	private static void addTinkerLevels() {
		List<Level> addList = (List<Level>)((ArrayList<Level>)addLevelListT).clone();
		for(Level level : addList) {
			if(level.getLevel() < tinkerLevelList.size() && ModConfig.TINKER_INTEGRATION.tinkerMineLevels) {
				List<Level> shiftedLevelList = tinkerLevelList.subList(level.getLevel(), tinkerLevelList.size());
				for(Level shiftedLevel : shiftedLevelList) {
					shiftedLevel.setLevel(shiftedLevel.getLevel()+1);
				}
				tinkerLevelList.add(level.getLevel(), level);
			}else {
				if(level.getLevel() > (tinkerLevelList.get(tinkerLevelList.size()-1).getLevel()+1))
					level.setLevel((tinkerLevelList.get(tinkerLevelList.size()-1).getLevel()+1));
				tinkerLevelList.add(level);
			}
		}
	}
}