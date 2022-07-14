package mqtt.value.packet;

import mqtt.value.MQTTByte;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTConnectRes extends MQTTPacket {

	public final MQTTByte flags;
	public final MQTTByte sessionPresent;
	public final MQTTProperties properties;
	
	public MQTTConnectRes() {
		super(MQTTPacketType.CONNACK);
		this.flags = new MQTTByte("connect acknowledge flags", (byte)0);
		this.add(this.flags);
		this.sessionPresent = new MQTTByte("session present", (byte)0);
		this.add(this.sessionPresent);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
	}

}
