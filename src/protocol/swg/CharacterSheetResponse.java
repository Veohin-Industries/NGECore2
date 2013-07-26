package protocol.swg;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import resources.common.Opcodes;

public class CharacterSheetResponse extends SWGMessage{

	private float bindX = -500;
	private float bindY = 28;
	private float bindZ = -4500;
	
	private String bindPlanet = "corellia";
	
	private float homeX = -500;
	private float homeY = 28;
	private float homeZ = -4500;
	
	private String homePlanet = "tatooine";
	
	private String spouseName = "Waverunner";
	
	private int lotsRemaining = 10;
	private int factionCRC = 0x16148850;
	private int factionStatus = 0;
	
	@Override
	public void deserialize(IoBuffer data) {
		
	}

	@Override
	public IoBuffer serialize() {
		IoBuffer result = IoBuffer.allocate(300).order(ByteOrder.LITTLE_ENDIAN);
		result.setAutoExpand(true);
		
		result.putShort((short) 13);
		result.putInt(Opcodes.CharacterSheetResponseMessage);
		result.putInt(0);
		result.putInt(0);
		
		result.putFloat(getBindX());
		result.putFloat(getBindY());
		result.putFloat(getBindZ());
		result.put(getAsciiString(getBindPlanet()));
		
		result.putInt(0); // bank x
		result.putInt(0); // bank y
		result.putInt(0); // bank z
		result.put(getAsciiString("tatooine"));
		
		result.putFloat(getHomeX());
		result.putFloat(getHomeY());
		result.putFloat(getHomeZ());
		result.put(getAsciiString(getHomePlanet()));
		
		result.put(getUnicodeString(getSpouseName()));
		
		result.putInt(getLotsRemaining());
		result.putInt(getFactionCRC());
		result.putInt(getFactionStatus());
		
		result.flip();
		System.out.println(result.getHexDump());
		return result;
	}
	
	public void setBindLocation(float x, float y, float z, String planet) {
		this.bindX = x;
		this.bindY = y;
		this.bindZ = z;
		this.bindPlanet = planet;
	}
	
	public void setHomeLocation(float x, float y, float z, String planet) {
		this.homeX = x;
		this.homeY = y;
		this.homeZ = z;
		this.bindPlanet = planet;
	}
	
	public void setSpouseName(String spouseName) { this.spouseName = spouseName; }
	
	public void setLotsRemaining(int lotsRemaining) { this.lotsRemaining = lotsRemaining; }
	
	public void setFactionCRC(int factionCRC) { this.factionCRC = factionCRC; }
	
	public void setFactionStatus(int factionStatus) { this.factionStatus = factionStatus; }
	
	public float getBindX() { return this.bindX; }
	public float getBindY() { return this.bindY; }
	public float getBindZ() { return this.bindZ; }
	public String getBindPlanet() { return this.bindPlanet; }
	
	public float getHomeX() { return this.homeX; }
	public float getHomeY() { return this.homeY; }
	public float getHomeZ() { return this.homeZ; }
	public String getHomePlanet() { return this.homePlanet; }
	
	public String getSpouseName() { return this.spouseName; }
	
	public int getLotsRemaining() { return this.lotsRemaining; }
	
	public int getFactionCRC() { return this.factionCRC; }
	public int getFactionStatus() { return this.factionStatus; }

}
