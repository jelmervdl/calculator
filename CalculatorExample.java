import calc.*;

class CalculatorExample
{
	static public void main(String[] args)
	{
		try
		{
			Expression e = ExpressionParser.parse(args[0]);

			Calculator c = new CalculatorImpl();

			System.out.println(c.calculate(e));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}