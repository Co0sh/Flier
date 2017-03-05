/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.core.defaults;

import org.bukkit.configuration.ConfigurationSection;

import pl.betoncraft.flier.api.content.Engine;
import pl.betoncraft.flier.api.core.LoadingException;

/**
 * Default Engine implementation.
 *
 * @author Jakub Sapalski
 */
public abstract class DefaultEngine extends DefaultItem implements Engine {
	
	protected final double maxFuel;
	protected final double consumption;
	protected final double regeneration;
	protected final int glowTime;
	
	protected double fuel;

	public DefaultEngine(ConfigurationSection section) throws LoadingException {
		super(section);
		maxFuel = loader.loadPositiveDouble("max_fuel");
		consumption = loader.loadNonNegativeDouble("consumption");
		regeneration = loader.loadNonNegativeDouble("regeneration");
		glowTime = loader.loadNonNegativeInt("glow_time");
		fuel = maxFuel;
	}

	@Override
	public double getMaxFuel() {
		return maxFuel;
	}

	@Override
	public double getConsumption() {
		return consumption;
	}

	@Override
	public double getRegeneration() {
		return regeneration;
	}
	
	@Override
	public int getGlowTime() {
		return glowTime;
	}

	@Override
	public double getFuel() {
		return fuel;
	}
	
	@Override
	public boolean addFuel(double amount) {
		if (fuel >= maxFuel) {
			return false;
		}
		if (fuel + amount > maxFuel) {
			fuel = maxFuel;
		} else {
			fuel += amount;
		}
		return true;
	}
	
	@Override
	public boolean removeFuel(double amount) {
		if (fuel <= 0) {
			return false;
		}
		if (fuel < amount) {
			fuel = 0;
		} else {
			fuel -= amount;
		}
		return true;
	}
	
	@Override
	public void refill() {
		fuel = maxFuel;
	}

}
