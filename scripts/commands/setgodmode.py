from engine.resources.scene import Point3D
from engine.resources.scene import Planet
from resources.objects.creature import CreatureObject

import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    if commandString.startswith('speed'):
        actor.setSpeedMultiplierBase(5)
        actor.setRunSpeed(5)
        actor.sendSystemMessage('FASTER!!!!!', 0)
    
    if commandString.startswith('speedr'):
        actor.setSpeedMultiplierBase(1)
        actor.setRunSpeed(1)
        actor.sendSystemMessage('Speed reset', 0)
        
    if commandString.startswith('currentPosition'):
        posX = actor.getPosition().x
        posY = actor.getPosition().y
        posZ = actor.getPosition().z
        print ('Your current position is ' + str(posX) + " " + str(posY) + " " + str(posZ))
        actor.sendSystemMessage('Your current position is ' + str(posX) + " " + str(posY) + " " + str(posZ), 0)
    
    if commandString.startswith('teleport'):   
        commandArgs = commandString.split(" ")        
        posX = float(commandArgs[1])
        posY = float(commandArgs[2])
        posZ = float(commandArgs[3])
        actor.setPosition(Point3D(posX, posY, posZ))
        actor.sendSystemMessage('Position set to ' + str(posX) + ' ' + str(posY) + ' ' + str(posZ), 0)        
        return
    
    if commandString.startswith('planet'):
        currentPlanet = actor.getPlanet()
        print currentPlanet
        actor.sendSystemMessage(currentPlanet, 0) 
        return  
    
    if commandString.startswith('credits'):
        commandArgs = commandString.split(" ")
        wantedAmount = int(commandArgs[1])
        currentCredits = actor.getCashCredits()
        newAmount = currentCredits + wantedAmount
        actor.setCashCredits(newAmount)
        actor.sendSystemMessage('Gave you ' + str(wantedAmount) + ' extra credits, scum', 0) 
    return