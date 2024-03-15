package com.keyboard.myanglish.input.wm

import android.view.View
import org.mechdancer.dependency.Dependent
import org.mechdancer.dependency.IUniqueComponent
import org.mechdancer.dependency.ScopeEvent
import org.mechdancer.dependency.manager.DependencyManager
import kotlin.reflect.KClass

sealed class InputWindow : Dependent {
    protected val manager: DependencyManager = DependencyManager()

    final override fun handle(scopeEvent: ScopeEvent) = manager.handle(scopeEvent)

    abstract class ExtendedInputWindow<T : ExtendedInputWindow<T>> : IUniqueComponent<T>,
        InputWindow() {

        open val showTitle: Boolean = true

        open val title: String = ""

        open fun onCreateBarExtension(): View? {
            return null
        }

        override val type: KClass<out IUniqueComponent<*>> by lazy { defaultType() }

        override fun equals(other: Any?): Boolean = defaultEquals(other)

        override fun hashCode(): Int = defaultHashCode()

        override fun toString(): String = javaClass.name

    }
}