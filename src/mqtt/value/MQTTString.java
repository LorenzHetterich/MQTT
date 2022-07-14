package mqtt.value;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import mqtt.Input;

public class MQTTString implements RWMQTTValue<String> {
	
	private final String name;
	private String value;
	private MQTTShort size;
	
	public MQTTString(String name, String value) {
		this.name = name;
		this.size = new MQTTShort(String.format("%s_size", this.name), (short)0);
		this.set(value);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void encode(ByteBuffer buf) {
		this.size.encode(buf);
		buf.put(this.value.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public int getSize() {
		return size.getSize() + size.get();
	}

	@Override
	public void set(String value) {
		this.value = value;
		this.size.set((short)this.value.getBytes(StandardCharsets.UTF_8).length);
	}

	@Override
	public String get() {
		return this.value;
	}

	@Override
	public String toString() {
		return String.format("%s = %s", this.name, this.value);
	}

	@Override
	public void fromInput(Input input) {
		this.size.fromInput(input);
		this.value = new String(input.read(this.size.get()), StandardCharsets.UTF_8);
	}
}
