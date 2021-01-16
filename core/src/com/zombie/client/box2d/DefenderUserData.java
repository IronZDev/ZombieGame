package com.zombie.client.box2d;

import com.zombie.client.enums.UserDataType;

import com.badlogic.gdx.math.Vector2;
import com.zombie.client.utils.Constants;

public class DefenderUserData extends UserData {

    private Vector2 linearVelocity;

    public DefenderUserData(float width, float height) {
        super(width, height);
        userDataType = UserDataType.DEFENDER;
        linearVelocity = Constants.DEFENDER_LINEAR_VELOCITY;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }
}