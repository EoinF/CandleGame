package com.github.ddtc

import com.badlogic.gdx.graphics.Texture

class TextureManager {
    fun dispose() {
        player.dispose()
        block.dispose()
    }

    val player: Texture = Texture("Player.png")
    val block: Texture = Texture("tile0-light.png")
    val mapLayout: Texture = Texture("map0.png") // replace me with a map 40x30!
}