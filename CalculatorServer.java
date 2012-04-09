import calc.*;

import http.*;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

class CalculatorServer extends UnicastRemoteObject implements Calculator, RequestHandler
{
	private static final long serialVersionUID = 1L;

	public CalculatorServer() throws RemoteException
	{
		super();
	}

	// Implements Calculator interface
	public double calculate(Expression e) throws RemoteException
	{
		return e.evaluate();
	}

	// Implements RequestHandler interface
	public void respond(Request request, Response response) throws Exception
	{
		response.headers.put("Content-Type", "text/html; charset=utf8");
		response.writeHeader(200, "OK");

		PrintStream pout = new PrintStream(response.out);
		
		// If the form was sent, process it
		if (request.method.equals("POST"))
		{
			try
			{
				Hashtable<String,String> formData = request.parseFormData();
				Expression expression = ExpressionParser.parse(formData.get("expression"));
				double result = calculate(expression);
				pout.println("<pre>" + expression + " = " + result + "</pre>");
			}
			catch (Exception e)
			{
				pout.println("<pre>");
				e.printStackTrace(pout);
				pout.println("</pre>");
			}
		}

		pout.println("<form method=\"post\">");
		pout.println("	<input type=\"text\" name=\"expression\">");
		pout.println("	<input type=\"submit\" value=\"calculate!\">");
		pout.println("</form>");
	}

	static public void main(String[] args)
	{
		// Create and install a security manager
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
	
		try
		{
			CalculatorServer calculator = new CalculatorServer();
	
			// Bind this object instance to the name "calculator"
			Registry registery = LocateRegistry.createRegistry(9999);
			registery.rebind("calculator", calculator);
			System.out.println("Calculator bound in registry");

			// Run the webservice in a separate thread.
			Webserver webserver = new Webserver(calculator, 8080);
			new Thread(webserver).start();
			System.out.println("Calculator webservice running on port 8080");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}