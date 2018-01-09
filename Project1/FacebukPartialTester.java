import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Make absolute sure that your code can compile together with this tester!
 * If it does not, you may get a very low grade for your assignment.
 */
public class FacebukPartialTester {
	private Person _barack, _michelle, _kevin, _ina, _joe, _malia;
	private Pet _bo, _sunny;
	private Moment _meAndBarack;
	private ArrayList _michelleAndBarack, _michelleJoeBoAndMalia;

	@Before
	public void setUp () {
		initPeople();
		initPets();
		initGroups();
		initPossessions();
		initMoments();
	}

	private void initPeople () {
		_michelle = new Person("Michelle", new Image("Michelle.png"));
		_barack = new Person("Barack", new Image("Barack.png"));
		_kevin = new Person("Kevin", new Image("Kevin.png"));
		_ina = new Person("Ina", new Image("Ina.png"));
		_joe = new Person("Joe", new Image("Joe.png"));
		_malia = new Person("Malia", new Image("Malia.png"));
	}

	private void initPets () {
		_bo = new Pet("Bo", new Image("Bo.png"));
		_sunny = new Pet("Sunny", new Image("Sunny.png"));

		_bo.setOwner(_michelle);
		_sunny.setOwner(_michelle);
	}

	private void initGroups () {
		// Kevin, Barack, and Ina
		final ArrayList michelleFriends = new ArrayList();
		michelleFriends.add(_kevin);
		michelleFriends.add(_barack);
		michelleFriends.add(_ina);

		// Michelle and Barack
		_michelleAndBarack = new ArrayList();
		_michelleAndBarack.add(_michelle);
		_michelleAndBarack.add(_barack);

		// Michelle, Joe, Bo, and Malia
		_michelleJoeBoAndMalia = new ArrayList();
		_michelleJoeBoAndMalia.add(_michelle);
		_michelleJoeBoAndMalia.add(_joe);
		_michelleJoeBoAndMalia.add(_bo);
		_michelleJoeBoAndMalia.add(_malia);

		// Malia and Sunny
		final ArrayList maliaAndSunny = new ArrayList();
		maliaAndSunny.add(_malia);
		maliaAndSunny.add(_sunny);

		// Malia and Bo
		final ArrayList maliaAndBo = new ArrayList();
		maliaAndSunny.add(_malia);
		maliaAndSunny.add(_bo);

		// Michelle
		final ArrayList michelleList = new ArrayList();
		michelleList.add(_michelle);

		// Bo
		final ArrayList boList = new ArrayList();
		boList.add(_bo);

		// Set people's friend lists
		_michelle.setFriends(michelleFriends);
		_malia.setFriends(boList);
		_sunny.setFriends(boList);
		_barack.setFriends(michelleList);
		_kevin.setFriends(michelleList);
		_ina.setFriends(michelleList);
	
		// Finish configuring pets
		_bo.setFriends(maliaAndSunny);
		_sunny.setFriends(maliaAndBo);
		final ArrayList boAndSunny = new ArrayList();
		boAndSunny.add(_bo);
		boAndSunny.add(_sunny);
		_michelle.setPets(boAndSunny);
	}

	private void initPossessions () {
		final Possession boxingBag = new Possession("BoxingBag", new Image("BoxingBag.png"), 20.0f);
		boxingBag.setOwner(_michelle);
		final ArrayList michellePossessions = new ArrayList();
		michellePossessions.add(boxingBag);
		_michelle.setPossessions(michellePossessions);
	}

