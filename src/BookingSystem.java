import java.util.ArrayList;

public class BookingSystem {

    private ArrayList<Event> events;
    private ArrayList<Customer> customers;
    private ArrayList<Booking> bookings;
    private BookingPolicy policy;
    private boolean policyEnabled;
    private int groupNumber;
    private int nextBookingID;

    public BookingSystem(int groupNumber, ArrayList<Event> events, ArrayList<Customer> customers, boolean policyEnabled){
        this.groupNumber = groupNumber;
        this.events = events;
        this.customers = customers;
        this.bookings = new ArrayList<Booking>();

        this.policy = calculatePolicy(groupNumber);
        this.policyEnabled = policyEnabled;
        this.nextBookingID = 1;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    public ArrayList<Booking> getBookings() {
        return bookings;
    }
    public BookingPolicy getPolicy() {
        return policy;
    }
    public int getPremiumCustomerCount(){
        int count = 0;
        for (Customer customer : customers){
            if (customer.getMembershipType() == MembershipType.PREMIUM){
                count++;
            }
        }
        return count;
    }


    private BookingPolicy calculatePolicy(int groupNumber){
        int ruleNumber = (groupNumber - 1) % 5;
        return BookingPolicy.values()[ruleNumber];
        }


        public Customer findCustomer(int customerID){
        for (Customer customer : customers){
            if (customer.getCustomerId() == customerID){
                return customer;
            }
        }
        return null;
    }
    public Event findEvent(int eventID){
        for (Event event : events){
            if (event.getEventId() == eventID){
                return event;
            }
        }
        return null;
    }
    public Booking findBooking(int bookingID) {
        for (Booking booking : bookings){
            if (booking.getBookingId() == bookingID){
                return booking;
            }
        }
        return null;
    }


    public void addEvent(Event event){
        if (findEvent(event.getEventId()) == null){
            System.out.println("This event is already exist.");
        }
            events.add(event);
    }
    public void addCustomer (Customer customer){
        if (customers.contains(customer)){
            System.out.println("This customer is already exist.");
        }
        else {customers.add(customer);
        }
    }

    public int activeTicketOfEvent(int eventID){
        int totalActiveTicket = 0;
        for (Booking booking : bookings){
            if(booking.getEvent().getEventId() == eventID && booking.getStatus() == BookingStatus.ACTIVE){
                totalActiveTicket += booking.getTicketQuantity();
            }
        }
        return totalActiveTicket;
    }

    public int getAvailableSeats(int eventID){
        Event event = findEvent(eventID);
        if (event == null){
            return 0;
        }
        return event.getCapacity() - activeTicketOfEvent(eventID);
    }

    public Booking bookTicket(String label, int customerID, int eventID, int quantity){
        Customer customer = findCustomer(customerID);
        Event event = findEvent(eventID);

        if (event == null){
            System.out.println(label + ": FAILED | Event Not Found"); return null;}
        if (customer == null){
            System.out.println(label + ": FAILED | Customer Not Found"); return null;}
        if (quantity <= 0){
            System.out.println(label + ": FAILED | Invalid Quantity"); return null;}
        if (quantity > getAvailableSeats(eventID)){
            System.out.println(label + ": FAILED | Insufficient Capacity"); return null;}

        double finalPaidAmount = calculateFinalPaidAmount(customer,event,quantity);
        if (customer.getBalance() < finalPaidAmount){
            System.out.println(label + ": FAILED | Insufficient Balance"); return null;}

        Booking booking = new Booking(nextBookingID,customer,event,quantity,finalPaidAmount);
        bookings.add(booking);
        customer.deductBalance(finalPaidAmount);
        nextBookingID++;

        System.out.println(label + ": SUCCESS | Paid: " + String.format("%.2f", finalPaidAmount));
        return booking;
    }


    private double calculateFinalPaidAmount(Customer customer, Event event, int quantity) {
        double price = event.getTicketPrice() * quantity;
        if (customer instanceof PremiumCustomer) {
            price *= 0.95;
        }
        return price;
    }

    public int getCancellationFeeRate() {
        return 5 + 5 * (groupNumber % 3);
    }

    public double calculateCancellationFee(Booking booking) {
        if (policyEnabled && policy == BookingPolicy.CANCELLATION_FEE){
            return booking.getFinalPaidAmount() * getCancellationFeeRate() / 100.0;
        }
        return 0;
    }

    public double calculateRefund(Booking booking){
        return booking.getFinalPaidAmount() - calculateCancellationFee(booking);
    }


    public void cancelBooking(String label, Booking booking){
        if (booking == null){
            System.out.println(label + ": FAILED | Booking Not Found"); return;}
        if (!booking.isActive()){
            System.out.println(label + ": FAILED | Already Cancelled"); return;}

        double refund = calculateRefund(booking);
        booking.cancel();
        booking.getCustomer().addBalance(refund);
        if (policyEnabled && policy == BookingPolicy.CANCELLATION_FEE) {
            System.out.println("Original Payment: " + String.format("%.2f", booking.getFinalPaidAmount()));
            System.out.println("Cancellation Fee: " + getCancellationFeeRate() + "% | "
                    + String.format("%.2f", calculateCancellationFee(booking)));
        }
        System.out.println(label + ": SUCCESS | Refund: " + String.format("%.2f", refund));
    }

    public void printSystemInformation() {
        System.out.println();
        System.out.println("========== SYSTEM INFORMATION ==========");
        System.out.println("Events: " + events.size());
        System.out.println("Customers: " + customers.size());
        System.out.println("Premium Customers: " + getPremiumCustomerCount());
    }

    public void printGroupRuleInfo() {
        System.out.println();
        System.out.println("========== GROUP-SPECIFIC RULE ==========");
        System.out.println("Group: " + groupNumber);
        System.out.println("Rule: " + policy);
        System.out.println("Parameter: " + getCancellationFeeRate() + "%");
    }
}



