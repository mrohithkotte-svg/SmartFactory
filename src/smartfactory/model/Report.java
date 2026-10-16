package smartfactory.model;

public class Report {
    private String id;
    private String machine;
    private String severity;
    private String description;

    public Report(String id, String machine, String severity, String description) {
        this.id = id;
        this.machine = machine;
        this.severity = severity;
        this.description = description;
    }

    public String getId() { return id; }
    public String getMachine() { return machine; }
    public String getSeverity() { return severity; }
    public String getDescription() { return description; }
}
