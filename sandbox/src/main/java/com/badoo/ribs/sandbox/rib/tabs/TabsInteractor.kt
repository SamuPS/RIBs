package com.badoo.ribs.sandbox.rib.tabs

import androidx.lifecycle.Lifecycle
import com.badoo.mvicore.android.lifecycle.startStop
import com.badoo.ribs.clienthelper.interactor.Interactor
import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.routing.source.backstack.BackStack
import com.badoo.ribs.routing.source.backstack.operation.replace
import com.badoo.ribs.sandbox.rib.tabs.TabsView.Event.Tab1Clicked
import com.badoo.ribs.sandbox.rib.tabs.TabsView.Event.Tab2Clicked
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.FirstTab
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.SecondTab
import io.reactivex.functions.Consumer

internal class TabsInteractor(
    buildParams: BuildParams<*>,
    backStack: BackStack<TabsRouter.Configuration>
) : Interactor<Tabs, TabsView>(
    buildParams = buildParams
) {

    override fun onViewCreated(view: TabsView, viewLifecycle: Lifecycle) {
        viewLifecycle.startStop {
            bind(view to tabsOutputConsumer)
        }
    }

    private val tabsOutputConsumer = Consumer<TabsView.Event> {
        when (it) {
            Tab1Clicked -> backStack.replace(FirstTab)
            Tab2Clicked -> backStack.replace(SecondTab)
        }
    }
}