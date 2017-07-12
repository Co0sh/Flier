/**
 * Copyright (c) 2017 Jakub Sapalski
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package pl.betoncraft.flier.effect;

import java.util.Optional;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import pl.betoncraft.flier.api.Flier;
import pl.betoncraft.flier.api.core.InGamePlayer;
import pl.betoncraft.flier.api.core.LoadingException;

/**
 * Makes the player glow for a specified time. 
 *
 * @author Jakub Sapalski
 */
public class GlowingEffect extends DefaultEffect {
	
	private static final String TIME = "time";

	private final int time;
	private final Flier plugin;

	private BukkitRunnable canceler;

	public GlowingEffect(ConfigurationSection section) throws LoadingException {
		super(section);
		if (!type.isPlayerInvolved()) {
			throw new LoadingException("Game sound effect cannot be run on a non-player event.");
		}
		plugin = Flier.getInstance();
		time = loader.loadPositiveInt(TIME);
	}

	@Override
	public void fire(Optional<InGamePlayer> player) {
		if (canceler != null) {
			canceler.cancel();
		}
		canceler = new BukkitRunnable() {
			@Override
			public void run() {
				if (player.get().getPlayer().isGlowing()) {
					player.get().getPlayer().setGlowing(false);
				}
				canceler = null;
			}
		};
		canceler.runTaskLater(plugin, time);
		if (!player.get().getPlayer().isGlowing()) {
			player.get().getPlayer().setGlowing(true);
		}
	}

}