	private void initMoments () {
		// Smiles
		final ArrayList michelleAndBarackSmiles = new ArrayList();
		michelleAndBarackSmiles.add(0.25f);
		michelleAndBarackSmiles.add(0.75f);

		final ArrayList michelleJoeBoAndMaliaSmiles = new ArrayList();
		michelleJoeBoAndMaliaSmiles.add(0.2f);
		michelleJoeBoAndMaliaSmiles.add(0.3f);
		michelleJoeBoAndMaliaSmiles.add(0.4f);
		michelleJoeBoAndMaliaSmiles.add(0.5f);

		// Moments
		_meAndBarack = new Moment("Me & Barack", new Image("MeAndBarack.png"), _michelleAndBarack, michelleAndBarackSmiles);
		final Moment meJoeAndCo = new Moment("Me, Joe & co.", new Image("MeJoeAndCo.png"), _michelleJoeBoAndMalia, michelleJoeBoAndMaliaSmiles);

		final ArrayList michelleMoments = new ArrayList();
		michelleMoments.add(_meAndBarack);
		michelleMoments.add(meJoeAndCo);
		_michelle.setMoments(michelleMoments);

		final ArrayList barackMoments = new ArrayList();
		barackMoments.add(_meAndBarack);
		_barack.setMoments(barackMoments);

		final ArrayList joeMoments = new ArrayList();
		joeMoments.add(meJoeAndCo);
		_joe.setMoments(joeMoments);

		final ArrayList maliaMoments = new ArrayList();
		maliaMoments.add(meJoeAndCo);
		_malia.setMoments(maliaMoments);

		final ArrayList boMoments = new ArrayList();
		boMoments.add(meJoeAndCo);
		_bo.setMoments(boMoments);
	}

	@Test
	public void testEquals () {
		assertEquals(_michelle, new Person("Michelle", new Image("Michelle.png")));
		assertEquals(_michelle, new Person("Michelle", new Image("Michelle2.png")));  // should still work
		assertNotEquals(_michelle, _barack);
	}

	@Test
	public void testFindBestMoment () {
		assertEquals(_michelle.getOverallHappiestMoment(), _meAndBarack);
		System.out.println("CS210XGRDR +1 (R2 getOverallHappiestMoment A)");
	}

	@Test
	public void testGetFriendWithWhomIAmHappiest () {
		assertEquals(_michelle.getFriendWithWhomIAmHappiest(), _barack);
		System.out.println("CS210XGRDR +2 (R2 getFriendWithWhomIAmHappiest A)");
	}

	@Test
	public void testFacebukFromProjectDescription () {
		// People
		assertEquals(true, true);  // getting through the setUp without crashing is already worth something
		System.out.println("CS210XGRDR +13 (R1 basic functionality)");
	}


	@Test 
	public void testGetOverallHappiestMomentEmpty () {
		Person person1 = new Person("person1", new Image("person1.png"));
		person1.setMoments(new ArrayList());
		Moment happiestMoment = (Moment) person1.getOverallHappiestMoment();

		assertNull(happiestMoment);
		System.out.println("CS210XGRDR +1 (R2 getOverallHappiestMoment B)");
	}

	@Test 
	public void testGetOverallHappiestMomentSingleParticipant () {
		Person person1 = new Person("person1", new Image("person1.png"));

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);

		ArrayList smiles1 = new ArrayList();
		smiles1.add(4f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);

		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1.setMoments(person1Moments);

