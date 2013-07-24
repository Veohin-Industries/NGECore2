package protocol.swg.clientSecureTradeManager;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;

public class AddItemMessage extends SWGMessage {

	private long objectID;
	
	public AddItemMessage(SWGObject objectID) {
		this.objectID = objectID.getObjectID();
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		result.putShort((short) 1);
		result.putInt(0x1E8D1356);
		result.putLong(objectID);
		return result.flip();
	}

}
