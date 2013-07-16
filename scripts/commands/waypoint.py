import sys
from resources.objects.waypoint import WaypointObject

def setup():
    return

def run(core, actor, target, commandString):
    #/waypoint tatooine x y z name
    #           0       1 2 3 4
    commandArgs = commandString.split(" ")
    waypoint = WaypointObject()
    
    actor.sendSystemMessage('New waypoint created at the location.', 0)
    return