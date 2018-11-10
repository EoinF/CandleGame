package com.github.ddtc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.github.ddtc.CandleGame.Companion.PIXELS_TO_METERS
import com.github.ddtc.CandleGame.Companion.PLAYER_ENTITY
import com.github.ddtc.CandleGame.Companion.PLAYER_FEET_ENTITY
import com.github.ddtc.CandleGame.Companion.numPlayerContacts
import com.badlogic.gdx.graphics.g2d.TextureRegion


class Player(
        private val walkSheet: Texture,
        var position: Vector2,
        world: World
) {
    val body: Body
    var isHoldingCandle = false
    var walkTime: Float
    var walkAnimation: Animation<TextureRegion>
    val playerHeight = 48
    val playerWidth = 48

    private fun origin() = Vector2(position.x + playerWidth/2, position.y + playerHeight/2)

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(origin().x / PIXELS_TO_METERS, origin().y / PIXELS_TO_METERS)

        // Create a body in the world using our definition
        body = world.createBody(bodyDef)

        // Add main player fixture
        val shape = PolygonShape()
        shape.setAsBox(playerWidth / 2 / PIXELS_TO_METERS, playerHeight / 2 / PIXELS_TO_METERS)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.filter.categoryBits = PLAYER_ENTITY

        body.createFixture(fixtureDef)

        //add foot sensor fixture
        shape.setAsBox(playerWidth / 2 / PIXELS_TO_METERS, playerHeight / 2 / PIXELS_TO_METERS,
                Vector2(0f,0f), 0f)
        fixtureDef.isSensor = true
        fixtureDef.filter.categoryBits = PLAYER_FEET_ENTITY
        body.createFixture(fixtureDef)

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose()

        walkTime = 0f;
        val FRAME_COLS = 2
        val FRAME_ROWS = 2

        val tmp = TextureRegion.split(walkSheet,
                walkSheet.width / FRAME_COLS,
                walkSheet.height / FRAME_ROWS)

        val walkFrames = com.badlogic.gdx.utils.Array<TextureRegion>(FRAME_COLS * FRAME_ROWS)
        var index = 0
        for (i in 0 until FRAME_ROWS) {
            for (j in 0 until FRAME_COLS) {
                walkFrames.insert(index++, tmp[i][j])
            }
        }

        walkAnimation = Animation<TextureRegion>(0.1f, walkFrames)
    }

    fun draw(batch: Batch) {
        walkTime += Gdx.graphics.deltaTime // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        val currentFrame = walkAnimation.getKeyFrame(walkTime, true)
        batch.draw(currentFrame, position.x, position.y)
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

        position = Vector2((body.position.x * PIXELS_TO_METERS) - playerWidth/2,
                (body.position.y * PIXELS_TO_METERS) - playerHeight/2)
    }
}