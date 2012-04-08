import http.*;

import java.io.*;

class WebserverExample implements RequestHandler
{
	public void respond(Request request, Response response) throws Exception
	{
		response.headers.put("Content-Type", "text/html; charset=utf8");
		response.writeHeader(200, "OK");

		// For fun, lets just respond with the request >:)
		PrintStream pout = new PrintStream(response.out);
		pout.println("<h1>Did you just say…</h1>");
		pout.println("<pre>");
		pout.print(request);
		pout.println("</pre>");
	}

	static public void main(String[] args)
	{
		try
		{
			Webserver server = new Webserver(new WebserverExample());
			server.run();
		}
		catch (IOException e)
		{
			System.err.println("Could not start webserver");
			e.printStackTrace();
		}
	}
}