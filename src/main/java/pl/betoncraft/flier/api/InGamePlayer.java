/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.api;

import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import pl.betoncraft.flier.api.Damager.DamageResult;

/**
 * Represents a player who is in a Game.
 *
 * @author Jakub Sapalski
 */
public interface InGamePlayer {

	/**
	 * Stuff which needs to happen to the player to let him fly. Speeding up,
	 * taking fuel, regenerating stuff.
	 */
	public void fastTick();

	/**
	 * Stuff which helps the player fly, but is not critical to the game, like
	 * updating displayed statistics.
	 */
	public void slowTick();

	/**
	 * Called to indicate the player left clicked.
	 */
	public void leftClick();

	/**
	 * @return whenever the player left clicked;
	 */
	public boolean didLeftClick();

	/**
	 * Called to indicate the player right clicked.
	 */
	public void rightClick();

	/**
	 * @return whenever the player right clicked;
	 */
	public boolean didRightClick();

	/**
	 * @return whenever the player is holding this item or not
	 */
	public boolean isHolding(UsableItem item);

	/**
	 * Called when the Game decides the player was damaged by a Damager object,
	 * optionally fired by attacker.
	 * 
	 * @param attacker
	 *            the player who fired the damager
	 * @param damager
	 *            the Damager object (for example Weapon)
	 * @return the result of damage
	 */
	public DamageResult damage(InGamePlayer attacker, Damager damager);

	/**
	 * @return the the lobby this player is in
	 */
	public Lobby getLobby();

	/**
	 * @return the Player object of this player
	 */
	public Player getPlayer();

	/**
	 * @return total weight of all items carried by the player
	 */
	public double getWeight();

	/**
	 * @return whenever the player is currently playing
	 */
	public boolean isPlaying();

	/**
	 * @param isPlaying
	 *            whenever the player is currently playing
	 */
	public void setPlaying(boolean isPlaying);

	/**
	 * @return the PlayerClass object of this player
	 */
	public PlayerClass getClazz();

	/**
	 * Updates the player with his class items.
	 */
	public void updateClass();
	
	/**
	 * Clears the player, resetting all properties like health, food level, velocity etc.
	 */
	public void clear();

	/**
	 * @return the player who last attacked this player
	 */
	public InGamePlayer getAttacker();

	/**
	 * @param attacker
	 *            the player who last attacked this player
	 */
	public void setAttacker(InGamePlayer attacker);

	/**
	 * @return the amount of money the player has
	 */
	public int getMoney();

	/**
	 * @param amount
	 *            the amount of money to set for the player
	 */
	public void setMoney(int amount);

	/**
	 * @return the color of this player
	 */
	public ChatColor getColor();

	/**
	 * Sets the color of this player to specified one. It will be used as a team
	 * color to control glow and name color.
	 * 
	 * @param color
	 *            the color to set
	 */
	public void setColor(ChatColor color);

	/**
	 * Update Scoreboard teams with these players' colors.
	 * 
	 * @param map
	 *            map containing player names with their colors
	 */
	public void updateColors(Map<String, ChatColor> map);

	/**
	 * @return a mutable list of SidebarLines this player has.
	 */
	public List<SidebarLine> getLines();

	/**
	 * Makes the player leave the game back to the lobby.
	 */
	public void exitGame();

	/**
	 * Makes the player leave the lobby back to Minecraft.
	 */
	public void exitLobby();

}
