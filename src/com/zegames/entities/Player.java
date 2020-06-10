package com.zegames.entities;

import com.zegames.main.Game;
import com.zegames.world.Camera;
import com.zegames.world.World;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    public boolean right, left;
    private double gravity = 2;
    public int dir = 1;
    public boolean jump = false;
    public boolean isJumping = false;
    public int jumpHeight = 32;
    public int jumpFrames = 0;
    private int framesAnimation = 0;
    private int maxFrames = 5;
    private int maxSprite = 3;
    private int curSprite = 0;
    private boolean moved = false;
    public double life = 100;

    public BufferedImage ATTACK_RIGHT;
    public BufferedImage ATTACK_LEFT;

    public boolean attack = false;
    public boolean isAttacking = false;
    public int attackFrames = 0;
    public int maxFramesAttack = 10;

    public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
        super(x, y, width, height,speed,sprite);

        ATTACK_RIGHT = Game.spritesheet.getSprite(80, 0, 16, 16);
        ATTACK_LEFT = Game.spritesheet.getSprite(96, 0, 16, 16);
    }

    public void setIdle() {
        this.curSprite = 0;
    }

    public void tick() {
        depth = 2;
        moved = false;

        if (World.isFree(this.getX(), (int) (this.getY() + gravity)) && !isJumping) {
            y += gravity;
        }

        if (right && World.isFree((int) (x + speed), this.getY())) {
            x += speed;
            dir = 1;
            moved = true;

        } else if (left && World.isFree((int) (x - speed), this.getY())) {
            if ((x - speed) < 0) {
                return;
            }

            x -= speed;
            dir = -1;
            moved = true;
        }

        if (jump) {
            if (!World.isFree(this.getX(), this.getY() + 1)) {
                isJumping = true;
            } else {
                jump = false;
            }
        }

        if (isJumping) {
            if (y - 2 <= 0) {
                isJumping = false;
                jump = false;
                jumpFrames = 0;
            } else {
                if (World.isFree(this.getX(), (int) (this.getY() - 2))) {
                    y -= 2;
                    jumpFrames += 2;

                    if (jumpFrames == jumpHeight) {
                        isJumping = false;
                        jump = false;
                        jumpFrames = 0;
                    }
                } else {
                    isJumping = false;
                    jump = false;
                    jumpFrames = 0;
                }
            }
        }

        if (attack) {
            if (!isAttacking) {
                attack = false;
                isAttacking = true;
            }
        }

        if (isAttacking) {
            attackFrames++;

            if (attackFrames == maxFramesAttack) {
                attackFrames = 0;
                isAttacking = false;
            }
        }

        collisionEnemy();

        Camera.x = Camera.clamp(this.getX() - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
    }

    private void collisionEnemy() {
        for (int i = 0; i < Game.entities.size(); i++) {
            Entity entity = Game.entities.get(i);

            if (entity instanceof Enemy) {
                if (Entity.rand.nextInt(100) < 30) {
                    if (Entity.isColidding(this, entity)) {
                        if (isAttacking) {
                            ((Enemy) entity).vida--;
                        } else {
                            life -= 0.3;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (moved) {
            framesAnimation++;

            if (framesAnimation == maxFrames) {
                curSprite++;
                framesAnimation = 0;

                if (curSprite == maxSprite) {
                    curSprite = 0;
                }
            }
        }

        if (dir == 1) {
            sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];

            if (isAttacking) {
                g.drawImage(ATTACK_RIGHT, this.getX() + 8 - Camera.x, this.getY() - Camera.y, null);
            }
        } else if (dir == -1) {
            sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];

            if (isAttacking) {
                g.drawImage(ATTACK_LEFT, this.getX() - 8 - Camera.x, this.getY() - Camera.y, null);
            }
        }


        super.render(g);
    }
}
