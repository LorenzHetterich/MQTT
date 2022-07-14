package mqtt.value.packet;

import mqtt.Input;
import mqtt.value.MQTTByte;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTDisconnect extends MQTTPacket {

	public final MQTTByte reason;
	public final MQTTProperties properties;
	
	public MQTTDisconnect() {
		super(MQTTPacketType.DISCONNECT);
		this.reason = new MQTTByte("reason", (byte)0);
		this.add(this.reason);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
	}
	
	@Override
	public void fromInput(Input input) {
		this.header.fromInput(input);
		if(this.header.getRemainingLenght() == 0) {
			this.reason.set((byte)0);
		} else {
			this.reason.fromInput(input);
		}
	}
	
}
