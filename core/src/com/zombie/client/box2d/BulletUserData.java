package com.zombie.client.box2d;

import com.badlogic.gdx.math.Vector2;
import com.zombie.client.enums.UserDataType;
import com.zombie.client.utils.Constants;

public class BulletUserData extends UserData {

    private Vector2 linearVelocity;

    public BulletUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.BULLET;
        linearVelocity = Constants.BULLET_LINEAR_VELOCITY;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }
}