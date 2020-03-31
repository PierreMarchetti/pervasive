package org.example.follow.me.configuration.client;

import org.example.follow.me.configuration.FollowMeConfiguration;
import java.util.Map;

public class FollowMeClientImpl implements FollowMeConfiguration {

	/** Field for followMeServices dependency */
	private FollowMeConfiguration[] followMeServices;
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

	/** Bind Method for followMeServices dependency */
	public void bindFollowMe(FollowMeConfiguration followMeConfiguration, Map properties) {
		System.out.println("binding follow me configuration" + " ---> " + this.getMaximumNumberOfLightsToTurnOn());
	}

	/** Unbind Method for followMeServices dependency */
	public void unbindFollowMe(FollowMeConfiguration followMeConfiguration, Map properties) {
		System.out.println("unbinding follow me configuration");
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("FollowMeClient using services is stopping");
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("FollowMeClient using services is starting");
	}

}
