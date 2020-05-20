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

package shadowvappy.harvestcore.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import shadowvappy.harvestcore.util.Reference;

@Config(modid = Reference.MODID)
@Config.LangKey("harvestcore.config.title")
public class ModConfig 
{
	public static final TinkerIntegration TINKER_INTEGRATION = new TinkerIntegration();
	
	/** If true, add harvest levels before diamond. */
	@Config.Comment("Adds harvest levels before diamond." + "\n" 
			+ "Boolean[Accepted_Values={true, false}, Default=false]")
	@Config.RequiresMcRestart
	public static boolean addMineLevels = false;
	
	/** Tinkers Integrations Configuration */
	public static class TinkerIntegration {
		/** If true, add harvest levels before cobalt into tinkers. */
		@Config.Comment("Adds mining levels for Tinkers' Construct that would be before Cobalt." + "\n" 
				+ "Boolean[Accepted_Values={true, false}, Default=false]" + "\n"
				+ "Warning: If set to a different value than addMineLevels, things will be weird with tinkers' tools. If you do not have Tinkers' Construct, you can ignore this warning.")
		@Config.RequiresMcRestart
		public boolean tinkerMineLevels = false;
	}
	
	@Mod.EventBusSubscriber(modid = Reference.MODID)
	private static class EventHandler {
		/**
		 * Inject the new values and save to the config when the config is changed through the GUI.
		 * 
		 * @param event The event
		 */
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(Reference.MODID)) {
				ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
