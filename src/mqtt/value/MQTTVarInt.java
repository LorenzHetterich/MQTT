package mqtt.value;

import java.nio.ByteBuffer;

import mqtt.Input;

public class MQTTVarInt implements RWMQTTValue<Integer>  {

	private final String name;
	private int value;
	
	public MQTTVarInt(String name, int value) {
		this.name = name;
		this.value = value;
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
	public void encode(ByteBuffer buf) {
		int v = this.value;
		
		if(this.value == 0) {
			buf.put((byte)0);
			return;
		}
		while(v > 0) {
			byte val = (byte)(v % 128);
			v = v / 128;
			if(v > 0)
				val |= 128;
			buf.put(val);
		}
	}
	
	@Override
	public int getSize() {
		if(value < 0)
			throw new IllegalStateException("Value is negative!");
		if(value < 128)
			return 1;
		if(value < 16384)
			return 2;
		if(value < 2097152)
			return 3;
		if(value < 268435456)
			return 4;
		throw new IllegalStateException("Value is too large!");
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return String.format("%s = %d", this.name, this.value);
	}

	@Override
	public void fromInput(Input input) {
		int multiplier = 1;
		int value = 0;
		int cur;
		do {
			cur = Byte.toUnsignedInt(input.read());
			value += (cur & 127) * multiplier;
			multiplier *= 128;
		} while(cur > 127);
		this.set(value);
	}
}
