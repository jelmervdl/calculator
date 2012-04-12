import calc.*;
import http.*;
import util.*;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

import java.nio.charset.*;

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

		Template template = new Template(new File("template.html"), Charset.forName("UTF-8"));
		Hashtable<String,String> variables = new Hashtable<String,String>();

		// Default values
		variables.put("expression", "");
		variables.put("evaluated_expression", "");
		variables.put("result", "");
		variables.put("exception", "");

		// If the form was sent, process it
		if (request.method.equals("POST"))
		{
			try
			{
				Hashtable<String,String> formData = request.parseFormData();
				variables.put("expression", formData.get("expression"));

				response.writeHeader(200, "OK");
				
				Expression expression = ExpressionParser.parse(formData.get("expression"));
				variables.put("evaluated_expression", expression.toString());
				variables.put("result", Double.toString(calculate(expression)));
			}
			catch (ParseException e)
			{
				// Parse exceptions don't do much harm, it is just malformed user input.
				variables.put("exception", e.getMessage());	
			}
			catch (HTTPException e)
			{
				// Now there is something wrong with the client that encoded the users request.
				response.writeHeader(400, "Bad Request");
				variables.put("exception", e.getMessage());
			}
		}
		else
		{
			response.writeHeader(200, "OK");
		}

		PrintStream pout = new PrintStream(response.out);
		pout.print(template.render(variables));
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