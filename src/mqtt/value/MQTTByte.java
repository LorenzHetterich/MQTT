package mqtt.value;

import java.nio.ByteBuffer;

import mqtt.Input;

public class MQTTByte implements RWMQTTValue<Byte>{

	private byte value;
	private final String name;
	
	public MQTTByte(String name, byte value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void encode(ByteBuffer buf) {
		buf.put(this.value);
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public void set(Byte value) {
		this.value = value;
	}

	@Override
	public Byte get() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.format("%s = %d", this.name, this.value);
	}

	@Override
	public void fromInput(Input input) {
		this.set(input.read());
	}
}
