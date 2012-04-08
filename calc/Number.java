package calc;

class Number implements Expression
{
	private static final long serialVersionUID = 1L;
	
	double value;

	public Number(double value)
	{
		this.value = value;
	}

	public Number(String value)
	{
		this.value = Double.parseDouble(value);
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
