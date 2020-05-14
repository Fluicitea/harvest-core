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

public class Reference 
{
	public static final String MODID = "harvestcore";
	public static final String NAME = "Harvest Core";
	public static final String VERSION = "1.12.2-1.0.0";
	public static final String ACCEPTED_VERSIONS = "[1.12.2, 1.13)";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.1.2577,);"
			   								  + "before:tconstruct@[1.12.2-2.12.0.157,)";
	public static final String CLIENT_PROXY_CLASS = "shadowvappy.harvestcore.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "shadowvappy.harvestcore.proxy.ServerProxy";
}
