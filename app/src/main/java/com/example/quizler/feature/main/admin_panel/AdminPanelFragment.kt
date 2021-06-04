package com.example.quizler.feature.main.admin_panel

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizler.R

class AdminPanelFragment : Fragment() {

    companion object {
        fun newInstance() = AdminPanelFragment()
    }

    private lateinit var viewModel: AdminPanelViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.admin_panel_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdminPanelViewModel::class.java)
        // TODO: Use the ViewModel
    }

}