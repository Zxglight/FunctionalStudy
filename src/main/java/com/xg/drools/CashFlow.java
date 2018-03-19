package com.xg.drools;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

/**
 * @author xg.zhao   2018 03 18 15:44
 */
@Data
@ToString
public class CashFlow {
    private Date date;
    private double amount;
    private int type;
    private long accountNo;
}
