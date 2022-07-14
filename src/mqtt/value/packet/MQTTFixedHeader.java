package mqtt.value.packet;

import java.nio.ByteBuffer;

import mqtt.Input;
import mqtt.value.MQTTCollection;
import mqtt.value.MQTTVarInt;
import mqtt.value.MQTTValue;

public class MQTTFixedHeader implements MQTTValue {

	public final MQTTPacketType type;
	public int flags;
	private MQTTVarInt remainingLength;
	
	public MQTTFixedHeader(MQTTPacketType type) {
		this.type = type;
		this.remainingLength = new MQTTVarInt(this.type.toString() + " Content Length", 0);
	}
	
	@Override
	public String getName() {
		return this.type.toString() + " Header";
	}

	public int getRemainingLenght() {
		return this.remainingLength.get();
	}
	
	public void update(MQTTCollection packet) {
		int size = 0;
		for(MQTTValue v : packet)
			if(v != this)
				size += v.getSize();
		this.remainingLength.set(size);
	}
	
	@Override
	public void encode(ByteBuffer buf) {
		buf.put((byte)((this.type.ordinal() << 4) | flags));
		this.remainingLength.encode(buf);
	}

	@Override
	public int getSize() {
		return this.remainingLength.getSize() + 1;
	}

	@Override
	public String toString() {
		return String.format("%s = %d", this.getName(), this.flags);
	}

	@Override
	public void fromInput(Input input) {
		int val = Byte.toUnsignedInt(input.read());
		assert(val >> 4 == this.type.ordinal());
		this.flags = val & 0x0F;
		this.remainingLength.fromInput(input);
		
	}
}
