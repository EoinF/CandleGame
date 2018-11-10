package com.github.ddtc;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*


class CandleGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var map: Map
    private lateinit var player: Player
    private var sheeps: ArrayList<Sheep> = ArrayList()
    private lateinit var textureManager: TextureManager

    private lateinit var world: World

    private lateinit var debugRenderer: Box2DDebugRenderer

    companion object {
        const val PIXELS_TO_METERS = 100f
        const val SHEEP_ENTITY: Short = 0x1 // 0001
        const val WORLD_ENTITY: Short = 0x2 // 0010 or 0x2 in hex
        const val PLAYER_ENTITY: Short = 0x4
        const val PLAYER_FEET_ENTITY: Short = 0x8
        var numPlayerContacts = 0
        var timeToSpawn = 0f
    }

    override fun create() {
        batch = SpriteBatch()

        textureManager = TextureManager()
        world = World(Vector2(0f, -9.8f), true)
        debugRenderer = Box2DDebugRenderer()

        val defaultPosition = Vector2(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)

        player = Player(textureManager.playerWalk, textureManager.playerWalkCandle, defaultPosition, world)

        val sheepCount = 2
        val sheepGap = Gdx.graphics.width/(sheepCount+1)
        for (x in 1..sheepCount) {
            sheeps.add(Sheep(textureManager.sheepWalk, textureManager.sheepWalkCandle, Vector2((sheepGap*x).toFloat(), Gdx.graphics.height / 2f), world))
        }

        map = Map(textureManager, world)

        world.setContactListener(object : ContactListener {
            override fun beginContact(contact: Contact) {
                if (isFeetColliding(contact)) {
                    numPlayerContacts++
                }

                if (isSheepColliding(contact)) {
                    if (player.isHoldingCandle) {
                        player.isHoldingCandle = false
                    } else {
                        player.isAlive = false
                    }
                }
            }

            override fun endContact(contact: Contact) {
                if (isFeetColliding(contact)) {
                    numPlayerContacts--
                }
            }

            override fun preSolve(contact: Contact, oldManifold: Manifold) {}

            override fun postSolve(contact: Contact, impulse: ContactImpulse) {}
        })
    }

    private var debugMatrix: Matrix4? = null

    override fun render() {
        timeToSpawn += Gdx.graphics.deltaTime

        println(timeToSpawn)
        if (timeToSpawn > 4) {
            sheeps.add(Sheep(textureManager.sheepWalk, textureManager.sheepWalkCandle,
                    Vector2(Gdx.graphics.width/ 2f, Gdx.graphics.height.toFloat()), world))
            timeToSpawn = 0f
        }

        if (player.isAlive) {
            if (player.isHoldingCandle) {
                Gdx.gl.glClearColor(58/255f, 79/255f, 98/255f, 1f) // dream background
            } else {
                Gdx.gl.glClearColor(0.15f, 0.2f, 0.15f, 1f) // nightmare background
            }
        } else {
            Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(Gdx.graphics.deltaTime, 6, 2)
        player.update()
        for(sheep in sheeps) {
            sheep.update()
        }

        debugMatrix = batch.projectionMatrix.cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0f)

        batch.begin()
        if (player.isAlive) {
            player.draw(batch)
        }
        map.draw(batch, player.isHoldingCandle)
        for (sheep in sheeps) {
            sheep.draw(batch, player.isHoldingCandle)
        }
        batch.end()

        //debugRenderer.render(world, debugMatrix)
    }

    override fun dispose() {
        batch.dispose()
        textureManager.dispose()
    }

}
