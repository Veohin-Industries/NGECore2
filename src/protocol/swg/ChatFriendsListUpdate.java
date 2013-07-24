package protocol.swg;

import java.nio.ByteOrder;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

public class ChatFriendsListUpdate extends SWGMessage{

	private String serverName;
	private String friendName;
	private byte onlineFlag;
	private NGECore core;
	
	public ChatFriendsListUpdate (String friendName, byte onlineFlag) {
		this.friendName = friendName;
		this.onlineFlag = onlineFlag;
		this.serverName = core.getConfig().getString("GALAXY_NAME");
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// XXX Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(10).order(ByteOrder.LITTLE_ENDIAN);
		result.putShort((short) 3);
		result.putInt(0x6CD2FCD8);
		result.put(getUnicodeString("SWG"));
		result.put(getUnicodeString(serverName));
		result.put(getUnicodeString(friendName));
		result.put(onlineFlag);
		return result.flip();
	}

}
