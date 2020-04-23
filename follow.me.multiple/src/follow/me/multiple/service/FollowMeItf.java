package follow.me.multiple.service;

public interface FollowMeItf {
	public static final String PROP_MAX_NB_LIGHTS_ON = "maxNbLightsToTurnOn";
	
	public int getMaxNbLightsToTurnOn();
	
	public void setMaxNbLightsToTurnOn(int maxNbLights);
}
