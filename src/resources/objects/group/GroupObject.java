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

import java.util.Vector;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import engine.clients.Client;
import engine.resources.objects.SWGObject;
import resources.objects.Group;
import resources.objects.SWGList;
import resources.objects.group.GroupMessageBuilder;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;


public class GroupObject extends SWGObject {
	
	protected NGECore core;
	
	private Vector<SWGObject> memberList = new Vector<SWGObject>();
	private int memberListUpdateCounter;
	
	private SWGObject groupLeader;
	private SWGObject lootMaster;
	
	private short groupLevel;
	private int lootMode;
	
	private int volume = 0;
	
	private String STFFile = "string_id_table";
	private String STFName = "";
	private int STFSpacer = 0;
	private String customName = "";
	
	private String unkAsciiString = "";
	
	
	
	public GroupObject(long objectId) {
		super(objectId, null, new Point3D(0, 0, 0), new Quaternion(0, 0, 0, 1), "object/group/shared_group_object.iff");
	}
	
	public Vector<SWGObject> getMemberList() {
		return memberList;
	}

	public int getMemberListUpdateCounter() {
		synchronized(objectMutex) {
			return memberListUpdateCounter;
		}
	}

	public void setMemberListUpdateCounter(int memberListUpdateCounter) {
		synchronized(objectMutex) {
			this.memberListUpdateCounter = memberListUpdateCounter;
		}
	}

	public SWGObject getGroupLeader() {
		synchronized(objectMutex) {
			return groupLeader;
		}
	}

	public void setGroupLeader(SWGObject groupLeader) {
		synchronized(objectMutex) {
			this.groupLeader = groupLeader;
		}
	}

	public SWGObject getLootMaster() {
		synchronized(objectMutex) {
			return lootMaster;
		}
	}

	public void setLootMaster(SWGObject lootMaster) {
		synchronized(objectMutex) {
			this.lootMaster = lootMaster;
		}
	}

	public short getGroupLevel() {
		synchronized(objectMutex) {
			return groupLevel;
		}
	}

	public void setGroupLevel(short groupLevel) {
		synchronized(objectMutex) {
			this.groupLevel = groupLevel;
		}
	}

	public int getLootMode() {
		synchronized(objectMutex) {
			return lootMode;
		}
	}

	public void setLootMode(int lootMode) {
		synchronized(objectMutex) {
			this.lootMode = lootMode;
		}
	}

	public int getSTFSpacer() {
		synchronized(objectMutex) {
			return STFSpacer;
		}
	}
	
	public void setSTFSpacer(int STFSpacer) {
		synchronized(objectMutex) {
			this.STFSpacer = STFSpacer;
		}
	}
	
	public String getSTFFile() {
		synchronized(objectMutex) {
			return STFFile;
		}
	}
	
	public void setSTFFile(String STFFile) {
		synchronized(objectMutex) {
			this.STFFile = STFFile;
		}
	}
	
	public String getCustomName() {
		synchronized(objectMutex) {
			return customName;
		}
	}
	
	public void setCustomName(String customName) {
		synchronized(objectMutex) {
			this.customName = customName;
		}
	}
	
	public int getVolume() {
		synchronized(objectMutex) {
			return volume;
		}
	}
	
	public void setUnkAsciiString(String unkAsciiString) {
		synchronized(objectMutex) {
			this.unkAsciiString = unkAsciiString;
		}
	}
	
	public String getUnkAsciiString() {
		synchronized(objectMutex) {
			return unkAsciiString;
		}
	}
	
	@Override
	public void sendBaselines(Client client) {
		// TODO Auto-generated method stub
		
	}
	
	private void notifyAll(IoBuffer buffer) {
		//for (int i = 0; i < core.getActiveConnectionsMap().size(); i++) {
		//	core.getActiveConnectionsMap().get(i).getSession().write(buffer);
		//}
		System.out.println("Notifying all...");
		synchronized(core.getActiveConnectionsMap()) {
			for (Client client : core.getActiveConnectionsMap().values()) {
				System.out.println("Notifying..." + client.getParent().getCustomName());
				client.getSession().write(buffer);
				System.out.println("Notified them.");
				System.out.println("Packet: " + buffer.getHexDump());
			}
		}
	}

}
