import java.util.ArrayList;
import java.util.Collection;

public class ActorNode extends IMDBNode implements Node {
	
	private Collection<MovieNode> movies;
	
	public ActorNode (String name) {
		super(name);
		movies = new ArrayList<MovieNode>();
	}
	
	/**
	 * Set the movies as the Actor's neighbors
	 * @param movies the movies to pass in
	 */
	public void setMovies (Collection<MovieNode> movies) {
		this.movies.addAll(movies);
	}

	@Override
	public ArrayList<MovieNode> getNeighbors() {
		return (ArrayList<MovieNode>) movies;
	}

}
