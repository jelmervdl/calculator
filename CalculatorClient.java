import calc.*;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

class CalculatorClient
{
	static public void main(String[] args)
	{
		try
		{
			Registry registry = LocateRegistry.getRegistry("localhost", 9999);

			Calculator calculator = (Calculator) registry.lookup("calculator");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			String line;
			while ((line = in.readLine()) != null)
			{
				// Empty line = stop
				if (line.isEmpty())
					break;

				try
				{
					Expression expression = ExpressionParser.parse(line);

					System.out.println(calculator.calculate(expression));
				} 
				catch (ParseException e)
				{
					System.out.println("Could not parse expression: " + e.getMessage());
				}
			}

			in.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}