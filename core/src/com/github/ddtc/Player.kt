package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.FixtureDef


class Player(
        private val sprite: Sprite,
        position: Vector2,
        world: World
) {
    val body: Body

    init {
        sprite.setPosition(position.x, position.y)

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(sprite.x, sprite.y)

        // Create a body in the world using our definition
        body = world.createBody(bodyDef)

        // Now define the dimensions of the physics shape
        val shape = PolygonShape()
        shape.setAsBox(sprite.width / 2, sprite.height / 2)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        val fixture = body.createFixture(fixtureDef)

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose()
    }

    fun draw(batch: Batch) {
        sprite.draw(batch)
    }

    fun update() {
        sprite.setPosition(body.position.x, body.position.y)
    }
}