package http;

import java.util.*;
import java.io.*;
import java.net.URLDecoder;

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

	public Hashtable<String,String> parseFormData() throws HTTPException, IOException
	{
		// Only accept simple formatted form data of which we know the length
		if (!headers.containsKey("Content-Type"))
			throw new HTTPException("Content-Type header missing");

		if (!headers.get("Content-Type").equals("application/x-www-form-urlencoded"))
			throw new HTTPException("Specified Content-Type not supported");

		if (!headers.containsKey("Content-Length"))
			throw new HTTPException("Content-Length header missing");
		
		Hashtable<String,String> values = new Hashtable<String,String>();
		int bytesToRead = Integer.parseInt(headers.get("Content-Length"));

		// Two buffers for the key and value of the pairs plus a little reading state.
		StringBuffer key = new StringBuffer();
		StringBuffer value = new StringBuffer();
		StringBuffer buffer = key;

		// Read only as many bytes as the Content-Length header specified. Block if necessary.
		while (bytesToRead-- > 0)
		{
			int c = data.read();

			// Look for special characters of meaning
			switch (c)
			{
				// Stream closed already? Bork!
				case -1:
					throw new IOException("Stream closed before Content-Length bytes were read");

				// Next key-value pair comming up! Commit the current (finished!) pair to the hashtable.
				case '&':
					values.put(
						URLDecoder.decode(key.toString(), "UTF-8"),
						URLDecoder.decode(value.toString(), "UTF-8"));
					key.setLength(0);
					value.setLength(0);
					buffer = key;
					break;

				// Reached the end of the key, start reading the value of the pair.
				case '=':
					buffer = value;
					break;

				// Just data, add it to the appropriate buffer.
				default:
					buffer.append((char) c);
					break;
			}
		}

		// If we reached the end, the buffers should still be filled with the final pair.
		// Add that pair to the hashtable as well.
		if (key.length() > 0)
			values.put(
				URLDecoder.decode(key.toString(), "UTF-8"),
				URLDecoder.decode(value.toString(), "UTF-8"));

		return values;
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
			request.headers.put(header[0], header[1].trim());
		}

		return request;
	}
}
