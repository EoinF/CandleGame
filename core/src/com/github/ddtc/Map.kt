package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.math.Vector2

class Map(
        private val textureManager: TextureManager,
        val world: World
) {
    val blocks: ArrayList<Block> = ArrayList()

    init {
        loadBlocks()
    }

    fun draw(batch: Batch, isBright: Boolean) {
        for(block in blocks) {
            block.draw(batch, isBright)
        }
    }

    fun loadBlocks() {
        val textureData = textureManager.mapLayout.getTextureData()
        textureData.prepare()
        val layout = textureData.consumePixmap()
        for(x in 0..layout.getWidth()) {
            for(y in 0..layout.getHeight()) {
                val b = layout.getPixel(x,layout.getHeight()-y-1) // why -1?!
                val blockMarker = 255
                if(b == blockMarker) {
                    blocks.add(Block(textureManager.blockLight, textureManager.blockDark, Vector2(x*32f, y*32f), world))
                }
            }
        }
    }
}