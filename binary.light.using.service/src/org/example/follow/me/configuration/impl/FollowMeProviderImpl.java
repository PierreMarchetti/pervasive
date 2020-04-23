package org.example.follow.me.configuration.impl;

import org.example.follow.me.configuration.FollowMeConfiguration;

public class FollowMeProviderImpl implements FollowMeConfiguration {

	/** Injected field for the service property maxLightsToTurnOn */
	private Integer maxLightsToTurnOn;

	@Override
	public int getMaximumNumberOfLightsToTurnOn() {
		// TODO Auto-generated method stub
		return maxLightsToTurnOn;
	}

	@Override
	public void setMaximumNumberOfLightsToTurnOn(int maximumNumberOfLightsToTurnOn) {
		// TODO Auto-generated method stub
		maxLightsToTurnOn = maximumNumberOfLightsToTurnOn;
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("FollowMeProvider service is stopping");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("FollowMeProvider service is starting");
	}

}
