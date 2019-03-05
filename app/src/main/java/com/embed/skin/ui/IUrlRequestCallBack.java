package com.embed.skin.ui;


import com.embed.skin.util.Task;

public interface IUrlRequestCallBack {
	  public void urlRequestStart(Task result);
	  public void urlRequestEnd(Task result);
	  public void urlRequestException(Task result);
}
