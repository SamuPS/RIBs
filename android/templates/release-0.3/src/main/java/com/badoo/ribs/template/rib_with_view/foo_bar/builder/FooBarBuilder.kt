package com.badoo.ribs.template.rib_with_view.foo_bar.builder

import com.badoo.ribs.core.Builder
import com.badoo.ribs.core.Node
import com.badoo.ribs.customisation.customisationsBranchFor
import com.badoo.ribs.customisation.getOrDefault
import com.badoo.ribs.template.rib_with_view.foo_bar.FooBar
import com.badoo.ribs.template.rib_with_view.foo_bar.FooBarView

class FooBarBuilder(
    dependency: FooBar.Dependency
) : Builder<FooBar.Dependency>() {

    override val dependency : FooBar.Dependency = object : FooBar.Dependency by dependency {
        override fun ribCustomisation() = dependency.customisationsBranchFor(FooBar::class)
    }

    fun build(): Node<FooBarView> =
        DaggerFooBarComponent
            .factory()
            .create(dependency, dependency.getOrDefault(FooBar.Customisation()))
            .node()
}