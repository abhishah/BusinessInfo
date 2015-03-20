package in.co.info.business;

/**
 * Created by marauder on 3/17/15.
 */
public class PaymentClass {
    String payment_date;
    int payment_id,user_id;
    float payment_value;

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public float getPayment() {
        return payment_value;
    }

    public void setPayment(float payment) {
        this.payment_value = payment;
    }
}
