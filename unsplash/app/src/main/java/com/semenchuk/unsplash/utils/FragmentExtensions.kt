package com.semenchuk.unsplash.utils

import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.semenchuk.unsplash.R

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.permissionLauncher(): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (map.values.all { it }) {
            this.toast(R.string.thx_message_permissions)
        } else {
            this.toast(R.string.sry_message_permissions)
        }
    }
}