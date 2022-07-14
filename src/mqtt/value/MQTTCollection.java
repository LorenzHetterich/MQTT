package mqtt.value;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import mqtt.Input;

public class MQTTCollection implements MQTTValue, Iterable<MQTTValue> {

	protected final String name;
	protected List<MQTTValue> content;
	
	public MQTTCollection(String name) {
		this.name = name;
		this.content = new ArrayList<>();
	}
	
	public MQTTValue get(String name) {
		for(MQTTValue value : this.content) {
			if(value.getName().equals(name))
				return value;
		}
		throw new IllegalArgumentException("Value not present!");
	}
	
	public void clear() {
		this.content.clear();
	}
	
	public void add(MQTTValue value) {
		this.content.add(value);
	}
	
	public void remove(MQTTValue value) {
		this.content.remove(value);
	}
	
	@Override
	public void encode(ByteBuffer buf) {
		for(MQTTValue value : this.content) {
			value.encode(buf);
		}
	}

	@Override
	public int getSize() {
		return content.stream().mapToInt(MQTTValue::getSize).sum();
	}

	@Override
	public Iterator<MQTTValue> iterator() {
		return content.iterator();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(", ", this.getName() + " = {", "}");
		for(MQTTValue val : this.content)
			joiner.add(val.toString());
		return joiner.toString();
		
	}

	@Override
	public void fromInput(Input input) {
		for(MQTTValue val : this.content) {
			val.fromInput(input);
		}
	}
}
