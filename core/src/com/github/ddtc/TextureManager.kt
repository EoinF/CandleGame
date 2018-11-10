package com.github.ddtc

import com.badlogic.gdx.graphics.Texture

class TextureManager {
    fun dispose() {
        player.dispose()
        blockLight.dispose()
        blockDark.dispose()
        mapLayout.dispose()
    }

    val player: Texture = Texture("Player.png")
    val blockLight: Texture = Texture("tile0-light.png")
    val blockDark: Texture = Texture("tile0-dark.png")
    val mapLayout: Texture = Texture("map0.png") // replace me with a map 40x30!
}