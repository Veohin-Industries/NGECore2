from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	chance_cube = owner.getSlottedObject('chance_cube')
	if chance_cube:
		radials.add(RadialOptions(0, 21, 1, '')) #Use
		radials.add(RadialOptions(0, 7, 1, '')) #Examine
		radials.add(RadialOptions(1, RadialOptions.diceRoll, 3, '@sui:dice_roll_single'))
	return
	
def handleSelection(core, owner, target, option):
	if option == RadialOptions.diceRoll or option == 21: #if roll selected or Use
		random_number = core.random.randint(0, 100)
		owner.sendSystemMessage('You rolled ' + str(random_number))
	return
	