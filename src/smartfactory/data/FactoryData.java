package smartfactory.data;

import smartfactory.model.Document;
import smartfactory.model.Machine;
import smartfactory.model.Report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FactoryData {
    private static final List<Machine> machines = new ArrayList<>();
    private static final List<Report> reports = new ArrayList<>();
    private static final List<Document> documents = new ArrayList<>();

    static {
        loadDefaultData();
    }

    private static void loadDefaultData() {
        machines.add(new Machine("MTR-101", "CNC Lathe", "Running", 10, 60));
        machines.add(new Machine("MTR-102", "Robotic Arm", "Running", 20, 100));
        machines.add(new Machine("MTR-103", "Press Machine", "Maintenance", 30, 120));
        machines.add(new Machine("MTR-104", "Assembly Robot", "Running", 15, 80));
        machines.add(new Machine("MTR-105", "Drilling Unit", "Idle", 12, 70));
        machines.add(new Machine("MTR-204", "CNC Motor", "Running", 18, 110));
        machines.add(new Machine("MTR-205", "Cutting Unit", "Running", 14, 75));
        machines.add(new Machine("MTR-304", "Welding Robot", "Running", 16, 90));

        reports.add(new Report("MR-1001", "MTR-204", "High", "Machine MTR-204 reported overheating during gear production. Motor vibration detected."));
        reports.add(new Report("MR-1002", "MTR-101", "Medium", "CNC spindle vibration observed during the morning production cycle."));
        reports.add(new Report("MR-1003", "MTR-102", "Critical", "Robotic arm motor failure and overheating detected. Emergency maintenance required."));
        reports.add(new Report("MR-1004", "MTR-304", "High", "Welding unit reported leakage near the cooling line and abnormal vibration."));
        reports.add(new Report("MR-1005", "MTR-204", "High", "MTR-204 motor overheating detected again after preventive maintenance."));

        documents.add(new Document("DOC-001", "Maintenance Report Collection",
            "MTR-204 motor overheating detected during gear production. Motor vibration was observed. Preventive maintenance was performed and motor overheating was checked again."));
        documents.add(new Document("DOC-002", "Quality Control Report",
            "Quality inspection detected dimensional variation in gear assembly. Motor vibration was not observed. Production line calibration was requested."));
        documents.add(new Document("DOC-003", "Production Observation",
            "Production observation recorded MTR-101 spindle vibration and MTR-304 cooling leakage. Maintenance team inspected the affected machines."));
    }

    public static List<Machine> getMachines() {
        return Collections.unmodifiableList(machines);
    }

    public static List<Report> getReports() {
        return Collections.unmodifiableList(reports);
    }

    public static List<Document> getDocuments() {
        return Collections.unmodifiableList(documents);
    }

    public static String buildReportCorpus() {
        StringBuilder sb = new StringBuilder();
        for (Report r : reports) {
            sb.append(r.getId()).append(" ")
              .append(r.getMachine()).append(" ")
              .append(r.getDescription()).append(" ");
        }
        return sb.toString();
    }
}
