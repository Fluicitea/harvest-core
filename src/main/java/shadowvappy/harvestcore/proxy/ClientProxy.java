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

package shadowvappy.harvestcore.proxy;

import shadowvappy.harvestcore.config.ModConfig;
import shadowvappy.harvestcore.harvestlevels.handlers.TinkerMineLevelHandler;
import shadowvappy.harvestcore.util.ModChecker;

public class ClientProxy implements IProxy
{
	@Override
	public void preInit() {}

	@Override
	public void init() {}

	@Override
	public void postInit() {
		if(ModChecker.isTinkersConstructLoaded) {
			TinkerMineLevelHandler.addLevelsToBook();
		}
	}
}
