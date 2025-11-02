package testNG;

import org.testng.annotations.Test;

public class Feature_Payment_02 {
    @Test(groups = "CreditCard")
    public void Payment_CreditCard_Valid(){
        System.out.println("Group: Credit Card -- File 02");
    }
    @Test(groups = "CreditCard")
    public void Payment_CreditCard_Invalid(){
        System.out.println("Group: Credit Card -- File 02");
    }

    @Test(groups = "Paypal")
    public void Payment_Paypal_Valid(){
        System.out.println("Group: Paypal -- File 02");
    }
    @Test(groups = "Paypal")
    public void Payment_Paypal_Invalid(){
        System.out.println("Group: Paypal -- File 02");
    }
}
