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
package services;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import main.NGECore;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.MeshVisitor;
import engine.clientdata.visitors.PortalVisitor;
import engine.clientdata.visitors.PortalVisitor.Cell;
import engine.clients.Client;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.scene.quadtree.QuadTree;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

import protocol.swg.CmdStartScene;
import protocol.swg.HeartBeatMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.OpenedContainerMessage;
import protocol.swg.UpdateTransformMessage;
import protocol.swg.UpdateTransformWithParentMessage;
import protocol.swg.objectControllerObjects.DataTransform;
import protocol.swg.objectControllerObjects.DataTransformWithParent;
import protocol.swg.objectControllerObjects.TargetUpdate;

import resources.objects.cell.CellObject;
import resources.objects.creature.CreatureObject;
import resources.common.*;
import wblut.geom.WB_AABBNode;
import wblut.geom.WB_AABBTree;
import wblut.geom.WB_Intersection;
import wblut.geom.WB_Point3d;
import wblut.geom.WB_Ray;
import wblut.geom.WB_Transform;
import wblut.hemesh.HE_Mesh;

@SuppressWarnings("unused")

public class SimulationService implements INetworkDispatch {
	
	Map<String, QuadTree<SWGObject>> quadTrees;
	private NGECore core;

	public SimulationService(NGECore core) {
		this.core = core;
		TerrainService terrainService = core.terrainService;
		quadTrees = new ConcurrentHashMap<String, QuadTree<SWGObject>>();
		
		for (int i = 0; i < core.terrainService.getPlanetList().size(); i++) {
			quadTrees.put(terrainService.getPlanetList().get(i).getName(), new QuadTree<SWGObject>(-8192, -8192, 8192, 8192));
		}
		
		List<SWGObject> objectList = core.objectService.getObjectList();
		synchronized(objectList) {
			for(SWGObject obj : objectList) {
				if(obj.getParentId() == 0) {
					add(obj, obj.getPosition().x, obj.getPosition().z);
				}
			}
		}
		core.commandService.registerCommand("opencontainer");
		core.commandService.registerCommand("transferitem");
		core.commandService.registerCommand("transferitemarmor");
		core.commandService.registerCommand("transferitemweapon");
		core.commandService.registerCommand("transferitemmisc");
		core.commandService.registerCommand("equip");
		core.commandService.registerCommand("prone");
		core.commandService.registerCommand("stand");
		core.commandService.registerCommand("sitserver");
		core.commandService.registerCommand("kneel");
		core.commandService.registerCommand("serverdestroyobject");
		core.commandService.registerCommand("giveitem");
		core.commandService.registerCommand("object");
		core.commandService.registerCommand("getattributesbatch");
		core.commandService.registerCommand("setgodmode");
		System.out.println("Commands registered...");
		
	}
	
	public void add(SWGObject object, int x, int y) {
		object.setIsInQuadtree(true);
		quadTrees.get(object.getPlanet().getName()).put(x, y, object);
	}
	
	public void add(SWGObject object, float x, float y) {
		object.setIsInQuadtree(true);
		quadTrees.get(object.getPlanet().getName()).put(x, y, object);
	}
	
