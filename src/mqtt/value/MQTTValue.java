package mqtt.value;

import java.nio.ByteBuffer;

import mqtt.Input;

public interface MQTTValue {
	
	String getName();
	
	void encode(ByteBuffer buf);
	
	int getSize();

	void fromInput(Input input);
}
