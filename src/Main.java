import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Guest {
    private String name;
    private List<Reservation> reservations;

    public Guest(String name) {
        this.name = name;
        this.reservations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
}

class Reservation {
    private int reservationId;
    private Guest guest;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(int reservationId, Guest guest, String checkInDate, String checkOutDate) {
        this.reservationId = reservationId;
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }
}

class HotelReservationSystem {
    private List<Guest> guests;
    private List<Reservation> reservations;
    private int reservationIdCounter;

    public HotelReservationSystem() {
        this.guests = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationIdCounter = 1;
    }

    public void createReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        Guest guest = findOrCreateGuest(guestName);

        System.out.print("Enter check-in date: ");
        String checkInDate = scanner.nextLine();

        System.out.print("Enter check-out date: ");
        String checkOutDate = scanner.nextLine();

        Reservation reservation = new Reservation(reservationIdCounter++, guest, checkInDate, checkOutDate);
        reservations.add(reservation);
        guest.addReservation(reservation);

        System.out.println("Reservation created successfully!");
    }

    public void updateReservationDates() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();

        Reservation reservation = findReservationById(reservationId);

        if (reservation != null) {
            System.out.print("Enter new check-in date: ");
            String newCheckInDate = scanner.next();

            System.out.print("Enter new check-out date: ");
            String newCheckOutDate = scanner.next();

            reservation = new Reservation(reservation.getReservationId(), reservation.getGuest(),
                    newCheckInDate, newCheckOutDate);

            System.out.println("Reservation dates updated successfully!");
        } else {
            System.out.println("Reservation not found!");
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter reservation ID to cancel: ");
        int reservationId = scanner.nextInt();

        Reservation reservation = findReservationById(reservationId);

        if (reservation != null) {
            Guest guest = reservation.getGuest();
            guest.getReservations().remove(reservation);
            reservations.remove(reservation);

            System.out.println("Reservation canceled successfully!");
        } else {
            System.out.println("Reservation not found!");
        }
    }

    public void listReservationsByGuest() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter guest name to list reservations: ");
        String guestName = scanner.nextLine();

        Guest guest = findGuestByName(guestName);

        if (guest != null) {
            List<Reservation> guestReservations = guest.getReservations();

            System.out.println("Reservations for " + guest.getName() + ":");
            for (Reservation reservation : guestReservations) {
                System.out.println("Reservation ID: " + reservation.getReservationId() +
                        ", Check-in: " + reservation.getCheckInDate() +
                        ", Check-out: " + reservation.getCheckOutDate());
            }
        } else {
            System.out.println("Guest not found!");
        }
    }

    private Guest findOrCreateGuest(String guestName) {
        Guest guest = findGuestByName(guestName);
        if (guest == null) {
            guest = new Guest(guestName);
            guests.add(guest);
        }
        return guest;
    }

    private Guest findGuestByName(String guestName) {
        for (Guest guest : guests) {
            if (guest.getName().equalsIgnoreCase(guestName)) {
                return guest;
            }
        }
        return null;
    }

    private Reservation findReservationById(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        HotelReservationSystem reservationSystem = new HotelReservationSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("Hotel Reservation System");
            System.out.println("1. Create Reservation");
            System.out.println("2. Update Reservation Dates");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. List Reservations by Guest");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    reservationSystem.createReservation();
                    break;
                case 2:
                    reservationSystem.updateReservationDates();
                    break;
                case 3:
                    reservationSystem.cancelReservation();
                    break;
                case 4:
                    reservationSystem.listReservationsByGuest();
                    break;
                case 0:
                    System.out.println("Exiting Hotel Reservation System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
