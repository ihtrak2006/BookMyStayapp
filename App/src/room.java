import java.util.HashMap;

public class room {

    private HashMap<String, Integer> inventory;

    // Constructor → initialize inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Add room type with count
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Book a room (decrease count)
    public void bookRoom(String roomType) {
        int current = getAvailability(roomType);

        if (current > 0) {
            inventory.put(roomType, current - 1);
            System.out.println(roomType + " booked successfully.");
        } else {
            System.out.println(roomType + " not available.");
        }
    }

    // Cancel booking (increase count)
    public void cancelRoom(String roomType) {
        int current = getAvailability(roomType);
        inventory.put(roomType, current + 1);
        System.out.println(roomType + " booking cancelled.");
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " → " + inventory.get(roomType));
        }
    }
}
