package com.github.ddtc

import com.badlogic.gdx.graphics.Texture

class TextureManager {
    fun dispose() {
        player.dispose()
        block.dispose()
    }

    val player: Texture = Texture("Player.png")
    val block: Texture = Texture("block.png")
}