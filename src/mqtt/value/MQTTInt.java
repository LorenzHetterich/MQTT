package mqtt.value;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mqtt.Input;

public class MQTTInt implements RWMQTTValue<Integer>{

	private int value;
	private final String name;
	
	public static MQTTInt parse(String name, Input in) {
		return new MQTTInt(name, in.buf(4).getInt());
	}
	
	public MQTTInt(String name, int value) {
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
		buf.putInt(this.value);
	}

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public void set(Integer value) {
		this.value = value;
	}

	@Override
	public Integer get() {
		return this.value;
	}
	
	@Override
	public String toString() {
		return String.format("%s = %d", this.name, this.value);
	}

	@Override
	public void fromInput(Input input) {
		set(input.buf(4).getInt());
	}
}
