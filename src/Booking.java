public class Booking {

    private int bookingId;
    private Customer customer;
    private Event event;
    private int ticketQuantity;
    private double originalTotalPrice;
    private double finalPaidAmount;
    private BookingStatus status;

    public Booking(int bookingId, Customer customer, Event event, int ticketQuantity, double finalPaidAmount) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.event = event;
        this.ticketQuantity = ticketQuantity;

        this.originalTotalPrice = event.getTicketPrice() * ticketQuantity;
        this.finalPaidAmount = finalPaidAmount;
        this.status = BookingStatus.ACTIVE;
    }

    public int getBookingId() {
        return this.bookingId;
    }
    public Customer getCustomer() {
        return this.customer;
    }
    public Event getEvent() {
        return this.event;
    }
    public int getTicketQuantity() {
        return this.ticketQuantity;
    }
    public double getOriginalTotalPrice() {
        return this.originalTotalPrice;
    }
    public double getFinalPaidAmount() {
        return this.finalPaidAmount;
    }
    public BookingStatus getStatus() {
        return this.status;
    }
    public boolean isActive() {
        return this.status == BookingStatus.ACTIVE;
    }
    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
