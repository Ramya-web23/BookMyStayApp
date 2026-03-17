// File: BookMyStay.java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

// Abstract Room class
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() { return roomType; }
    public int getNumberOfBeds() { return numberOfBeds; }
    public double getSize() { return size; }
    public double getPrice() { return price; }

    public abstract void displayDetails();
}

// Concrete Room classes
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 15.0, 50.0); }
    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 25.0, 90.0); }
    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 50.0, 200.0); }
    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

// Centralized inventory
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Controlled updates
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;
        if (updated < 0) {
            System.out.println("Cannot reduce availability below 0 for " + roomType);
            return;
        }
        inventory.put(roomType, updated);
    }

    public HashMap<String, Integer> getInventorySnapshot() {
        return new HashMap<>(inventory); // Read-only copy
    }
}

// Search service: read-only access
class RoomSearchService {
    private RoomInventory inventory;
    private List<Room> rooms;

    public RoomSearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    // Display only available rooms
    public void displayAvailableRooms() {
        System.out.println("\n=== Available Rooms ===");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
        System.out.println("======================\n");
    }
}

// Main application
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Book My Stay App ===\n");

        // Initialize rooms
        List<Room> rooms = new ArrayList<>();
        rooms.add(new SingleRoom());
        rooms.add(new DoubleRoom());
        rooms.add(new SuiteRoom());

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 0); // Example: unavailable
        inventory.addRoomType("Suite Room", 2);

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Display available rooms
        searchService.displayAvailableRooms();

        // Inventory remains unchanged
        System.out.println("=== End of Room Search ===");
    }
}