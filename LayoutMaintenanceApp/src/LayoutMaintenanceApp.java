import java.util.Scanner;

public class LayoutMaintenanceApp {

    private Scanner sc;
    private User currentUser;

    public LayoutMaintenanceApp() {
        this.sc = new Scanner(System.in);
        this.currentUser = null;
    }

    public void start() {
        displayWelcome();

        while (true) {
            displayMainMenu();

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 2) {
                System.out.println("Program terminated. Goodbye!");
                sc.close();
                return;
            }

            if (choice == 1) {
                handleLogin();
            }
        }
    }

    private void displayWelcome() {
        System.out.println("LAYOUT MAINTENANCE APPLICATION");
    }

    private void displayMainMenu() {
        System.out.println("\n===== MAIN MENU =====");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter choice: ");
    }

    private void handleLogin() {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        currentUser = Database.login(username, password);

        if (currentUser == null) {
            System.out.println("Invalid Username or Password!");
            return;
        }

        System.out.println("Logged in as: " + currentUser.getRole());
        System.out.println("Welcome, " + currentUser.getName() + "!");

        if (currentUser.getRole().equals("ADMIN")) {
            handleAdminSession();
        } else {
            handleOwnerSession();
        }

        System.out.println("Logged out successfully.");
        currentUser = null;
    }

    private void handleAdminSession() {
        Adminoperations adminOps = new Adminoperations(sc);
        adminOps.showMenu();
    }

    private void handleOwnerSession() {
        Owneroperations ownerOps = new Owneroperations(sc, currentUser);
        ownerOps.showMenu();
    }

    public static void main(String[] args) {
        LayoutMaintenanceApp app = new LayoutMaintenanceApp();
        app.start();
    }
}