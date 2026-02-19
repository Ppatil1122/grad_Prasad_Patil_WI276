import java.util.Scanner;

public class Adminoperations {
    private Scanner sc;

    public Adminoperations(Scanner sc) {
        this.sc = sc;
    }

    // ========== OWNER MANAGEMENT ==========

    public void manageOwners() {
        while (true) {
            System.out.println("\n--- Owner Management ---");
            System.out.println("1. View All Owners");
            System.out.println("2. Add Owner");
            System.out.println("3. Edit Owner");
            System.out.println("4. Remove Owner");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> Database.viewAllOwners();
                case 2 -> addOwner();
                case 3 -> editOwner();
                case 4 -> removeOwner();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void addOwner() {
        System.out.println("\n--- Add New Owner ---");

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        User newOwner = new User(name, username, password, phone, email, address, "OWNER");

        if (Database.addOwner(newOwner)) {
            System.out.println("Owner added successfully!");
        } else {
            System.out.println("Error: Username might already exist!");
        }
    }

    private void editOwner() {
        System.out.println("\n--- Edit Owner ---");

        System.out.print("Enter Owner User ID to edit: ");
        int ownerId = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name (or press Enter to skip): ");
        String name = sc.nextLine();

        System.out.print("Enter New Username (or press Enter to skip): ");
        String username = sc.nextLine();

        System.out.print("Enter New Password (or press Enter to skip): ");
        String password = sc.nextLine();

        System.out.print("Enter New Phone (or press Enter to skip): ");
        String phone = sc.nextLine();

        System.out.print("Enter New Email (or press Enter to skip): ");
        String email = sc.nextLine();

        System.out.print("Enter New Address (or press Enter to skip): ");
        String address = sc.nextLine();

        if (Database.updateOwner(ownerId, name, username, password, phone, email, address)) {
            System.out.println("Owner updated successfully!");
        } else {
            System.out.println("No updates provided or error occurred!");
        }
    }

    private void removeOwner() {
        System.out.println("\n--- Remove Owner ---");

        System.out.print("Enter Owner User ID to remove: ");
        int ownerId = sc.nextInt();

        if (Database.deleteOwner(ownerId)) {
            System.out.println("Owner removed successfully!");
        } else {
            System.out.println("Owner not found or has associated data!");
        }
    }

    // ========== SITE MANAGEMENT ==========

    public void manageSites() {
        while (true) {
            System.out.println("\n--- Site Management ---");
            System.out.println("1. View All Sites");
            System.out.println("2. Add Site");
            System.out.println("3. Edit Site");
            System.out.println("4. Remove Site");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> Database.viewAllSites();
                case 2 -> addSite();
                case 3 -> editSite();
                case 4 -> removeSite();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void addSite() {
        System.out.println("\n--- Add New Site ---");

        System.out.print("Enter Site Number: ");
        int siteNumber = sc.nextInt();

        System.out.print("Enter Site Type (VILLA/APARTMENT/INDEPENDENT_HOUSE/OPEN_SITE): ");
        String siteType = sc.next().toUpperCase();

        System.out.print("Enter Length (ft): ");
        int length = sc.nextInt();

        System.out.print("Enter Width (ft): ");
        int width = sc.nextInt();

        System.out.print("Enter Occupancy Status (OPEN/RENTED/SELF_OCCUPIED/VACANT/SOLD): ");
        String occupancy = sc.next().toUpperCase();

        System.out.print("Enter Owner ID (or 0 for no owner): ");
        int ownerId = sc.nextInt();

        Site newSite = new Site(siteNumber, siteType, length, width, occupancy, ownerId == 0 ? null : ownerId);

        if (Database.addSite(newSite)) {
            System.out.println("Site added successfully! Area: " + newSite.getAreaSqft() + " sqft");
        } else {
            System.out.println("Error adding site!");
        }
    }

    private void editSite() {
        System.out.println("\n--- Edit Site ---");

        System.out.print("Enter Site Number to edit: ");
        int siteNumber = sc.nextInt();

        System.out.print("Enter New Type (or 0 to skip): ");
        String type = sc.next();

        System.out.print("Enter New Occupancy Status (or 0 to skip): ");
        String status = sc.next();

        System.out.print("Enter New Owner ID (or -1 to skip, 0 to remove owner): ");
        int ownerId = sc.nextInt();

        if (Database.updateSite(siteNumber, type, status, ownerId)) {
            System.out.println("Site updated successfully!");
        } else {
            System.out.println("No updates provided or error occurred!");
        }
    }

    private void removeSite() {
        System.out.println("\n--- Remove Site ---");

        System.out.print("Enter Site Number to remove: ");
        int siteNumber = sc.nextInt();

        if (Database.deleteSite(siteNumber)) {
            System.out.println("Site removed successfully!");
        } else {
            System.out.println("Site not found or has associated data!");
        }
    }

    // ========== MAINTENANCE MANAGEMENT ==========

    public void manageMaintenance() {
        while (true) {
            System.out.println("\n--- Maintenance Management ---");
            System.out.println("1. Collect Maintenance (Generate Bill)");
            System.out.println("2. View Pending Maintenance");
            System.out.println("3. Approve Payment");
            System.out.println("4. Back");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> collectMaintenance();
                case 2 -> viewPendingMaintenance();
                case 3 -> approvePayment();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void collectMaintenance() {
        System.out.println("\n--- Collect Maintenance ---");

        System.out.print("Enter Site Number: ");
        int siteNumber = sc.nextInt();

        System.out.print("Enter Month (1-12): ");
        int month = sc.nextInt();

        System.out.print("Enter Year: ");
        int year = sc.nextInt();

        if (Database.collectMaintenance(siteNumber, month, year)) {
            System.out.println("Maintenance collected successfully!");
        } else {
            System.out.println("Error - Site not found or maintenance already exists!");
        }
    }

    private void viewPendingMaintenance() {
        System.out.println("\n1. View All Pending");
        System.out.println("2. View Specific Site");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice == 2) {
            System.out.print("Enter Site Number: ");
            int siteNumber = sc.nextInt();
            Database.viewPendingMaintenance(siteNumber);
        } else {
            Database.viewPendingMaintenance(null);
        }
    }

    private void approvePayment() {
        System.out.print("Enter Payment ID to approve: ");
        int paymentId = sc.nextInt();

        if (Database.approvePayment(paymentId)) {
            System.out.println("Payment Approved");
        } else {
            System.out.println("Payment not found!");
        }
    }

    // ========== SITE UPDATE REQUESTS ==========

    public void manageUpdateRequests() {
        while (true) {
            System.out.println("\n--- Site Update Requests ---");
            System.out.println("1. View Pending Requests");
            System.out.println("2. Approve/Reject Request");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> Database.viewPendingRequests();
                case 2 -> approveRejectRequest();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void approveRejectRequest() {
        System.out.print("Enter Request ID: ");
        int requestId = sc.nextInt();

        System.out.print("Approve or Reject? (1=Approve, 2=Reject): ");
        int choice = sc.nextInt();

        if (choice == 1) {
            if (Database.approveRequest(requestId)) {
                System.out.println("Request approved and site updated!");
            } else {
                System.out.println("Error approving request!");
            }
        } else {
            if (Database.rejectRequest(requestId)) {
                System.out.println("Request rejected!");
            } else {
                System.out.println("Error rejecting request!");
            }
        }
    }

    // ========== ADMIN MENU ==========

    public void showMenu() {
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Owner Management");
            System.out.println("2. Site Management");
            System.out.println("3. Maintenance Management");
            System.out.println("4. Site Update Requests");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> manageOwners();
                case 2 -> manageSites();
                case 3 -> manageMaintenance();
                case 4 -> manageUpdateRequests();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}