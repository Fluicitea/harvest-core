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

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHelper 
{
	/**
	 * Method for generating a logger with name set as the mod id.
	 * 
	 * @param The type of the logger.
	 */
	public static Logger getLogger() {
		String name = Reference.MODID;
		Logger logger = LogManager.getFormatterLogger(name);
		return logger;
	}
	/**
	 * Method for generating a logger with name set as the mod id with a type label.
	 * 
	 * @param The type of the logger.
	 */
	public static Logger getLogger(@Nullable String type) {
		String name = Reference.MODID;
		Logger logger = LogManager.getFormatterLogger(name + ": " + type);
		return logger;
	}
}
