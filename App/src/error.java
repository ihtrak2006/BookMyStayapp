public class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}
import java.util.Map;

public class BookingValidator {

    // Validate booking request
    public static void validateReservation(Reservation reservation,
                                           RoomInventory inventory)
            throws InvalidBookingException {

        // 1. Null check
        if (reservation == null) {
            throw new InvalidBookingException("Reservation cannot be null");
        }

        // 2. Guest name validation
        if (reservation.getGuestName() == null ||
                reservation.getGuestName().trim().isEmpty()) {

            throw new InvalidBookingException("Guest name is invalid");
        }

        // 3. Room type validation
        String roomType = reservation.getRoomType();

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type is invalid");
        }

        // 4. Check if room type exists in inventory
        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (!availability.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        // 5. Prevent negative inventory
        if (inventory.getAvailability(roomType) < 0) {
            throw new InvalidBookingException("Inventory corrupted for: " + roomType);
        }
    }
}
public void processBookings() {

    while (bookingQueue.hasPendingRequests()) {

        Reservation request = bookingQueue.getNextRequest();

        try {
            // 🔥 VALIDATION STEP (Fail-Fast)
            BookingValidator.validateReservation(request, inventory);

            String roomType = request.getRoomType();
            String guestName = request.getGuestName();

            int available = inventory.getAvailability(roomType);

            if (available > 0) {

                String roomId = generateRoomId(roomType);

                while (allAllocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allAllocatedRoomIds.add(roomId);

                allocatedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.bookRoom(roomType);

                bookingHistory.addBooking(request);

                System.out.println("Booking Confirmed: " + guestName +
                        " → " + roomType + " (" + roomId + ")");

            } else {
                System.out.println("Booking Failed: No availability for " + roomType);
            }

        } catch (InvalidBookingException e) {

            // 🔥 Graceful failure (no crash)
            System.out.println("Error: " + e.getMessage());
        }
    }
}
public class UseCase9ErrorHandling {

    public static void main(String[] args) {

        System.out.println("Booking System with Validation\n");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 1);
        inventory.addRoomType("Double", 1);

        BookingRequestQueue queue = new BookingRequestQueue();

        // VALID request
        queue.addRequest(new Reservation("Abhil", "Single"));

        // INVALID: empty name
        queue.addRequest(new Reservation("", "Double"));

        // INVALID: wrong room type
        queue.addRequest(new Reservation("Rahul", "Deluxe"));

        // INVALID: null
        queue.addRequest(null);

        BookingHistory history = new BookingHistory();

        RoomAllocationService service =
                new RoomAllocationService(inventory, queue, history);

        service.processBookings();
    }
}
