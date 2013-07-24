package protocol.swg.clientSecureTradeManager;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import protocol.swg.SWGMessage;

public class RemoveItemMessage extends SWGMessage {

	private long objectID;
	
	public RemoveItemMessage (SWGObject object) {
		this.objectID = object.getObjectID();
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		result.putShort((short) 1);
		result.putInt(0x4417AF8B);
		result.putLong(objectID);
		return result.flip();
	}

}
