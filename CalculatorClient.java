import calc.*;

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

			Expression expression = ExpressionParser.parse("2 + 3 + 7");

			System.out.println(calculator.calculate(expression));
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}