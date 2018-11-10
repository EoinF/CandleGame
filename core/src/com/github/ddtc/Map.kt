package com.github.ddtc

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

class Map(
        private val blockTexture: Texture,
        private val mapTexture: Texture,
        val world: World
) {
    val blocks: ArrayList<Block> = ArrayList()

    init {
        loadBlocks()
    }

    fun draw(batch: Batch) {
        for(block in blocks) {
            block.draw(batch)
        }
    }

    fun loadBlocks() {
        val textureData = mapTexture.getTextureData()
        textureData.prepare()
        val layout = textureData.consumePixmap()
        for(x in 1..layout.getHeight()) {
            for(y in 1..layout.getWidth()) {
                val b = layout.getPixel(x,y)
                if(b > 0) {
                    blocks.add(Block(blockTexture, Vector2(x*32f, y*32f), world))
                }
            }
        }
    }
}