package com.hz.booking.vo;

import java.util.Date;
import java.util.List;

public class BillListVo {
    private Date day;

    private List<BillVo> billList;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<BillVo> getBillList() {
        return billList;
    }

    public void setBillList(List<BillVo> billList) {
        this.billList = billList;
    }
}
