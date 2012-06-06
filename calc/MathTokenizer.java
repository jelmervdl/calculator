package calc;

import java.util.*;
import java.nio.CharBuffer;

public class MathTokenizer
{
	private String tokens;

	private int position;

	MathTokenizer(String expression)
	{
		tokens = expression.trim();
		position = 0;
	}

	public boolean hasMoreTokens()
	{
		return position < tokens.length();
	}

	public String nextToken() throws NoSuchElementException
	{
		if (!hasMoreTokens())
			throw new NoSuchElementException("No more tokens!");

		if (Character.isWhitespace(peak()))
		{
			skip();
			return nextToken();
		}

		else if (isNumericToken(peak()))
			return readNumber();
		
		else
			return Character.toString(read());
	}

	private boolean isNumericToken(char token)
	{
		return token == '.' || token == '-' || Character.isDigit(token);
	}

	private String readNumber()
	{
		String number = new String();

		// prefixed with minus?
		if (peak() == '-')
		{
			skip();
			number += "-";
		}

		// read the whole
		number += readDigits();
		
		// does it have a remainder? If so, read.
		if (hasMoreTokens() && peak() == '.')
		{
			skip();
			number += "." + readDigits();
		}

		return number;
	}

	private String readDigits()
	{
		String digits = new String();

		while (hasMoreTokens() && Character.isDigit(peak()))
			digits += read();

		return digits.toString();
	}

	private char read()
	{
		return tokens.charAt(position++);
	}

	private char peak()
	{
		return tokens.charAt(position);
	}

	private void skip()
	{
		++position;
	}
}