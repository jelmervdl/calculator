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
			String left = tokenizer.nextToken();
			Expression lhs;

			switch (left.charAt(0))
			{
				case '(':
					lhs = parseExpression();
					break;

				case '-':
					lhs = new Multiplication(new Number(-1.0), parseExpression());
					break;

				default:
					lhs = new Number(left);

			}

			if (!tokenizer.hasMoreTokens())
				return lhs;

			String operator = tokenizer.nextToken();

			switch (operator.charAt(0))
			{
				case '+':
					return new Addition(lhs, parseExpression());

				case '-':
					return new Subtraction(lhs, parseExpression());

				case '*':
					return new Multiplication(lhs, parseExpression());

				case '/':
					return new Division(lhs, parseExpression());

				case ')':
					return lhs;

				default:
					throw new ParseException("Expected operator, found '" + operator + "'");
			}
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