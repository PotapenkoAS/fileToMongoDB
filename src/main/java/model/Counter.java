package model;

import java.util.UUID;

public class Counter {
    String id;
    String counterName;
    String counterValue;

    public Counter(String counterName, String counterValue) {
        this.id = UUID.randomUUID().toString();
        this.counterName = counterName;
        this.counterValue = counterValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterValue() {
        return counterValue;
    }

    public void setCounterValue(String counterValue) {
        this.counterValue = counterValue;
    }
}
