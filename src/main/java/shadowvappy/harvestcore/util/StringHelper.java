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

public class StringHelper 
{
	/**
	 * Capitalize the first letter of a given string.
	 * @param string The string to capitalize.
	 * @return The capitalized string.
	 */
	public static String capitalize(String string) {
		String capitalizedString = string.substring(0, 1).toUpperCase() + string.substring(1);
		return capitalizedString;
	}
	
	/**
	 * Converts an unlocalized item name from "material_item" form to proper oreDict "itemMaterial" form.
	 * @param itemName The unlocalized name to convert.
	 * @return The oreDict entry name.
	 */
	public static String getOreDictName(String itemName) {
		String[] Split = itemName.split(":");
		String[] Split_Name = Split[1].split("_");
		String Material_Name = capitalize(Split_Name[0]);
		String Type_Name = Split_Name[1];
		String name = Type_Name + Material_Name;
		return name;
	}
}
