package mqtt.value.packet.property;

import java.nio.ByteBuffer;

import mqtt.Input;
import mqtt.value.MQTTValue;

public class MQTTProperty implements MQTTValue {

	public final MQTTPropertyType type;
	private MQTTValue value;
	
	public static MQTTProperty parse(Input in) {
		MQTTPropertyType type = MQTTPropertyType.fromByte(in.peek());
		MQTTProperty prop = new MQTTProperty(type, type.parser.get());
		prop.fromInput(in);
		return prop;
	}
	
	public MQTTProperty(MQTTPropertyType type, MQTTValue value) {
		this.type = type;
		this.value = value;
	}
	
	@Override
	public String getName() {
		return value.getName();
	}

	@Override
	public void encode(ByteBuffer buf) {
		buf.put(type.value);
		value.encode(buf);
	}

	@Override
	public int getSize() {
		return 1 + value.getSize();
	}

	@Override
	public void fromInput(Input input) {
		byte t = input.read();
		assert(t == type.value);
		value.fromInput(input);
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
