package com.badoo.ribs.sandbox.rib.tabs

import com.badoo.ribs.builder.SimpleBuilder
import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.routing.source.RoutingSource
import com.badoo.ribs.routing.source.backstack.BackStack
import com.badoo.ribs.routing.transition.handler.TransitionHandler
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsContainerChildBuilders
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter

class TabsBuilder : SimpleBuilder<Tabs>() {

    override fun build(buildParams: BuildParams<Nothing?>): Tabs {
        val connections = TabsContainerChildBuilders()
        val customisation = buildParams.getOrDefault(Tabs.Customisation())
        val backStack = backStack(buildParams)
        val interactor = TabsInteractor(buildParams, backStack)
        val router = router(
            buildParams = buildParams,
            routingSource = backStack,
            builders = connections,
            transitionHandler = customisation.transitionHandler
        )
        return node(
            buildParams = buildParams,
            customisation = customisation,
            interactor = interactor,
            router
        )
    }

    private fun backStack(buildParams: BuildParams<*>) =
        BackStack<TabsRouter.Configuration>(
            buildParams = buildParams,
            initialConfiguration = TabsRouter.Configuration.FirstTab
        )

    private fun router(
        buildParams: BuildParams<*>,
        routingSource: RoutingSource<TabsRouter.Configuration>,
        builders: TabsContainerChildBuilders,
        transitionHandler: TransitionHandler<TabsRouter.Configuration>
    ) = TabsRouter(
        buildParams = buildParams,
        builders = builders,
        routingSource = routingSource,
        transitionHandler = transitionHandler
    )

    private fun node(
        buildParams: BuildParams<Nothing?>,
        customisation: Tabs.Customisation,
        interactor: TabsInteractor,
        router: TabsRouter
    ) = TabsNode(
        buildParams = buildParams,
        viewFactory = customisation.viewFactory(null),
        plugins = listOf(
            interactor,
            router
        )
    )
}
