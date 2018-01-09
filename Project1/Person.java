import java.util.ArrayList;

/*
 * A person has a String name, an Image image,
 * ArrayList petList, possesionList, friendList, and momentList
 */

public class Person extends ThingWithFriends {
	private ArrayList _petList, _possessionList;
	
	public Person (String name, Image image) {
		super (name, image);
		_petList = new ArrayList();
		_possessionList = new ArrayList();
	}	
	
	public void setPossessions (ArrayList possessions) {
		for (int i = 0; i < possessions.size(); i++) {
			_possessionList.add(possessions.get(i));
		}
	}
	
	public ArrayList getPossessions () {
		return _possessionList;
	}
	
	public void setPets (ArrayList pets) {
		for (int i = 0; i < pets.size(); i++) {
			_petList.add(pets.get(i));
		}
	}
	
	public ArrayList getPets () {
		return _petList;
	}
}
