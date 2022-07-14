package mqtt.value;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mqtt.Input;

public class MQTTShort implements RWMQTTValue<Short>{

	private short value;
	private final String name;
	
	public static MQTTShort parse(String name, Input in) {
		return new MQTTShort(name, ByteBuffer.wrap(in.read(2)).order(ByteOrder.BIG_ENDIAN).getShort());
	}
	
	public MQTTShort(String name, short value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void encode(ByteBuffer buf) {
		buf.order(ByteOrder.BIG_ENDIAN);
		buf.putShort(this.value);
	}

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public void set(Short value) {
		this.value = value;
	}

	@Override
	public Short get() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return String.format("%s = %d", this.name, this.value);
	}

	@Override
	public void fromInput(Input input) {
		set(input.buf(2).getShort());
	}
}
