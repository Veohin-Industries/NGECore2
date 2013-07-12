from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):

    radials.add(RadialOptions(0, 247, 1, ''))
    radials.add(RadialOptions(0, 246, 1, ''))
    radials.add(RadialOptions(0, 245, 1, ''))
    radials.add(RadialOptions(0, 244, 1, ''))
    radials.add(RadialOptions(0, 243, 1, ''))
    return

def handleSelection(core, owner, target, option):
    if option == RadialOptions.diceRoll or 21:
        owner.sendSystemMessage('You rolled nothing trololol', 0)
    return