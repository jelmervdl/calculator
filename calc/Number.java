package calc;

class Number implements Expression
{
	private static final long serialVersionUID = 1L;
	
	double value;

	public Number(double value)
	{
		this.value = value;
	}

	public Number(String value) throws ParseException
	{
		try
		{
			this.value = Double.parseDouble(value);
		}
		catch (NumberFormatException e)
		{
			throw new ParseException("Could not parse expression as number", e);
		}
	}

	public double evaluate()
	{
		return value;
	}

	public String toString()
	{
		return Double.toString(this.value);
	}
}
