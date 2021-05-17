package com.badoo.ribs.sandbox.rib.tabs.routing

import android.os.Parcelable
import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.routing.Routing
import com.badoo.ribs.routing.resolution.ChildResolution.Companion.child
import com.badoo.ribs.routing.resolution.Resolution
import com.badoo.ribs.routing.router.Router
import com.badoo.ribs.routing.source.RoutingSource
import com.badoo.ribs.routing.transition.handler.TransitionHandler
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.FirstTab
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.SecondTab
import kotlinx.android.parcel.Parcelize

class TabsRouter internal constructor(
    buildParams: BuildParams<*>,
    routingSource: RoutingSource<Configuration>,
    private val builders: TabsContainerChildBuilders,
    transitionHandler: TransitionHandler<Configuration>? = null
) : Router<Configuration>(
    buildParams = buildParams,
    routingSource = routingSource,
    transitionHandler = transitionHandler
) {
    sealed class Configuration : Parcelable {

        @Parcelize
        object FirstTab : Configuration()

        @Parcelize
        object SecondTab : Configuration()

    }

    override fun resolve(routing: Routing<Configuration>): Resolution =
        with(builders) {
            when (routing.configuration) {
                is FirstTab -> child { firstTabBuilder.build(it) }
                is SecondTab -> child { secondTabBuilder.build(it) }
            }
        }
}

