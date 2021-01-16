package com.zombie.client.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.zombie.client.box2d.UserData;
import com.zombie.client.enums.UserDataType;

public class BodyUtils {

    public static boolean bodyIsDefender(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.DEFENDER;
    }

    public static boolean bodyIsBullet(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.BULLET;
    }

    public static boolean bodyInBounds(Object body) {
        UserData userData = (UserData) ((Body)body).getUserData();
        switch (userData.getUserDataType()) {
            case DEFENDER:
            case ENEMY:
                return ((Body)body).getPosition().x > 0;
            case BULLET:
                return ((Body)body).getPosition().x + userData.getWidth() / 2 < Constants.VIEWPORT_WIDTH;
        }
        return true;
    }

    public static boolean bodyIsEnemy(Body body) {
        UserData userData = (UserData) body.getUserData();

        return userData != null && userData.getUserDataType() == UserDataType.ENEMY;
    }

}