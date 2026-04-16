public class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}
import java.util.*;

public class AddOnServiceManager {

    // Map → ReservationID → List of Services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation: " + reservationId);
    }

    // Get services for a reservation
    public List<AddOnService> getServices(String reservationId) {
        return serviceMap.getOrDefault(reservationId, new ArrayList<>());
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        for (AddOnService s : getServices(reservationId)) {
            total += s.getCost();
        }

        return total;
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = getServices(reservationId);

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " : ₹" + s.getCost());
        }

        System.out.println("Total Add-On Cost: ₹" + calculateTotalCost(reservationId));
    }
}
public class UseCase7AddOnServices {

    public static void main(String[] args) {

        System.out.println("Add-On Service System\n");

        // Example reservation IDs (from Use Case 6)
        String res1 = "SI-101";
        String res2 = "SU-201";

        // Create manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService spa = new AddOnService("Spa", 1500);

        // Add services to reservations
        manager.addService(res1, breakfast);
        manager.addService(res1, wifi);

        manager.addService(res2, spa);

        // Display services
        manager.displayServices(res1);
        manager.displayServices(res2);
    }
}
