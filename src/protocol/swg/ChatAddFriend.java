package protocol.swg;

import org.apache.mina.core.buffer.IoBuffer;

public class ChatAddFriend extends SWGMessage {

	private String server;
	private String creatorName;
	private String addedName;
	
	@Override
	public void deserialize(IoBuffer buffer) {
		buffer.getShort(); // Opcount 4
		buffer.getInt(); // Opcode 6FE7BD90
		
		getUnicodeString(buffer); // 03 and then SWG
		
		setServer(getUnicodeString(buffer));
		
		setCreatorName(getUnicodeString(buffer));
		
		setAddedName(getUnicodeString(buffer));
		
		buffer.getInt(); // online status?
		System.out.println("addedName: " + getAddedName());
	}

	@Override
	public IoBuffer serialize() {
		// XXX Auto-generated method stub
		return null;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	
	public String getAddedName() {
		return addedName;
	}
	
	public String getServer() {
		return server;
	}
	
	public void setAddedName(String addedName) {
		this.addedName = addedName;
	}
	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public void setServer(String server) {
		this.server = server;
	}

}
