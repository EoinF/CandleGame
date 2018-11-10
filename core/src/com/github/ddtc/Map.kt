package com.github.ddtc

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

class Map(
        private val texture: Texture,
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
        for(i in 1..16) {
            blocks.add(Block(texture, Vector2(i*32f, 10f), world))
        }
    }
}