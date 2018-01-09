import java.util.ArrayList;

public class Moment extends Thing {
	
	//Instance Variables
	private ArrayList _participants;
	private ArrayList _smileValues;
	
	public Moment (String name, Image image, ArrayList participants, ArrayList smileValues) {
		super(name, image);
		_participants = participants;
		_smileValues = smileValues;
	}
	
	public void addParticipant (ThingWithFriends participant) {
		if (!_participants.contains(participant)) {
			_participants.add(participant);
		}
	}
	
	public ArrayList getParticipants () {
		return _participants;
	}
	
	public ArrayList getSmileValues () {
		return _smileValues;
	}

}
