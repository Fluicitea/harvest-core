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

package shadowvappy.harvestcore.harvestlevels;

import javax.annotation.Nullable;

public class Level {
	private String name;
	private int level;
	private String modid;
	private String color;
	
	public Level(String name, int level) {
		this(name, level, "vanilla");
	}
	public Level(String name, String modid, int level) {
		this(name, level, modid, null);
	}
	public Level(String name, int level, @Nullable String color) {
		this(name, level, "vanilla", color);
	}
	public Level(String name, int level, String modid, @Nullable String color) {
		setName(name);
		setLevel(level);
		setModId(modid);
		setColor(color);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getModId() {
		return modid;
	}
	public void setModId(String modid) {
		this.modid = modid;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}