package org.jetbrains.uast

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiModifierListOwner
import org.jetbrains.uast.visitor.UastTypedVisitor

/**
 * A [PsiElement] declaration wrapper.
 */
interface UDeclaration : UElement, PsiModifierListOwner, UAnnotated {
    /**
     * Returns the original declaration (which is *always* unwrapped, never a [UDeclaration]).
     */
    override val psi: PsiModifierListOwner
    
    override fun getOriginalElement(): PsiElement? = psi.originalElement

    /**
     * Returns the declaration name identifier, or null if the declaration is anonymous.
     */
    val uastAnchor: UElement?

    /**
     * Returns `true` if this declaration has a [PsiModifier.STATIC] modifier.
     */
    val isStatic: Boolean
        get() = hasModifierProperty(PsiModifier.STATIC)

    /**
     * Returns `true` if this declaration has a [PsiModifier.FINAL] modifier.
     */
    val isFinal: Boolean
        get() = hasModifierProperty(PsiModifier.FINAL)

    /**
     * Returns a declaration visibility.
     */
    val visibility: UastVisibility
        get() = UastVisibility[this]

    override fun <D, R> accept(visitor: UastTypedVisitor<D, R>, data: D) = visitor.visitDeclaration(this, data)
}

fun UElement.getContainingDeclaration() = withContainingElements.filterIsInstance<UDeclaration>().firstOrNull()
