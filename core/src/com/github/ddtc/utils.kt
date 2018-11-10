package com.github.ddtc

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Contact

fun isFeetColliding(contact: Contact): Boolean {
    var playerEntity: Body? = null
    var worldEntity: Body? = null
    if (contact.fixtureA.filterData.categoryBits == CandleGame.PLAYER_FEET_ENTITY) {
        playerEntity = contact.fixtureA.body
    } else if (contact.fixtureB.filterData.categoryBits == CandleGame.PLAYER_FEET_ENTITY) {
        playerEntity = contact.fixtureB.body
    }
    if (contact.fixtureA.filterData.categoryBits == CandleGame.WORLD_ENTITY) {
        worldEntity = contact.fixtureA.body
    } else if (contact.fixtureB.filterData.categoryBits == CandleGame.WORLD_ENTITY) {
        worldEntity = contact.fixtureB.body
    }
    return playerEntity != null && worldEntity != null
}

fun isSheepColliding(contact: Contact): Boolean {
    var playerEntity: Body? = null
    var sheepEntity: Body? = null
    if (contact.fixtureA.filterData.categoryBits == CandleGame.PLAYER_FEET_ENTITY) {
        playerEntity = contact.fixtureA.body
    } else if (contact.fixtureB.filterData.categoryBits == CandleGame.PLAYER_FEET_ENTITY) {
        playerEntity = contact.fixtureB.body
    }
    if (contact.fixtureA.filterData.categoryBits == CandleGame.SHEEP_ENTITY) {
        sheepEntity = contact.fixtureA.body
    } else if (contact.fixtureB.filterData.categoryBits == CandleGame.SHEEP_ENTITY) {
        sheepEntity = contact.fixtureB.body
    }
    return playerEntity != null && sheepEntity != null
}