		assertEquals(moment1, person1.getOverallHappiestMoment());
		System.out.println("CS210XGRDR +1 (R2 getOverallHappiestMoment C)");
	}

	@Test
	public void testGetOverallHappiestMomentPerson () {
		Person person1 = new Person("person1", new Image("person1.png"));
		Person person2 = new Person("person2", new Image("person2.png"));
		Person person3 = new Person("person3", new Image("person3.png"));
		Person person4 = new Person("person4", new Image("person4.png"));
		Person person5 = new Person("person5", new Image("person5.png"));

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);

		ArrayList smiles1 = new ArrayList();
		smiles1.add(5f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);

		ArrayList actors2 = new ArrayList();
		actors2.add(person1);
		actors2.add(person2);
		actors2.add(person3);
		actors2.add(person4);
		actors2.add(person5);

		ArrayList smiles2 = new ArrayList();
		smiles2.add(10f);
		smiles2.add(1f);
		smiles2.add(1f);
		smiles2.add(1f);
		smiles2.add(1f);

		Moment moment2 = new Moment("moment2", new Image("moment2.png"), actors2, smiles2);

		ArrayList actors3 = new ArrayList();
		actors3.add(person1);
		actors3.add(person2);
		
		ArrayList smiles3 = new ArrayList();
		smiles3.add(5f);
		smiles3.add(6f);

		Moment moment3 = new Moment("moment3", new Image("moment3.png"), actors3, smiles3);
		
		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1Moments.add(moment2);
		person1Moments.add(moment3);
		person1.setMoments(person1Moments);

		Moment happiestMoment = (Moment) person1.getOverallHappiestMoment();
		assertEquals(moment3, happiestMoment);
		System.out.println("CS210XGRDR +1 ((R2 getOverallHappiestMoment D)");
	}

	@Test
	public void testGetOverallHappiestMomentPet () {
		Person person1 = new Person("person1", new Image("person1.png"));
		Pet pet1 = new Pet("person2", new Image("person2.png"));
		Pet pet2 = new Pet("person3", new Image("person3.png"));

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);

		ArrayList smiles1 = new ArrayList();
		smiles1.add(2f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);


		ArrayList actors2 = new ArrayList();
		actors2.add(person1);
		actors2.add(pet1);
		actors2.add(pet2);

		ArrayList smiles2 = new ArrayList();
		smiles2.add(1f);
		smiles2.add(3f);
		smiles2.add(3f);

		Moment moment2 = new Moment("moment2", new Image("moment2.png"), actors2, smiles2);

		ArrayList actors3 = new ArrayList();
		actors3.add(person1);
		actors3.add(pet1);
		
		ArrayList smiles3 = new ArrayList();
		smiles3.add(5f);
		smiles3.add(2f);

		Moment moment3 = new Moment("moment3", new Image("moment3.png"), actors3, smiles3);
		
		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1Moments.add(moment2);
		person1Moments.add(moment3);
		person1.setMoments(person1Moments);

		Moment happiestMoment = (Moment) person1.getOverallHappiestMoment();
		assertEquals(moment3, happiestMoment);
		System.out.println("CS210XGRDR +1 (R2 getOverallHappiestMoment E)");
	}


	/*@Test
	public void testGetFriendWithWhomIAmHappiestEmpty1() {
		Person person1 = new Person("person1", new Image("person1.png"));
		Object bestFriend = person1.getFriendWithWhomIAmHappiest();
		assertNull(bestFriend);
	}*/

	@Test
	public void testGetFriendWithWhomIAmHappiestEmpty2 () {
		Person person1 = new Person("person1", new Image("person1.png"));
		Person person2 = new Person("person2", new Image("person2.png"));

		ArrayList empty = new ArrayList();
		person1.setFriends(empty);

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);
		actors1.add(person2);
		
		ArrayList smiles1 = new ArrayList();
		smiles1.add(1f);
		smiles1.add(1f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);
		
		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1.setMoments(person1Moments);

		Object bestFriend = person1.getFriendWithWhomIAmHappiest();
		assertNull(bestFriend);
		System.out.println("CS210XGRDR +1 (R2 getFriendWithWhomIAmHappiest B)");
	}


	@Test
	public void testGetFriendWithWhomIAmHappiestPerson () {
		Person person1 = new Person("person1", new Image("person1.png"));
		Person person2 = new Person("person2", new Image("person2.png"));
		Person person3 = new Person("person3", new Image("person3.png"));

		ArrayList person1Friends = new ArrayList();
		person1Friends.add(person2);
		person1Friends.add(person3);
		person1.setFriends(person1Friends);

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);

		ArrayList smiles1 = new ArrayList();
		smiles1.add(3f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);


		ArrayList actors2 = new ArrayList();
		actors2.add(person1);
		actors2.add(person2);
		actors2.add(person3);

		ArrayList smiles2 = new ArrayList();
		smiles2.add(2f);
		smiles2.add(1f);
		smiles2.add(1f);

		Moment moment2 = new Moment("moment2", new Image("moment2.png"), actors2, smiles2);

		ArrayList actors3 = new ArrayList();
		actors3.add(person1);
		actors3.add(person2);
		
		ArrayList smiles3 = new ArrayList();
		smiles3.add(1f);
		smiles3.add(1f);

		Moment moment3 = new Moment("moment3", new Image("moment3.png"), actors3, smiles3);
		
		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1Moments.add(moment2);
		person1Moments.add(moment3);
		person1.setMoments(person1Moments);

		Object bestFriend = person1.getFriendWithWhomIAmHappiest();
		assertEquals(person3, bestFriend);
		System.out.println("CS210XGRDR +1 (R2 getFriendWithWhomIAmHappiest C)");
	}

	@Test
	public void testGetFriendWithWhomIAmHappiestPet () {
		Person person1 = new Person("person1", new Image("person1.png"));
		Pet pet1 = new Pet("pet1", new Image("pet1.png"));
		Pet pet2 = new Pet("pet2", new Image("pet2.png"));

		ArrayList person1Friends = new ArrayList();
		person1Friends.add(pet1);
		person1Friends.add(pet2);
		person1.setFriends(person1Friends);

		ArrayList actors1 = new ArrayList();
		actors1.add(person1);

		ArrayList smiles1 = new ArrayList();
		smiles1.add(3f);

		Moment moment1 = new Moment("moment1", new Image("moment1.png"), actors1, smiles1);


		ArrayList actors2 = new ArrayList();
		actors2.add(person1);
		actors2.add(pet1);
		actors2.add(pet2);

		ArrayList smiles2 = new ArrayList();
		smiles2.add(2f);
		smiles2.add(1f);
		smiles2.add(1f);

		Moment moment2 = new Moment("moment2", new Image("moment2.png"), actors2, smiles2);

		ArrayList actors3 = new ArrayList();
		actors3.add(person1);
		actors3.add(pet1);
		
		ArrayList smiles3 = new ArrayList();
		smiles3.add(1f);
		smiles3.add(1f);

		Moment moment3 = new Moment("moment3", new Image("moment3.png"), actors3, smiles3);
		
		ArrayList person1Moments = new ArrayList();
		person1Moments.add(moment1);
		person1Moments.add(moment2);
		person1Moments.add(moment3);
		person1.setMoments(person1Moments);

		Object bestFriend = person1.getFriendWithWhomIAmHappiest();
		assertEquals(pet2, bestFriend);
		System.out.println("CS210XGRDR +1 (R2 getFriendWithWhomIAmHappiest D)");
	}

	@Test(timeout=2000)
	public void testIsClique1 () {
		boolean[][] isFriends = new boolean[][] {
			{ false, true, true, true },
			{ true, false, true, true },
			{ true, true, false, true },
			{ true, true, true, false }
		};
		testIsClique(isFriends, true, 2, "TestIsClique1");
	}

	@Test(timeout=2000)
	public void testIsClique2 () {
		boolean[][] isFriends = new boolean[][] {
			{ true, false, true, true },
			{ false, true, true, true },
			{ true, true, true, true },
			{ true, true, true, true }
		};
		testIsClique(isFriends, false, 2, "TestIsClique2");
	}

	@Test(timeout=2000)
	public void testIsClique3 () {
		boolean[][] isFriends = new boolean[][] {
			{ true, true, true, true },
			{ true, true, true, true },
			{ true, true, true, true },
			{ true, true, true, true }
		};
		testIsClique(isFriends, true, 2, "TestIsClique3");
	}

	@Test(timeout=2000)
	public void testFindMaximumClique1 () {
		boolean[][] isFriends = new boolean[][] {
			{ true, true, true, true },
			{ true, true, true, true },
			{ true, true, true, true },
			{ true, true, true, true }
		};
		testFindMaximumClique(isFriends, new String[] { "1", "2", "3", "4" }, new String[] { }, 3, "FindMaximumCliqueTest1");
	}

	@Test(timeout=2000)
	public void testFindMaximumClique2 () {
		boolean[][] isFriends = new boolean[][] {
			{ true, false, false, false },
			{ false, true, true, true },
			{ false, true, true, true },
			{ false, true, true, true }
		};
		testFindMaximumClique(isFriends, new String[] { "2", "3", "4" }, new String[] { "1" }, 3, "FindMaximumCliqueTest2");
	}

	@Test(timeout=2000)
	public void testMaximumCliqueSize () {
		boolean[][] isFriends = new boolean[][] {
			{ true, true, false, true },
			{ true, true, true, false },
			{ false, true, true, true },
			{ true, false, true, true }
		};
		testMaximumCliqueSize(isFriends, 2, 1, "FindMaximumCliqueSize");
	}

	@Test(timeout=2000)
	public void testFindMaximumClique3 () {
		boolean[][] isFriends = new boolean[][] {
			{ true, false, false, false },
			{ false, true, false, false },
			{ false, false, true, true },
			{ false, false, true, true }
		};
		testFindMaximumClique(isFriends, new String[] { "3", "4" }, new String[] { "1", "2" }, 2, "FindMaximumCliqueTest3");
	}

	private Person makePersonWithFriends (boolean[][] isFriends) {
		Person person1 = new Person("1", new Image("1.png"));
		Person person2 = new Person("2", new Image("2.png"));
		Person person3 = new Person("3", new Image("3.png"));
		Person person4 = new Person("4", new Image("4.png"));
		Person[] people = new Person[] { person1, person2, person3, person4 };

		Person person = new Person("p", new Image("p.png"));
		ArrayList friends;
		friends = new ArrayList();
		friends.add(person1);
		friends.add(person2);
		friends.add(person3);
		friends.add(person4);
		person.setFriends(friends);

		for (int i = 0; i < friends.size(); i++) {
			ArrayList friendsOfPersonI = new ArrayList<Person>();
			friendsOfPersonI.add(person);
			for (int j = 0; j < friends.size(); j++) {
				if (isFriends[i][j]) {
					friendsOfPersonI.add(friends.get(j));
				}
			}
			((Person) friends.get(i)).setFriends(friendsOfPersonI);
		}
	
		return person;
	}

	private ArrayList findMaximumCliqueHelper (boolean[][] isFriends) {
		Person person = makePersonWithFriends(isFriends);
		ArrayList maximumClique = new ArrayList(person.findMaximumCliqueOfFriends());
		maximumClique.remove(person);
		return maximumClique;
	}

	private void testMaximumCliqueSize (boolean[][] isFriends, int desiredMaxCliqueSize, int numPoints, String testName) {
		ArrayList maximumClique = findMaximumCliqueHelper(isFriends);
		final int maxCliqueSize = maximumClique.size();
		assertEquals(desiredMaxCliqueSize, maxCliqueSize);
		System.out.println("CS210XGRDR +" + numPoints + " " + testName);
	}

	private void testIsClique (boolean[][] isFriends, boolean isClique, int numPoints, String testName) {
		Person person = makePersonWithFriends(isFriends);
		assertEquals(isClique, person.isClique(person.getFriends()));
		System.out.println("CS210XGRDR +" + numPoints + " " + testName);
	}

	private void testFindMaximumClique (boolean[][] isFriends, String[] namesInSolution, String[] namesNotInSolution, int numPoints, String testName) {
		ArrayList maximumClique = findMaximumCliqueHelper(isFriends);

		for (String name : namesInSolution) {
			assertTrue(maximumClique.contains(new Person(name, new Image(name + ".png"))));
		}
		for (String name : namesNotInSolution) {
			assertTrue(! maximumClique.contains(new Person(name, new Image(name + ".png"))));
		}
		System.out.println("CS210XGRDR +" + numPoints + " " + testName);
	}
}
