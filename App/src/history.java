import java.util.*;

public class BookingHistory {

    // Stores confirmed reservations in order
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addBooking(Reservation reservation) {
        history.add(reservation);
        System.out.println("Added to history: "
                + reservation.getGuestName() + " → "
                + reservation.getRoomType());
    }

    // Get all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }
}
import java.util.*;

public class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> bookings) {

        System.out.println("\n--- Booking History ---");

        for (Reservation r : bookings) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            ", Room: " + r.getRoomType()
            );
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> bookings) {

        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : bookings) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n--- Booking Summary ---");

        for (String type : countMap.keySet()) {
            System.out.println(type + " Rooms Booked: " + countMap.get(type));
        }
    }
}
private BookingHistory bookingHistory;

public RoomAllocationService(RoomInventory inventory,
                             BookingRequestQueue bookingQueue,
                             BookingHistory bookingHistory) {
    this.inventory = inventory;
    this.bookingQueue = bookingQueue;
    this.bookingHistory = bookingHistory;
    this.allocatedRooms = new HashMap<>();
    this.allAllocatedRoomIds = new HashSet<>();
}
public class UseCase8BookingHistory {

    public static void main(String[] args) {

        System.out.println("Booking History & Reporting System\n");

        // Step 1: Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);

        // Step 2: Queue
        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Abhil", "Single"));
        queue.addRequest(new Reservation("Subha", "Double"));
        queue.addRequest(new Reservation("Rahul", "Single"));

        // Step 3: History
        BookingHistory history = new BookingHistory();

        // Step 4: Allocation Service
        RoomAllocationService service =
                new RoomAllocationService(inventory, queue, history);

        service.processBookings();

        // Step 5: Reporting
        BookingReportService reportService = new BookingReportService();

        reportService.displayAllBookings(history.getAllBookings());
        reportService.generateSummary(history.getAllBookings());
    }
}
