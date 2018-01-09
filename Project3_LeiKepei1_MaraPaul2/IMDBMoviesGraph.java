import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class contains all the MovieNodes in Collection and a HashMap (for convenience purpose)
 * @author Kepei Lei, PJ Mara
 */
public class IMDBMoviesGraph implements Graph {
	
	private Collection<MovieNode> movies;
	private HashMap<String, MovieNode> moviesMap;
	
	public IMDBMoviesGraph (String actorsFileName, String actressesFileName) throws IOException {
		// We are using the IMDBActorsGraph to reduce code lines
		IMDBActorsGraph actorsGraph = new IMDBActorsGraph(actorsFileName, actressesFileName);
		ArrayList<ActorNode> actors = (ArrayList<ActorNode>) actorsGraph.getNodes();
		movies = new ArrayList<MovieNode>();
		moviesMap = new HashMap<String, MovieNode>();
		for (int i = 0; i < actors.size(); i++) {
			ActorNode currentActor = actors.get(i);
			ArrayList<MovieNode> movies = (ArrayList<MovieNode>) currentActor.getNeighbors();
			for (int j = 0; j < movies.size(); j++) {
				MovieNode currentMovie = movies.get(j);
				if (!moviesMap.containsKey(currentMovie.getName())) {
					this.movies.add(currentMovie);
					moviesMap.put(currentMovie.getName(), currentMovie);
				}
			}
		}
	}

	@Override
	public Collection<MovieNode> getNodes() {
		return movies;
	}

	public Node getNodeByName(String name) {
		return moviesMap.get(name);
	}

}
