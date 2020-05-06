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

public class Levels 
{
	/** List in which to store ignored mods. */
	public static List<String> modIgnoreList = new ArrayList<String>();
	/** List in which to store vanilla harvest levels. */
	public static List<Level> levelList = new ArrayList<Level>();
	public static List<Level> originalLevelList = new ArrayList<Level>();
	/** List in which to store tinker harvest levels. */
	public static List<Level> tinkerLevelList = new ArrayList<Level>();
	public static List<Level> originalTinkerLevelList = new ArrayList<Level>();
	
	/* Vanilla Levels */
	public static final Level STONE = new Level("Stone", 0);
	public static final Level IRON = new Level("Iron", 1);
	public static final Level DIAMOND = new Level("Diamond", 2);
	public static final Level OBSIDIAN = new Level("Obsidian", 3);
	/* Tinkers Levels */
	public static final Level COBALT = new Level("Cobalt", 4);
	
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
	
	public static void addModToIgnoreList(String modid) {
		if(!modIgnoreList.contains(modid))
			modIgnoreList.add(modid);
	}
	public static void addLevel(String name, int level) {
		Level harvestLevel = new Level(name, level);
		
		if(levelList.get(level) != null) {
			List<Level> newList = levelList.subList(level, levelList.size()-1);
			levelList.set(level, harvestLevel);
			for(Level postLevel : newList) {
				levelList.set(postLevel.getLevel()+1, postLevel);
			}
		}else {
			levelList.add(harvestLevel);
		}
	}
	public static void addTinkerLevel(String name, int level) {
		Level harvestLevel = new Level(name, level);
		
		if(tinkerLevelList.get(level) != null) {
			List<Level> newList = tinkerLevelList.subList(level, tinkerLevelList.size()-1);
			tinkerLevelList.set(level, harvestLevel);
			for(Level postLevel : newList) {
				tinkerLevelList.set(postLevel.getLevel()+1, postLevel);
			}
		}else {
			tinkerLevelList.add(harvestLevel);
		}
	}
}