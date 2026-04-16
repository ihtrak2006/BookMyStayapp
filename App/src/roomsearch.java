import java.util.HashMap;
import java.util.Map;

public class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getRoomAvailability() {
        return inventory; // read-only usage (don’t modify outside)
    }
}
import java.util.Map;

public class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        System.out.println("\nAvailable Rooms:\n");

        // Single Room
        if (availability.getOrDefault("Single", 0) > 0) {
            System.out.println("Single Room Available:");
            singleRoom.displayRoomDetails();
            System.out.println("----------------------");
        }

        // Double Room
        if (availability.getOrDefault("Double", 0) > 0) {
            System.out.println("Double Room Available:");
            doubleRoom.displayRoomDetails();
            System.out.println("----------------------");
        }

        // Suite Room
        if (availability.getOrDefault("Suite", 0) > 0) {
            System.out.println("Suite Room Available:");
            suiteRoom.displayRoomDetails();
            System.out.println("----------------------");
        }
    }
}
public class UseCase4RoomSearch {

    public static void main(String[] args) {

        // Step 1: Create inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 2);
        inventory.addRoomType("Double", 0);
        inventory.addRoomType("Suite", 1);

        // Step 2: Create room objects
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Step 3: Create search service
        RoomSearchService searchService = new RoomSearchService();

        // Step 4: Perform search (READ-ONLY)
        searchService.searchAvailableRooms(inventory, single, dbl, suite);
    }
}
