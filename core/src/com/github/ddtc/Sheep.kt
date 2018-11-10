package com.github.ddtc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.github.ddtc.CandleGame.Companion.PIXELS_TO_METERS
import java.util.*

class Sheep (
        private val walkingLight: Texture,
        private val walkingDark: Texture,
        var position: Vector2,
        world: World
) {
    val body: Body
    val height = 48
    val width = 48
    var walkTime: Float
    var walkingLightAnimation: Animation<TextureRegion>
    var walkingDarkAnimation: Animation<TextureRegion>
    var direction = 1

    init {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set((position.x + width/2) / PIXELS_TO_METERS, (position.y + height/2) / PIXELS_TO_METERS)
        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / PIXELS_TO_METERS, height / 2 / PIXELS_TO_METERS)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f
        fixtureDef.filter.categoryBits = CandleGame.SHEEP_ENTITY

        body.createFixture(fixtureDef)

        shape.dispose()

        walkTime = 0f
        walkingLightAnimation = createAnimation(walkingLight)
        walkingDarkAnimation = createAnimation(walkingDark)
    }
    private fun createAnimation(animationTexture: Texture): Animation<TextureRegion> {
        val FRAME_COLS = 2
        val FRAME_ROWS = 2

        val tmp = TextureRegion.split(animationTexture,
                animationTexture.width / FRAME_COLS,
                animationTexture.height / FRAME_ROWS)

        val walkFrames = Array<TextureRegion>(FRAME_COLS * FRAME_ROWS)
        var index = 0
        for (i in 0 until FRAME_ROWS) {
            for (j in 0 until FRAME_COLS) {
                walkFrames.insert(index++, tmp[i][j])
            }
        }

        return Animation(0.1f, walkFrames)
    }

    fun draw(batch: Batch, isBright: Boolean) {
        walkTime += Gdx.graphics.deltaTime // Accumulate elapsed animation time

        val isFacingRight = body.linearVelocity.x <= 0

        // Get current frame of animation for the current stateTime
        val walkingAnimation = if (isBright) {
            walkingLightAnimation
        } else {
            walkingDarkAnimation
        }
        val currentFrame = walkingAnimation.getKeyFrame(walkTime, true)
        if (currentFrame.isFlipX) {
            currentFrame.flip(isFacingRight, false)
        } else {
            currentFrame.flip(!isFacingRight, false)
        }

        batch.draw(currentFrame, position.x, position.y)
    }

    fun update() {
        // jump randomly
        if (Random().nextDouble() > 0.95 && body.linearVelocity.y < 3f && position.y < Gdx.graphics.height-32) {
            body.applyForceToCenter(Vector2(0f, 30f), true)
        }

        // randomly change direction
        if (Random().nextDouble() > 0.8) {
            direction *= -1
        }

        // walk in direction
        body.setLinearVelocity( body.linearVelocity.x + direction * 0.2f, body.linearVelocity.y)

        position = Vector2((body.position.x * CandleGame.PIXELS_TO_METERS) - width/2,
                (body.position.y * CandleGame.PIXELS_TO_METERS) - height/2)
    }
}