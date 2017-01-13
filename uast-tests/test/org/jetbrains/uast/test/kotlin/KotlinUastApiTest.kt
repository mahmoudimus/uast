package org.jetbrains.uast.test.kotlin

import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UFile
import org.jetbrains.uast.test.env.findElementByText
import org.junit.Test


class KotlinUastApiTest : AbstractKotlinUastTest() {
    override fun check(testName: String, file: UFile) {
    }

    @Test fun testAnnotationParameters() {
        doTest("AnnotationParameters") { name, file ->
            val annotation = file.findElementByText<UAnnotation>("@IntRange(from = 10, to = 0)")
            assertEquals(annotation.findAttributeValue("from")?.expression?.evaluate(), 10)
            assertEquals(annotation.findAttributeValue("to")?.expression?.evaluate(), 0)
        }
    }
}

