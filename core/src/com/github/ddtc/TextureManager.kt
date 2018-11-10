package com.github.ddtc

import com.badlogic.gdx.graphics.Texture

class TextureManager {
    fun dispose() {
        player.dispose()
        blockLight.dispose()
        blockDark.dispose()
        playerWalk.dispose()
        playerWalkCandle.dispose()
        sheepWalk.dispose()
        sheepWalkCandle.dispose()
        mapLayout.dispose()
    }

    val player: Texture = Texture("Player.png")
    val blockLight: Texture = Texture("tile0-light.png")
    val blockDark: Texture = Texture("tile0-dark.png")
    val playerWalk: Texture = Texture("Player-walk.png")
    val playerWalkCandle: Texture = Texture("Player-walk-candle.png")
    val sheepWalk: Texture = Texture("Enemy0-walk.png")
    val sheepWalkCandle: Texture = Texture("Enemy1-walk.png")
    val mapLayout: Texture = Texture("map0.png") // must be 40x30! (view-port/ tile-size)
}