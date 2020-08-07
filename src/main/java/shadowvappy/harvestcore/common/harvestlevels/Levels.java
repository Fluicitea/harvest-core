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

package shadowvappy.harvestcore.common.harvestlevels;

import net.minecraft.util.text.TextFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadowvappy.harvestcore.config.HarvestCoreConfig;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.ModChecker;
import slimeknights.tconstruct.tools.TinkerMaterials;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Levels
{
    private static final Logger LOG = LogHelper.getLogger(null, "Levels");
    private static int added0 = 0, added1 = 0, added2 = 0, added3 = 0, added4 = 0, added5 = 0;

    /** List in which to store added harvest levels. */
    private static final Map<Integer, String> addLevelListV = new TreeMap<>();
    private static final Map<Integer, String> addLevelListT = new TreeMap<>();
    /** List in which to store vanilla harvest levels. */
    public static Map<Integer, String> levelList = new TreeMap<>();
    public static Map<Integer, String> originalLevelList = new TreeMap<>();
    /** List in which to store tinker harvest levels. */
    public static Map<Integer, String> tinkerLevelList = new TreeMap<>();
    public static Map<Integer, String> originalTinkerLevelList = new TreeMap<>();

    public static Map<String, String> modidList = new HashMap<>();
    public static Map<String, String> colorList = new HashMap<>();

    public static void preLoad() {
        setupBaseLevelLists();
        setupBaseColorList();
    }

    public static void preInit() {
        if(ModChecker.isTinkersConstructLoaded) {
            addTinkerLevels();
        }
        addVanillaLevels();
        checkLevelList();
    }

    private static void setupBaseLevelLists() {
        /* Add vanilla levels to vanilla level list */
        levelList.put(0, "Stone");
        levelList.put(1, "Iron");
        levelList.put(2, "Diamond");
        levelList.put(3, "Obsidian");
        originalLevelList.putAll(levelList);

        /* Add vanilla and tinker levels to tinker level list */
        tinkerLevelList.put(0, "Stone");
        tinkerLevelList.put(1, "Iron");
        tinkerLevelList.put(2, "Diamond");
        tinkerLevelList.put(3, "Obsidian");
        tinkerLevelList.put(4, "Cobalt");
        originalTinkerLevelList.putAll(tinkerLevelList);
    }

    private static void setupBaseColorList() {
        colorList.put("Stone", TinkerMaterials.stone.getTextColor());
        colorList.put("Iron", TinkerMaterials.iron.getTextColor());
        colorList.put("Diamond", TextFormatting.AQUA.toString());
        colorList.put("Obsidian", TinkerMaterials.obsidian.getTextColor());
        colorList.put("Cobalt", TinkerMaterials.cobalt.getTextColor());
    }

    /**
     * Add a harvest level to the list. <br>
     * Must be placed before preInit, eg. static {addLevel(Copper, MODID, 1, null)}. <br>
     * Put levels you add with the same harvest level in consecutive order, or set them to their corresponding harvest level. <br>
     * eg. if adding Copper and Lead levels before Iron, add Copper as 1 first then Lead as 1, or Copper as 1 first then Lead as 2. <br>
     * TConstruct harvest levels (in terms of what it can mine): Stone=0; Iron=1; Diamond=2; Obsidian=3; Cobalt=4;
     *
     * @param name The name for the harvest level to add
     * @param modid The modid of the mod adding the harvest level
     * @param level The harvest level to add, <i>always</i> give the TConstruct level you wish to add your level before
     * @param color The color for the harvest level to be set in the TConstruct book, please use the TConstruct material color if possible
     */
    public static void addLevel(String name, String modid, int level, @Nullable String color) {
        if(level >= 0) {
            boolean hasLevel = false;
            for(String val : ModChecker.isTinkersConstructLoaded ? originalTinkerLevelList.values():originalLevelList.values()) {
                if(name.equalsIgnoreCase(val)) {
                    hasLevel = true;
                    break;
                }
            }
            if(!hasLevel) {
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
                    default:
                        level += added0+added1+added2+added3+added4+added5;
                        added5++;
                }
                addVanillaLevel(name, modid, level);
                addTinkerLevel(name, level, color);
            }
        }else {
            if(modid != null) {
                LogManager.getFormatterLogger(modid + ": " + "Levels").warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
            }else {
                LOG.warn("Level can not be a negative number, aborting. Please use a level of 0 to add a mine level below stone.");
            }
        }
    }
    private static void addVanillaLevel(String name, String modid, int level) {
        boolean hasLevel = false;
        if(HarvestCoreConfig.addMineLevels || level >= originalLevelList.size()) {
            if(addLevelListV.size() > 0) {
                for(Map.Entry<Integer, String> lvl : addLevelListV.entrySet()) {
                    if(name.equalsIgnoreCase(lvl.getValue())) {
                        hasLevel = true;
                    }
                    if(level <= lvl.getKey()) {
                        addLevelListV.replace(lvl.getKey()+1, lvl.getValue());
                    }
                }
                if(!hasLevel) {
                    addLevelListV.put(level, name);
                    modidList.put(name, modid);
                }
            }else {
                addLevelListV.put(level, name);
                modidList.put(name, modid);
            }
        }
    }
    private static void addTinkerLevel(String name, int level, String color) {
        boolean hasLevel = false;
        if(HarvestCoreConfig.TINKER_INTEGRATION.tinkerMineLevels || level >= originalLevelList.size()) {
            if(addLevelListT.size() > 0) {
                for(Map.Entry<Integer, String> lvl : addLevelListT.entrySet()) {
                    if(name.equalsIgnoreCase(lvl.getValue())) {
                        hasLevel = true;
                    }
                    if(level <= lvl.getKey()) {
                        addLevelListT.replace(lvl.getKey()+1, lvl.getValue());
                    }
                }
                if(!hasLevel) {
                    addLevelListT.put(level, name);
                    if(color != null) {
                        if(color.equals(colorList.get("Diamond"))  && (HarvestCoreConfig.addMineLevels || level >= originalTinkerLevelList.size()))
                            colorList.replace("Diamond", TextFormatting.DARK_AQUA.toString());
                        colorList.put(name, color);
                    }
                }
            }else {
                addLevelListT.put(level, name);
                if(color != null) {
                    if(color.equals(colorList.get("Diamond")) && (HarvestCoreConfig.addMineLevels || level >= originalTinkerLevelList.size()))
                        colorList.replace("Diamond", TextFormatting.DARK_AQUA.toString());
                    colorList.put(name, color);
                }
            }
        }
    }

    private static void addVanillaLevels() {
        Map<Integer, String> existingLevels = new TreeMap<>();
        for(Map.Entry<Integer, String> level : addLevelListV.entrySet()) {
            existingLevels.putAll(levelList);
            for(Map.Entry<Integer, String> existingLevel : existingLevels.entrySet()) {
                if(level.getKey() <= existingLevel.getKey() && !(addLevelListV.containsValue(existingLevel.getValue())))
                    levelList.put(existingLevel.getKey()+1, existingLevel.getValue());
            }
            levelList.put(level.getKey(), level.getValue());
        }
    }
    private static void addTinkerLevels() {
        Map<Integer, String> existingLevels = new TreeMap<>();
        for(Map.Entry<Integer, String> level : addLevelListT.entrySet()) {
            existingLevels.putAll(tinkerLevelList);
            for(Map.Entry<Integer, String> existingLevel : existingLevels.entrySet()) {
                if(level.getKey() <= existingLevel.getKey() && !(addLevelListT.containsValue(existingLevel.getValue())))
                    tinkerLevelList.put(existingLevel.getKey()+1, existingLevel.getValue());
            }
            tinkerLevelList.put(level.getKey(), level.getValue());
        }
    }

    private static void checkLevelList() {
        for(Map.Entry<Integer, String> level : tinkerLevelList.entrySet()) {
            LOG.info(level.getValue() + ": " + level.getKey());
        }
    }
}