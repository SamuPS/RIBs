package com.badoo.ribs.clienthelper.connector

import com.badoo.ribs.core.Node
import com.badoo.ribs.core.helper.TestNode
import com.badoo.ribs.core.plugin.SubtreeChangeAware
import com.badoo.ribs.test.helper.connectable.ConnectableTestNode
import com.badoo.ribs.test.helper.connectable.ConnectableTestRib
import com.badoo.ribs.test.helper.connectable.ConnectableTestRib.Output.Output1
import com.badoo.ribs.test.helper.connectable.ConnectableTestRib.Output.Output2
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test


class ConnectableNodeTest {

    private lateinit var parent: TestNode
    private val testObserver = TestObserver<ConnectableTestRib.Output>()
    private val changesAwarePlugin = object : SubtreeChangeAware {
        override fun onChildAttached(child: Node<*>) {
            (child as ConnectableTestNode).output.observe {
                testObserver.onNext(it)
            }
        }
    }

    @Before
    fun setUp() {
        parent = TestNode(viewFactory = null, plugins = listOf(changesAwarePlugin))
    }

    @After
    fun tearDown() {
        testObserver.dispose()
    }

    @Test
    fun `WHEN child emit some output before it is attached to parent THEN parent receive the output after child attach finished`() {
        val children = ConnectableTestNode(parent = parent)

        children.output.emit(Output1)
        parent.attachChildNode(children)

        testObserver.assertValue(Output1)
    }

    @Test
    fun `WHEN child is attached and emit some output THEN parent receive the exact output`() {
        val children = ConnectableTestNode(parent = parent)

        parent.attachChildNode(children)
        children.output.emit(Output1)

        testObserver.assertValueCount(1)
        testObserver.assertValue(Output1)
    }

    @Test
    fun `WHEN child emit multiple outputs before it is attached to parent THEN parent receive the output after attach finished in correct order`() {
        val children = ConnectableTestNode(parent = parent)

        children.output.emit(Output1)
        children.output.emit(Output2)
        parent.attachChildNode(children)

        testObserver.assertValueAt(0, Output1)
        testObserver.assertValueAt(1, Output2)
    }

    @Test
    fun `WHEN child emit output before it is attached to parent and then after it is attached to parent THEN parent receive the output in correct order`() {
        val children = ConnectableTestNode(parent = parent)

        children.output.emit(Output1)
        parent.attachChildNode(children)
        children.output.emit(Output2)

        testObserver.assertValueAt(0, Output1)
        testObserver.assertValueAt(1, Output2)
    }
}