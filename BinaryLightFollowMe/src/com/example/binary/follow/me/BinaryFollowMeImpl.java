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
	
	private static final int maxLightsToTurnOnPerRoom = 2;

	/** Field for binaryLights dependency */
	private BinaryLight[] binaryLights;

	/** Field for presenceSensors dependency */
	private PresenceSensor[] presenceSensors;

	/** Field for dimmerLights dependency */
	private DimmerLight[] dimmerLights;

	/**
	 * Bind Method for binaryLights dependency.
	 * This method is not mandatory and implemented for debug purpose only.
	 */
	public void bindBinaryLight(BinaryLight binaryLight, Map properties) {
		binaryLight.addListener(this);
		System.out.println("bind binary light " + binaryLight.getSerialNumber());
	}
	/**
	 * Unbind Method for binaryLights dependency. 
	 * This method is not mandatory and implemented for debug purpose only.
	 */
	public void unbindBinaryLight(BinaryLight binaryLight, Map properties) {
		binaryLight.removeListener(this);
		System.out.println("unbind binary light " + binaryLight.getSerialNumber());
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
	
	/** Bind Method for dimmerLights dependency */
	public void bindDimmerLight(DimmerLight dimmerLight, Map properties) {
		dimmerLight.addListener(this);
		System.out.println("bind dimmer light " + dimmerLight.getSerialNumber());
	}

	/** Unbind Method for dimmerLights dependency */
	public void unbindDimmerLight(DimmerLight dimmerLight, Map properties) {
		dimmerLight.addListener(this);
		System.out.println("unbind dimmer light " + dimmerLight.getSerialNumber());
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
		if (device instanceof BinaryLight) {
			BinaryLight changingLight = (BinaryLight) device;
			// check the change is related to location
			if (propertyName.equals(BinaryLight.LOCATION_PROPERTY_NAME)) {
				// get the location where the sensor is:
				String location = (String) changingLight.getPropertyValue(LOCATION_PROPERTY_NAME);
				// if the location is known :
				if (!location.equals(LOCATION_UNKNOWN)) {
					List<BinaryLight> sameLocationLights = getBinaryLightsFromLocation(location);
					List<PresenceSensor> sameLocationPresenceSensors = getPresenceSensorsFromLocation(location);					
					for (PresenceSensor p : sameLocationPresenceSensors) {
						// turn on/off the binary lights in the same location as the presence sensor
						turnOnBinaryLights(p, sameLocationLights);
					}
				}
			}
		} 
		else if (device instanceof PresenceSensor) {
			PresenceSensor changingSensor = (PresenceSensor) device;
			// check the change is related to presence sensing
			if (propertyName.equals(PresenceSensor.PRESENCE_SENSOR_SENSED_PRESENCE)) {
				// get the location where the sensor is:
				String detectorLocation = (String) changingSensor.getPropertyValue(LOCATION_PROPERTY_NAME);
				// if the location is known :
				if (!detectorLocation.equals(LOCATION_UNKNOWN)) {
					// get the related binary lights
					List<BinaryLight> sameLocationLights = getBinaryLightsFromLocation(detectorLocation);
					turnOnBinaryLights(changingSensor, sameLocationLights);
					
					// get the related dimmer lights
					List<DimmerLight> sameLocationDimmerLights = getDimmerLightsFromLocation(detectorLocation);
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



	}

	@Override
	public void devicePropertyRemoved(GenericDevice arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceRemoved(GenericDevice arg0) {
		// TODO Auto-generated method stub

	}

	private synchronized List<BinaryLight> getBinaryLightsFromLocation(String location) {
		List<BinaryLight> binaryLightsLocation = new ArrayList<BinaryLight>();
		for (BinaryLight binLight : binaryLights) {
			if (binLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				binaryLightsLocation.add(binLight);
			}
		}
		return binaryLightsLocation;
	}
	
	
	private synchronized List<DimmerLight> getDimmerLightsFromLocation(String location) {
		List<DimmerLight> dimmerLightsLocation = new ArrayList<DimmerLight>();
		for (DimmerLight binLight : dimmerLights) {
			if (binLight.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
				dimmerLightsLocation.add(binLight);
			}
		}
		return dimmerLightsLocation;
	}

	

	private synchronized List<PresenceSensor> getPresenceSensorsFromLocation(String location) {
		  List<PresenceSensor> presenceSensorsFromLocation = new ArrayList<PresenceSensor>();
		  
		  for (PresenceSensor presenceSensor : presenceSensors) {
		    if (presenceSensor.getPropertyValue(LOCATION_PROPERTY_NAME).equals(location)) {
		    	presenceSensorsFromLocation.add(presenceSensor);
		    }
		  }
		  return presenceSensorsFromLocation;
	}
	
	private synchronized void turnOnBinaryLights(PresenceSensor changingSensor, List<BinaryLight> binarylights) {
		for (BinaryLight binaryLight : binarylights) {
			binaryLight.turnOff();
		}
		
		int i = 0;
		for (BinaryLight binaryLight : binarylights) {
			if (i < maxLightsToTurnOnPerRoom && changingSensor.getSensedPresence()) {
				binaryLight.turnOn();
				i++;
			} 
			else {
				binaryLight.turnOff();
			}
		}
	}
	

}