import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actor.setSpeedMultiplierBase(5)
    actor.setRunSpeed(5)
    actor.sendSystemMessage('FASTER!!!!!', 0)
    return