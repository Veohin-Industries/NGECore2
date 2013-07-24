from resources.objects.creature import CreatureObject
from protocol.swg import UpdatePVPStatusMessage
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorFaction = actor.getFaction()
    actorStatus = actor.getFactionStatus()
    
    if commandString == ('imperial') and actorFaction != "imperial":  
        actor.setFaction('imperial')
        return
    
    if commandString == ('rebel') and actorFaction != "rebel":
        actor.setFaction('rebel')
        return
    
    if commandString == ('neutral') and actorFaction != "neutral":
        actor.setFaction('neutral')
        return
    
    if actorStatus == 0:
        actor.setFactionStatus(1)
        actor.sendSystemMessage('You are no longer On Leave.', 0)
        return
    # 54 showed SF players as red, but couldn't attack them as any faction
    
    # 52 shows SF players as red, can't attack
    
    # 20 SF opposing factions can't attack.
    
    
    if actorStatus != 0 and actorStatus == 1 and actorFaction == "imperial":
        # 4 (Overt) + 16 (Player) + 1 (Attackable) <<< DOESNT WORK
        # 4 + 16 + 1 + 2 = 23 << DOESNT WORK
        # 4 + 16 + 2 + 32 = 
        pvpStatus = UpdatePVPStatusMessage(actor.getObjectId(), 55, UpdatePVPStatusMessage.faction.Imperial)
        actor.setFactionStatus(2)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
        return
    
    if actorStatus != 0 and actorStatus == 1 and actorFaction == "rebel":
        # 4 (Overt) + 16 (Player) + 32 (Enemy) + 2 (Aggressive) + 1 (Attackable)  = 52 
        pvpStatus = UpdatePVPStatusMessage(actor.getObjectId(), 55, UpdatePVPStatusMessage.faction.Rebel)
        actor.setFactionStatus(2)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
        return
    
    if actorStatus == 2:
        # 16 (Player)
        pvpStatus = UpdatePVPStatusMessage(actor.getObjectId(), 16, 0)
        actor.setFactionStatus(1)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
        return   
    return
    