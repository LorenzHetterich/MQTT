package mqtt.value;

import java.nio.ByteBuffer;

import mqtt.Input;

public class MQTTStringPair implements MQTTValue {

	private final String name;
	public final MQTTString key, value;	
	
	public MQTTStringPair(String name, String key, String value) {
		this.name = name;
		this.key = new MQTTString(String.format("%s_key", name), key);
		this.value = new MQTTString(String.format("%s_value", name), value);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void encode(ByteBuffer buf) {
		this.key.encode(buf);
		this.value.encode(buf);
	}

	@Override
	public int getSize() {
		return this.key.getSize() + this.value.getSize();
	}

	@Override
	public void fromInput(Input input) {
		this.key.fromInput(input);
		this.value.fromInput(input);
	}

	@Override
	public String toString() {
		return String.format("%s = (%s, %s)", name, key.get(), value.get());
	}
}
