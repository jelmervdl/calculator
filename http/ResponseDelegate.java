package http;

import java.net.*;
import java.io.*;

class ResponseDelegate implements Runnable
{
	Socket connection;

	RequestHandler handler;

	public ResponseDelegate(Socket connection, RequestHandler handler)
	{
		this.connection = connection;
		this.handler = handler;
	}

	public void run()
	{
		try
		{
			BufferedReader in = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));

			OutputStream out = new BufferedOutputStream(connection.getOutputStream());

			Request request = Request.parseHeader(in);

			Response response = new Response(out);

			handler.respond(request, response);

			out.flush();
		}
		catch (Exception e)
		{
			System.out.println("Could not handle request");
			e.printStackTrace();
		}

		try
		{
			connection.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not close connection");
			e.printStackTrace();
		}
	}
}