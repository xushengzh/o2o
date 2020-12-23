package com.dist.o2o.exceptions;

public class ShopOperationException extends RuntimeException{

	/**
	 * 序列号ID
	 */
	private static final long serialVersionUID = 1L;
	
	public ShopOperationException(String msg){
		super(msg);
	}

}
