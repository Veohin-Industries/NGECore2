from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from resources.objects.guild import GuildObject
from resources.objects import Guild
from java.util import Vector

def createRadial(core, owner, target, radials):
    radials.add(RadialOptions(0, RadialOptions.serverGuildCreate, 3, '@guild:menu_create'))
    return
    
def handleSelection(core, owner, target, option):
    if option == RadialOptions.serverGuildCreate or 21:
        suiSvc = core.suiService
        suiWindow = suiSvc.createSUIWindow('Script.inputBox', owner, target, 10)
        suiWindow.setProperty('bg.caption.lblTitle:Text', '@guild:create_name_title')
        suiWindow.setProperty('Prompt.lblPrompt:Text', '@guild:create_name_prompt')
        returnParams = Vector()
        returnParams.add('textInput:Text')
        suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleAction)
        suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleAction)
        
        suiSvc.openSUIWindow(suiWindow)
        return
    return

def handleAction(core, owner, eventType, returnList):
    if eventType == 0:        
        Guild(1, '-SB-', 'SpaceBallz')
        
    return


        
        
        
        
        