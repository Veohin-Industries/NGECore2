#guild_registry_initial
from resources.common import RadialOptions
from resources.objects.guild import GuildObject
from resources.objects.creature import CreatureObject

from services.guild import GuildService
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger

from java.util import Vector
import sys
#/giveitem object/tangible/furniture/technical/shared_guild_registry_initial.iff
def createRadial(core, owner, target, radials):
    #if owner.getGuildId() >= 0:
        #radials.add(RadialOptions(0, RadialOptions.itemMoveLeft, 3, '@guild:menu_create')) #94
        #return
    radials.add(RadialOptions(0, RadialOptions.itemMoveRight, 3, '@guild:menu_guild_management')) #95
    radials.add(RadialOptions(0, RadialOptions.imageDesign, 3, '@guild:menu_member_management')) #50
    
    # Guild Management
    radials.add(RadialOptions(3, RadialOptions.lootAll, 3, '@guild:menu_disband')) #37
    radials.add(RadialOptions(3, RadialOptions.groupLootOptions, 3, '@guild:menu_info')) #73
    radials.add(RadialOptions(3, RadialOptions.missionDetails, 3, '@guild:menu_namechange'))#35
    
    # Member Management
    radials.add(RadialOptions(4, RadialOptions.itemMail, 3, '@guild:menu_sponsor'))#25
    radials.add(RadialOptions(4, RadialOptions.groupTell, 3, '@guild:menu_permission_list')) #86
    radials.add(RadialOptions(4, RadialOptions.setName, 3, '@guild:menu_members')) #51
    
    return
       
def handleSelection(core, owner, target, option):
    suiSvc = core.suiService
    returnParams = Vector()
    
    #Create Guild
    if option == RadialOptions.itemMoveLeft: 
        print('Opening ui service')
        wndGuildCreate = suiSvc.createSUIWindow('Script.inputBox', owner, target, 10)
        wndGuildCreate.setProperty('bg.caption.lblTitle:Text', '@guild:create_name_title')
        wndGuildCreate.setProperty('Prompt.lblPrompt:Text', '@guild:create_name_prompt')
        wndGuildCreate.setProperty('cmbInput:visible', 'False')
        returnParams.add('inputBox.txtInput:Text')
        wndGuildCreate.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleGuild)
        wndGuildCreate.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleGuild)
        suiSvc.openSUIWindow(wndGuildCreate)
        print ('RadialOptions.serverGuildCreate selected')
        return
    # ============================
    #  ===== Guild Management =====
    # =============================
    
    #Guild Disband
    if option == RadialOptions.lootAll:
        wndGuildDisband = suiSvc.createMessageBox(suiSvc.MessageBoxType.MESSAGE_BOX_YES_NO, '@guild:disband_title', '@guild:disband_prompt', owner, target, 10)
        wndGuildDisband.addHandler(2, '', Trigger.TRIGGER_OK, returnParams, handleGuild)
        wndGuildDisband.addHandler(3, '', Trigger.TRIGGER_CANCEL, returnParams, handleGuild)
        suiSvc.openSUIWindow(wndGuildDisband)
        print ('RadialOptions.serverGuildDisband selected')
        return
    
    #Guild Information
    if option == RadialOptions.groupLootOptions:
        gPromptName = str("Spaceballz")
        gPromptAlign = "Imperial aligned"
        gPromptAbrv = "SB"
        gPromptLeader = "Osskscosco Zoli"
        gPromptGCWDefender = "Corellia LOS Island (started defending on Thrs Jul 18 9:40:13 2013 PDT"
        gPromptGCWRegionDefenderBnus = "100%"
        gPromptMembers = "90000000"
        guildInfoPrompt = "Guild Name: " + gPromptName + "\n"  + \
                          "(" + gPromptAlign + ")" + "\n" + \
                          "Guild Leader: " + gPromptLeader + "\n"+ \
                          "GCW Region Defender: " + gPromptGCWDefender + "\n"+ \
                          "GCW Region Defender Bonus: " + gPromptGCWRegionDefenderBnus + "\n" + \
                          "Guild Members: " + gPromptMembers
        wndGuildInfo = suiSvc.createMessageBox(suiSvc.MessageBoxType.MESSAGE_BOX_OK, '@guild:info_title', guildInfoPrompt, owner, target, 10)
        suiSvc.openSUIWindow(wndGuildInfo)
        print ('RadialOptions.serverGuildInfo selected')
        return
    
    # Change Name
    if option == RadialOptions.missionDetails:
        wndGuildNameChng = suiSvc.createSUIWindow('Script.inputBox', owner, target, 10)
        wndGuildNameChng.setProperty('bg.caption.lblTitle:Text', '@guild:namechange_name_title')
        wndGuildNameChng.setProperty('Prompt.lblPrompt:Text', '@guild:namechange_name_prompt')
        wndGuildNameChng.setProperty('cmbInput:visible', 'False')
        suiSvc.openSUIWindow(wndGuildNameChng)
        return
    
    # Guild Members
    if option == RadialOptions.setName:
        wndGuildMembers = suiSvc.createSUIWindow('Script.tablePage', owner, target, 10)
        wndGuildMembers.setProperty('bg.caption.lblTitle:Text', '@guild:members_title')
        
        #wndGuildMembers.addDataSource('comp.TablePage.dataTable', 'memberNumber')
        wndGuildMembers.addDataSource('comp.TablePage.dataTable', 'memberName')        
        #wndGuildMembers.addDataSource('comp.TablePage.dataTable', 'memberTitle')
        wndGuildMembers.setProperty('comp.TablePage.dataTable.memberName:Label', '@guild:members_title')
        #wndGuildMembers.setProperty('comp.b.TablePage.containerall.type:Label', '@guild:members_title') <<< barMenu WORKED, CUSTOM TEXT SHOWED
        #wndGuildMembers.setProperty('comp.TablePage.dataTable.memberNumber:Type', 'integer')
        
        suiSvc.openSUIWindow(wndGuildMembers)
        return
    
    #============================
    # ==== Member Management ====
    # ===========================
    
    #Guild Member Permissions
    if option == RadialOptions.groupTell:
        strData = 'Osskscosco Zoli'
        gVector = Vector()
        gVector.add(strData)
        wndGuildPerm = suiSvc.createListBox(suiSvc.ListBoxType.LIST_BOX_OK_CANCEL, '@guild:permissions_title', '@guild:permissions_prompt', gVector, owner, target, 10)
        suiSvc.openSUIWindow(wndGuildPerm)
        return
    
    #Sponsor A Member
    if option == RadialOptions.itemMail:
        wndGuildSponsor = suiSvc.createSUIWindow('Script.inputBox', owner, target, 10)
        wndGuildSponsor.setProperty('bg.caption.lblTitle:Text', '@guild:sponsor_title')
        wndGuildSponsor.setProperty('Prompt.lblPrompt:Text', '@guild:sponsor_prompt')
        wndGuildSponsor.setProperty('cmbInput:visible', 'False')
        suiSvc.openSUIWindow(wndGuildSponsor)
        return
    print ('Couldnt find a option')
       
    return
     
def handleGuild(core, owner, eventType, returnList):
    if eventType == 0:
        #ADD: Add event for creating guild
        return
       
    if eventType == 2:
        #ADD: Add event for disbanding guild
        return
    return

