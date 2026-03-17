// File: BookMyStay.java
import java.util.LinkedList;
import java.util.Queue;

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

    // Add a reservation request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
        System.out.println("Booking request added for guest: " + reservation.getGuestName());
    }

    // Peek at the next request without removing
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    // Process (remove) the next request
    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    // Display all pending requests
    public void displayPendingRequests() {
        System.out.println("\n=== Pending Booking Requests ===");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation res : requestQueue) {
                res.displayReservation();
            }
        }
        System.out.println("================================\n");
    }

    // Get number of pending requests
    public int getPendingCount() {
        return requestQueue.size();
    }
}

// Main application class
public class BookMyStay {
    public static void main(String[] args) {
        System.out.println("=== Welcome to Book My Stay App ===\n");

        // Initialize booking request queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single Room", 1);
        Reservation r2 = new Reservation("Bob", "Double Room", 2);
        Reservation r3 = new Reservation("Charlie", "Suite Room", 1);

        // Add requests to queue in arrival order
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display pending requests
        bookingQueue.displayPendingRequests();

        // Example: peek at next request
        Reservation next = bookingQueue.peekNextRequest();
        if (next != null) {
            System.out.println("Next request to process: " + next.getGuestName() + "\n");
        }

        // Example: process requests one by one (without modifying inventory yet)
        System.out.println("Processing booking requests in FIFO order:");
        while (bookingQueue.getPendingCount() > 0) {
            Reservation processed = bookingQueue.processNextRequest();
            System.out.print("Processed request -> ");
            processed.displayReservation();
        }

        System.out.println("\nAll booking requests processed (ready for allocation).");
        System.out.println("=== End of Booking Request Queue Demo ===");
    }
}