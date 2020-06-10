package com.zegames.main;

import com.zegames.entities.Enemy;
import com.zegames.entities.Entity;
import com.zegames.world.World;

public class EnemySpawn {
    public int interval = 60 * 10;
    public int curTime = 0;

    public void tick() {
        curTime++;

        if (curTime == interval) {
            curTime = 0;
            int xInitial = Entity.rand.nextInt((World.WIDTH/2) * 16 - 16 -16) + 16;
            Enemy enemy = new Enemy(xInitial, 16, 16, 16, 1, Entity.ENEMY1[0]);
            Game.entities.add(enemy);
        }
    }
}
