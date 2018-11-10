package com.github.ddtc

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.PolygonShape

class Map(world: World) {
    val platformBody: Body
    val platformBox: PolygonShape

    init {
        val platformDef = BodyDef()
        platformDef.position.set(Vector2(0f, 10f))
        platformBody = world.createBody(platformDef)
        platformBox = PolygonShape()
        platformBox.setAsBox(1000f, 10.0f)  // camera.viewPortWidth
        platformBody.createFixture(platformBox, 0.0f);
        platformBox.dispose();
    }
}