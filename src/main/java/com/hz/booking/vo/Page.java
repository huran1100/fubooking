package com.hz.booking.vo;

public class Page {

  // 当前页号
  private int currPageNo = 1;

  // 页面大小，即每页显示记录数
  private int pageSize = 10;

  private String orderBy = "id desc";

  public int getCurrPageNo() {
    return currPageNo;
  }

  public void setCurrPageNo(int currPageNo) {
    this.currPageNo = currPageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }
}
