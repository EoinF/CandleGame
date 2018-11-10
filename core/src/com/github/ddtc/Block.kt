package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.github.ddtc.CandleGame.Companion.PIXELS_TO_METERS

class Block(
        private val texture: Texture,
        position: Vector2,
        world: World
) {
    val sprite: Sprite
    val body: Body
    val box: PolygonShape
    val blockWidth = 32f
    val blockHeight = 32f
    val origin = Vector2(position.x + blockWidth/2, position.y+blockHeight/2)

    init {
        sprite = Sprite(texture)
        sprite.setPosition(position.x, position.y)
        sprite.setOriginCenter()

        val bodyDef = BodyDef()
        bodyDef.position.set(origin.x/ PIXELS_TO_METERS, origin.y/PIXELS_TO_METERS)
        body = world.createBody(bodyDef)
        box = PolygonShape()
        box.setAsBox(blockWidth/2/ PIXELS_TO_METERS, blockHeight/2/ PIXELS_TO_METERS)
        body.createFixture(box, 0.0f);
        box.dispose();
    }

    fun draw(batch: Batch) {
        sprite.draw(batch)
    }
}