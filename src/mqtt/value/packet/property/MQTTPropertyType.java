package mqtt.value.packet.property;

import java.util.function.Supplier;

import mqtt.value.MQTTByte;
import mqtt.value.MQTTInt;
import mqtt.value.MQTTShort;
import mqtt.value.MQTTString;
import mqtt.value.MQTTStringPair;
import mqtt.value.MQTTValue;
import mqtt.value.MQTTVarInt;

public enum MQTTPropertyType {

	PAYLOAD_FORMAT_INDICATOR(0x01, () -> new MQTTByte("payload format indicator", (byte)0)),
	MESSAGE_EXPIRY_INTERVAL(0x02, () -> new MQTTInt("message expiry interval", 0)),
	CONTENT_TYPE(0x03, () -> new MQTTString("content type", "")),
	RESPONSE_TOPIC(0x08, () -> new MQTTString("response topic", "")),
	CORRELATION_DATA(0x09, null),
	SUBSCRIPTION_IDENTIFIER(0x0B, () -> new MQTTVarInt("subscription identifier", 0)),
	SESSION_EXPIRY_INTERVAL(0x11, () -> new MQTTInt("session expiry interval", 0)),
	ASSIGNED_CLIENT_IDENTIFIER(0x12, () -> new MQTTString("assigned client identifier", "")),
	SERVER_KEEP_ALIVE(0x13, () -> new MQTTShort("server keep alive", (short)0)),
	AUTHENTICATION_METHOD(0x15, () -> new MQTTString("authentication method", "")),
	AUTHENTICATION_DATA(0x16, null),
	REQUEST_PROBLEM_INFORMATION(0x17, () -> new MQTTByte("request problem information", (byte)0)),
	WILL_DELAY_INTERVAL(0x18, () -> new MQTTInt("will delay interval", 0)),
	REQUEST_RESPONSE_INFORMATION(0x19, () -> new MQTTByte("request response information", (byte)0)),
	RESPONSE_INFORMATION(0x1A, () -> new MQTTString("response information", "")),
	SERVER_REFERENCE(0x1C, () -> new MQTTString("server reference", "")),
	REASON_STRING(0x1F, () -> new MQTTString("reason string", "")),
	RECEIVE_MAXIMUM(0x21, () -> new MQTTShort("receive maximum", (short)0)),
	TOPIC_ALIAS_MAXIMUM(0x22, () -> new MQTTShort("topic alias maximum", (short)0)),
	TOPIC_ALIAS(0x23, () -> new MQTTShort("topic alias", (short)0)),
	MAXIMUM_QOS(0x24, () -> new MQTTByte("maximum qos", (byte)0)),
	RETAIN_AVAILABLE(0x25, () -> new MQTTByte("retain available", (byte)0)),
	USER_PROPERTY(0x26, () -> new MQTTStringPair("user property", "", "")),
	MAXIMUM_PACKET_SIZE(0x27, () -> new MQTTInt("maximum packet size", 0)),
	WILDCARD_SUBSCRIPTION_AVAILABLE(0x28, () -> new MQTTByte("wildcard subscription available", (byte)0)),
	SUBSCRIPTION_IDENTIFIER_AVAILABLE(0x29, () -> new MQTTByte("subscription identifier available", (byte)0)),
	SHARED_SUBSCRIPTION_AVAILABLE(0x2A, () -> new MQTTByte("shared subscription available", (byte)0));
	
	public final byte value;
	public final Supplier<? extends MQTTValue> parser;
	
	public static MQTTPropertyType fromByte(byte value) {
		for(MQTTPropertyType type : MQTTPropertyType.values()) {
			if(type.value == value)
				return type;
		}
		throw new IllegalArgumentException("No MQTTPropertyType for value " + value);
	}
	
	MQTTPropertyType(int value, Supplier<? extends MQTTValue> parser){
		this.value = (byte)value;
		this.parser = parser;
	}
	
}
