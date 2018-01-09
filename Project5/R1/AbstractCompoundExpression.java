import java.util.LinkedList;
import java.util.List;

public abstract class AbstractCompoundExpression implements CompoundExpression {
	
	protected String str;
	protected List<Expression> children;
	private CompoundExpression parent;
	
	public AbstractCompoundExpression() {
		children = new LinkedList<Expression>();
	}

	public CompoundExpression getParent() {
		return parent;
	}

	public void setParent(CompoundExpression parent) {
		this.parent = parent;
	}

	public Expression deepCopy() {
		final AbstractCompoundExpression copy;
		if (str.equals("*")) {
			copy = new MultiplicativeExpression();
		} else if (str.equals("+")) {
			copy = new AdditiveExpression();
		} else {
			copy = new ParentheticalExpression();
		}
		for (Expression child : children) {
			copy.addSubexpression(child.deepCopy());
		}
		return copy;
	}

	public String itself() {
		return str;
	}
	
	public void flatten() {
		final List<Expression> newChildren = new LinkedList<Expression>();
		int idx = 0;
		for (Expression child: children) {
			child.flatten();
			if (str.equals(child.itself())) {
				newChildren.addAll(((AbstractCompoundExpression) child).getChildren());
				for (Expression grandChild: ((AbstractCompoundExpression) child).getChildren()) {
					grandChild.setParent(this);
				}
				idx = children.indexOf(child);
				children.remove(child);
			}
		}
		children.addAll(idx, newChildren);
		
	}
	
	/**
	 * Return all the subexpressions in a List
	 * @return List of subexpressions
	 */
	private List<Expression> getChildren() {
		return children;
	}
	
	public String convertToString(int indentLevel) {
		String conversion = str;
		for (int i = 0; i < indentLevel; i++) {
			conversion = "\t" + conversion;
		}
		for (Expression child: children) {
			conversion = conversion + "\n" + child.convertToString(indentLevel + 1);
		}
		if (parent == null) {
			conversion += "\n";
		}
		return conversion;
	}

	public void addSubexpression(Expression subexpression) {
		children.add(subexpression);
	}

}
