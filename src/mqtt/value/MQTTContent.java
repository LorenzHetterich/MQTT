package mqtt.value;

import mqtt.Input;

public interface MQTTContent extends MQTTValue {

	public void setSize(int size);
	
	public default void fromInput(Input input, int size) {
		setSize(size);
		fromInput(input);
	}
	
	
}
