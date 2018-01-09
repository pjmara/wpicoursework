import java.util.ArrayList;
import java.util.Collection;

public class MovieNode extends IMDBNode implements Node {
	
	private Collection<ActorNode> actors;
	
	public MovieNode (String name) {
		super(name);
		actors = new ArrayList<ActorNode>();
	}
	
	/**
	 * add the ActorNode to actors
	 * @param actor the ActorNode to add
	 */
	public void addActor (ActorNode actor) {
		if (!actors.contains(actor)) actors.add(actor);
	}

	@Override
	public Collection<ActorNode> getNeighbors() {
		return actors;
	}

}
