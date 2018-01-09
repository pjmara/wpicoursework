
public class Possession extends Thing {
	private Person _owner;
	private float _price;
	
	public Possession(String name, Image image, float price) {
		super (name, image);
		_price = price;
		_owner = null;
	}
	
	public void setOwner (Person owner) {
		_owner = owner;
	}
	
	public Person getOwner() {
		return _owner;
	}
	
	public float getPrice() {
		return _price;
	}

}
