package calc;

import java.util.*;

public class ExpressionParser
{
	private StringTokenizer tokenizer;

	private ExpressionParser(String expression)
	{
		tokenizer = new StringTokenizer(expression);
	}

	private Expression parseExpression() throws ParseException
	{
		try
		{
			Expression left = new Number(tokenizer.nextToken());

			if (!tokenizer.hasMoreTokens())
				return left;

			String operator = tokenizer.nextToken();

			Expression right = parseExpression();

			if (operator.equals("+"))
				return new Addition(left, right);

			else if (operator.equals("*"))
				return new Multiplication(left, right);
			
			else
				throw new ParseException("Expected operator, found " + operator);
		}
		catch (NoSuchElementException e)
		{
			throw new ParseException("Could not parse expression because of the lack of tokens", e);
		}
	}

	static public Expression parse(String expression) throws ParseException
	{
		ExpressionParser parser = new ExpressionParser(expression);

		return parser.parseExpression();
	}
}