/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package resources.objects.group;

import java.nio.ByteOrder;

import org.apache.mina.core.buffer.IoBuffer;

import engine.resources.objects.SWGObject;
import resources.objects.ObjectMessageBuilder;
import resources.objects.creature.CreatureObject;
import resources.objects.group.GroupObject;


public class GroupMessageBuilder extends ObjectMessageBuilder{

	public GroupMessageBuilder(GroupObject groupObject) {
		setObject(groupObject);
	}
	
	public IoBuffer buildBaseline3() {
		GroupObject group = (GroupObject) object;
		IoBuffer buffer = bufferPool.allocate(80 + group.getMemberList().size(), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 4);
		
		buffer.putFloat(group.getComplexity());
		buffer.put(getAsciiString(group.getSTFFile()));
		buffer.putInt(group.getSTFSpacer());
		buffer.put(getAsciiString(group.getStfName()));
		buffer.put(getUnicodeString(group.getCustomName()));
		buffer.putInt(group.getVolume());
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("GRUP", (byte) 3, buffer, size);
		return buffer;
	}
	
	public IoBuffer buildBaseline6() {
		GroupObject group = (GroupObject) object;
		IoBuffer buffer = bufferPool.allocate(80 + group.getMemberList().size(), false).order(ByteOrder.LITTLE_ENDIAN);
		
		buffer.putShort((short) 8);
		
		buffer.putInt(0); //unk
		
		if (group.getMemberList().isEmpty()) {
			buffer.putInt(0);
			buffer.putInt(0);
		} else {
			buffer.putInt(group.getMemberList().size());
			buffer.putInt(group.getMemberListUpdateCounter());
			
			for(SWGObject obj : group.getMemberList()) {
				int index = 0;
				CreatureObject member = (CreatureObject) group.getMemberList().get(index++);
				buffer.putLong(member.getObjectID());
				buffer.put(getAsciiString(member.getCustomName()));
			}
		}
		

		buffer.putInt(0); // ship group list size?
		buffer.putInt(0); // ship group list update counter?
		
		buffer.putInt(0); // ship group member object ID?
		buffer.putInt(0); // ship group member name?
		
		buffer.put(getAsciiString(group.getUnkAsciiString())); // unk Ascii string
		
		buffer.putInt(5); // Difficulty TODO: Research this
		
		buffer.putInt(0); //unk
		
		buffer.putLong(group.getLootMaster().getObjectID());
		buffer.putInt(group.getLootMode());
		
		int size = buffer.position();
		buffer = bufferPool.allocate(size, false).put(buffer.array(), 0, size);
		buffer.flip();
		buffer = createBaseline("GRUP", (byte) 6, buffer, size);
		
		return buffer;
		
	}
	
	public IoBuffer buildLootMasterDelta(long lootMaster) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putLong(lootMaster);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GRUP", (byte) 6, (short) 1, (short) 0x06, buffer, size + 4);
		
		return buffer;

	}
	
	public IoBuffer buildLootRuleDelta(int lootRule) {
		
		IoBuffer buffer = bufferPool.allocate(8, false).order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(lootRule);
		int size = buffer.position();
		buffer.flip();
		buffer = createDelta("GRUP", (byte) 6, (short) 1, (short) 0x07, buffer, size + 4);
		
		return buffer;

	}
	
	@Override
	public void sendBaselines() {
		
	}
	@Override
	public void sendListDelta(short updateType, IoBuffer buffer) {
		
	}

}
