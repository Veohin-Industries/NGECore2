import sys
from protocol.swg.objectControllerObjects import BiographyUpdate
from org.apache.mina.core.session import IoSession
from protocol.swg import ObjControllerMessage
from org.apache.mina.core.buffer import IoBuffer
from java.nio import ByteOrder
def setup():
    return
    
def run(core, actor, target, commandString):
    
    #targetId = target.getObjectID()
    
    targetGhost = target.getSlottedObject("ghost")
    
    biographyUpdate = BiographyUpdate(targetGhost)
    
    objControllerMessage = ObjControllerMessage(0x0B, biographyUpdate)
    
    actor.getClient().getSession().write(objControllerMessage.serialize())
    
    return
    