import java.util.Collection;

public class IMDBNode implements Node {
	private String name;

	public IMDBNode (String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Node n) {
		return (name.equals(n.getName()));
	}

	/**
	 * This will be overridden by the child classes
	 */
	public Collection<? extends Node> getNeighbors() {
		return null;
	}

}
