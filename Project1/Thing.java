/*
 * Object is the base of the four main classes. It has the common variable name and image
 */

abstract class Thing {
	private String _name;
	private Image _image;
	
	public Thing (String name, Image image) {
		_name = name;
		_image = image;
	}
	
	@Override
	public boolean equals (Object o) {
		return _name.equals(((Thing)o).getName());
	}
	
	public String getName() {
		return _name;
	}
	
	public Image getImage() {
		return _image;
	}
}
