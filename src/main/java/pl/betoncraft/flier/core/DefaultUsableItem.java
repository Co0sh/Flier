/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import pl.betoncraft.flier.api.content.Action;
import pl.betoncraft.flier.api.content.Activator;
import pl.betoncraft.flier.api.core.InGamePlayer;
import pl.betoncraft.flier.api.core.Item;
import pl.betoncraft.flier.api.core.LoadingException;
import pl.betoncraft.flier.api.core.UsableItem;
import pl.betoncraft.flier.api.core.Usage;
import pl.betoncraft.flier.core.defaults.DefaultItem;

/**
 * Default implementation of UsableItem.
 *
 * @author Jakub Sapalski
 */
public class DefaultUsableItem extends DefaultItem implements UsableItem {
	
	protected final boolean consumable;
	protected final int maxAmmo;
	protected final List<Usage> usages = new ArrayList<>();

	protected int time = 0;
	protected int whole = 0;
	protected int ammo;

	protected boolean set = false;
	protected int def;
	protected int max;
	protected int min;
	protected int amount;

	public DefaultUsableItem(ConfigurationSection section) throws LoadingException {
		super(section);
		consumable = loader.loadBoolean("consumable", false);
		maxAmmo = loader.loadNonNegativeInt("ammo", 0);
		ammo = maxAmmo;
		ConfigurationSection usagesSection = section.getConfigurationSection("usages");
		if (usagesSection != null) for (String id : usagesSection.getKeys(false)) {
			ConfigurationSection usageSection = usagesSection.getConfigurationSection(id);
			if (usageSection != null) try {
				usages.add(new DefaultUsage(usageSection));
			} catch (LoadingException e) {
				throw (LoadingException) new LoadingException(String.format("Error in '%s' usage.", id)).initCause(e);
			}
		}
	}

	@Override
	public boolean isReady() {
		return time == 0;
	}

	@Override
	public int getWholeCooldown() {
		return whole;
	}

	@Override
	public int getCooldown() {
		return time;
	}
	
	@Override
	public boolean isConsumable() {
		return consumable;
	}

	@Override
	public int getMaxAmmo() {
		return maxAmmo;
	}

	@Override
	public int getAmmo() {
		return ammo;
	}

	@Override
	public void setAmmo(int ammo) {
		this.ammo = ammo;
		if (this.ammo < 0) {
			this.ammo = 0;
		}
		if (this.ammo > maxAmmo) {
			this.ammo = maxAmmo;
		}
	}

	@Override
	public List<Usage> getUsages() {
		return usages;
	}

	@Override
	public boolean use(InGamePlayer player) {
		if (time > 0) {
			time--;
		}
		boolean used = false;
		if (isReady()) {
			usages:
			for (Usage usage : usages) {
				if (!usage.canUse(player)) {
					continue;
				}
				if (maxAmmo > 0 && ammo - usage.getAmmoUse() < 0) {
					continue;
				}
				for (Activator activator : usage.getActivators()) {
					if (!activator.isActive(player, this)) {
						continue usages;
					}
				}
				used = true;
				time = usage.getCooldown();
				setAmmo(ammo - usage.getAmmoUse());
				whole = time;
				for (Action action : usage.getActions()) {
					action.act(player);
				}
			}
		}
		return used;
	}
	
	@Override
	public int getAmount() {
		return amount;
	}
	
	@Override
	public boolean setAmount(int amount) {
		if (amount < 0 || amount > max) {
			return false;
		} else {
			this.amount = amount;
			return true;
		}
	}
	
	@Override
	public int getMax() {
		return max;
	}
	
	@Override
	public int getMin() {
		return min;
	}
	
	@Override
	public void setDefaultAmounts(int def, int max, int min) throws UnsupportedOperationException {
		if (!set) {
			set = true;
			this.def = def;
			this.max = max;
			this.min = min;
			this.amount = def;
		} else {
			throw new UnsupportedOperationException("Cannot set default values twice!");
		}
	}

	@Override
	public int getDefaultAmount() {
		return def;
	}
	
	@Override
	public boolean isSimilar(Item item) {
		if (item instanceof DefaultUsableItem && super.isSimilar(item)) {
			DefaultUsableItem usable = (DefaultUsableItem) item;
			return usable.consumable == consumable &&
					usable.ammo == ammo &&
					usable.usages.stream().allMatch(thatUsage -> usages.stream().anyMatch(thisUsage -> thatUsage.equals(thisUsage)));
		}
		return false;
	}
	
	@Override
	public void refill() {
		ammo = maxAmmo;
		time = 0;
		whole = 0;
	}
}
