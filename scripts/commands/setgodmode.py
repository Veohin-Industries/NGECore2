from engine.resources.scene import Point3D
from engine.resources.scene import Planet
from resources.objects.creature import CreatureObject
from resources.objects.player import PlayerObject
from services.chat import ChatService
from services.chat import Mail
from protocol.swg import PlayMusicMessage
from java.util import Date
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    if commandString.startswith('speed'):
        commandArgs = commandString.split(" ")
        runSpeed = commandArgs[1]
        speedMultiplier = commandArgs[2]
        actor.setSpeedMultiplierBase(int(speedMultiplier))
        actor.setRunSpeed(int(runSpeed))
        actor.sendSystemMessage('Speed set to ' + str(runSpeed), 0)
    
    if commandString == ('resetSpeed'):
        actor.setSpeedMultiplierBase(1)
        actor.setRunSpeed(float(7.3))
        actor.sendSystemMessage('Speed reset', 0)
        
    if commandString == ('currentPosition'):
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
        point3d = Point3D(posX, posY, posZ)
        actor.teleport(actor, point3d, actor.getOrientation())
        actor.sendSystemMessage('Position set to ' + str(posX) + ' ' + str(posY) + ' ' + str(posZ), 0)        
        return
    
    if commandString == ('planet'):
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
    
    if commandString.startswith('bankcredits'):
        commandArgs = commandString.split(" ")
        wantedAmount = int(commandArgs[1])
        currentCredits = actor.getBankCredits()
        newAmount = currentCredits + wantedAmount
        actor.setBankCredits(newAmount)
        actor.sendSystemMessage('Gave you ' + str(wantedAmount) + ' extra credits, scum', 0)
        
    if commandString == ('faction'):
        actor.sendSystemMessage('Removed you from the ' + actor.getFaction(), 0)
        actor.setFaction('imperial')
        actor.setFactionStatus(1)
        actor.sendSystemMessage('Welcome to the Empire.', 0)
        
    if commandString.startswith('mail'):
        
        testMail = Mail()
        chatSrvc = ChatService(core)
        date = Date()
        testMail.setRecieverId(actor.getObjectID())
        testMail.setMessage("This mail was sent through a script")
        testMail.setSenderName("System")
        testMail.setSubject("Mail Script Test")
        testMail.setStatus(Mail.NEW)
        testMail.setTimeStamp(int(date.getTime() / 1000))   
        testMail.setMailId(chatSrvc.generateMailId())
        print ('Mail: ' + str(testMail.getMailId()) + ' Msg: ' + testMail.getMessage() + ' Reciever: ' + str(testMail.getRecieverId()))
        print ('Sender: ' + testMail.getSenderName() + ' Status: ' + str(testMail.getStatus()) + ' Time: '+ str(testMail.getTimeStamp()))
        chatSrvc.sendPersistentMessage(actor.getClient(), testMail)
        print (' Sent mail.')

    if commandString == ('biography'):
        ghost = actor.getSlottedObject("ghost")
        bio = ghost.getBiography()
        print ('Biography: ' + str(bio))
        return
    
    if commandString == ('soundplz'):
        playMusic = PlayMusicMessage('sound/music_combat_bfield_vict.snd')
        actor.getClient().getSession().write(playMusic.serialize())
        print ('Sent sound!')
        return
    
    if commandString == ('myID'):
        actor.sendSystemMessage('ID is: ' + str(actor.getObjectID()), 0)
        print ('objectID: ' + str(actor.getObjectID))
        return
    
    if commandString.startswith('itemID'):
        commandArgs = commandString.split('itemID')
        print ('commandArgs = ' + str(commandArgs))
        
        itemName = commandArgs
        itemID = core.objectService.getObjectByCustomName(str(commandArgs)).getObjectID()
        
        print (str(commandArgs) + ' ID is: ' + str(itemID))
        return
    return