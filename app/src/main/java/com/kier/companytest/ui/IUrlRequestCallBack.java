package com.kier.companytest.ui;


import com.kier.companytest.util.Task;

public interface IUrlRequestCallBack {
	  public void urlRequestStart(Task result);
	  public void urlRequestEnd(Task result);
	  public void urlRequestException(Task result);
}
