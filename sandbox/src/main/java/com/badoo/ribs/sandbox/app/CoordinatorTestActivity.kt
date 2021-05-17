package com.badoo.ribs.sandbox.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.badoo.ribs.android.integrationpoint.ActivityIntegrationPoint
import com.badoo.ribs.core.Rib
import com.badoo.ribs.core.modality.BuildContext
import com.badoo.ribs.sandbox.R
import com.badoo.ribs.sandbox.rib.tabs.TabsBuilder
import com.badoo.ribs.sandbox.RootCoordinatorView

class CoordinatorTestActivity : AppCompatActivity() {

    private lateinit var integrationPoint: ActivityIntegrationPoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_up_concept)

        integrationPoint = ActivityIntegrationPoint(
            activity = this,
            savedInstanceState = savedInstanceState,
            rootViewHostFactory = {
                RootCoordinatorView(
                    androidView = findViewById(R.id.coordinatorroot),
                    coordinatedContainerId = R.id.tabs_root
                )
            }
        )
        val root = createRib(savedInstanceState)
        integrationPoint.attach(root)
    }

    private fun createRib(savedInstanceState: Bundle?): Rib =
        TabsBuilder().build(buildContext = BuildContext.root(savedInstanceState))

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        integrationPoint.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        integrationPoint.onLowMemory()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        integrationPoint.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        integrationPoint.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        if (!integrationPoint.handleBackPress()) {
            super.onBackPressed()
        }
    }
}