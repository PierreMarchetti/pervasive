package com.example.binary.follow.me;

import fr.liglab.adele.icasa.device.DeviceListener;
import fr.liglab.adele.icasa.device.GenericDevice;
import fr.liglab.adele.icasa.device.light.BinaryLight;
import fr.liglab.adele.icasa.device.presence.PresenceSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fr.liglab.adele.icasa.device.light.DimmerLight;

public class BinaryFollowMeImpl implements DeviceListener {

	/**
	 * The name of the LOCATION property
	 */
	public static final String LOCATION_PROPERTY_NAME = "Location";

	/**
	 * The name of the location for unknown value
	 */
	public static final String LOCATION_UNKNOWN = "unknown";

	/** Field for binaryLights dependency */
	private BinaryLight[] binaryLights;

	/** Field for presenceSensors dependency */
	private PresenceSensor[] presenceSensors;

	private int maxLightsToTurnOnPerRoom = 1;

	/** Field for dimmerLights dependency */
	private DimmerLight[] dimmerLights;

	/**
	 * Bind Method for binaryLights dependency.
	 * This method is not mandatory and implemented for debug purpose only.
	 */
	public void bindBinaryLight(BinaryLight binaryLight, Map<Object, Object> properties) {
		System.out.println("--------------------bind binary light " + binaryLight.getSerialNumber());
	}

	/**
	 * Unbind Method for binaryLights dependency. 
	 * This method is not mandatory and implemented for debug purpose only.
	 */
	public void unbindBinaryLight(BinaryLight binaryLight, Map<Object, Object> properties) {
		System.out.println("--------------------unbind binary light " + binaryLight.getSerialNumber());
	}

	/** 
	 * Bind Method for PresenceSensors dependency.
	 * This method will be used to manage device listener.
	 */
	public void bindPresenceSensor(PresenceSensor presenceSensor, Map<Object, Object> properties) {
		System.out.println("--------------------bind presence sensor " + presenceSensor.getSerialNumber());
		presenceSensor.addListener(this);
	}

	/** 
	 * Unbind Method for PresenceSensors dependency.
	 * This method will be used to manage device listener.
	 */
	public void unbindPresenceSensor(PresenceSensor presenceSensor, Map properties) {
		System.out.println("--------------------Unbind presence sensor " + presenceSensor.getSerialNumber());
		presenceSensor.removeListener(this);
	}

	/** Component Lifecycle Method */
	public void stop() {
		System.out.println("--------------------Component is stopping...");
		for (PresenceSensor sensor : presenceSensors) {
			sensor.removeListener(this);
		}
	}

	/** Component Lifecycle Method */
	public void start() {
		System.out.println("--------------------Component is starting...");
	}

	@Override
	public void deviceAdded(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceEvent(GenericDevice arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void devicePropertyAdded(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void devicePropertyModified(GenericDevice device, String propertyName, Object oldValue, Object newValue) {
		PresenceSensor changingSensor = (PresenceSensor) device;
		// check the change is related to presence sensing
		if (propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
			// get the location where the sensor is:
			String detectorLocation = (String) changingSensor.getPropertyValue(LOCATION_PROPERTY_NAME);
			// if the location is known :
			if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
				// get the related binary lights
				List<BinaryLight> sameLocationLigths = getBinaryLightFromLocation(detectorLocation);
				for (BinaryLight binaryLight : sameLocationLigths) {
					// and switch them on/off depending on the sensed presence
					if (changingSensor.getSensedPresence()) {
						binaryLight.turnOn();
					} else {
						binaryLight.turnOff();
					}
				}
				
				// get the related dimmer lights
				List<DimmerLight> sameLocationDimmerLights = getDimmerLightFromLocation(detectorLocation);
				for (DimmerLight dimmerLight : sameLocationDimmerLights) {
					// and switch them on/off depending on the sensed presence
					if (changingSensor.getSensedPresence()) {
						dimmerLight.setPowerLevel(1.0);
					} else {
						dimmerLight.setPowerLevel(0.0);
					}
				}
			}
		}
	}

	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	private synchronized List<BinaryLight> getBinaryLightFromLocation(String location) {
		List<BinaryLight> binaryLightsLocation = new ArrayList<BinaryLight>();
		for (BinaryLight binLight : binaryLights) {
			if (binLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				binaryLightsLocation.add(binLight);
			}
		}
		return binaryLightsLocation;
	}
	
	
	private synchronized List<DimmerLight> getDimmerLightFromLocation(String location) {
		List<DimmerLight> dimmerLightsLocation = new ArrayList<DimmerLight>();
		for (DimmerLight binLight : dimmerLights) {
			if (binLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				dimmerLightsLocation.add(binLight);
			}
		}
		return dimmerLightsLocation;
	}

	/** Bind Method for dimmerLights dependency */
	public void BindDimmerLight(DimmerLight dimmerLight, Map properties) {
		// TODO: Add your implementation code here
	}

	/** Unbind Method for dimmerLights dependency */
	public void UnbindDimmerLight(DimmerLight dimmerLight, Map properties) {
		// TODO: Add your implementation code here
	}

}