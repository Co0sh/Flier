/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.bonus;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import pl.betoncraft.flier.api.Flier;
import pl.betoncraft.flier.api.content.Game;
import pl.betoncraft.flier.api.core.InGamePlayer;
import pl.betoncraft.flier.api.core.LoadingException;
import pl.betoncraft.flier.api.core.UsableItem;

/**
 * A Bonus without physical manifestation, activated by proximity.
 *
 * @author Jakub Sapalski
 */
public class ProximityBonus extends DefaultBonus {

	protected Location location;
	protected final double distance;
	protected BukkitRunnable checker;

	public ProximityBonus(ConfigurationSection section, Game game, Optional<InGamePlayer> creator,
			Optional<UsableItem> item) throws LoadingException {
		super(section, game, creator, item);
		distance = Math.pow(loader.loadNonNegativeDouble("distance"), 2);
		location = game.getArena().getLocationSet(loader.loadString("location")).getSingle();
	}
	
	public void check() {
		for (InGamePlayer player : game.getPlayers().values()) {
			if (player != null &&
					player.isPlaying() &&
					player.getPlayer().getLocation().distanceSquared(location) <= distance) {
				apply(player);
			}
		}
	}
	
	@Override
	public void release() {
		super.release();
		checker = new BukkitRunnable() {
			@Override
			public void run() {
				check();
			}
		};
		checker.runTaskTimer(Flier.getInstance(), 1, 1);
	}
	
	@Override
	public void block() {
		super.block();
		if (checker != null) {
			checker.cancel();
			checker = null;
		}
	}

}
