from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, RadialOptions.diceRoll, 3, '@dice/dice:dice_roll_single'))
	radials.add(RadialOptions(0, 7, 1, ''))
	return

def handleSelection(core, owner, target, option):
	if option == RadialOptions.diceRoll:
		print 'dice rolled'
		owner.sendSystemMessage('You rolled nothing trololol')
	return
	