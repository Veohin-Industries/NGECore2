from services.trade import TradeService
from protocol.swg import ObjControllerMessage
from protocol.swg.objectControllerObjects import SecureTrade

import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    
    tradeSrvc = TradeService(core)
    
    tradeSrvc.setTradeActor(actor)
    tradeSrvc.setTradeTarget(target)
    
    objController = ObjControllerMessage(0x0B, tradeSrvc);

    targetClient = target.getClient()
    
    targetClient.getSession().write(objController.serialize());
    
    actor.sendSystemMessage('You sent a trade request to ' + target.getCustomName(), 0)
    
    return