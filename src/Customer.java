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

    public void deductBalance(double amount) {
        if(amount > this.balance || amount < 0){
            return;
        }
        this.balance -= amount;
    }

    public void addBalance(double amount) {
        if(amount < 0) {
            return;
        }
        this.balance += amount;
    }

    public double getDiscountRate() {
        return 0;
    }

    public MembershipType getMembershipType() {
        return MembershipType.REGULAR;
    }
}
