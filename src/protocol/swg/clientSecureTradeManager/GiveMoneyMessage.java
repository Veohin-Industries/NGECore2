package protocol.swg.clientSecureTradeManager;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.SWGMessage;

public class GiveMoneyMessage extends SWGMessage {

	private int credits;
	
	public GiveMoneyMessage (int credits) {
		this.credits = credits;
	}
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(20);
		result.putShort((short) 2);
		result.putInt(0xD1527EE8);
		result.putInt(credits);
		return result.flip();
	}

}
