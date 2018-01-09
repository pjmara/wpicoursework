
public class LiteralExpression implements Expression {
	private String str;
	private CompoundExpression parent;

	public LiteralExpression (String str) {
		this.str = str;
	}
	
	public CompoundExpression getParent() {
		return parent;
	}

	public void setParent(CompoundExpression parent) {
		this.parent = parent;
	}

	public Expression deepCopy() {
		return new LiteralExpression(str);
	}

	public void flatten() {
	}

	public String convertToString(int indentLevel) {
		String conversion = str;
		for (int i = 0; i < indentLevel; i ++) {
			conversion = "\t" + conversion;
		}
		return conversion;
	}

	public String itself() {
		return str;
	}

}
