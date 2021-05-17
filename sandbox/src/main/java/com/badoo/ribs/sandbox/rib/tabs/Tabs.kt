package com.badoo.ribs.sandbox.rib.tabs

import com.badoo.ribs.core.Rib
import com.badoo.ribs.core.customisation.RibCustomisation
import com.badoo.ribs.routing.transition.handler.TabSwitcher
import com.badoo.ribs.routing.transition.handler.TransitionHandler
import com.badoo.ribs.rx2.clienthelper.connector.Connectable
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.FirstTab
import com.badoo.ribs.sandbox.rib.tabs.routing.TabsRouter.Configuration.SecondTab

interface Tabs : Rib{

    class Customisation(
        val transitionHandler: TransitionHandler<TabsRouter.Configuration> =
            TabSwitcher(
                tabsOrder = listOf(FirstTab, SecondTab)
            ),
        val viewFactory: TabsView.Factory = TabsViewImpl.Factory()
    ) : RibCustomisation
}
