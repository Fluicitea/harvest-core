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
import java.util.Random;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.FMLCommonHandler;
import shadowvappy.harvestcore.util.LogHelper;
import shadowvappy.harvestcore.util.Reference;

public class StructureBase extends WorldGenerator
{
private static final Logger LOG = LogHelper.getLogger(null, "WorldGen");
	
	/** Use this value to skip generating a block */
	public static final int SET_NO_BLOCK = Integer.MAX_VALUE;
	
	/** The directional values*/
	public static final int SOUTH = 0, WEST = 1, NORTH = 2, EAST = 3;
	/** Stores the direction the structure faces. Default EAST */
	private int structureFacing = EAST;
	
	/** Stores amount to offset structure generation on Y axis */
	private int offsetY = 0;
	
	/** Stores the data for current layer */
	private int[][][][] blockArray;
	/** Stores a list of the structure to build, in 'layers' made up of individual blockArrays. */
	private final List<int[][][][]> blockArrayList = new LinkedList();
	
	/**
	 * Basic constructor. Sets generator to notify other blocks of blocks it changes.
	 */
	public StructureBase() {
		super(true);
	}
	
	/**
	 * Sets the default direction the structure is facing
	 */
	public final void setStructureFacing(int facing) {
		structureFacing = facing % 4;
	}
	/**
	 * Returns a string describing current facing of structure
	 */
	public final String currentStructureFacing() {
		return (structureFacing == EAST ? "East" : structureFacing == WEST ? "West" : structureFacing == NORTH ? "North" : "South");
	}
	
	/**
	 * Adds a block array 'layer' to the list to be generated
	 */
	public final void addBlockArray(int blocks[][][][])
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			blockArrayList.add(blocks);
			if (blockArray == null)
				blockArray = blocks;
		}
	}
	/**
	 * Overwrites current list with the provided blockArray
	 */
	public final void setBlockArray(int blocks[][][][])
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			blockArrayList.clear();
			blockArrayList.add(blocks);
			blockArray = blocks;
		}
	}
	
	/**
	 * Adds all elements contained in the parameter list to the structure
	 */
	public final void addBlockArrayList(List<int[][][][]> list)
	{
		blockArrayList.addAll(list);

		if (blockArray == null && list.size() > 0)
			blockArray = list.get(0);
	}
	/**
	 * Overwrites current blockArrayList with list provided
	 */
	public final void setBlockArrayList(List<int[][][][]> list)
	{
		blockArrayList.clear();
		blockArrayList.addAll(list);
		blockArray = (list.size() > 0 ? list.get(0) : null);
	}
	
	/**
	 * Overwrites current Structure information with passed in structure
	 * Sets structure facing to the default facing of the structure
	 * Does NOT set offset for the structure
	 */
	public final void setStructure(Structure structure) {
		if (structure != null) {
			reset();
			setBlockArrayList(structure.blockArrayList());
			setStructureFacing(structure.getFacing());
		} else {
			LOG.error("NULL Structure cannot be set!");
		}
	}
	
	/**
	 * Returns true if the generator has enough information to generate a structure
	 */
	public final boolean canGenerate() {
		return blockArrayList.size() > 0 || blockArray != null;
	}
	/**
	 * Generates each consecutive blockArray in the current list at location pos, 
	 * incremented by the height of each previously generated blockArray.
	 */
	@Override
	public final boolean generate(World world, Random random, BlockPos pos) {
		if (world.isRemote || !canGenerate()) {
			return false;
		}
		
		int posX = pos.getX(), posY = pos.getY(), posZ = pos.getZ();
		boolean generated = true;

		for (int[][][][] blocks : blockArrayList) {
			if (!generated) { break; }
			this.blockArray = blocks;
			generated = generateLayer(world, random, posX, posY, posZ);
			offsetY += blocks.length;
		}
		reset();
		return generated;
	}
	/**
	 * Generate method that generates a single 'layer' from the list of blockArrays
	 */
	private final boolean generateLayer(World world, Random random, int posX, int posY, int posZ) {
		int centerX = blockArray[0].length / 2, centerZ = blockArray[0][0].length / 2;
		for(int y=0; y<blockArray.length; y++) {
			for(int x=0; x<blockArray[y].length; x++) {
				for(int z=0; z<blockArray[y][x].length; z++) {
					if(blockArray[y][x][z].length == 0 || blockArray[y][x][z][0] == SET_NO_BLOCK) {
						continue;
					}

					int rotX = posX+x-centerX, rotY = posY+y+offsetY, rotZ = posZ+z-centerZ;
					
					int id = blockArray[y][x][z][0];
					int meta = (blockArray[y][x][z].length > 1 ? blockArray[y][x][z][1] : 0);

					setBlockAt(world, id, meta, rotX, rotY, rotZ);
				}
			}
		}
		return true;
	}
	/**
	 * Handles setting block at x/y/z in world.
	 * Arguments should be those retrieved from blockArray
	 */
	private final void setBlockAt(World world, int id, int meta, int x, int y, int z) {
		Block block = Block.getBlockById(id);
		if (id >= 0 || world.isAirBlock(new BlockPos(x,y,z)) || !world.getBlockState(new BlockPos(x,y,z)).getMaterial().blocksMovement()) {
			world.setBlockState(new BlockPos(x,y,z), block.getStateFromMeta(meta));
		}
	}
	/**
	 * Clears blockArray, blockArrayList and offsets for next structure
	 */
	private final void reset() {
		blockArrayList.clear();
		blockArray = null;
		offsetY = 0;
	}
}
