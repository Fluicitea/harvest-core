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

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadowvappy.harvestcore.harvestlevels.Level;
import shadowvappy.harvestcore.harvestlevels.Levels;
import shadowvappy.harvestcore.util.ModChecker;
import slimeknights.tconstruct.library.events.MaterialEvent.StatRegisterEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class TinkerMineLevelHandler 
{
	public static TinkerMineLevelHandler instance = new TinkerMineLevelHandler();
	
	public void init() {
		if(ModChecker.isTinkersConstructLoaded) {
			MinecraftForge.EVENT_BUS.register(this);
		}
	}
	
	public static void postInit() {
		if(ModChecker.isTinkersConstructLoaded) {
			addLevelsToBook();
		}
	}
	
	public static void addLevelsToBook() {
		for(Level level : Levels.tinkerLevelList) {
			HarvestLevels.harvestLevelNames.put(level.getLevel(), level.getColor() + level.getName());
		}
	}
	
	@SubscribeEvent
	public void onStatRegister(StatRegisterEvent<IMaterialStats> statRegisterEvent) {
		IMaterialStats newStats = null;
		IMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats:statRegisterEvent.stats;
		HeadMaterialStats headStats = null;
		String mat = null;
		
		if(oldStats instanceof HeadMaterialStats) {
			headStats = (HeadMaterialStats)oldStats;
			mat = statRegisterEvent.material.getLocalizedName();
			boolean levelIsVanilla = true;
			for(Level level : Levels.tinkerLevelList) {
				if(level.getName().equalsIgnoreCase(Levels.originalTinkerLevelList.get(level.getLevel()).getName())) {
					levelIsVanilla = true;
				}else {
					levelIsVanilla = false;
				}
				if(mat.equalsIgnoreCase("cobalt") || mat.equalsIgnoreCase("ardite") || mat.equalsIgnoreCase("manyullyn")) {
					if(level.getName().equalsIgnoreCase("cobalt") && Levels.tinkerLevelList.size() > level.getLevel()+1) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getLevel()+1);
						break;
					}
				}
				if(mat.equalsIgnoreCase("electrum") || mat.equalsIgnoreCase("silver")) {
					if(level.getName().equalsIgnoreCase("diamond")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getLevel());
						break;
					}
				}
				if(mat.equalsIgnoreCase("prismarine")) {
					if(level.getName().equalsIgnoreCase("iron")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getLevel());
						break;
					}
				}
				if(mat.equalsIgnoreCase("netherrack")) {
					if(level.getName().equalsIgnoreCase("iron")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getLevel()-1);
						break;
					}
				}
				if(mat.equalsIgnoreCase("stone") || mat.equalsIgnoreCase("flint") || mat.equalsIgnoreCase("bone") || mat.equalsIgnoreCase("cactus")) {
					newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, 1);
					break;
				}
				if(mat.equalsIgnoreCase(level.getName())) {
					newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getLevel());
					break;
				}
				if(headStats.harvestLevel == level.getLevel() && !levelIsVanilla) {
					newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, headStats.harvestLevel+1);
					headStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, headStats.harvestLevel+1);
				}
			}
		}
		if(newStats != oldStats) {
			statRegisterEvent.overrideResult(newStats);
		}
	}
}
