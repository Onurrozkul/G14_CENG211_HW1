public class Event {

    private int eventId;
    private String eventName;
    private EventCategory category;
    private int capacity;
    private double ticketPrice;

    public Event(int eventId, String eventName, EventCategory category, int capacity, double ticketPrice) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.category = category;
        this.capacity = capacity;
        this.ticketPrice = ticketPrice;
    }
    public int getEventId() {
        return this.eventId;
    }
    public String getEventName() {
        return this.eventName;
    }
    public EventCategory getCategory() {
        return this.category;
    }
    public int getCapacity() {
        return this.capacity;
    }
    public double getTicketPrice() {
        return this.ticketPrice;
    }
}
