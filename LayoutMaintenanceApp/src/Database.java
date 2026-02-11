import java.sql.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/LayoutMaintenance";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    // Get connection
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
    }

    // ========== AUTHENTICATION ==========
    
    public static User login(String username, String password) {
        String sql = "SELECT user_id, name, username, role FROM users WHERE username = ? AND password = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User(rs.getString("name"), rs.getString("username"), "", "", "", "", rs.getString("role"));
                user.setUserId(rs.getInt("user_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ========== OWNER OPERATIONS ==========
    
    public static boolean addOwner(User user) {
        String sql = "INSERT INTO users (name, username, password, phone, email, address, role) VALUES (?, ?, ?, ?, ?, ?, 'OWNER')";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getAddress());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void viewAllOwners() {
        String sql = "SELECT user_id, name, username, phone, email FROM users WHERE role = 'OWNER'";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- All Owners ---");
            System.out.printf("%-5s %-20s %-15s %-15s %-25s%n", "ID", "Name", "Username", "Phone", "Email");
            System.out.println("--------------------------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-15s %-15s %-25s%n",
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("phone"),
                    rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateOwner(int ownerId, String name, String username, String password, String phone, String email, String address) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        boolean hasUpdate = false;
        
        if (!name.isEmpty()) { sql.append("name = ?, "); hasUpdate = true; }
        if (!username.isEmpty()) { sql.append("username = ?, "); hasUpdate = true; }
        if (!password.isEmpty()) { sql.append("password = ?, "); hasUpdate = true; }
        if (!phone.isEmpty()) { sql.append("phone = ?, "); hasUpdate = true; }
        if (!email.isEmpty()) { sql.append("email = ?, "); hasUpdate = true; }
        if (!address.isEmpty()) { sql.append("address = ?, "); hasUpdate = true; }
        
        if (!hasUpdate) return false;
        
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE user_id = ?");
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            
            int i = 1;
            if (!name.isEmpty()) ps.setString(i++, name);
            if (!username.isEmpty()) ps.setString(i++, username);
            if (!password.isEmpty()) ps.setString(i++, password);
            if (!phone.isEmpty()) ps.setString(i++, phone);
            if (!email.isEmpty()) ps.setString(i++, email);
            if (!address.isEmpty()) ps.setString(i++, address);
            ps.setInt(i, ownerId);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOwner(int ownerId) {
        String sql = "DELETE FROM users WHERE user_id = ? AND role = 'OWNER'";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, ownerId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== SITE OPERATIONS ==========
    
    public static boolean addSite(Site site) {
        String sql = "INSERT INTO sites (site_number, site_type, length_ft, width_ft, area_sqft, occupancy_status, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, site.getSiteNumber());
            ps.setString(2, site.getSiteType());
            ps.setInt(3, site.getLengthFt());
            ps.setInt(4, site.getWidthFt());
            ps.setInt(5, site.getAreaSqft());
            ps.setString(6, site.getOccupancyStatus());
            ps.setObject(7, site.getOwnerId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Site getSite(int siteNumber) {
        String sql = "SELECT * FROM sites WHERE site_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, siteNumber);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Site site = new Site(
                    rs.getInt("site_number"),
                    rs.getString("site_type"),
                    rs.getInt("length_ft"),
                    rs.getInt("width_ft"),
                    rs.getString("occupancy_status"),
                    (Integer) rs.getObject("owner_id")
                );
                site.setSiteId(rs.getInt("site_id"));
                return site;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void viewAllSites() {
        String sql = "SELECT s.*, u.name as owner_name FROM sites s LEFT JOIN users u ON s.owner_id = u.user_id ORDER BY s.site_number";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- All Sites ---");
            System.out.printf("%-8s %-20s %-10s %-15s %-20s%n", "Site#", "Type", "Area", "Status", "Owner");
            System.out.println("--------------------------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-8d %-20s %-10d %-15s %-20s%n",
                    rs.getInt("site_number"),
                    rs.getString("site_type"),
                    rs.getInt("area_sqft"),
                    rs.getString("occupancy_status"),
                    rs.getString("owner_name") != null ? rs.getString("owner_name") : "No Owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateSite(int siteNumber, String siteType, String occupancyStatus, Integer ownerId) {
        StringBuilder sql = new StringBuilder("UPDATE sites SET ");
        boolean hasUpdate = false;
        
        if (siteType != null && !siteType.equals("0")) { sql.append("site_type = ?, "); hasUpdate = true; }
        if (occupancyStatus != null && !occupancyStatus.equals("0")) { sql.append("occupancy_status = ?, "); hasUpdate = true; }
        if (ownerId != null && ownerId != -1) { sql.append("owner_id = ?, "); hasUpdate = true; }
        
        if (!hasUpdate) return false;
        
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE site_number = ?");
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            
            int i = 1;
            if (siteType != null && !siteType.equals("0")) ps.setString(i++, siteType);
            if (occupancyStatus != null && !occupancyStatus.equals("0")) ps.setString(i++, occupancyStatus);
            if (ownerId != null && ownerId != -1) ps.setObject(i++, ownerId == 0 ? null : ownerId);
            ps.setInt(i, siteNumber);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteSite(int siteNumber) {
        String sql = "DELETE FROM sites WHERE site_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, siteNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== MAINTENANCE OPERATIONS ==========
    
    public static boolean collectMaintenance(int siteNumber, int month, int year) {
        Site site = getSite(siteNumber);
        if (site == null) return false;
        
        int rate = site.getOccupancyStatus().equals("OPEN") ? 6 : 9;
        int totalAmount = site.getAreaSqft() * rate;
        
        String sql = "INSERT INTO maintenance (site_id, month, year, rate_per_sqft, total_amount, payment_status) VALUES (?, ?, ?, ?, ?, 'UNPAID')";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, site.getSiteId());
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setInt(4, rate);
            ps.setInt(5, totalAmount);
            ps.executeUpdate();
            
            System.out.println("Area: " + site.getAreaSqft() + " sqft, Rate: ₹" + rate + "/sqft, Total: ₹" + totalAmount);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void viewPendingMaintenance(Integer siteNumber) {
        String sql = siteNumber == null ?
            "SELECT s.site_number, m.maintenance_id, m.month, m.year, m.total_amount, m.payment_status FROM maintenance m JOIN sites s ON m.site_id = s.site_id WHERE m.payment_status != 'PAID' ORDER BY s.site_number" :
            "SELECT s.site_number, m.maintenance_id, m.month, m.year, m.total_amount, m.payment_status FROM maintenance m JOIN sites s ON m.site_id = s.site_id WHERE m.payment_status != 'PAID' AND s.site_number = ?";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if (siteNumber != null) ps.setInt(1, siteNumber);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- Pending Maintenance ---");
            System.out.printf("%-12s %-8s %-12s %-12s %-15s%n", "Maint ID", "Site#", "Month/Year", "Amount", "Status");
            System.out.println("----------------------------------------------------------------------");
            
            while (rs.next()) {
                System.out.printf("%-12d %-8d %-12s %-12s %-15s%n",
                    rs.getInt("maintenance_id"),
                    rs.getInt("site_number"),
                    rs.getInt("month") + "/" + rs.getInt("year"),
                    "₹" + rs.getInt("total_amount"),
                    rs.getString("payment_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean approvePayment(int paymentId) {
        String sql = "UPDATE payments SET approved_by_admin = TRUE WHERE payment_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, paymentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== SITE UPDATE REQUEST OPERATIONS ==========
    
    public static void viewPendingRequests() {
        String sql = "SELECT r.request_id, s.site_number, u.name, r.requested_site_type, r.requested_occupancy_status FROM site_update_requests r JOIN sites s ON r.site_id = s.site_id JOIN users u ON r.owner_id = u.user_id WHERE r.status = 'PENDING'";
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            System.out.println("\n--- Pending Site Update Requests ---");
            System.out.printf("%-8s %-8s %-20s %-20s %-20s%n", "Req ID", "Site#", "Owner", "New Type", "New Status");
            System.out.println("------------------------------------------------------------------------------------");
            
            boolean hasRequests = false;
            while (rs.next()) {
                hasRequests = true;
                System.out.printf("%-8d %-8d %-20s %-20s %-20s%n",
                    rs.getInt("request_id"),
                    rs.getInt("site_number"),
                    rs.getString("name"),
                    rs.getString("requested_site_type"),
                    rs.getString("requested_occupancy_status"));
            }
            if (!hasRequests) System.out.println("No pending requests.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean approveRequest(int requestId) {
        try (Connection con = getConnection()) {
            con.setAutoCommit(false);
            
            String getSql = "SELECT site_id, requested_site_type, requested_occupancy_status FROM site_update_requests WHERE request_id = ?";
            PreparedStatement ps1 = con.prepareStatement(getSql);
            ps1.setInt(1, requestId);
            ResultSet rs = ps1.executeQuery();
            
            if (!rs.next()) {
                con.rollback();
                return false;
            }
            
            int siteId = rs.getInt("site_id");
            String newType = rs.getString("requested_site_type");
            String newStatus = rs.getString("requested_occupancy_status");
            
            String updateSiteSql = "UPDATE sites SET site_type = ?, occupancy_status = ? WHERE site_id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateSiteSql);
            ps2.setString(1, newType);
            ps2.setString(2, newStatus);
            ps2.setInt(3, siteId);
            ps2.executeUpdate();
            
            String updateReqSql = "UPDATE site_update_requests SET status = 'APPROVED' WHERE request_id = ?";
            PreparedStatement ps3 = con.prepareStatement(updateReqSql);
            ps3.setInt(1, requestId);
            ps3.executeUpdate();
            
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean rejectRequest(int requestId) {
        String sql = "UPDATE site_update_requests SET status = 'REJECTED' WHERE request_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, requestId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========== OWNER OPERATIONS ==========
    
    public static void viewOwnSites(int ownerId) {
        String sql = "SELECT site_number, site_type, length_ft, width_ft, area_sqft, occupancy_status FROM sites WHERE owner_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- Your Site Details ---");
            
            boolean hasSite = false;
            while (rs.next()) {
                hasSite = true;
                System.out.println("Site Number: " + rs.getInt("site_number"));
                System.out.println("Type: " + rs.getString("site_type"));
                System.out.println("Dimensions: " + rs.getInt("length_ft") + " x " + rs.getInt("width_ft") + " ft");
                System.out.println("Area: " + rs.getInt("area_sqft") + " sqft");
                System.out.println("Occupancy: " + rs.getString("occupancy_status"));
                System.out.println("-----------------------------------");
            }
            
            if (!hasSite) System.out.println("You don't own any sites yet.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean requestSiteUpdate(int ownerId, String newType, String newStatus) {
        String getSiteSql = "SELECT site_id FROM sites WHERE owner_id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(getSiteSql)) {
            
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) return false;
            
            int siteId = rs.getInt("site_id");
            
            String insertSql = "INSERT INTO site_update_requests (site_id, owner_id, requested_site_type, requested_occupancy_status, status) VALUES (?, ?, ?, ?, 'PENDING')";
            PreparedStatement ps2 = con.prepareStatement(insertSql);
            ps2.setInt(1, siteId);
            ps2.setInt(2, ownerId);
            ps2.setString(3, newType);
            ps2.setString(4, newStatus);
            ps2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean makePayment(int maintenanceId, int amount) {
        String sql = "INSERT INTO payments (maintenance_id, paid_amount, payment_status) VALUES (?, ?, 'PARTIALLY_PAID')";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, maintenanceId);
            ps.setInt(2, amount);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void viewPaymentHistory(int ownerId) {
        String sql = "SELECT m.maintenance_id, s.site_number, m.month, m.year, m.total_amount, p.payment_id, p.paid_amount, p.payment_date, p.approved_by_admin FROM payments p JOIN maintenance m ON p.maintenance_id = m.maintenance_id JOIN sites s ON m.site_id = s.site_id WHERE s.owner_id = ? ORDER BY p.payment_date DESC";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("\n--- Your Payment History ---");
            System.out.printf("%-10s %-10s %-12s %-12s %-12s %-25s %-10s%n", "Pay ID", "Maint ID", "Site#", "Month/Year", "Paid", "Date", "Approved");
            System.out.println("----------------------------------------------------------------------------------------------------");
            
            boolean hasPayments = false;
            while (rs.next()) {
                hasPayments = true;
                System.out.printf("%-10d %-10d %-12d %-12s %-12s %-25s %-10s%n",
                    rs.getInt("payment_id"),
                    rs.getInt("maintenance_id"),
                    rs.getInt("site_number"),
                    rs.getInt("month") + "/" + rs.getInt("year"),
                    "₹" + rs.getInt("paid_amount"),
                    rs.getTimestamp("payment_date"),
                    rs.getBoolean("approved_by_admin") ? "Yes" : "No");
            }
            
            if (!hasPayments) System.out.println("No payment history found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}