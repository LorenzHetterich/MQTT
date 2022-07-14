package mqtt.value;

public interface RWMQTTValue<T> extends MQTTValue {

	void set(T value);
	
	T get();
	
}
