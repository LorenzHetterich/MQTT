package mqtt.value.packet;

import java.nio.ByteBuffer;

import mqtt.Input;
import mqtt.value.MQTTCollection;

public abstract class MQTTPacket extends MQTTCollection {
	
	public final MQTTFixedHeader header;
	
	public static MQTTPacket parse(Input in) {
		int i = Byte.toUnsignedInt(in.peek());
		MQTTPacket p = MQTTPacketType.values()[i >> 4].parser.get();
		p.fromInput(in);
		return p;
	}
	
	public MQTTPacket(MQTTPacketType type) {
		super(type.toString());
		this.header = new MQTTFixedHeader(type);
		this.add(this.header);
	}
	
	public void update() {
		this.header.update(this);
	}
	
	@Override
	public void encode(ByteBuffer buf) {
		this.update();
		super.encode(buf);
	}
	
	@Override
	public int getSize() {
		this.update();
		return super.getSize();
	}

}
