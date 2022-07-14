package mqtt.value;

import java.nio.ByteBuffer;
import java.util.Arrays;

import mqtt.Input;

public class MQTTBytes implements RWMQTTValue<byte[]>, MQTTContent {

	private byte[] value;
	private final String name;
	
	public MQTTBytes(String name, byte[] value) {
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
		return value.length;
	}

	@Override
	public void set(byte[] value) {
		this.value = value;
	}

	@Override
	public byte[] get() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", this.name, Arrays.toString(this.value));
	}

	@Override
	public void fromInput(Input input) {
		this.value = input.read(this.value.length);
	}

	@Override
	public void setSize(int size) {
		this.value = new byte[size];
	}
}
