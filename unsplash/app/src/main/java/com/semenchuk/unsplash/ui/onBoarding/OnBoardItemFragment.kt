package com.semenchuk.unsplash.ui.onBoarding

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.semenchuk.unsplash.R

class OnBoardItemFragment(val onBoardText: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_on_board_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val textView: TextView = view.findViewById(R.id.onboard_tv)
        textView.text = onBoardText
//        if (this.requireContext().isDarkThemeOn()) {
//            view.setBackgroundColor(Color.BLACK)
//        } else {
//            view.setBackgroundColor(Color.WHITE)
//        }

//        arguments?.takeIf { it.containsKey(OnBoardingFragment.ARG_OBJECT) }?.apply {
//            val textView: TextView = view.findViewById(R.id.onboard_tv)
//            textView.text = getString(OnBoardingFragment.ARG_OBJECT).toString()
//        }
    }

    private fun Context.isDarkThemeOn(): Boolean {
        return resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }
}