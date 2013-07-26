from protocol.swg import CharacterSheetResponse
from engine.resources.common import CRC

import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	crc = CRC()
	
	response = CharacterSheetResponse()
	#response.setSpouseName("Osskscosco")

	actor.getClient().getSession().write(response.serialize())
	return