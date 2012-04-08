import calc.*;

import http.*;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

class CalculatorServer extends UnicastRemoteObject implements Calculator
{
	private static final long serialVersionUID = 1L;

	private Expression lastExpression;

	public CalculatorServer() throws RemoteException
	{
		super();
	}

	public double calculate(Expression e) throws RemoteException
	{
		lastExpression = e;
		return e.evaluate();
	}

	public Expression getLastExpression()
	{
		return lastExpression;
	}

	static public void main(String[] args)
	{
		// Create and install a security manager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
	
		try
		{
			final CalculatorServer calculator = new CalculatorServer();
	
			// Bind this object instance to the name "calculator"
			Registry registery = LocateRegistry.createRegistry(9999);
			registery.rebind("calculator", calculator);
	
			System.out.println("Calculator bound in registry");

			Webserver webserver = new Webserver(new RequestHandler() {
				public void respond(Request request, Response response) throws Exception {
					response.headers.put("Content-Type", "text/html; charset=utf8");

					if (calculator.getLastExpression() != null)
					{
						response.writeHeader(200, "OK");

						PrintStream pout = new PrintStream(response.out);
						pout.println("<pre>" + calculator.getLastExpression() + "</pre>");
					}
					else
						response.writeHeader(404, "Not found");
				}
			}, 8080);

			// Run the webserver in a separate thread.
			new Thread(webserver).start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}