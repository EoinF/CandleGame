package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.math.Vector2

class Map(
        private val textureManager: TextureManager,
        val world: World
) {
    val blocks: ArrayList<Block> = ArrayList()
    lateinit var player: Player
    val blockMarker = 255
    val playerMarker = -16776961 // red

    init {
        loadLayout()
    }

    fun draw(batch: Batch, isBright: Boolean) {
        for(block in blocks) {
            block.draw(batch, isBright)
        }
    }

    fun loadLayout() {
        val textureData = textureManager.mapLayout.getTextureData()
        textureData.prepare()
        val layout = textureData.consumePixmap()
        for(x in 0..layout.getWidth()) {
            for(y in 0..layout.getHeight()) {
                val element = layout.getPixel(x,layout.getHeight()-y)

                if(element == blockMarker) {
                    blocks.add(Block(textureManager.blockLight, textureManager.blockDark, Vector2(x*32f, (y-1)*32f), world))
                }

                if(element == playerMarker) {
                    player = Player(textureManager.playerWalk, textureManager.playerWalkCandle, Vector2(x*32f, (y-1)*32f), world)
                }
            }
        }
    }
}