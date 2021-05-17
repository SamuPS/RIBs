package com.badoo.ribs.sandbox.rib.tabs

import com.badoo.ribs.core.modality.BuildParams
import com.badoo.ribs.core.plugin.Plugin
import com.badoo.ribs.core.view.ViewFactory
import com.badoo.ribs.rx2.workflows.RxWorkflowNode

class TabsNode internal constructor(
    buildParams: BuildParams<*>,
    viewFactory: ViewFactory<TabsView>,
    plugins: List<Plugin> = emptyList(),
) : RxWorkflowNode<TabsView>(
    buildParams = buildParams,
    viewFactory = viewFactory,
    plugins = plugins
), Tabs