import java.util.ArrayList;
import java.util.Locale;

public class TicketBookingApp {
    public static void main(String[] args){
        final int GROUP_NUMBER = 14;
        Locale.setDefault(Locale.US);

        ArrayList<Event> events = FileIO.loadEvents();
        ArrayList<Customer> customers = FileIO.loadCustomers();
        BookingSystem phase1 = new BookingSystem(GROUP_NUMBER,events,customers,false);

        phase1.printSystemInformation();

        System.out.println("\n========== COMMON OPERATIONS ==========");
        Booking b1 = phase1.bookTicket("B1",201,101,1);
        phase1.bookTicket("B2",202,103,1);
        phase1.bookTicket("B3",205,104,6);
        phase1.bookTicket("B4",203,101,1);
        phase1.bookTicket("B5",201,102,0);
        phase1.bookTicket("B6",205,102,2);
        phase1.cancelBooking("C1",b1);
        phase1.cancelBooking("C2",b1);

        Query query = new Query(phase1);
        query.printQueries();


        ArrayList<Event> events2 = FileIO.loadEvents();
        ArrayList<Customer> customers2 = FileIO.loadCustomers();
        BookingSystem phase2 = new BookingSystem(GROUP_NUMBER,events2,customers2,true);

        phase2.printGroupRuleInfo();
        Booking r1 = phase2.bookTicket("R1", 201,102,2);
        phase2.cancelBooking("R2",r1);


    }


}
