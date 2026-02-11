import java.util.Scanner;

public class Owneroperations {
    private Scanner sc;
    private User currentUser;

    public Owneroperations(Scanner sc, User currentUser) {
        this.sc = sc;
        this.currentUser = currentUser;
    }

    // ========== OWNER MENU ==========
    
    public void showMenu() {
        while (true) {
            System.out.println("\n===== OWNER MENU =====");
            System.out.println("1. View My Site Details");
            System.out.println("2. Request Site Update");
            System.out.println("3. Make Payment");
            System.out.println("4. View Payment History");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewOwnSites();
                case 2 -> requestSiteUpdate();
                case 3 -> makePayment();
                case 4 -> viewPaymentHistory();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void viewOwnSites() {
        Database.viewOwnSites(currentUser.getUserId());
    }

    private void requestSiteUpdate() {
        System.out.print("Enter New Site Type (VILLA/APARTMENT/INDEPENDENT_HOUSE/OPEN_SITE): ");
        String newType = sc.next().toUpperCase();

        System.out.print("Enter New Occupancy Status (OPEN/RENTED/SELF_OCCUPIED/VACANT/SOLD): ");
        String newStatus = sc.next().toUpperCase();

        if (Database.requestSiteUpdate(currentUser.getUserId(), newType, newStatus)) {
            System.out.println("Update request submitted! Awaiting admin approval.");
        } else {
            System.out.println("Error submitting request!");
        }
    }

    private void makePayment() {
        System.out.print("Enter Maintenance ID: ");
        int maintenanceId = sc.nextInt();

        System.out.print("Enter Amount: ");
        int amount = sc.nextInt();

        if (Database.makePayment(maintenanceId, amount)) {
            System.out.println("Payment added (Pending Admin Approval)");
        } else {
            System.out.println("Error adding payment!");
        }
    }

    private void viewPaymentHistory() {
        Database.viewPaymentHistory(currentUser.getUserId());
    }
}