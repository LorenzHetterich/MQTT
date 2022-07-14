package mqtt.value.packet;

import mqtt.Input;
import mqtt.value.MQTTBytes;
import mqtt.value.MQTTOptional;
import mqtt.value.MQTTShort;
import mqtt.value.MQTTString;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTPublishReq extends MQTTPacket {
	
	public final MQTTString topic;
	public final MQTTOptional<MQTTShort> identifier;
	public final MQTTProperties properties;
	public final MQTTBytes content;
	
	public MQTTPublishReq() {
		super(MQTTPacketType.PUBLISH);
		this.topic = new MQTTString("topic name", "");
		this.add(this.topic);
		this.identifier = new MQTTOptional<>(new MQTTShort("packet identifier", (short)0), false);
		this.add(this.identifier);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
		this.content = new MQTTBytes("content", new byte[0]);
		this.add(this.content);
	}
	
	@Override
	public void fromInput(Input input) {
		super.fromInput(input);
		int remaining = this.header.getRemainingLenght() - (this.getSize() - this.header.getSize());
		this.content.set(new byte[remaining]);
		this.content.fromInput(input);
	}
}
