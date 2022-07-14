package mqtt.value.packet;

import mqtt.Input;
import mqtt.MQTT;
import mqtt.value.MQTTByte;
import mqtt.value.MQTTOptional;
import mqtt.value.MQTTShort;
import mqtt.value.MQTTString;
import mqtt.value.packet.property.MQTTProperties;
public class MQTTConnectReq extends MQTTPacket {
	
	public final MQTTString protocolName;
	public final MQTTByte version;
	public final MQTTByte connectionFlags;
	public final MQTTShort keepAlive;
	public final MQTTProperties properties;
	public final MQTTString clientId;
	
	public static final int FLAG_CLEAN_START = 1;
	public static final int FLAG_WILL = 2;
	public static final int FLAG_WILL_QOS_1 = 3;
	public static final int FLAG_WILL_QOS_0 = 4;
	public static final int FLAG_WILL_RETAIN = 5;
	public static final int FLAG_PASSWORD = 6;
	public static final int FLAG_USER_NAME = 7;
	
	public final MQTTOptional<MQTTString> username;
	public final MQTTOptional<MQTTString> password;
	
	public MQTTConnectReq() {
		super(MQTTPacketType.CONNECT);
		this.protocolName = new MQTTString("protocol name", "MQTT");
		this.add(this.protocolName);
		this.version = new MQTTByte("version", MQTT.VERSION);
		this.add(this.version);
		this.connectionFlags = new MQTTByte("connection flags", (byte)0);
		this.add(this.connectionFlags);
		this.keepAlive = new MQTTShort("keep alive", (short)120);
		this.add(this.keepAlive);
		this.properties = new MQTTProperties("properties");
		this.add(this.properties);
		this.clientId = new MQTTString("client identifier", "");
		this.add(this.clientId);
		// TODO: Will properties
		// TODO: will topic
		// TODO: will payload
		this.username = new MQTTOptional<>(new MQTTString("username", ""), false);
		this.add(this.username);
		this.password = new MQTTOptional<>(new MQTTString("password", ""), false);
		this.add(this.password);
	}
	
	private void updateOptionals() {
		this.username.active = getFlag(FLAG_USER_NAME);
		this.password.active = getFlag(FLAG_PASSWORD);
	}
	
	@Override
	public void update() {
		super.update();
		this.updateOptionals();
	}
	
	@Override
	public void fromInput(Input in) {
		super.fromInput(in);
		this.updateOptionals();
		// TODO: Will properties
		// TODO: will topic
		// TODO: will payload
		this.username.fromInput(in);
		this.password.fromInput(in);
	}
	
	public boolean getFlag(int flag) {
		return (this.connectionFlags.get() & (1 << flag)) != 0;
	}
	
	public void setFlag(int flag, boolean value) {
		if(value) {
			this.connectionFlags.set((byte) (this.connectionFlags.get() | (1 << flag)));
		} else {
			this.connectionFlags.set((byte) (this.connectionFlags.get() & ~(1 << flag)));
		}
	}

}
