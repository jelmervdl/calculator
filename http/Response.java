package http;

import java.util.*;
import java.io.*;

public class Response
{
	public Hashtable<String,String> headers;

	public OutputStream out;

	public Response(OutputStream out)
	{
		this.headers = new Hashtable<String,String>();
		this.out = out;
	}

	public void writeHeader(int code, String text) throws IOException
	{
		PrintStream pout = new PrintStream(out);

		pout.print("HTTP/1.0 " + code + " " + text + "\r\n");

		for (Map.Entry<String,String> header : headers.entrySet())
			pout.print(header.getKey() + ": " + header.getValue() + "\r\n");

		pout.print("\r\n");
	}
}