import http.*;

import java.io.*;
import java.util.*;

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

		if (request.method.equals("POST"))
		{
			Hashtable<String,String> formData = request.parseFormData();

			pout.println("<h1>You've also submitted some data!</h1>");
			pout.println("<table border=\"1\"><tr><th>key</th><th>value</th></tr>");

			for (Map.Entry<String,String> pair : formData.entrySet())
				pout.println("<tr><td>" + pair.getKey() + "</td><td>" + pair.getValue() + "</td></tr>");

			pout.println("</table>");
		}

		pout.println("<form method=\"post\"><input type=\"text\" name=\"test\"><input type=\"submit\" name=\"action\" value=\"search\"></form>");
	}

	static public void main(String[] args)
	{
		try
		{
			Webserver server = new Webserver(new WebserverExample(), 8080);
			server.run();
		}
		catch (IOException e)
		{
			System.err.println("Could not start webserver");
			e.printStackTrace();
		}
	}
}