import java.util.ArrayList;

public class Pet extends ThingWithFriends {
	
	// Instance variables
	private Person _owner;
	
	public Pet (String name, Image image) {
		super (name, image);
		_owner = null;
	}
	
	public void setOwner (Person owner) {
		_owner = owner;
	}
	
	public Person getOwner () {
		return _owner;
	}
}
