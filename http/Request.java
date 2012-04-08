package http;

import java.util.*;
import java.io.*;

public class Request
{
	public String method;

	public String path;

	public Hashtable<String,String> headers;

	public BufferedReader data;

	public Request(String method, String path, BufferedReader data)
	{
		this.method = method;
		this.path = path;
		this.headers = new Hashtable<String,String>();

		this.data = data;
	}

	public String toString()
	{
		StringBuffer str = new StringBuffer();

		str.append(method + " " + path + "\n");

		for (Map.Entry<String,String> header : headers.entrySet())
			str.append(header.getKey() + ": " + header.getValue() + "\n");

		return str.toString();
	}

	static public Request parseHeader(BufferedReader in) throws HTTPException, IOException
	{
		String line = in.readLine();

		if (line == null)
			throw new HTTPException("Could not read data header data");

		String[] parts = line.split(" ");

		if (parts.length != 3)
			throw new HTTPException("Invalid HTTP header");

		Request request = new Request(parts[0], parts[1], in);

		while ((line = in.readLine()) != null)
		{
			// The empty line separating header and body
			if (line.isEmpty())
				break;

			String[] header = line.split(":", 2);
			request.headers.put(header[0], header[1]);
		}

		return request;
	}
}
