import java.util.*;

public class CancellationService {

    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack;

    // Track cancelled reservations
    private Set<String> cancelledReservations;

    public CancellationService(RoomInventory inventory,
                               Map<String, Set<String>> allocatedRooms) {
        this.inventory = inventory;
        this.allocatedRooms = allocatedRooms;
        this.rollbackStack = new Stack<>();
        this.cancelledReservations = new HashSet<>();
    }

    // Cancel booking
    public void cancelReservation(String roomType, String roomId) {

        // 1. Validate existence
        if (!allocatedRooms.containsKey(roomType) ||
                !allocatedRooms.get(roomType).contains(roomId)) {

            System.out.println("Cancellation Failed: Invalid reservation.");
            return;
        }

        // 2. Prevent duplicate cancellation
        if (cancelledReservations.contains(roomId)) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        // 3. Remove allocation
        allocatedRooms.get(roomType).remove(roomId);

        // 4. Push to rollback stack
        rollbackStack.push(roomId);

        // 5. Restore inventory
        inventory.cancelRoom(roomType);

        // 6. Mark as cancelled
        cancelledReservations.add(roomId);

        System.out.println("Cancellation Successful:");
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
        System.out.println("------------------------");
    }

    // Show rollback history
    public void showRollbackHistory() {
        System.out.println("\nRollback Stack (Recent First):");

        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}
