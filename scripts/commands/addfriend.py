from resources.objects.player import PlayerObject
from engine.resources.database import DatabaseConnection
from engine.resources.objects import SWGObject
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    #/addfriend waverunner << returns as a commandString
    
    playerFriends = actor.getSlottedObject('ghost').getFriendList()
    
    if playerFriends is not None:
        playerFriends.add(commandString)
        actor.sendSystemMessage('You added ' + commandString + ' to your friends list.', 0)
        print ('FriendList: ' + str(playerFriends))
        return    
    return
    