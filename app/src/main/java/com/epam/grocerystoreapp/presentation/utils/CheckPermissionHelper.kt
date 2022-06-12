package com.epam.grocerystoreapp.presentation.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.epam.grocerystoreapp.R
import android.Manifest
import com.epam.grocerystoreapp.App
import com.epam.grocerystoreapp.presentation.model.CheckPermissionResult
import com.epam.grocerystoreapp.presentation.model.PermissionTextResourcesData

class CheckPermissionHelper(
    private val activityResultCaller: ActivityResultCaller,
    private val activity: Activity,
    private val permission: String,
    private val onPermissionGranted: () -> Unit,
) {
    private val checkPermissionLauncher = activityResultCaller.registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotCheckPermissionResult
    )

    private val permissionResources = getPermissionResources(permission)

    private fun onGotCheckPermissionResult(granted: Boolean) {
        if (granted) {
            onPermissionGranted()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
            ) {
                showGoToSettingsDialog()
            } else {
                failedGracefully()
            }
        }
    }

    private fun getPermissionResult(): CheckPermissionResult {
        return when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> CheckPermissionResult.GRANTED

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            ) -> CheckPermissionResult.NEED_TO_EXPLAIN

            else -> CheckPermissionResult.NEED_TO_REQUEST
        }
    }

    fun checkPermission() {
        when (getPermissionResult()) {
            CheckPermissionResult.GRANTED -> onPermissionGranted()
            CheckPermissionResult.DENIED -> failedGracefully()
            CheckPermissionResult.NEED_TO_REQUEST -> askForPermission()
            CheckPermissionResult.NEED_TO_EXPLAIN -> showPermissionExplanation()
        }
    }

    private fun askForPermission() {
        checkPermissionLauncher.launch(permission)
    }

    private fun showPermissionExplanation() {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(permissionResources.titleResId))
            .setMessage(activity.getString(permissionResources.explanationTextResId))
            .setPositiveButton(activity.getString(R.string.ok)) { _, _ -> askForPermission() }
            .show()
    }

    private fun failedGracefully() {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(permissionResources.titleResId))
            .setMessage(activity.getString(permissionResources.deniedTextResId))
            .setPositiveButton(activity.getString(R.string.default_permission_on_change_mind_text)) { _, _ ->
                checkPermission()
            }
            .setNegativeButton(activity.getString(R.string.ok), null)
            .show()
    }

    private fun showGoToSettingsDialog() {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(permissionResources.titleResId))
            .setMessage(activity.getString(permissionResources.deniedWithNotAskAgainResId))
            .setPositiveButton(activity.getString(R.string.default_permission_go_to_settings)) { _, _ ->
                activity.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    this.data =
                        Uri.fromParts(App.PACKAGE_URI_SCHEME, activity.packageName, null)
                })
            }
            .setNegativeButton(activity.getString(R.string.ok), null)
            .show()
    }

    private fun getPermissionResources(permission: String): PermissionTextResourcesData =
        when (permission) {
            Manifest.permission.CAMERA -> PermissionTextResourcesData(
                titleResId = R.string.camera_permission_title,
                explanationTextResId = R.string.camera_permission_explanation_text,
                deniedTextResId = R.string.camera_permission_rejected_text,
                deniedWithNotAskAgainResId = R.string.camera_permission_rejected_with_not_ask_again
            )
            Manifest.permission.ACCESS_FINE_LOCATION -> PermissionTextResourcesData(
                titleResId = R.string.maps_permission_tittle,
                explanationTextResId = R.string.maps_permission_explanation_text,
                deniedTextResId = R.string.maps_permission_rejected_text,
                deniedWithNotAskAgainResId = R.string.maps_permission_rejected_with_not_ask_again
            )
            else -> PermissionTextResourcesData(
                titleResId = R.string.default_permission_title,
                explanationTextResId = R.string.default_permission_explanation_text,
                deniedTextResId = R.string.default_permission_rejected_text,
                deniedWithNotAskAgainResId = R.string.default_permission_rejected_with_not_ask_again
            )
        }
}