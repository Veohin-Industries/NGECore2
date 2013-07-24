package protocol.swg.clientSecureTradeManager;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class DenyTradeMessage extends SWGMessage {

	@Override
	public void deserialize(IoBuffer data) {
		// XXX Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		result.putShort((short) 1);
		result.putInt(0x6EC28670);
		return result.flip();
	}

}
