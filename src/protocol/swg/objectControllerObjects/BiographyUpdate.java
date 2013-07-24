package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.ObjControllerMessage;
import protocol.Message;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;

public class BiographyUpdate extends ObjControllerObject{

	private PlayerObject owner;
	private String biography;
	
	public BiographyUpdate(PlayerObject owner) {
		try {
			this.owner = owner;
			
		}
		
		catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deserialize(IoBuffer buffer) {
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(55 + owner.getBiography().length()).order(ByteOrder.LITTLE_ENDIAN);
		result.putInt(ObjControllerMessage.BIOGRAPHY_UPDATE);
		result.putLong(owner.getObjectID());
		result.putInt(0);
		result.putLong(owner.getObjectID());
		result.put(getUnicodeString(owner.getBiography()));
		return result.flip();
	}

}
