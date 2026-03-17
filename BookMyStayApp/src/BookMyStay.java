// File: BookMyStay.java
import java.util.*;

// Reservation class representing a guest booking request
class Reservation {
    private String guestName;
    private String roomType;
    private int numberOfRooms;

    public Reservation(String guestName, String roomType, int numberOfRooms) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNumberOfRooms() { return numberOfRooms; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + ", Room Type: " + roomType +
                ", Rooms Requested: " + numberOfRooms);
    }
}

// Booking request queue class
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for guest: " + reservation.getGuestName());
    }

    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
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

    public boolean decrementAvailability(String roomType) {
        int current = inventory.getOrDefault(roomType, 0);
        if (current <= 0) return false;
        inventory.put(roomType, current - 1);
        return true;
    }
}

// Room allocation service
class RoomAllocationService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms; // roomType -> allocated room IDs
    private int roomIdCounter;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        roomIdCounter = 1000; // starting room ID
    }

    // Allocate rooms for a reservation
    public void allocateRooms(Reservation reservation) {
        String roomType = reservation.getRoomType();
        int roomsRequested = reservation.getNumberOfRooms();

        for (int i = 0; i < roomsRequested; i++) {
            if (inventory.decrementAvailability(roomType)) {
                String roomId = generateUniqueRoomId();
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                allocatedRooms.get(roomType).add(roomId);

                System.out.println("Confirmed: " + reservation.getGuestName() +
                        " -> " + roomType + " assigned Room ID: " + roomId);
            } else {
                System.out.println("Cannot allocate " + roomType +
                        " for " + reservation.getGuestName() +
                        " - No availability");
                break;
            }
        }
    }

    private String generateUniqueRoomId() {
        roomIdCounter++;
        return "R" + roomIdCounter;
    }

    public void displayAllocatedRooms() {
        System.out.println("\n=== Allocated Rooms ===");
        for (String roomType : allocatedRooms.keySet()) {
            System.out.println(roomType + ": " + allocatedRooms.get(roomType));
        }
        System.out.println("======================\n");
    }
}

// Main application
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Book My Stay App ===\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 2);
        inventory.addRoomType("Suite Room", 1);

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        bookingQueue.addRequest(new Reservation("Alice", "Single Room", 1));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room", 2));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room", 1));
        bookingQueue.addRequest(new Reservation("Diana", "Single Room", 1)); // may fail if inventory exhausted

        // Initialize room allocation service
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        // Process and allocate rooms in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation res = bookingQueue.processNextRequest();
            allocationService.allocateRooms(res);
        }

        // Display all allocations
        allocationService.displayAllocatedRooms();

        System.out.println("=== End of Booking Allocation Demo ===");
    }
}