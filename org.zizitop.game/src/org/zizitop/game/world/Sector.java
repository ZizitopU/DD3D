package org.zizitop.game.world;

import org.zizitop.game.GameUtils;
import org.zizitop.game.sprites.Entity;
import org.zizitop.game.sprites.LinkedListElement;
import org.zizitop.game.sprites.Sprite;
import org.zizitop.game.sprites.Structure;
import org.zizitop.pshell.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Just a closed polygon that have foor and ceiling with needed height and can have "neighbours",
 * connected trough "portals"(wall value is < 0 => we have a portal to the sector -(wall + 1)).
 * Vertices for walls are stored in {@link Level}. Here stored only their indexes.
 * <br><br>
 * Created 30.01.19 19:09
 *
 * @author Zizitop
 */
public class Sector {
	public static final double DEFAULT_FRICTION = 0.1;
	final int[] verticies;
	final int[] walls;
	Sector[] neighbours;
	final double floor, ceiling;
	int id;

	LinkedListElement spritesList;
	LinkedListElement structuresList;
	LinkedListElement entitiesList;

	public Sector(int[] verticies, int[] walls, double floor, double ceiling) {
		this(verticies, walls, floor, ceiling, null, null, null);
	}

	public Sector(int[] verticies, int[] walls, double floor, double ceiling, LinkedListElement spritesList, LinkedListElement structuresList, LinkedListElement entitiesList) {
		this.verticies = verticies;
		this.walls = walls;
		this.floor = floor;
		this.ceiling = ceiling;
		this.spritesList = spritesList;
		this.structuresList = structuresList;
		this.entitiesList = entitiesList;
	}

	/**
	 * Just proceed some data for some needs.
	 */
	void init(Level level, int id) {
		this.id = id;
		ArrayList<Sector> neighbours = new ArrayList<>();

		for(int i = 0; i < walls.length; ++i) {
			int wall = walls[i];

			if(wall < 0) {
				// Remove duplicates
				boolean put = true;

				Sector ss = level.sectors[-(wall + 1)];

				for(Sector s: neighbours) {
					if(s == ss) {
						put = false;
						break;
					}
				}

				if(put) {
					neighbours.add(ss);
				}
			}
		}

		this.neighbours = Utils.toArray(Sector.class, neighbours);
	}

	void add(Sprite s) {
		if(s instanceof Structure) {
			if(s instanceof Entity) {
				addEntity((Entity) s);
			} else {
				addStructure((Structure) s);
			}
		} else {
			addSprite(s);
		}
	}

	void addSprite(Sprite s) {
		if(spritesList == null) {
			spritesList = s;
			s.listNext = null;
		}

		LinkedListElement prev = spritesList, list = prev.listNext;

		while(list != null) {
			prev = list;
			list = prev.listNext;
		}

		prev.listNext = s;
		s.listNext = null;
	}

	void addStructure(Structure s) {
		if(structuresList == null) {
			structuresList = s;
			s.listNext = null;
		}

		LinkedListElement prev = structuresList, list = prev.listNext;

		while(list != null) {
			prev = list;
			list = prev.listNext;
		}

		prev.listNext = s;
		s.listNext = null;
	}

	void addEntity(Entity s) {
		if(entitiesList == null) {
			entitiesList = s;
			s.listNext = null;
		}

		LinkedListElement prev = entitiesList, list = prev.listNext;

		while(list != null) {
			prev = list;
			list = prev.listNext;
		}

		prev.listNext = s;
		s.listNext = null;
	}

	boolean removeSprite(Sprite s) {
		if(spritesList == null) {
			return false;
		} else if(spritesList == s) {
			spritesList = spritesList.listNext;
			return true;
		}

		LinkedListElement prev = spritesList, list = prev.listNext;

		while(list != null && list != s) {
			prev = list;
			list = prev.listNext;
		}

		if(list == s) {
			prev.listNext = list.listNext;
			return true;
		} else {
			return false;
		}
	}

	boolean removeStructure(Structure s) {
		if(structuresList == null) {
			return false;
		} else if(structuresList == s) {
			structuresList = structuresList.listNext;
			return true;
		}

		LinkedListElement prev = structuresList, list = prev.listNext;

		while(list != null && list != s) {
			prev = list;
			list = prev.listNext;
		}

		if(list == s) {
			prev.listNext = list.listNext;
			return true;
		} else {
			return false;
		}
	}

	boolean removeEntity(Entity s) {
		if(entitiesList == null) {
			return false;
		} else if(entitiesList == s) {
			entitiesList = entitiesList.listNext;
			return true;
		}

		LinkedListElement prev = entitiesList, list = prev.listNext;

		while(list != null && list != s) {
			prev = list;
			list = prev.listNext;
		}

		if(list == s) {
			prev.listNext = list.listNext;
			return true;
		} else {
			return false;
		}
	}

	boolean remove(Sprite s) {
		if(s instanceof Structure) {
			if(s instanceof Entity) {
				return removeEntity((Entity) s);
			} else {
				return removeStructure((Structure) s);
			}
		} else {
			return removeSprite(s);
		}
	}

//	static void add(Collection<? extends Sprite> sprites, Level level) {
//		for(Sprite s: sprites) {
//			Chunk ch = level.getChunk(s.x, s.y);
//
//			if(ch == null) continue;
//
//			ch.add(s);
//		}
//	}
//
//	static void addSprites(Collection<Sprite> sprites, Level level) {
//		for(Sprite s: sprites) {
//			Chunk ch = level.getChunk(s.x, s.y);
//
//			if(ch == null) continue;
//
//			ch.addSprite(s);
//		}
//	}
//
//	static void addStructures(Collection<Structure> structures, Level level) {
//		for(Structure s: structures) {
//			Chunk ch = level.getChunk(s.x, s.y);
//
//			if(ch == null) continue;
//
//			ch.addStructure(s);
//		}
//	}
//
//	static void addEntities(Collection<Entity> entities, Level level) {
//		for(Entity e: entities) {
//			Chunk ch = level.getChunk(e.x, e.y);
//
//			if(ch == null) continue;
//
//			ch.addEntity(e);
//		}
//	}
//
//	static void add(Sprite s, Level l) {
//		Chunk ch = l.getChunk(s.x, s.y);
//
//		if(ch != null) {
//			ch.add(s);
//		}
//	}
//
//	static void remove(Sprite s, Level l) {
//		Chunk ch = l.getChunk(s.x, s.y);
//
//		if(ch != null) {
//			ch.remove(s);
//		}
//	}
//
//	static boolean containsPoint(double px, double py, int chunkX, int chunkY) {
//		chunkX *= CHUNK_SIZE;
//		chunkY *= CHUNK_SIZE;
//
//		return (px >= chunkX && py >= chunkY) && (px <= chunkX + CHUNK_SIZE && py <= chunkY + CHUNK_SIZE);
//	}

	public double getFriction() {
		return DEFAULT_FRICTION;
	}
}
