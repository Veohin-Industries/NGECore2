import sys
from resources.objects.creature import CreatureObject
from resources.objects.player import PlayerObject
def setup():
    return
    
def run(core, actor, target, commandString):
    player = actor.getSlottedObject("ghost")
    player.setBiography(commandString)
    print ('Biography set to: ' + commandString)
    return