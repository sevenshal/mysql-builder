package com.sevenshal.sql.exception;

import com.sevenshal.sql.operator.Operator;

public class OperatorBuilderException extends RuntimeException{
	
	private static final long serialVersionUID = -1982176387629587475L;

	public OperatorBuilderException(Operator operator,int paramNum) {
		super("Operator:"+operator.name()+" need "+operator.getParamNum() + " params, but current params number is "+paramNum);
	}
	
}
