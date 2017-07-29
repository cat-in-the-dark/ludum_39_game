package org.catinthedark.shared.serialization

import com.esotericsoftware.kryo.Kryo
import org.reflections.Reflections
import org.slf4j.LoggerFactory
import java.io.Serializable
import kotlin.reflect.KClass

object KryoCustomizer {
    val log = LoggerFactory.getLogger(this::class.java)

    fun register(kryo: Kryo, vararg klasses: KClass<*>) {
        klasses.forEach {
            kryo.register(it.java, ImmutableClassSerializer(it))
            log.info("REGISTER class ${it.qualifiedName}")
        }
    }

    fun register(kryo: Kryo, klasses: Iterable<KClass<*>>) {
        klasses.forEach {
            kryo.register(it.java, ImmutableClassSerializer(it))
            log.info("REGISTER class ${it.qualifiedName}")
        }
    }

    fun buildAndRegister(packageName: String): Kryo {
        val klasses = Reflections(packageName).getSubTypesOf(Serializable::class.java)
        val kryo = Kryo()
        register(kryo, klasses.map { it.kotlin })
        return kryo
    }
}