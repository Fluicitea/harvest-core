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

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadowvappy.harvestcore.common.harvestlevels.Levels;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.ModChecker;
import slimeknights.tconstruct.library.events.MaterialEvent.StatRegisterEvent;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.utils.HarvestLevels;

import java.util.Iterator;
import java.util.Map;

public class TinkerMineLevelHandler 
{
	private static final Logger LOG = LogHelper.getLogger(null, "TinkerLevels");
	
	public static void preInit() {
		if(ModChecker.isTinkersConstructLoaded)
			MinecraftForge.EVENT_BUS.register(new TinkerMineLevelHandler());
	}
	
	public static void postInit() {
		if(ModChecker.isTinkersConstructLoaded)
			TinkerMineLevelHandler.addLevelsToBook();
	}
	
	public static void addLevelsToBook() {
		for(Map.Entry<Integer, String> level : Levels.tinkerLevelList.entrySet()) {
			if(Levels.colorList.containsKey(level.getValue()))
				HarvestLevels.harvestLevelNames.put(level.getKey(), Levels.colorList.get(level.getValue()) + level.getValue());
			else
				HarvestLevels.harvestLevelNames.put(level.getKey(), level.getValue());
		}
	}
	
	@SubscribeEvent
	public void onStatRegister(StatRegisterEvent<IMaterialStats> statRegisterEvent) {
		IMaterialStats newStats = null;
		IMaterialStats oldStats = statRegisterEvent.newStats != null ? statRegisterEvent.newStats:statRegisterEvent.stats;
		HeadMaterialStats headStats;
		String mat = null;
		if(oldStats instanceof HeadMaterialStats) {
			headStats = (HeadMaterialStats)oldStats;
			mat = statRegisterEvent.material.getLocalizedName();
			Iterator<Map.Entry<Integer, String>> originalLevels = Levels.originalTinkerLevelList.entrySet().iterator();
			Iterator<Map.Entry<Integer, String>> levels = Levels.tinkerLevelList.entrySet().iterator();
			Map.Entry<Integer, String> originalLevel = originalLevels.next();
			boolean levelIsVanilla;
			for(Map.Entry<Integer, String> level : Levels.tinkerLevelList.entrySet()) {
				if(level.getValue().equalsIgnoreCase(originalLevel.getValue())) {
					if(originalLevels.hasNext())
						originalLevel = originalLevels.next();
					levelIsVanilla = true;
				}else
					levelIsVanilla = false;
				if(mat.equalsIgnoreCase("cobalt") ||
						mat.equalsIgnoreCase("ardite") ||
						mat.equalsIgnoreCase("manyullyn")) {
					if(level.getValue().equalsIgnoreCase("cobalt")) {
						while(levels.hasNext()) {
							if(levels.next().getKey() > level.getKey()) {
								newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey()+1);
								break;
							}
						}
						if(newStats == null)
							newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey());
						break;
					}
				}else if(mat.equalsIgnoreCase("electrum") ||
						mat.equalsIgnoreCase("silver")) {
					if(level.getValue().equalsIgnoreCase("diamond")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey());
						break;
					}
				}else if(mat.equalsIgnoreCase("pigiron")) {
					if(level.getValue().equalsIgnoreCase("iron")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey()+1);
						break;
					}
				}else if(mat.equalsIgnoreCase("prismarine")) {
					if(level.getValue().equalsIgnoreCase("iron")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey());
						break;
					}
				}else if(mat.equalsIgnoreCase("netherrack")) {
					if(level.getValue().equalsIgnoreCase("iron")) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey()-1);
						break;
					}
				}else if(mat.equalsIgnoreCase("stone") ||
						mat.equalsIgnoreCase("flint") ||
						mat.equalsIgnoreCase("cactus") ||
						mat.equalsIgnoreCase("bone")) {
					newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, 1);
					break;
				}else if(mat.equalsIgnoreCase("wood")) {
					newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, 0);
					break;
				}else if(mat.equalsIgnoreCase(level.getValue())) {
					if(level.getKey() < Levels.tinkerLevelList.size() - 1) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey()+1);
					}else {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, level.getKey());
					}
					break;
				}else if(!levelIsVanilla) {
					if(headStats.harvestLevel > level.getKey()) {
						newStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, headStats.harvestLevel+1);
						headStats = new HeadMaterialStats(headStats.durability, headStats.miningspeed, headStats.attack, headStats.harvestLevel+1);
					}
				}
			}
		}
		if(newStats != null) {
			statRegisterEvent.overrideResult(newStats);
			LOG.info(mat + " stats have been changed!");
		}
	}
}