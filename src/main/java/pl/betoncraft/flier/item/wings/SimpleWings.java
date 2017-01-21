/** This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://www.wtfpl.net/ for more details.
 */
package pl.betoncraft.flier.item.wings;

import org.bukkit.configuration.ConfigurationSection;

import pl.betoncraft.flier.api.InGamePlayer;
import pl.betoncraft.flier.core.Utils.ImmutableVector;
import pl.betoncraft.flier.core.ValueLoader;
import pl.betoncraft.flier.exception.LoadingException;

/**
 * Simple wings with scalable lifting force and air resistance.
 *
 * @author Jakub Sapalski
 */
public class SimpleWings extends DefaultWings {
	
	private final double aerodynamics;
	private final double liftingForce;
	private final double maxLift;
	
	public SimpleWings(ConfigurationSection section) throws LoadingException {
		super(section);
		aerodynamics = ValueLoader.loadDouble(section, "aerodynamics");
		liftingForce = ValueLoader.loadDouble(section, "liftingforce");
		maxLift = ValueLoader.loadDouble(section, "maxLift");
	}
	
	@Override
	public ImmutableVector applyFlightModifications(InGamePlayer data) {
		ImmutableVector velocity = ImmutableVector.fromVector(data.getPlayer().getVelocity());
		double horizontalSpeed = Math.sqrt((velocity.getX() * velocity.getX()) + (velocity.getZ() * velocity.getZ()));
		double length = (liftingForce * horizontalSpeed) - data.getWeight();
		length = length >= maxLift ? maxLift : length;
		velocity = velocity.add(new ImmutableVector(0, length, 0));
		ImmutableVector airResistance = velocity.multiply(aerodynamics);
		return velocity.add(airResistance);
	}
	
	@Override
	public SimpleWings replicate() {
		try {
			return new SimpleWings(base);
		} catch (LoadingException e) {
			return null; // dead code
		}
	}

}
