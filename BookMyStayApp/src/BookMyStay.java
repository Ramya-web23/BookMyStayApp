// File: BookMyStay.java
abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double size; // in square meters
    private double price; // per night

    // Constructor
    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    // Getters
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method to display room details
    public abstract void displayDetails();
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 15.0, 50.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 25.0, 90.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 50.0, 200.0);
    }

    @Override
    public void displayDetails() {
        System.out.println(getRoomType() + " - Beds: " + getNumberOfBeds() +
                ", Size: " + getSize() + " sqm, Price: $" + getPrice());
    }
}

// Main class renamed to BookMyStay
public class BookMyStay {
    public static void main(String[] args) {
        // Static availability variables
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        // Initialize room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Display room details and availability
        System.out.println("=== Welcome to Book My Stay App ===\n");

        singleRoom.displayDetails();
        System.out.println("Available: " + singleRoomAvailable + "\n");

        doubleRoom.displayDetails();
        System.out.println("Available: " + doubleRoomAvailable + "\n");

        suiteRoom.displayDetails();
        System.out.println("Available: " + suiteRoomAvailable + "\n");

        System.out.println("=== End of Room Listings ===");
    }
}