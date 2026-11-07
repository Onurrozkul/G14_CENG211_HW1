public class PremiumCustomer extends Customer{

    public PremiumCustomer(int customerId, String customerName, double balance) {
        super(customerId, customerName, balance);
    }

    @Override
    public double getDiscountRate() {
        return 0.05;
    }

    @Override
    public MembershipType getMembershipType() {
        return MembershipType.PREMIUM;
    }
}
