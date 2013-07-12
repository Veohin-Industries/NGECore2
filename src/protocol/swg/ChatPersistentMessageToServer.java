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
package protocol.swg;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.mina.core.buffer.IoBuffer;


public class ChatPersistentMessageToServer extends SWGMessage {

	private String message;
	private int counter;
	private String subject;
	private String recipient;

	@Override
	public void deserialize(IoBuffer buffer) {
		buffer.getShort();
		buffer.getInt();
		
		
		int size;
		
		try {
			size = buffer.getInt();
			setMessage(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
			buffer.position(buffer.position() + size * 2);
			
			int attachmentsSize = buffer.getInt(); 	// TODO: Implement when waypoints are done
			
			while(attachmentsSize > 0) {
				buffer.get();
				--attachmentsSize;
			}

			setCounter(buffer.getInt());
			
			size = buffer.getInt();
			setSubject(new String(ByteBuffer.allocate(size * 2).put(buffer.array(), buffer.position(), size * 2).array(), "UTF-16LE"));
			buffer.position(buffer.position() + size * 2);
			
			buffer.getInt(); // spacer

			size = buffer.getShort();
			setRecipient(new String(ByteBuffer.allocate(size).put(buffer.array(), buffer.position(), size).array(), "US-ASCII"));
			buffer.position(buffer.position() + size);

			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public IoBuffer serialize() {
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
