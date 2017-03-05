/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.api.core;

import org.bukkit.inventory.ItemStack;

/**
 * Represents an item.
 *
 * @author Jakub Sapalski
 */
public interface Item {

	/**
	 * @return the ItemStack equal to this item
	 */
	public ItemStack getItem();

	/**
	 * @return weight of an item
	 */
	public double getWeight();

	/**
	 * @return the slot in which this item is to be placed
	 */
	public int slot();

	/**
	 * Compares two items by the default values (ignores mutable values). This
	 * returns true for replicated items.
	 * 
	 * @param key
	 *            another item to compare
	 * @return true if the item was replicated from this one, false if not
	 */
	public boolean isSimilar(Item key);

}