package mqtt.value.packet;

import java.util.function.Supplier;

public enum MQTTPacketType {

	RESERVED(null),
	CONNECT(MQTTConnectReq::new),
	CONNACK(MQTTConnectRes::new),
	PUBLISH(MQTTPublishReq::new),
	PUBACK(MQTTPubackRes::new),
	PUBREC(MQTTPubrecRes::new),
	PUBREL(null),
	PUBCOMP(null),
	SUBSCRIBE(MQTTSubscribeReq::new),
	SUBACK(MQTTSubackRes::new),
	UNSUBSCRIBE(null),
	UNSUBACK(null),
	PINGREQ(null),
	PINGRESP(null),
	DISCONNECT(MQTTDisconnect::new),
	AUTH(null);
	
	public final Supplier<? extends MQTTPacket> parser;
	
	MQTTPacketType(Supplier<? extends MQTTPacket> parser){
		this.parser = parser;
	}
	
}
