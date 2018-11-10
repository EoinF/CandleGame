package com.github.ddtc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.github.ddtc.CandleGame.Companion.PIXELS_TO_METERS
import com.github.ddtc.CandleGame.Companion.PLAYER_ENTITY
import com.github.ddtc.CandleGame.Companion.PLAYER_FEET_ENTITY
import com.github.ddtc.CandleGame.Companion.numPlayerContacts


class Player(
        private val sprite: Sprite,
        position: Vector2,
        world: World
) {
    val body: Body
    var isHoldingCandle = false

    private fun origin() = Vector2(sprite.x + sprite.originX, sprite.y + sprite.originY)

    init {
        sprite.setPosition(position.x, position.y)
        sprite.setOriginCenter()

        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(origin().x / PIXELS_TO_METERS, origin().y / PIXELS_TO_METERS)

        // Create a body in the world using our definition
        body = world.createBody(bodyDef)

        // Add main player fixture
        val shape = PolygonShape()
        shape.setAsBox(sprite.width / 2 / PIXELS_TO_METERS, sprite.height / 2 / PIXELS_TO_METERS)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.filter.categoryBits = PLAYER_ENTITY

        body.createFixture(fixtureDef)

        //add foot sensor fixture
        shape.setAsBox(sprite.width / 2 / PIXELS_TO_METERS, sprite.height / 2 / PIXELS_TO_METERS,
                Vector2(0f,0f), 0f)
        fixtureDef.isSensor = true
        fixtureDef.filter.categoryBits = PLAYER_FEET_ENTITY
        body.createFixture(fixtureDef)

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose()
    }

    fun draw(batch: Batch) {
        sprite.draw(batch)
    }

    fun update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && numPlayerContacts > 0
                && body.linearVelocity.y < 3f) {
            body.applyForceToCenter(Vector2(0f, 60f), true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            body.setLinearVelocity(body.linearVelocity.x - 0.2f, body.linearVelocity.y)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            body.applyForceToCenter(Vector2(0f, -2f), true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            body.setLinearVelocity(body.linearVelocity.x + 0.2f, body.linearVelocity.y)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isHoldingCandle = !isHoldingCandle
        }

        sprite.setPosition((body.position.x * PIXELS_TO_METERS) - sprite.originX,
                (body.position.y * PIXELS_TO_METERS) - sprite.originY)
    }
}