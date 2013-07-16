from resources.objects.creature import CreatureObject
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
        
    commandArgs = commandString.split(" ")
    commandLength = len(commandArgs)
    
    #/tip 300000 && /tip Waverunner 300000
    if commandLength == 1:
        
        tipAmount = commandArgs[0]
        actorFunds = actor.getCashCredits()
        currentTarget = core.objectService.getObject(target.getObjectId())
        
        if int(tipAmount) > 0 and int(tipAmount) <= 1000000:
            if actorFunds >= tipAmount:
                
                currentTarget.setCashCredits(int(tipAmount))       
                actor.setCashCredits(actorFunds - int(tipAmount))
        
                currentTarget.sendSystemMessage(actor.getCustomName() + ' tips you ' + tipAmount + ' credits.', 0)
                actor.sendSystemMessage('You successfully tip ' + tipAmount + ' credits to ' + currentTarget.getCustomName() + '.', 0)
                return
            actor.sendSystemMessage('You lack the cash funds to tip ' + tipAmount + ' credits to ' + currentTarget.getCustomName() + '.')
            return
        actor.sendSystemMessage('Invalid tip amount, set amount between 1 and 1,000,000')             
        return
    #/tip Waverunner 30000000 bank  <<<tip amnt to bank, send mail
    if commandLength == 2: #place holder for mail
        return 
    return