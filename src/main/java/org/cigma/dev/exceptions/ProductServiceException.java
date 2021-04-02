package org.cigma.dev.exceptions;

public class ProductServiceException extends RuntimeException {


	private static final long serialVersionUID = -7709126283041029710L;

	public ProductServiceException(String message)
	{
		super(message);
	}
}
