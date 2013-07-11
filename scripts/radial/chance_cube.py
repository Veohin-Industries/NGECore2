from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	chance_cube = owner.getSlottedObject('chance_cube')
	
	if chance_cube:
		radials.add(RadialOptions(0, RadialOptions.diceRoll, 3, '@dice:dice_roll_single'))
	return

def handleSelection(core, owner, target, option):
	if option == RadialOptions.diceRoll:
		owner.sendSystemMessage('You rolled nothing trololol')
	return

def handleTransfer(core, owner, eventType, returnList):
	return
	