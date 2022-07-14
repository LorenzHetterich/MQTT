package mqtt;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import mqtt.value.packet.MQTTPacket;

public class MQTTConnection {

	private Socket socket;
	private Input input;
	
	public boolean debug;
	
	public MQTTConnection(Socket socket) {
		this.socket = socket;
		try {
			this.input = new Input(socket.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("IOException", e);
		}
	}
	
	public MQTTConnection(String host, int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}
	
	public void send(MQTTPacket packet) {
		if(debug) {
			System.out.printf(" >> %s\n", packet);
		}
		byte[] data = new byte[packet.getSize()];
		packet.encode(ByteBuffer.wrap(data));
		try {
			socket.getOutputStream().write(data);
			socket.getOutputStream().flush();
		} catch (IOException e) {
			throw new RuntimeException("IOException", e);
		}
	}
	
	public MQTTPacket receive() {
		MQTTPacket packet = MQTTPacket.parse(input);
		if(debug) {
			System.out.printf(" << %s\n", packet);
		}
		return packet;
	}
	
	
}
