package protocol.swg.objectControllerObjects;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ObjControllerMessage;
import resources.objects.creature.CreatureObject;

public class SecureTrade extends ObjControllerObject{

	long senderID;
	long recieverID;
	
	public SecureTrade(CreatureObject senderID, CreatureObject recieverID) {
		this.senderID = senderID.getObjectID();
		this.recieverID = recieverID.getObjectID();
	}
	
	@Override
	public void deserialize(IoBuffer data) {
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		
		result.putInt(ObjControllerMessage.SECURE_TRADE);
		result.putInt(1);
		result.putLong(senderID);
		result.putLong(recieverID);
		
		return result.flip();
	}

}
