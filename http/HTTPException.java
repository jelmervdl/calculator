package http;

public class HTTPException extends Exception
{
	static final long serialVersionUID = 1L;
	
	public HTTPException(String error)
	{
		super(error);
	}
}
