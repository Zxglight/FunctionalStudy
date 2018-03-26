package com.xg.drools;

import java.text.DateFormat;
import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * @author xg.zhao   2018 03 18 15:44
 */
@Data
public class CashFlow {

    public static final int CREDIT = 1;
    public static int DEBIT = 2;


    private Date date;
    private double amount;
    private int type;
    private long accountNo;

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append("-----CashFlow-----)\n");
        buff.append("Account no=" + this.accountNo + "\n");
        if (this.date != null) {
            buff.append("Mouvement Date= "
                                + DateFormat.getDateInstance().format(this.date)
                                + "\n");
        } else {
            buff.append("No Mouvement date was set\n");
        }
        buff.append("Mouvement Amount=" + this.amount + "\n");
        buff.append("-----CashFlow end--)");
        return buff.toString();
    }
}
