package com.badoo.ribs.sandbox

import android.view.ViewGroup
import com.badoo.ribs.core.Node
import com.badoo.ribs.core.view.AndroidRibView


abstract class CoordinatedAndroidView constructor(
    override val androidView: ViewGroup,
    val coordinatorViewGroup: ViewGroup,
    private val coordinatedContainer: ViewGroup
) : AndroidRibView() {

    constructor(
        androidView: ViewGroup,
        coordinatorViewGroup: ViewGroup,
        coordinatedSubTreeParentId: Int
    ) : this(
        androidView,
        coordinatorViewGroup,
        coordinatorViewGroup.findViewById(coordinatedSubTreeParentId)
    )

    override fun getParentViewForSubtree(subtreeOf: Node<*>): ViewGroup = coordinatedContainer
}


class RootCoordinatorView(androidView: ViewGroup, coordinatedContainerId: Int) :
    CoordinatedAndroidView(androidView,
        androidView,
        androidView.findViewById(coordinatedContainerId)
    )