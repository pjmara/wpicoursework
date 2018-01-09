In this project, all the classes belong to a mother abstract class Thing. Thing has instance variablees _name and _image.
They are needed for every object in this project. The class contains the shared methods equals, getName, and getImage.

Under Thing, there is another abstract class ThingWithFriends that has instance variables _friendList and _momentsIn. Objects
Person and Pet need them. ThingWithFriends contains several methods that Person and Pet need.
The methods include setFriends, getFriends, setMoments, getFriendWithWhomIAmHappiest, getOverallHappiestMoment, findMaximumCLiqueOfFriends,
and is Clique

Finally, Possession and Moment extend Thing, since they are reelatively unique. Possession and Pet could be grouped to an interface
but it is not worth it at this stage.


Here's a simple graph explaining the data structure
                     Thing
         ThingWithFriends       Posssession        Moment
   Person        Pet