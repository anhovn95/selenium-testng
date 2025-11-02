package testNG;

import org.testng.annotations.Test;

public class Feature_Payment {
    @Test(groups = "CreditCard", priority = 1, description = "Payment via Credit card")
    public void Payment_CreditCard_Valid(){
        System.out.println("Group: Credit Card");
    }
    @Test(groups = "CreditCard", priority = 2, enabled = false)
    public void Payment_CreditCard_Invalid(){
        System.out.println("Group: Credit Card");
    }

    @Test(groups = "Paypal", priority = 1, description = "Payment via Paypal")
    public void Payment_Paypal_Valid(){
        System.out.println("Group: Paypal");
    }
    @Test(groups = "Paypal", priority = 2)
    public void Payment_Paypal_Invalid(){
        System.out.println("Group: Paypal");
    }
}
