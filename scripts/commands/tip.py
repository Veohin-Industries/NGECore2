from resources.objects.creature import CreatureObject
from services.chat import ChatService
from services.chat import Mail
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
        
    commandArgs = commandString.split(" ")
    commandLength = len(commandArgs)
    
    #/tip 300000 && /tip Waverunner 300000
    if commandLength == 1:
        print (str(commandLength))
        tipAmount = commandArgs[0]
        actorFunds = actor.getCashCredits()
        currentTarget = core.objectService.getObject(target.getObjectId())
        
        if int(tipAmount) > 0 and int(tipAmount) <= 1000000:
            if actorFunds >= int(tipAmount):
                
                currentTarget.setCashCredits(int(tipAmount))       
                actor.setCashCredits(actorFunds - int(tipAmount))
        
                currentTarget.sendSystemMessage(actor.getCustomName() + ' tips you ' + tipAmount + ' credits.', 0)
                actor.sendSystemMessage('You successfully tip ' + tipAmount + ' credits to ' + currentTarget.getCustomName() + '.', 0)
                return
            actor.sendSystemMessage('You lack the cash funds to tip ' + tipAmount + ' credits to ' + currentTarget.getCustomName() + '.', 0)
            return
        actor.sendSystemMessage('Invalid tip amount, set amount between 1 and 1,000,000 credits', 0)             
        return
    #/tip Waverunner 30000000 bank  <<<tip amnt to bank, send mail
    #ADD: SystemMessage saying that 0 is invalid amount.
    if commandLength == 2:
        print(str(commandLength))
        bankSurcharge = int(0.05)
        tipAmount = commandArgs[0]
        tipTotal = int(tipAmount) * int(bankSurcharge)
        
        targetName = target.getCustomName()
        
        actorFunds = actor.getBankCredits()
        
        targetMail = Mail()
        targetMail.setMessage(tipAmount + ' credits from ' + actor.getCustomName() + ' have been successfully delivered from escrow to your bank account')
        targetMail.setSubject('@base_player:wire_mail_subject')
        targetMail.setSenderName('On behalf of ' + actor.getCustomName())

        actorMail = Mail()
        actorMail.setMessage('An amount of ' + tipAmount + ' credits have been transfered from your bank to escrow. It will be delivered to ' +
                             target.getCustomName() + ' as soon as possible.')
        actorMail.setSubject('@base_player:wire_mail_subject')
        actorMail.setSenderName('Galactic Banking')
        print ('Checking if can tip')
        if int(tipAmount) > 0 and int(actorFunds) >= int(tipTotal):
            print ('Can tip! Tipping...')
            target.setBankCredits(int(tipAmount))
            actor.setBankCredits(int(actorFunds) - int(tipTotal))
            actor.sendSystemMessage('You have successfully sent ' + tipAmount + ' bank credits to ' + target.getCustomName(), 0)
            target.sendSystemMessage('You have successfully received ' + tipAmount + ' bank credits from ' + actor.getCustomName(), 0)
            chatSvc = ChatService(core)
            chatSvc.sendPersistentMessage(actor.getClient(), actorMail)
            chatSvc.sendPersistentMessage(target.getClient(), targetMail)
            return
        
        actor.sendSystemMessage('You lack the bank funds to wire ' + tipAmount + ' bank funds to ' + target.getCustomName() + '.', 0)
        print ('Couldn\'t tip!')
        return
    return