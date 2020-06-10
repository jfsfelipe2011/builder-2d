package com.zegames.entities;

import com.zegames.main.Game;
import com.zegames.world.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private int framesAnimation = 0;
    private int maxFrames = 10;
    private int maxSprite = 3;
    private int curSprite = 0;
    public int dir = 1;
    public double vida = 15;
    public double maxVida = 15;

    public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
    }

    public void tick() {
        if (World.isFree(this.getX(), (this.getY() + 1))) {
            y += 1;
        }

        if (dir == 1) {
            if (World.isFree((int) (x + speed), (int) y)) {
                x += speed;
            } else {
                int xnext = (int) ((x + speed) / 16) + 1;
                int ynext = (int) (y / 16);

                if (World.tiles[xnext + ynext * World.WIDTH] instanceof WallTile && !World.tiles[xnext + ynext * World.WIDTH].solid) {
                    World.tiles[xnext + ynext * World.WIDTH] = new FloorTile(xnext * 16, ynext * 16, Tile.TILE_AR);
                }

                dir = -1;
            }
        } else if (dir == -1) {
            if (World.isFree((int) (x - speed), (int) y)) {
                x -= speed;
            } else {
                int xnext = (int) ((x - speed) / 16);
                int ynext = (int) (y / 16);

                if (World.tiles[xnext + ynext * World.WIDTH] instanceof WallTile && !World.tiles[xnext + ynext * World.WIDTH].solid) {
                    World.tiles[xnext + ynext * World.WIDTH] = new FloorTile(xnext * 16, ynext * 16, Tile.TILE_AR);
                }

                dir = 1;
            }
        }

        if (vida == 0) {
            Game.entities.remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        framesAnimation++;

        if (framesAnimation == maxFrames) {
            curSprite++;
            framesAnimation = 0;

            if (curSprite == maxSprite) {
                curSprite = 0;
            }
        }

        sprite = Entity.ENEMY1[curSprite];

        super.render(g);

        int curlife = (int)((vida/ maxVida) * 20);
        g.setColor(Color.red);
        g.fillRect(getX() - 4 - Camera.x, this.getY() - 8 - Camera.y, 20, 7);

        g.setColor(Color.green);
        g.fillRect(getX() - 4 - Camera.x, this.getY() - 8 - Camera.y, curlife, 7);
    }
}
