package calc;

import java.rmi.*;

public interface Calculator extends Remote
{
	public double calculate(Expression expression) throws RemoteException;
}
