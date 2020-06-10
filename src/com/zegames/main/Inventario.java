package com.zegames.main;

import com.zegames.world.*;

import java.awt.*;

public class Inventario {
    public int selected = 0;
    public boolean isPressed = false;
    public boolean isPlaceItem = false;
    public int mx, my;
    public String[] items = {"grama", "terra", "neve", "areia", "ar", ""};
    public int inventoryBoxSize = 45;
    public int initialPosition = ((Game.WIDTH * Game.SCALE) / 2) - ((items.length * inventoryBoxSize) / 2);

    public void tick() {
        if (isPressed) {
            isPressed = false;

            if (mx >= initialPosition && mx < initialPosition + (inventoryBoxSize * items.length)) {
                if (my >= Game.HEIGHT * Game.SCALE - inventoryBoxSize - 1 && my < Game.HEIGHT * Game.SCALE - inventoryBoxSize - 1 + inventoryBoxSize) {
                    selected = (int) (mx - initialPosition)/ inventoryBoxSize;
                }
            }
        }

        if (isPlaceItem) {
            isPlaceItem = false;

            mx = (int) mx / 3 + Camera.x;
            my = (int) my / 3 + Camera.y;

            int tileX = mx/16;
            int tileY = my/16;

            if (!World.tiles[tileX + tileY * World.WIDTH].solid) {
                if (items[selected] == "grama") {
                    World.tiles[tileX + tileY * World.WIDTH] = new WallTile(tileX * 16, tileY * 16, Tile.TILE_GRAMA);
                } else if (items[selected] == "terra") {
                    World.tiles[tileX + tileY * World.WIDTH] = new WallTile(tileX * 16, tileY * 16, Tile.TILE_TERRA);
                } else if (items[selected] == "ar") {
                    World.tiles[tileX + tileY * World.WIDTH] = new FloorTile(tileX * 16, tileY * 16, Tile.TILE_AR);
                } else if (items[selected] == "areia") {
                    World.tiles[tileX + tileY * World.WIDTH] = new WallTile(tileX * 16, tileY * 16, Tile.TILE_AREIA);
                } else if (items[selected] == "neve") {
                    World.tiles[tileX + tileY * World.WIDTH] = new WallTile(tileX * 16, tileY * 16, Tile.TILE_NEVE);
                }

                if (!World.isFree(Game.player.getX(), Game.player.getY())) {
                    World.tiles[tileX + tileY * World.WIDTH] = new FloorTile(tileX * 16, tileY * 16, Tile.TILE_AR);
                }
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < items.length; i++) {
            g.setColor(Color.GRAY);
            g.fillRect(initialPosition + (i * inventoryBoxSize) + 1,
                    Game.HEIGHT * Game.SCALE - inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);

            g.setColor(Color.BLACK);
            g.drawRect(initialPosition + (i * inventoryBoxSize) + 1,
                    Game.HEIGHT * Game.SCALE - inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);

            if (items[i] == "grama") {
                g.drawImage(Tile.TILE_GRAMA, initialPosition + (i * inventoryBoxSize) + 7,
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize + 7, 32, 32, null);
            } else if (items[i] == "terra") {
                g.drawImage(Tile.TILE_TERRA, initialPosition + (i * inventoryBoxSize) + 7,
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize + 7, 32, 32, null);
            } else if (items[i] == "ar") {
                g.drawImage(Tile.TILE_AR, initialPosition + (i * inventoryBoxSize) + 7,
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize + 7, 32, 32, null);
            } else if (items[i] == "areia") {
                g.drawImage(Tile.TILE_AREIA, initialPosition + (i * inventoryBoxSize) + 7,
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize + 7, 32, 32, null);
            } else if (items[i] == "neve") {
                g.drawImage(Tile.TILE_NEVE, initialPosition + (i * inventoryBoxSize) + 7,
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize + 7, 32, 32, null);
            }

            if (selected == i) {
                g.setColor(Color.RED);
                g.drawRect(initialPosition + (i * inventoryBoxSize),
                        Game.HEIGHT * Game.SCALE - inventoryBoxSize - 1, inventoryBoxSize, inventoryBoxSize);
            }
        }
    }
}
