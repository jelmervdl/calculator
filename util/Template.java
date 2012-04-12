package util;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Template
{
	private String template;

	public Template(File file, Charset charset) throws IOException
	{
		template = readFile(file, charset);
	}

	public String render(Map<String,String> variables)
	{
		String output = template;

		/**
		 * {{key}} -> "value" | ""
		 * {x{key}y} -> "xvaluey" | ""
		 */
		for (Map.Entry<String,String> pair : variables.entrySet())
		{
			String pattern = "\\{(.*?)\\{(" + Pattern.quote(pair.getKey()) + ")\\}(.*?)\\}";

			String replacement = pair.getValue().isEmpty()
				? ""
				: "$1" + Matcher.quoteReplacement(pair.getValue()) + "$3";

			output = output.replaceAll(pattern, replacement);
		}

		return output;
	}

	private String readFile(File file, Charset charset) throws IOException
	{
		FileInputStream in = new FileInputStream(file);

		try
		{
			FileChannel channel = in.getChannel();
			MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

			return charset.decode(buffer).toString();
		}
		finally
		{
			in.close();
		}
	}
}