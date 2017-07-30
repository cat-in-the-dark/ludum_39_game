package com.catinthedark.vvtf.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture

inline fun <reified T> AssetManager.load(fileName: String) {
    return load(fileName, T::class.java)
}

inline fun <reified T> AssetManager.isLoadedByType(fileName: String): Boolean {
    return isLoaded(fileName, T::class.java)
}

fun AssetManager.isLoaded(path: Assets.TexturePaths): Boolean {
    return isLoaded(path.path, Texture::class.java)
}

inline fun <reified T> AssetManager.getByType(fileName: String): T {
    return get(fileName, T::class.java)
}

fun AssetManager.get(path: Assets.TexturePaths): Texture {
    return get(path.path, Texture::class.java)
}
