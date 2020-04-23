package follow.me.multiple.impl;

import follow.me.multiple.service.FollowMeItf;

public class FollowMeProviderImpl implements FollowMeItf {

	/** Injected field for the service property maxNbLightsToTurnOn */
	private Integer maxNbLightsToTurnOn;

	@Override
	public int getMaxNbLightsToTurnOn() {
		return maxNbLightsToTurnOn;
	}

	@Override
	public void setMaxNbLightsToTurnOn(int maxNbLights) {
		maxNbLightsToTurnOn = maxNbLights;
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("Follow me multiple bundles provider is stopping");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("Follow me multiple bundles provider is starting");
	}

}
