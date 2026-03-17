// File: BookMyStay.java
import java.util.HashMap;

// Abstract Room class (unchanged)
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

// Centralized inventory class
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register a room type with initial availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get current availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (for booking or cancellation)
    public void updateAvailability(String roomType, int change) {
        int current = inventory.getOrDefault(roomType, 0);
        int updated = current + change;
        if (updated < 0) {
            System.out.println("Cannot reduce availability below 0 for " + roomType);
            return;
        }
        inventory.put(roomType, updated);
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("\n=== Current Room Inventory ===");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " - Available: " + inventory.get(roomType));
        }
        System.out.println("==============================\n");
    }
}

// Main application class
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Book My Stay App ===\n");

        // Initialize room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details
        singleRoom.displayDetails();
        doubleRoom.displayDetails();
        suiteRoom.displayDetails();

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(singleRoom.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suiteRoom.getRoomType(), 2);

        // Display inventory
        inventory.displayInventory();

        // Example updates
        inventory.updateAvailability("Single Room", -1); // One single room booked
        inventory.updateAvailability("Suite Room", 1);   // One suite room freed

        // Display inventory after updates
        inventory.displayInventory();

        System.out.println("=== End of Room Listings ===");
    }
}