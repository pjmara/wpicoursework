/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := E+M | M
 * M := M*X | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
	/**
	 * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * @param str the string to parse into an expression tree
	 * @param withJavaFXControls you can just ignore this variable for R1
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str, boolean withJavaFXControls) throws ExpressionParseException {
		// Remove spaces -- this simplifies the parsing logic
		str = str.replaceAll(" ", "");
		Expression expression = parseExpression(str);
		if (expression == null) {
			// If we couldn't parse the string, then raise an error
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		// Flatten the expression before returning
		expression.flatten();
		return expression;
	}

	/**
	 * Attempts to create an unflattened expression tree from the specified String.
	 * Returns null if the specified String is not legal
	 * @param str the string to parse into an expression tree
	 * @return the Expression object representing the parsed expression tree, null if the String cannot be parsed
	 */
	private Expression parseExpression (String str) {
		Expression expression;
		int idx = isE(str);

		if (idx > 0) { // The String is an (E+M)
			expression = new AdditiveExpression();
			Expression child1 = parseExpression(str.substring(0, idx));
			Expression child2 = parseExpression(str.substring(idx + 1));
			((AdditiveExpression) expression).addSubexpression(child1);
			((AdditiveExpression) expression).addSubexpression(child2);
			child1.setParent((CompoundExpression) expression);
			child2.setParent((CompoundExpression) expression);
		} else if (idx == 0) { // The String is an M
			idx = isM(str);
			if (idx > 0) { // The String is an M*M
				expression = new MultiplicativeExpression();
				Expression child1 = parseExpression(str.substring(0, idx));
				Expression child2 = parseExpression(str.substring(idx + 1));
				((MultiplicativeExpression) expression).addSubexpression(child1);
				((MultiplicativeExpression) expression).addSubexpression(child2);
				child1.setParent((CompoundExpression) expression);
				child2.setParent((CompoundExpression) expression);
			} else { // The String is an X
				idx = isX(str);
				if (idx > 0) { // The String is a (E)
					expression = new ParentheticalExpression();
					Expression child = parseExpression(str.substring(1, str.length() - 1));
					((ParentheticalExpression) expression).addSubexpression(child);
					child.setParent((CompoundExpression) expression);
				} else { // The String is an L
					expression = new LiteralExpression(str);
				}
			}
		} else { // The String does not fit in any of the rules, assign expression as null
			expression = null;
		}
		return expression;
	}

	/**
	 * Check if the parsed String is an E
	 * Returns the positive index of appropriate "+" if the String fits in the rule E+M
	 * Returns 0 if the String fits in the rule M
	 * Returns -1 if the String is not legal according to the rule (AKA not an E)
	 * @param str the string to parse into an expression tree
	 * @return the positive index of appropriate "+" if the String fits in the rule E+M, 0 if the String fits in the rule M, -1 if the String is not an E
	 */
	private int isE (String str) {

		// Check if the String is an E+M
		int idxOfPlus = str.indexOf("+");
		while (idxOfPlus >= 0) {
			if (isE(str.substring(0, idxOfPlus)) >= 0 && 
					isM(str.substring(idxOfPlus + 1)) >= 0) {
				return idxOfPlus;
			}
			idxOfPlus = str.indexOf("+", idxOfPlus + 1);
		}

		// Check if the String is an M
		if (isM(str) >= 0) {
			return 0;
		}

		return -1;
	}

	/**
	 * Check if the parsed String is an M
	 * Returns the positive index of appropriate "*" if the String fits in the rule E+M
	 * Returns 0 if the String fits in the rule X
	 * Returns -1 if the String is not legal according to the rule (AKA not an M)
	 * @param str the string to parse into an expression tree
	 * @return the positive index of appropriate "*" if the String fits in the rule M*M, 0 if the String fits in the rule X, -1 if the String is not an M
	 */
	private int isM (String str) {

		// Check if the String is an M*M
		int idxOfTimes = str.indexOf("*");
		while (idxOfTimes >= 0) {
			if (isM(str.substring(0, idxOfTimes)) >= 0 &&
					isX(str.substring(idxOfTimes + 1)) >= 0) {
				return idxOfTimes;
			}
			idxOfTimes = str.indexOf("*", idxOfTimes + 1);
		}

		// Check if the String if an X
		if (isX(str) >= 0) {
			return 0;
		}

		return -1;
	}

	/**
	 * Check if the parsed String is an X
	 * Returns 1 if the String is a (E)
	 * Returns 0 if the String fits in the rule L
	 * Returns -1 if the String is not legal according to the rule (AKA not an X)
	 * @param str the string to parse into an expression tree
	 * @return 1 if the String is a (E), 0 if the String is an L, -1 if the String is not an X
	 */
	private int isX (String str) {

		// Check if the String is empty already to avoid IndexOutOfBoundException
		if (str.length() < 1) return -1;

		// Check if the String is an L
		if (isL(str)) return 0;

		// Check if the String is a (E)
		if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')'
				&& isE(str.substring(1, str.length() - 1)) >= 0) {
			return 1;
		}

		return -1;
	}

	/**
	 * Check if the parsed String is an L
	 * Returns true if the String fits in the rule L
	 * else returns false
	 * @param str the string to parse into an expression tree
	 * @return true if the String fits in the rule L, else returns false
	 */
	private boolean isL (String str) {

		// Always false if it is an empty String
		if (str.length() < 1) return false;

		// When the length is 1, it has to be [a-z] | [0-9]
		else if (str.length() == 1) {
			char ch = str.charAt(0);
			if ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) return true;

			// When the length is greater than 1, it has to be a number
		} else {
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if (i == 0) { // The first digit has to be greater than 0
					if (ch <= '0' || ch > '9') return false;
				} else {
					if (ch < '0' || ch > '9') return false;
				}
			}
			return true;
		}
		return false;
	}

}
