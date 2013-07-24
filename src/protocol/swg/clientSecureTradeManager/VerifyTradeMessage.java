package protocol.swg.clientSecureTradeManager;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class VerifyTradeMessage extends SWGMessage {

	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		result.putShort((short) 1);
		result.putInt(0x9AE247EE);
		return result.flip();
	}

}
