from resources.common import RadialOptions
from resources.objects.player import PlayerObject

import sys
import random

def createRadial(core, owner, target, radials):

	radials.add(RadialOptions(0, 21, 1, '')) #Use
	radials.add(RadialOptions(0, RadialOptions.diceRoll, 3, '@dice/dice:dice_roll_single'))

	return

def handleSelection(core, owner, target, option):
	if option == RadialOptions.diceRoll or 21:
		stringOptions = ['red', 'blue']
		owner.sendSystemMessage('Your chance cube rolled the color ' + random.choice(stringOptions) , 0)
	return
	