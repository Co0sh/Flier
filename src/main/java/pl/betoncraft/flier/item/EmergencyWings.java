/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.item;

import org.bukkit.configuration.ConfigurationSection;

import pl.betoncraft.flier.api.InGamePlayer;
import pl.betoncraft.flier.api.Wings;
import pl.betoncraft.flier.core.DefaultUsableItem;
import pl.betoncraft.flier.exception.LoadingException;
import pl.betoncraft.flier.util.ValueLoader;

/**
 * An item which can raise wings' health above 0, thus recreating them.
 *
 * @author Jakub Sapalski
 */
public class EmergencyWings extends DefaultUsableItem {
	
	private double amount;

	public EmergencyWings(ConfigurationSection section) throws LoadingException {
		super(section);
		amount = ValueLoader.loadNonNegativeDouble(section, "amount");
	}

	@Override
	public boolean use(InGamePlayer player) {
		Wings wings = player.getClazz().getCurrentWings();
		if (wings.getHealth() == 0) {
			wings.addHealth(amount);
			return true;
		} else {
			// can't be used if wings have health (are not destroyed)
			return false;
		}
	}

}
