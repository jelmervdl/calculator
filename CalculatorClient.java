import calc.*;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.*;

class CalculatorClient
{
	private String host;

	private int port;

	public CalculatorClient()
	{
		// Host is null.
	}

	public CalculatorClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void run()
	{
		try
		{
			Calculator calculator = getCalculator();
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

	private Calculator getCalculator() throws RemoteException, NotBoundException
	{
		if (host == null)
			return new CalculatorImpl();

		Registry registry = LocateRegistry.getRegistry(host, port);
		return (Calculator) registry.lookup("calculator");
	}

	static public void main(String[] args)
	{
		CalculatorClient client = args.length >= 2
			? new CalculatorClient(args[0], Integer.parseInt(args[1]))
			: new CalculatorClient();

		client.run();
	}
}