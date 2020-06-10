package com.zegames.world;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile{
    public FloorTile(int x, int y, BufferedImage sprite) {
        super(x, y, sprite);
    }

    @Override
    public void render(Graphics g) {
        if (World.CICLO == World.DIA) {
            g.drawImage(Tile.TILE_AR, x - Camera.x, y - Camera.y, null);
        } else if (World.CICLO == World.NOITE) {
            g.drawImage(Tile.TILE_NOITE, x - Camera.x, y - Camera.y, null);
        }
    }
}
