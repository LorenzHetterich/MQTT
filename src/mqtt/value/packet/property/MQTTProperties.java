package mqtt.value.packet.property;

import java.nio.ByteBuffer;

import mqtt.Input;
import mqtt.value.MQTTCollection;
import mqtt.value.MQTTValue;
import mqtt.value.MQTTVarInt;

public class MQTTProperties extends MQTTCollection {
	
	private MQTTVarInt size;
	
	public MQTTProperties(String name) {
		super(name);
		this.size = new MQTTVarInt("size", 0);
	}
	
	@Override
	public void encode(ByteBuffer buf) {
		size.set(this.content.stream().mapToInt(MQTTValue::getSize).sum());
		size.encode(buf);
		super.encode(buf);
	}
	
	@Override
	public int getSize() {
		size.set(this.content.stream().mapToInt(MQTTValue::getSize).sum());
		return super.getSize() + this.size.getSize();
	}
	
	@Override
	public void fromInput(Input in) {
		this.size.fromInput(in);
		int read = 0;
		while(read < size.get()) {
			MQTTProperty property = MQTTProperty.parse(in);
			this.add(property);
			read += property.getSize();
		}
		
		assert(read == size.get());
	}
	

}
