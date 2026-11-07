public class Customer {
    private final int customerId;
    private final String customerName;
    private double balance;

    public Customer(int customerId, String customerName, double balance) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.balance = balance;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deductBalance(double amount) {
        if(amount > this.balance || amount < 0){
            return false;
        }
        this.balance -= amount;
        return true;
    }

    public boolean addBalance(double amount) {
        if(amount < 0) {
            return false;
        }
        this.balance += amount;
        return true;
    }

    public double getDiscountRate() {
        return 0;
    }

    public MembershipType getMembershipType() {
        return MembershipType.REGULAR;
    }
}
