import java.util.*;

public class RoomAllocationService {

    private RoomInventory inventory;
    private BookingRequestQueue bookingQueue;

    // Map → RoomType → Set of Allocated Room IDs
    private Map<String, Set<String>> allocatedRooms;

    // Global set to ensure uniqueness
    private Set<String> allAllocatedRoomIds;

    public RoomAllocationService(RoomInventory inventory, BookingRequestQueue bookingQueue) {
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
        this.allocatedRooms = new HashMap<>();
        this.allAllocatedRoomIds = new HashSet<>();
    }

    // Process all booking requests
    public void processBookings() {

        while (bookingQueue.hasPendingRequests()) {

            Reservation request = bookingQueue.getNextRequest();
            String roomType = request.getRoomType();
            String guestName = request.getGuestName();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                // Generate unique room ID
                String roomId = generateRoomId(roomType);

                // Ensure uniqueness (extra safety)
                while (allAllocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                // Store globally
                allAllocatedRoomIds.add(roomId);

                // Store per room type
                allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Update inventory
                inventory.bookRoom(roomType);

                // Confirm booking
                System.out.println("Booking Confirmed!");
                System.out.println("Guest: " + guestName);
                System.out.println("Room Type: " + roomType);
                System.out.println("Room ID: " + roomId);
                System.out.println("-------------------------");

            } else {
                System.out.println("Booking Failed for " + guestName +
                        " (" + roomType + " not available)");
                System.out.println("-------------------------");
            }
        }
    }

    // Generate Room ID
    private String generateRoomId(String roomType) {
        int random = (int) (Math.random() * 1000);
        return roomType.substring(0, 2).toUpperCase() + "-" + random;
    }
}
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("Room Allocation System\n");

        // Step 1: Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 1);
        inventory.addRoomType("Suite", 1);

        // Step 2: Create booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Abhil", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Rahul", "Single")); // should fail
        queue.addRequest(new Reservation("Priya", "Suite"));

        // Step 3: Process bookings
        RoomAllocationService service = new RoomAllocationService(inventory, queue);
        service.processBookings();
    }
}
