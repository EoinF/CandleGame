package com.github.ddtc;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World



class CandleGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var map: Map
    private lateinit var player: Player
    private lateinit var textureManager: TextureManager

    private lateinit var world: World

    override fun create() {
        batch = SpriteBatch()
        textureManager = TextureManager()
        world = World(Vector2(0f, -98f), true)

        val defaultPosition = Vector2(Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)

        player = Player(Sprite(textureManager.player), defaultPosition, world)

        map = Map(world)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(Gdx.graphics.deltaTime, 6, 2)
        player.update()

        batch.begin()
        player.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        textureManager.dispose()
    }
}
