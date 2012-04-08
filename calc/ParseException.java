package calc;

public class ParseException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public ParseException(String error)
	{
		super(error);
	}

	public ParseException(String error, Throwable cause)
	{
		super(error, cause);
	}
}