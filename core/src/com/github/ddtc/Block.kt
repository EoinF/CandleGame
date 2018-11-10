package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.PolygonShape

class Block(
        private val texture: Texture,
        x: Float,
        y: Float,
        world: World
) {
    val sprite: Sprite
    val body: Body
    val box: PolygonShape
    val blockWidth = 32f
    val blockHeight = 32f


    init {
        sprite = Sprite(texture)
        sprite.setPosition(x, y)

        val bodyDef = BodyDef()
        bodyDef.position.set(Vector2(x,y))
        body = world.createBody(bodyDef)
        box = PolygonShape()
        box.setAsBox(blockWidth, blockHeight)
        body.createFixture(box, 0.0f);
        box.dispose();
    }

    fun draw(batch: Batch) {
        sprite.draw(batch)
    }
}