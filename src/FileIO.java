import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileIO {
    private static final String EVENTS_FILE = "events.csv";
    private static final String CUSTOMERS_FILE = "customers.csv";

    public static ArrayList<Event> loadEvents() {
        ArrayList<Event> events = new ArrayList<>();
        for (StringTokenizer fields : readRows(EVENTS_FILE, 5)) {
            try {
                int eventId = Integer.parseInt(fields.nextToken().trim());
                String eventName = fields.nextToken().trim();
                EventCategory category = EventCategory.valueOf(fields.nextToken().trim().toUpperCase());
                int capacity = Integer.parseInt(fields.nextToken().trim());
                double ticketPrice = Double.parseDouble(fields.nextToken().trim());
                events.add(new Event(eventId, eventName, category, capacity, ticketPrice));
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping invalid event row");
            }
        }
        return events;
    }

    public static ArrayList<Customer> loadCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (StringTokenizer fields : readRows(CUSTOMERS_FILE, 4)) {
            try {
                int customerId = Integer.parseInt(fields.nextToken().trim());
                String customerName = fields.nextToken().trim();
                double balance = Double.parseDouble(fields.nextToken().trim());
                MembershipType membership = MembershipType.valueOf(fields.nextToken().trim().toUpperCase());

                if (membership == MembershipType.PREMIUM) {
                    customers.add(new PremiumCustomer(customerId, customerName, balance));
                } else {
                    customers.add(new Customer(customerId, customerName, balance));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping invalid customer row");
            }
        }
        return customers;
    }

    private static ArrayList<StringTokenizer> readRows(String fileName, int expectedColumns) {
        ArrayList<StringTokenizer> rows = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue;
                }
                StringTokenizer fields = new StringTokenizer(line, ",");
                if (fields.countTokens() != expectedColumns) {
                    System.out.println("Skipping malformed row in " + fileName + ": " + line);
                    continue;
                }
                rows.add(fields);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not read " + fileName + ": " + e.getMessage());
        }
        return rows;
    }
}
