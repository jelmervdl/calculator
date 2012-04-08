package calc;

import java.rmi.RemoteException;

public class CalculatorImpl implements Calculator
{
	public double calculate(Expression expression) throws RemoteException
	{
		return expression.evaluate();
	}
}