	public boolean move(SWGObject object, int oldX, int oldY, int newX, int newY) {
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldY, object)) {
			return quadTrees.get(object.getPlanet().getName()).put(newX, newY, object);
		}
			
		return false;
	}
	
	public boolean move(SWGObject object, float oldX, float oldY, float newX, float newY) {
		long startTime = System.nanoTime();
		if(quadTrees.get(object.getPlanet().getName()).remove(oldX, oldY, object)) {
			boolean success = quadTrees.get(object.getPlanet().getName()).put(newX, newY, object);
			return success;
		}
		System.out.println("Move failed.");
		return false;
	}
	
	public List<SWGObject> get(Planet planet, int x, int y, int range) {
		return quadTrees.get(planet.getName()).get(x, y, range);
	}
	
	public List<SWGObject> get(Planet planet, float x, float y, int range) {
		List<SWGObject> list = quadTrees.get(planet.getName()).get((int)x, (int)y, range);
		return list;
	}
	
	public boolean remove(SWGObject object, int x, int y) {
		return quadTrees.get(object.getPlanet().getName()).remove(x, y, object);
	}
	
	public boolean remove(SWGObject object, float x, float y) {
		boolean success = quadTrees.get(object.getPlanet().getName()).remove(x, y, object);
		return success;
	}

	@Override
	public void insertOpcodes(Map<Integer,INetworkRemoteEvent> swgOpcodes, Map<Integer,INetworkRemoteEvent> objControllerOpcodes) {

		objControllerOpcodes.put(ObjControllerOpcodes.DATA_TRANSFORM, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				DataTransform dataTransform = new DataTransform();
				dataTransform.deserialize(data);
				//System.out.println("Movement Counter: " + dataTransform.getMovementCounter());
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));

				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				
				CreatureObject object = (CreatureObject) client.getParent();
				Point3D newPos;
				Point3D oldPos;
				synchronized(object.getMutex()) {
					newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
					oldPos = object.getPosition();
					//Collection<Client> oldObservers = object.getObservers();
					//Collection<Client> newObservers = new HashSet<Client>();
					if(object.getContainer() == null)
						move(object, oldPos.x, oldPos.z, newPos.x, newPos.z);
					Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());
					object.setPosition(newPos);
					object.setOrientation(newOrientation);
					object.setMovementCounter(dataTransform.getMovementCounter());
				}
				if(object.getContainer() != null) {
					object.getContainer()._remove(object);
					add(object, newPos.x, newPos.z);
				} 
				
				

				
				//object.setParentId(0);
				//object.setParent(null);
			//	System.out.println("Parsed Height: " + core.terrainService.getHeight(object.getPlanetId(), dataTransform.getXPosition(), dataTransform.getZPosition())
				//		 + " should be: " + dataTransform.getYPosition());
				UpdateTransformMessage utm = new UpdateTransformMessage(object.getObjectID(), dataTransform.getTransformedX(), dataTransform.getTransformedY(), dataTransform.getTransformedZ(), dataTransform.getMovementCounter(), (byte) dataTransform.getMovementAngle(), dataTransform.getSpeed(), object.getCombatFlag());
	
				List<SWGObject> newAwareObjects = get(object.getPlanet(), newPos.x, newPos.z, 200);
				ArrayList<SWGObject> oldAwareObjects = new ArrayList<SWGObject>(object.getAwareObjects());
				Collection<SWGObject> updateAwareObjects = CollectionUtils.intersection(oldAwareObjects, newAwareObjects);
				object.notifyObservers(utm, false);

				for(int i = 0; i < oldAwareObjects.size(); i++) {
					SWGObject obj = oldAwareObjects.get(i);
					if(!updateAwareObjects.contains(obj) && obj != object && obj.getWorldPosition().getDistance2D(newPos) > 200 && obj.isInQuadtree() /*&& obj.getParentId() == 0*/) {
						object.makeUnaware(obj);
						if(obj.getClient() != null)
							obj.makeUnaware(object);
					}
				}
				for(int i = 0; i < newAwareObjects.size(); i++) {
					SWGObject obj = newAwareObjects.get(i);
					if(!updateAwareObjects.contains(obj) && obj != object && !object.getAwareObjects().contains(obj) && obj.getWorldPosition().getDistance2D(newPos) <= 200 && obj.getContainer() != object && obj.isInQuadtree()) {						
						object.makeAware(obj);
						if(obj.getClient() != null)
							obj.makeAware(object);
					}
				}
				
				

			}
				
				
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.DATA_TRANSFORM_WITH_PARENT, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				
				data.order(ByteOrder.LITTLE_ENDIAN);
				
				DataTransformWithParent dataTransform = new DataTransformWithParent();
				dataTransform.deserialize(data);
				//System.out.println("Movement Counter: " + dataTransform.getMovementCounter());
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));

				if(core.objectService.getObject(dataTransform.getCellId()) == null)
					return;

				SWGObject parent = core.objectService.getObject(dataTransform.getCellId());
				
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				CreatureObject object = (CreatureObject) client.getParent();
				
				Point3D newPos = new Point3D(dataTransform.getXPosition(), dataTransform.getYPosition(), dataTransform.getZPosition());
				Point3D oldPos = object.getPosition();
				Quaternion newOrientation = new Quaternion(dataTransform.getWOrientation(), dataTransform.getXOrientation(), dataTransform.getYOrientation(), dataTransform.getZOrientation());

				UpdateTransformWithParentMessage utm = new UpdateTransformWithParentMessage(object.getObjectID(), dataTransform.getCellId(), dataTransform.getTransformedX(), dataTransform.getTransformedY(), dataTransform.getTransformedZ(), dataTransform.getMovementCounter(), (byte) dataTransform.getMovementAngle(), dataTransform.getSpeed(), object.getCombatFlag());

				
				if(object.getContainer() != parent) {
					remove(object, oldPos.x, oldPos.z);
					if(object.getContainer() != null)
						object.getContainer()._remove(object);
					parent._add(object);
				}
				object.setPosition(newPos);
				object.setOrientation(newOrientation);
				object.setMovementCounter(dataTransform.getMovementCounter());
				object.notifyObservers(utm, false);

			}
				
				
		});
		
		swgOpcodes.put(Opcodes.ClientOpenContainerMessage, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
				System.out.println("Open Container Request");
			}
			
		});
		
		objControllerOpcodes.put(ObjControllerOpcodes.TARGET_UPDATE, new INetworkRemoteEvent() {

			@Override
			public void handlePacket(IoSession session, IoBuffer data) throws Exception {
								
				data = data.order(ByteOrder.LITTLE_ENDIAN);
				data.position(0);
				
				Client client = core.getClient((Integer) session.getAttribute("connectionId"));
				
				if(client == null) {
					System.out.println("NULL Client");
					return;
				}
				
				if(client.getParent() == null) {
					System.out.println("NULL Object");
					return;
				}
				CreatureObject object = (CreatureObject) client.getParent();

				TargetUpdate targetUpdate = new TargetUpdate();
				targetUpdate.deserialize(data);
				
				object.setTargetId(targetUpdate.getTargetId());
				
			}
			
		});

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}
	
	public WB_AABBTree getAABBTree(SWGObject object, int collisionBlockFlag) {
		
		if(object.getMeshVisitor() == null || object.getTemplateData() == null) {
			System.out.println("NULL Mesh Visitor for: " + object.getTemplate());
			return null;
		}
		
		if(object.getTemplateData().getAttribute("collisionActionBlockFlags") != null) {
			int bit = (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags") & collisionBlockFlag;
		
			if(bit == (Integer) object.getTemplateData().getAttribute("collisionActionBlockFlags"))
				return null;
		}
		
		Point3D position = object.getPosition();
		HE_Mesh mesh = object.getMeshVisitor().createMesh();
				
		if(mesh == null)
			return null;
		System.out.println(object.getHeading());
		float angle = (float) (object.getRadians() * (180 / Math.PI));
		System.out.println("Angle: " + angle);

		Quaternion quat = object.getOrientation();
		
		WB_Transform transform = new WB_Transform().addRotateZ(object.getRadians());
		mesh = mesh.move(position.x, position.z, position.y).transform(transform);
		mesh.clean();
		mesh.cleanUnusedElementsByFace();
		WB_AABBTree aabbTree = new WB_AABBTree(mesh, mesh.numberOfFaces());
		return aabbTree;
	}
		
	public WB_Ray convertRayToModelSpace(WB_Ray ray, SWGObject object) {
		
		WB_Transform transform = new WB_Transform().addTranslate(new WB_Point3d(-object.getPosition().x, -object.getPosition().z , -object.getPosition().y)).addRotateZ(object.getRadians());
		ray = transform.apply(ray);
		
		return ray;
		
	}

	public void handleDisconnect(IoSession session) {

		Client client = core.getClient((Integer) session.getAttribute("connectionId"));

		if(client == null)
			return;
		
		if(client.getParent() == null)
			return;

		CreatureObject object = (CreatureObject) client.getParent();
		boolean remove = remove(object, object.getPosition().x, object.getPosition().z);
		if(remove)
			System.out.println("Successful quadtree remove");

		HashSet<Client> oldObservers = new HashSet<Client>(object.getObservers());
		for(Iterator<Client> it = oldObservers.iterator(); it.hasNext();) {
			Client observerClient = it.next();
			if(observerClient.getParent() != null) {
				observerClient.getParent().makeUnaware(object);
			}
		}
		

		object.createTransaction(core.getCreatureODB().getEnvironment());
		core.getCreatureODB().put(object, Long.class, CreatureObject.class, object.getTransaction());
		object.getTransaction().commitSync();
		
		session.suspendRead();
		session.suspendWrite();
		core.objectService.destroyObject(object);
		core.getActiveConnectionsMap().remove((Integer) session.getAttribute("connectionId"));
	}

	public void handleZoneIn(Client client) {

		if(client.getParent() == null)
			return;
		
		CreatureObject object = (CreatureObject) client.getParent();
		Quaternion orientation = object.getOrientation();
		Point3D position = object.getPosition();


		
		Point3D pos = object.getWorldPosition();
		
		Collection<SWGObject> newAwareObjects = get(object.getPlanet(), pos.x, pos.z, 200);
		for(Iterator<SWGObject> it = newAwareObjects.iterator(); it.hasNext();) {
			SWGObject obj = it.next();
			object.makeAware(obj);
			if(obj.getClient() != null)
				obj.makeAware(object);
		}
		
		if(object.getParentId() == 0)
			add(object, pos.x, pos.z);
	
	}
	
	public void transferToPlanet(SWGObject object, Planet planet, Point3D newPos, Quaternion newOrientation) {
		
		Client client = object.getClient();
		
		if(client == null)
			return;
		
		IoSession session = client.getSession();
		
		if(session == null)
			return;
		
		Point3D position = object.getPosition();
		
		if(object.getParentId() == 0 && object.getContainer() == null) {
			remove(object, position.x, position.z);
		} else {
			object.getContainer().remove(object);
		}

		HashSet<Client> oldObservers = new HashSet<Client>(object.getObservers());

		for(Client observerClient : oldObservers) {
			if(observerClient.getParent() != null) {
				observerClient.getParent().makeUnaware(object);
			}
		}
		
		
		synchronized(object.getMutex()) {
			
			Iterator<SWGObject> it = object.getAwareObjects().iterator();
			
			while(it.hasNext()) {
				it.remove();
			}

		}
		
		object.setPlanet(planet);
		object.setPlanetId(planet.getID());
		object.setPosition(newPos);
		object.setOrientation(newOrientation);
		
		HeartBeatMessage heartBeat = new HeartBeatMessage();
		session.write(heartBeat.serialize());

		CmdStartScene startScene = new CmdStartScene((byte) 0, object.getObjectID(), object.getPlanet().getPath(), object.getTemplate(), newPos.x, newPos.y, newPos.z, System.currentTimeMillis() / 1000, object.getRadians());
		session.write(startScene.serialize());
		
		core.simulationService.handleZoneIn(client);
		object.makeAware(object);

		
	}
	
	public void openContainer(SWGObject requester, SWGObject container) {
		
		if(container.getPermissions().canView(requester, container)) {
			OpenedContainerMessage opm = new OpenedContainerMessage(container.getObjectID());
			if(requester.getClient() != null && requester.getClient().getSession() != null)
				requester.getClient().getSession().write(opm.serialize());
		}
		
	}
	
	public void teleport(SWGObject obj, Point3D position, Quaternion orientation) {
		
		if(position.x >= -8192 && position.x <= 8192 && position.z >= -8192 && position.z <= 8192) {
			
			DataTransform dataTransform = new DataTransform(new Point3D(position.x, position.y, position.z), orientation, obj.getMovementCounter(), obj.getObjectID());
			ObjControllerMessage objController = new ObjControllerMessage(0x1B, dataTransform);
			obj.notifyObservers(objController, true);
			
		}
			
	}
	
	// not fully working yet(rotation of meshes wrong)
	public boolean checkLineOfSight(SWGObject obj1, SWGObject obj2) {
		
		if(obj1.getPlanet() != obj2.getPlanet())
			return false;
		
		if((obj1.getContainer() != null && obj2.getContainer() != null) && (obj1.getContainer() == obj2.getContainer()))	// if both are in same cell they should always be in sight of each other
			return true;
		
		if(obj1.getGrandparent() != null && obj2.getGrandparent() != null) {
			
			if(obj1.getGrandparent() == obj2.getGrandparent())
				return checkLineOfSightInBuilding(obj1, obj2, obj1.getGrandparent());
			else
				return false; 
		}
		
		float heightOrigin = 1.f;
		float heightDirection = 1.f;
		
		if(obj1 instanceof CreatureObject)
			heightOrigin = getHeightOrigin((CreatureObject) obj1);
		
		if(obj2 instanceof CreatureObject)
			heightDirection = getHeightOrigin((CreatureObject) obj2);
		
		Point3D position1 = obj1.getWorldPosition();
		Point3D position2 = obj2.getWorldPosition();

		Vector3D origin = new Vector3D(position1.x, position1.z, position1.y + heightOrigin);
		Vector3D end = new Vector3D(position2.x, position2.z, position2.y + heightDirection);

		float distance = position1.getDistance2D(position2);
		
		List<SWGObject> inRangeObjects = get(obj1.getPlanet(), position1.x, position1.z, (int) distance + 1);

		
		WB_Ray ray = new WB_Ray(origin.getX(), origin.getY() , origin.getZ(), end.getX(), end.getY(), end.getZ());
		
		for(SWGObject object : inRangeObjects) {
			
			if(object == obj1 || object == obj2)
				continue;
			
			Point3D position = object.getWorldPosition();
			float otherDistance = position.getDistance2D(position1);
			System.out.println("Distance from origin to target: " + distance + " Distance from origin to current obj: " + otherDistance);
			if(!obj1.inRange(position, distance))
				continue;
			
			WB_AABBTree aabbTree = getAABBTree(object, 255);
			
			if(aabbTree == null)
				continue;
			
			//System.out.println(object.getTemplate());

			//ray = convertRayToModelSpace(ray, object);

			ArrayList<WB_AABBNode> collisions = WB_Intersection.getIntersection(ray, aabbTree);
			if(!collisions.isEmpty()) {
				System.out.println("Collided with " + object.getTemplate() + " X: " + object.getPosition().x + " Y: " + object.getPosition().y + " Z: " + object.getPosition().z);
				return false;
			}
			
		}
		

		/*if(obj1.getContainer() != null || obj2.getContainer() != null) {
			
			CellObject cell = null;
			
			if(obj1.getContainer() != null && obj1.getContainer() instanceof CellObject)
				cell = (CellObject) obj1.getContainer();
			else if(obj2.getContainer() != null && obj2.getContainer() instanceof CellObject)
				cell = (CellObject) obj2.getContainer();

			if(cell != null) 
				return checkLineOfSightWorldToCell(obj1, obj2, cell);
		}*/
		
		return true;

	}
	
	// not fully working yet(rotation of meshes wrong)
	public boolean checkLineOfSightInBuilding(SWGObject obj1, SWGObject obj2, SWGObject building) {
		
		PortalVisitor portalVisitor = building.getPortalVisitor();
		
		if(portalVisitor == null)
			return true;
		
		Point3D position1 = obj1.getPosition();
		Point3D position2 = obj2.getPosition();

		
		Vector3D origin = new Vector3D(position1.x, position1.z, position1.y + 1);
		Vector3D end = new Vector3D(position2.x, position2.z, position2.y + 1);
		
		//Vector3D direction = end.subtract(origin).normalize();
		
		WB_Ray ray = new WB_Ray(origin.getX(), origin.getY(), origin.getZ(), end.getX(), end.getY(), end.getZ());
		
		for(int i = 1; i < portalVisitor.cells.size(); i++) {
			
			Cell cell = portalVisitor.cells.get(i);
			System.out.println(cell.name);
			try {
				System.out.println(cell.mesh);
				MeshVisitor meshVisitor = ClientFileManager.loadFile(cell.mesh, MeshVisitor.class);
				WB_AABBTree aabbTree = meshVisitor.getAABBTree(meshVisitor.createMesh());
				
				if(aabbTree == null)
					continue;
				
				ArrayList<WB_AABBNode> collisions = WB_Intersection.getIntersection(ray, aabbTree);

				if(!collisions.isEmpty()) {
					System.out.println("Collision with: " + cell.name);
					return false;
				}

			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			
		}
		
		return true;

	}
	
	// not fully working yet(rotation of meshes wrong)
	public boolean checkLineOfSightWorldToCell(SWGObject obj1, SWGObject obj2, CellObject cell) {
		
		SWGObject building = cell.getContainer();
		
		if(building == null)
			return true;
		
		PortalVisitor portalVisitor = building.getPortalVisitor();
		
		if(portalVisitor == null)
			return true;
		
		float heightOrigin = 1.f;
		float heightDirection = 1.f;
		
		if(obj1 instanceof CreatureObject)
			heightOrigin = getHeightOrigin((CreatureObject) obj1);
		
		if(obj2 instanceof CreatureObject)
			heightDirection = getHeightOrigin((CreatureObject) obj2);
		
		Point3D position1 = obj1.getWorldPosition();
		Point3D position2 = obj2.getWorldPosition();

		Point3D origin = new Point3D(position1.x, position1.z, position1.y + heightOrigin);
		Point3D direction = new Point3D(position2.x, position2.z, position2.y + heightDirection);

		WB_Ray ray = new WB_Ray(origin.x, origin.y , origin.z, direction.x, direction.y, direction.z);
		ray = convertRayToModelSpace(ray, building);
		
		if(cell.getCellNumber() >= portalVisitor.cellCount)
			return true;
		
		try {
			MeshVisitor meshVisitor = ClientFileManager.loadFile(portalVisitor.cells.get(cell.getCellNumber()).mesh, MeshVisitor.class);
			WB_AABBTree aabbTree = meshVisitor.getAABBTree(meshVisitor.createMesh());
			ArrayList<WB_AABBNode> collisions = WB_Intersection.getIntersection(ray, aabbTree);

			if(!collisions.isEmpty())
				return false;

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public float getHeightOrigin(CreatureObject creature) {
		
		float height = (float) (creature.getHeight() - 0.3);
		
		if(creature.getPosture() == 2 || creature.getPosture() == 13)
			height = 0.3f;
		else if(creature.getPosture() == 1)
			height /= 2.f;
		
		return height;

	}

}
