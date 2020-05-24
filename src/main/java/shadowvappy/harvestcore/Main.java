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

package shadowvappy.harvestcore;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import shadowvappy.harvestcore.harvestlevels.Levels;
import shadowvappy.harvestcore.harvestlevels.handlers.MineLevelHandler;
import shadowvappy.harvestcore.harvestlevels.handlers.TinkerMineLevelHandler;
import shadowvappy.harvestcore.proxy.IProxy;
import shadowvappy.harvestcore.util.ModChecker;
import shadowvappy.harvestcore.util.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, 
acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = Reference.DEPENDENCIES)
public class Main 
{
	private static ModChecker modChecker;
	
	@Instance
    public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	{Levels.preLoad();}
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		modChecker = new ModChecker();
		Levels.preInit();
		TinkerMineLevelHandler.preInit();
	}
	
	@EventHandler
    public void init(FMLInitializationEvent event) {}
	
	@EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		MineLevelHandler.postInit();
		proxy.postInit();
	}
	
	@EventHandler
    public void postGameStart(FMLServerStartedEvent event) {}
}
