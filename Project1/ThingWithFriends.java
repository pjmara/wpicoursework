import java.util.ArrayList;

abstract class ThingWithFriends extends Thing{
	private ArrayList _friendList;
	private ArrayList _momentsIn;

	public ThingWithFriends (String name, Image image) {
		super (name, image);
		_friendList = new ArrayList();
		_momentsIn = new ArrayList();
	}

	public void setFriends(ArrayList friends) {
		for (int i = 0; i < friends.size(); i++) {
			final ThingWithFriends currentFriend = (ThingWithFriends) friends.get(i);
			if (!_friendList.contains(currentFriend) && !this.equals(currentFriend)) {
				_friendList.add(currentFriend);
				ArrayList thisPerson = new ArrayList();
				thisPerson.add(this);
				currentFriend.setFriends(thisPerson);
			}
		}
	}

	public ArrayList getFriends () {
		return _friendList;
	}

	public void setMoments (ArrayList moments) {
		for (int i = 0; i < moments.size(); i ++) {
			Moment currentMoment = (Moment) moments.get(i);
			if (!currentMoment.getParticipants().contains(this)) {
				currentMoment.addParticipant(this);
			}
			_momentsIn.add(currentMoment);
		}
	}

	public ThingWithFriends getFriendWithWhomIAmHappiest () {
		ArrayList participants = new ArrayList();
		ArrayList smileValues = new ArrayList();
		for (int i = 0; i < _momentsIn.size(); i++) {
			Moment currentMoment = (Moment) _momentsIn.get(i);
			for (int j = 0; j < currentMoment.getParticipants().size(); j++) {
				ThingWithFriends currentParticipant = (ThingWithFriends) currentMoment.getParticipants().get(j);
				if (!this.equals(currentParticipant) && !participants.contains(currentParticipant)){
					participants.add(currentParticipant);
					smileValues.add(currentMoment.getSmileValues().get(j));
				}
			}
		}
		if (participants.isEmpty()) return null;
		final boolean[] isUsed = new boolean[participants.size()];
		ThingWithFriends currentHappiestFriend = (ThingWithFriends) participants.get(0);
		float currentHappiestValue = calculateAverageValue(currentHappiestFriend, participants, smileValues);
		isUsed[0] = true;
		for (int k = 0; k < participants.size(); k++) {
			final ThingWithFriends currentComparison = (ThingWithFriends) participants.get(k);
			if (!currentHappiestFriend.equals(currentComparison) && !isUsed[k]) {
				isUsed[k] = true;
				float comparisonValue = calculateAverageValue(currentComparison, participants, smileValues);
				currentHappiestFriend = compareFriend(currentHappiestFriend, currentComparison, currentHappiestValue, comparisonValue);
				currentHappiestValue = calculateAverageValue(currentHappiestFriend, participants, smileValues);
			}
		}
		return currentHappiestFriend;
	}

	private static ThingWithFriends compareFriend (ThingWithFriends currentHappiestFriend, ThingWithFriends currentComparison, 
			float currentHappiestValue, float comparisonValue) {
		if (currentHappiestValue > comparisonValue) {
			return currentHappiestFriend;
		} else {
			return currentComparison;
		}
	}

	private static float calculateAverageValue (ThingWithFriends personCalculated, ArrayList participants, ArrayList smileValues) {
		int count = 0;
		float total = 0;
		for (int i = 0; i < participants.size(); i++) {
			if (personCalculated.equals(participants.get(i))) {
				count++;
				total += (float) smileValues.get(i);
			}
		}
		return total / count;
	}

	public Moment getOverallHappiestMoment() {
		if (_momentsIn.isEmpty()) return null;
		Moment currentHappiestMoment = (Moment) _momentsIn.get(0);
		float currentHappiestValue = getValueForMoment(currentHappiestMoment);
		for (int i = 1; i < _momentsIn.size(); i++) {
			final Moment comparisonMoment = (Moment) _momentsIn.get(i);
			final float comparisonValue = getValueForMoment(comparisonMoment);
			if (comparisonValue > currentHappiestValue) {
				currentHappiestMoment = comparisonMoment;
				currentHappiestValue = comparisonValue;
			}
		}
		return currentHappiestMoment;
	}

	private float getValueForMoment (Moment moment) {
		final ArrayList smileValues = moment.getSmileValues();
		float total = 0;
		for (int i = 0; i < smileValues.size(); i++) {
			total += (float) smileValues.get(i);
			}
		return total / smileValues.size();
	}

	public ArrayList findMaximumCliqueOfFriends () {
		if (_friendList.isEmpty()) return null;
		ArrayList maxClique = new ArrayList();
		maxClique.add(_friendList.get(0));
		int maxCount = maxClique.size();
		for (int i = 0; i < _friendList.size(); i ++) {
			ArrayList checkList = _friendList;
			for (int j = 0; j < i; j ++) {
				checkList.remove(0);
			}
			ArrayList currentClique = new ArrayList();
			for (int k = 0; k < checkList.size(); k++) {
				currentClique.add(checkList.get(k));
				if (isClique(currentClique)) {
					final int currentCount = currentClique.size();
					if (currentCount > maxCount) {
						maxClique = currentClique;
						maxCount = currentCount;
					}
				}
			}
		}
		return maxClique;
	}

	public static boolean isClique (ArrayList set) {
		for (int i = 0; i < set.size(); i++) 
			for (int j = 0; j < set.size(); j++) {
				if (j != i) {
					ThingWithFriends currentThing = (ThingWithFriends) set.get(i);
					if (!currentThing.getFriends().contains(set.get(j))) return false;
				}
			}
		return true;
	}

}
