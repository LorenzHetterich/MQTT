package mqtt.value.packet;

import mqtt.Input;
import mqtt.value.MQTTByte;
import mqtt.value.MQTTCollection;
import mqtt.value.MQTTShort;
import mqtt.value.MQTTString;
import mqtt.value.packet.property.MQTTProperties;

public class MQTTSubscribeReq extends MQTTPacket {
	
	public final MQTTShort identifier;
	public final MQTTProperties properties;
	public final MQTTCollection topicFilters;
	
	public MQTTSubscribeReq() {
		super(MQTTPacketType.SUBSCRIBE);
		this.header.flags = 0b0010;
		this.identifier = new MQTTShort("packet identifier", (short)0);
		this.add(this.identifier);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
		this.topicFilters = new MQTTCollection("topic filters");
		this.add(this.topicFilters);
	}
	
	@Override
	public void fromInput(Input input) {
		this.header.fromInput(input);
		this.identifier.fromInput(input);
		this.properties.fromInput(input);
		int remaining = this.header.getRemainingLenght() - (this.getSize() - this.header.getSize());
		int i = 0;
		while(remaining > 0) {
			MQTTString topic = new MQTTString("topic_" + i, "");
			topic.fromInput(input);
			MQTTByte options = new MQTTByte("topic_" + i+++ "_options", (byte)0);
			options.fromInput(input);
			this.topicFilters.add(topic);
			this.topicFilters.add(options);
			remaining -= topic.getSize() + options.getSize();
		}
	}


}
