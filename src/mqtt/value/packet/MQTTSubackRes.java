package mqtt.value.packet;

import mqtt.Input;
import mqtt.value.MQTTByte;
import mqtt.value.MQTTCollection;
import mqtt.value.MQTTShort;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTSubackRes extends MQTTPacket {
	
	public final MQTTShort identifier;
	public final MQTTProperties properties;
	public final MQTTCollection reasons;
	
	public MQTTSubackRes() {
		super(MQTTPacketType.SUBACK);
		this.header.flags = 0b0010;
		this.identifier = new MQTTShort("packet identifier", (short)0);
		this.add(this.identifier);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
		this.reasons = new MQTTCollection("reason codes");
		this.add(this.reasons);
	}

	@Override
	public void fromInput(Input input) {
		this.header.fromInput(input);
		this.identifier.fromInput(input);
		this.properties.fromInput(input);
		int remaining = this.header.getRemainingLenght() - (this.getSize() - this.header.getSize());
		int i = 0;
		while(remaining > 0) {
			MQTTByte reason = new MQTTByte("reason_" + i++, (byte)0);
			reason.fromInput(input);
			remaining -= reason.getSize();
		}
	}

}
