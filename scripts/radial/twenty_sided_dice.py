from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):

    radials.add(RadialOptions(0, 24, 1, '')) 
    radials.add(RadialOptions(0, 25, 1, ''))
    radials.add(RadialOptions(0, 26, 1, ''))
    radials.add(RadialOptions(0, 27, 1, ''))
    return

def handleSelection(core, owner, target, option):
    if option == RadialOptions.diceRoll or 21:
        owner.sendSystemMessage('You rolled nothing trololol', 0)
    return