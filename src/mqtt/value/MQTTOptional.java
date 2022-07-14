package mqtt.value;

import java.nio.ByteBuffer;

import mqtt.Input;

public class MQTTOptional<T extends MQTTValue> implements MQTTValue {

	public final T value;
	public boolean active;
	
	public MQTTOptional(T value, boolean active) {
		this.value = value;
		this.active = active;
	}

	@Override
	public String getName() {
		return value.getName();
	}

	@Override
	public void encode(ByteBuffer buf) {
		if(active)
			value.encode(buf);
	}

	@Override
	public int getSize() {
		return active ? value.getSize() : 0;
	}

	@Override
	public void fromInput(Input input) {
		if(active)
			this.value.fromInput(input);
	}
	
	@Override
	public String toString() {
		if(active)
			return this.value.toString();
		else
			return String.format("%s = <empty>", this.value.getName());
	}
	
}
