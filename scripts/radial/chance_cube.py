from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):

	#radials.add(RadialOptions(0, 21, 1, '')) #Use
	#radials.add(RadialOptions(0, RadialOptions.diceRoll, 3, '@dice/dice:dice_roll_single'))
	radials.add(RadialOptions(0, 252, 1, '')) 
	radials.add(RadialOptions(0, 251, 1, ''))
	radials.add(RadialOptions(0, 250, 1, ''))
	radials.add(RadialOptions(0, 249, 1, ''))
	radials.add(RadialOptions(0, 248, 1, ''))
	return

def handleSelection(core, owner, target, option):
	if option == RadialOptions.diceRoll or 21:
		owner.sendSystemMessage('You rolled nothing trololol', 0)
	return
	