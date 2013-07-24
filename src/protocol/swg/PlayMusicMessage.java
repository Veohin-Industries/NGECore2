package protocol.swg;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayMusicMessage extends SWGMessage {

	private String soundFile;

	public PlayMusicMessage(String soundFile) {
		this.soundFile = soundFile;
	}
	
	@Override
	public void deserialize(IoBuffer data) {
		// XXX Auto-generated method stub
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(100);
		
		result.putShort((short) 5);
		result.putInt(0x04270D8A);
		result.put(getUnicodeString(soundFile));
		result.putLong(0);
		result.putInt(0);
		result.put((byte) 0);
		System.out.println("Sent sound");
		return result.flip();
	}

}
