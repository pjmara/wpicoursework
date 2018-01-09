import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class contains all the ActorNodes in Collection and a HashMap (for convenience purpose)
 * @author Kepei Lei, PJ Mara
 */
public class IMDBActorsGraph implements Graph {

	private Collection<ActorNode> actors;
	private HashMap<String, ActorNode> actorsMap;

	public IMDBActorsGraph (String actorsFileName, String actressesFileName) throws IOException {
		Scanner actorScanner = new Scanner (new File (actorsFileName), "ISO-8859-1");
		Scanner actressesScanner = new Scanner (new File(actressesFileName), "ISO-8859-1");
		actors = new ArrayList<ActorNode>();
		actorsMap = new HashMap<String, ActorNode>();
		HashMap<String, MovieNode> moviesMap = scanActors(actorScanner, "THE ACTORS LIST", new HashMap<String,MovieNode>());
		scanActors(actressesScanner, "THE ACTRESSES LIST", moviesMap);
	}

	/**
	 * Scan the actors/actresses into the graph database
	 * @param scanner the scanner needed
	 * @param identifier the String where the method wants to start the parsing process
	 * @param existingMovies the existing movies that we don't want to recreate
	 * @return
	 */
	private HashMap<String,MovieNode> scanActors(Scanner scanner, String identifier, HashMap<String, MovieNode> existingMovies) {   //actors stand for both actors and actresses
		// Skip the line all the way until the scanner sees the String identifier
		String nextLine = scanner.nextLine();
		while (scanner.hasNextLine() && !nextLine.contains(identifier)) {
			nextLine = scanner.nextLine();
		}
		for (int i = 0; i < 4; i ++) {
			nextLine = scanner.nextLine();
		}
		// Creating an actor with the movies he/she is in
		while (scanner.hasNextLine()) {
			final ArrayList<MovieNode> movies = new ArrayList<MovieNode>();
			nextLine = scanner.nextLine();
			if (nextLine.equals("")) nextLine = scanner.nextLine();
			if (nextLine.contains("------------------------------------------------------")|| nextLine.indexOf("\t") == -1) break; // Break at the end of the actor list
			String actorName = nextLine.substring(0, nextLine.indexOf("\t"));
//			System.out.println(actorName);
			ActorNode actor = new ActorNode(actorName);
			while (true) {
				String movieName = nextLine.substring((nextLine.lastIndexOf("\t") + 1));
				if (!movieName.contains("(TV)") && !movieName.contains("\"")) { // Make sure it is not a TV or TV movie
					movieName = movieName.substring(0, (movieName.indexOf(")") +1));
					// If the movie exists, we add the actor to the movie's, and add movie to the ArrayList movies
					// If the movie does not exist yet, we create a new one and do the same thing
					if (existingMovies.containsKey(movieName)) {
						MovieNode movie = existingMovies.get(movieName);
						movie.addActor(actor);
						movies.add(movie);
					} else {
						MovieNode movie = new MovieNode(movieName);
						movie.addActor(actor);
						existingMovies.put(movieName, movie);
						movies.add(movie);
					}
				}
				if (scanner.hasNextLine()) nextLine = scanner.nextLine(); // Get ready for the next cycle
				if (nextLine.equals("") || !scanner.hasNextLine()) break;
			}
			if (!movies.isEmpty()) {
				actor.setMovies(movies);
				actors.add(actor);
				actorsMap.put(actorName, actor);
			}
			
		}
		return existingMovies;
	}


	@Override
	public Collection<ActorNode> getNodes() {
		return actors;
	}

	public Node getNodeByName(String name) {
		return actorsMap.get(name);
	}

}
