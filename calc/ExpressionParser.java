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
				// lhs is a complete expression. This will parse till ')' or the end.
				case '(':
					lhs = parseExpression();
					break;

				// it's a calculator, nobody is going to write something else than a number or an operator.
				default:
					lhs = new Number(left);

			}

			// e.g. "2 + 3", + token calls parseExpression(), tokenizer only contains "3"
			if (!tokenizer.hasMoreTokens())
				return lhs;

			String operator = tokenizer.nextToken();

			switch (operator.charAt(0))
			{
				// End of sub-clause
				case ')':
					return lhs;

				case '+':
					return new Addition(lhs, parseExpression());

				case '-':
					return new Subtraction(lhs, parseExpression());

				case '*':
					return new Multiplication(lhs, parseExpression());

				case '/':
					return new Division(lhs, parseExpression());

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