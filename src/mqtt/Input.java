package mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Input {

	private final InputStream in;
	
	private byte[] buf;
	private int offset;
	
	public Input(InputStream in) {
		this.in = in;
		this.buf = new byte[0];
	}
	
	public byte read() {
		return read(1)[0];
	}
	
	public ByteBuffer buf(int amount) {
		return ByteBuffer.wrap(read(amount)).order(ByteOrder.BIG_ENDIAN);
	}
	
	public byte peek() {
		return peek(1)[0];
	}
	
	public byte[] peek(int amount) {
		if(buf.length - offset >= amount) {
			byte[] result = new byte[amount];
			System.arraycopy(buf, offset, result, 0, result.length);
		}
		
		this.buf = read(amount);
		this.offset = 0;
		
		byte[] result = new byte[amount];
		System.arraycopy(buf, 0, result, 0, amount);
		return result;
	}
	
	public byte[] read(int amount) {
		
		if(buf.length - offset >= amount) {
			byte[] res = new byte[amount];
			System.arraycopy(buf, offset, res, 0, amount);
			offset += amount;
			return res;
		}
		
		
		try {
			if(buf.length == offset) {
				byte[] res = in.readNBytes(amount);
				if(res.length != amount)
					throw new IOException("Too few bytes read");
				return res;
			}
			
			// mixed
			byte[] result = new byte[amount];
			System.arraycopy(buf, offset, result, 0, buf.length - offset);
			if(in.readNBytes(result, buf.length - offset, amount - (buf.length - offset)) != amount - (buf.length - offset))
				throw new IOException("Too few bytes read");
			this.offset = buf.length;
			return result;
		} catch(IOException e) {
			throw new RuntimeException("IOException", e);
		}
	}
	
}
