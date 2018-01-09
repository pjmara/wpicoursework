
public class ParentheticalExpression extends AbstractCompoundExpression {

	public ParentheticalExpression () {
		super();
		str = "()";
	}
	
	@Override
	public void flatten() {
		Expression child = children.get(0);
		child.flatten();
	}
}
