import java.util.ArrayList;

public class Query {
    private static final String NO_ACTIVE_BOOKINGS = "No active bookings";

    private final BookingSystem bookingSystem;

    public Query(BookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;
    }

    public String mostPopularEvent() {
        ArrayList<Event> events = bookingSystem.getEvents();
        if (events.size() == 0) return null;
        Event best = events.get(0);
        int bestTickets = activeTicketsForEvent(best);
        boolean hasActiveTickets = bestTickets > 0;
        for (Event event : events) {
            int tickets = activeTicketsForEvent(event);
            if (tickets > 0) {
                hasActiveTickets = true;
            }
            if (isBetter(tickets, event.getEventId(), bestTickets, best.getEventId())) {
                best = event;
                bestTickets = tickets;
            }
        }
        if (!hasActiveTickets) {
            return NO_ACTIVE_BOOKINGS;
        }
        return best.getEventName() + " | " + bestTickets + " tickets";
    }

    public String highestRevenueEvent() {
        ArrayList<Event> events = bookingSystem.getEvents();
        if (events.size() == 0) return null;
        Event best = events.get(0);
        double bestRevenue = activeRevenueForEvent(best);
        boolean hasActiveRevenue = bestRevenue > 0;
        for (Event event : events) {
            double revenue = activeRevenueForEvent(event);
            if (revenue > 0) {
                hasActiveRevenue = true;
            }
            if (isBetter(revenue, event.getEventId(), bestRevenue, best.getEventId())) {
                best = event;
                bestRevenue = revenue;
            }
        }
        if (!hasActiveRevenue) {
            return NO_ACTIVE_BOOKINGS;
        }
        return best.getEventName() + " | " + String.format("%.2f", bestRevenue);
    }

    public String topCustomer() {
        ArrayList<Customer> customers = bookingSystem.getCustomers();
        if (customers.size() == 0) return null;
        Customer best = customers.get(0);
        int bestTickets = activeTicketsForCustomer(best);
        boolean hasActiveTickets = bestTickets > 0;
        for (Customer customer : customers) {
            int tickets = activeTicketsForCustomer(customer);
            if (tickets > 0) {
                hasActiveTickets = true;
            }
            if (isBetter(tickets, customer.getCustomerId(), bestTickets, best.getCustomerId())) {
                best = customer;
                bestTickets = tickets;
            }
        }
        if (!hasActiveTickets) {
            return NO_ACTIVE_BOOKINGS;
        }
        return best.getCustomerName() + " | " + best.getMembershipType() + " | " + bestTickets + " tickets";
    }

    public String ticketsByCategory() {
        StringBuilder result = new StringBuilder();
        for (EventCategory category : EventCategory.values()) {
            if (result.length() > 0) {
                result.append(" | ");
            }
            result.append(category).append(": ").append(activeTicketsForCategory(category));
        }
        return result.toString();
    }

    private static boolean isBetter(double value, int id, double bestValue, int bestId) {
        return value > bestValue || (value == bestValue && id < bestId);
    }

    private ArrayList<Booking> activeBookingsForEvent(Event event) {
        ArrayList<Booking> result = new ArrayList<>();
        for (Booking booking : bookingSystem.getBookings()) {
            if (booking.isActive() && booking.getEvent() == event) {
                result.add(booking);
            }
        }
        return result;
    }

    private int activeTicketsForEvent(Event event) {
        int total = 0;
        for (Booking booking : activeBookingsForEvent(event)) {
            total += booking.getTicketQuantity();
        }
        return total;
    }

    private double activeRevenueForEvent(Event event) {
        double total = 0;
        for (Booking booking : activeBookingsForEvent(event)) {
            total += booking.getFinalPaidAmount();
        }
        return total;
    }

    private int activeTicketsForCustomer(Customer customer) {
        int total = 0;
        for (Booking booking : bookingSystem.getBookings()) {
            if (booking.isActive() && booking.getCustomer() == customer) {
                total += booking.getTicketQuantity();
            }
        }
        return total;
    }

    private int activeTicketsForCategory(EventCategory category) {
        int total = 0;
        for (Event event : bookingSystem.getEvents()) {
            if (event.getCategory() == category) {
                total += activeTicketsForEvent(event);
            }
        }
        return total;
    }

    public void printQueries() {
        System.out.println();
        System.out.println("========== QUERIES ==========");
        System.out.println("Q1: " + mostPopularEvent());
        System.out.println("Q2: " + highestRevenueEvent());
        System.out.println("Q3: " + topCustomer());
        System.out.println("Q4: " + ticketsByCategory());
    }
}
