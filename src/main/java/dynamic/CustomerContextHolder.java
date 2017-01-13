package dynamic;

import org.springframework.util.Assert;

public class CustomerContextHolder {
    public enum CustomerType {
        BRONZE,
        SILVER,
        GOLD
    }


    //private static final ThreadLocal<CustomerType> contextHolder = new ThreadLocal<CustomerType>();
    private static CustomerType type = null;

    public static void setCustomerType(CustomerType customerType) {
        Assert.notNull(customerType, "customerType cannot be null");
        //contextHolder.set(customerType);
        type = customerType;
    }

    public static CustomerType getCustomerType() {
        return type;
        //return (CustomerType) contextHolder.get();
    }

    public static void clearCustomerType() {
        type = null;
        //contextHolder.remove();
    }

}