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

package shadowvappy.harvestcore.structures;

import java.util.LinkedList;
import java.util.List;

public class Structure 
{
	/** The name of this structure */
	public final String name;

	/** The List of all blockArray layers necessary to complete the structure */
	private final List<int[][][][]> blockArrayList = new LinkedList();

	/** Stores the direction this structure faces. Default is EAST.*/
	private int facing = StructureBase.EAST;
	
	/**
	 * Constructor for unnamed structures
	 */
	public Structure() {
		this.name = "";
	}
	/**
	 * Constructor for named structures
	 */
	public Structure(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the blockArray List for this structure
	 */
	public final List<int[][][][]> blockArrayList() {
		return this.blockArrayList;
	}
	/**
	 * Adds a block array 'layer' to the list to be generated
	 */
	public final void addBlockArray(int blocks[][][][]) {
		this.blockArrayList.add(blocks);
	}
	/**
	 * Adds all elements contained in the parameter list to the structure
	 */
	public final void addBlockArrayList(List<int[][][][]> list) {
		this.blockArrayList.addAll(list);
	}
	/**
	 * Remove all elements in the structure blockArray list
	 */
	public final void resetStructure() {
		this.blockArrayList.clear();
	}
	
	/**
	 * Returns the structure's default facing
	 */
	public final int getFacing() {
		return this.facing;
	}
	/**
	 * Sets the default direction the structure is facing
	 */
	public final void setFacing(int facing) {
		this.facing = facing;
	}
}
