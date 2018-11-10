package com.github.ddtc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.github.ddtc.CandleGame.Companion.PIXELS_TO_METERS


class Player(
        private val sprite: Sprite,
        position: Vector2,
        world: World
) {
    val body: Body

    private fun origin() = Vector2(sprite.x + sprite.originX, sprite.y + sprite.originY)

    init {
        sprite.setPosition(position.x, position.y)
        sprite.setOriginCenter()

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(origin().x / PIXELS_TO_METERS, origin().y / PIXELS_TO_METERS)

        // Create a body in the world using our definition
        body = world.createBody(bodyDef)

        // Now define the dimensions of the physics shape
        val shape = PolygonShape()
        shape.setAsBox(sprite.width / 2 / PIXELS_TO_METERS, sprite.height / 2 / PIXELS_TO_METERS)

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
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter(Vector2(0f, 10f), true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.applyForceToCenter(Vector2(-10f, 0f), true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.applyForceToCenter(Vector2(0f, -10f), true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.applyForceToCenter(Vector2(10f, 0f), true)
        }
        sprite.setPosition((body.position.x * PIXELS_TO_METERS) - sprite.originX,
                (body.position.y * PIXELS_TO_METERS) - sprite.originY)
    }
}