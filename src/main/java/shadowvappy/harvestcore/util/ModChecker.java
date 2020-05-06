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

package shadowvappy.harvestcore.util;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Loader;

public class ModChecker 
{
	private static final Logger LOG = LogHelper.getLogger("ModChecker");
	public static boolean isTinkersConstructLoaded;
	
	public ModChecker() {
		this.isTinkersConstructLoaded = Loader.isModLoaded("tconstruct");
	}
	
	public static void printSuccessMessage() {
		LOG.info("Tinkers Construct is Loaded: " + isTinkersConstructLoaded);
	}
}
