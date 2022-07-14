package mqtt.value.packet;

import mqtt.Input;
import mqtt.value.MQTTByte;
import mqtt.value.MQTTShort;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTPubrecRes extends MQTTPacket {
	

	public final MQTTShort packetId;
	public final MQTTByte reason;
	public final MQTTProperties properties;
	
	public MQTTPubrecRes() {
		super(MQTTPacketType.PUBREC);
		this.packetId = new MQTTShort("packet identifier", (short)0);
		this.add(this.packetId);
		this.reason = new MQTTByte("reason", (byte)0);
		this.add(this.reason);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
	}

	
	@Override
	public void fromInput(Input input) {
		this.header.fromInput(input);
		this.packetId.fromInput(input);
		if(this.header.getRemainingLenght() == 2) {
			this.reason.set((byte)0);
		} else {
			this.reason.fromInput(input);
		}
	}
}
