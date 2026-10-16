package smartfactory.model;

public class Machine {
    private String id;
    private String type;
    private String status;
    private int cost;
    private int value;

    public Machine(String id, String type, String status, int cost, int value) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.cost = cost;
        this.value = value;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public int getCost() { return cost; }
    public int getValue() { return value; }
}
