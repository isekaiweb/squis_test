package com.android.developer.sequis_test.core.presentation.base

import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.android.developer.sequis_test.core.presentation.plugins.UIEvent
import com.android.developer.sequis_test.core.presentation.plugins.UIState
import com.android.developer.sequis_test.core.presentation.util.autoCleaned
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * Template for fragments.
 *
 * @see ViewBinding
 * @see Fragment
 * @see UIState
 * @see UIEvent
 * @see UIState
 * @see UIEvent
 * @see BaseViewModel
 */
abstract class BaseFragment<VB : ViewBinding, STATE : UIState, EVENT : UIEvent, VM : BaseViewModel<STATE, EVENT>> :
    Fragment() {

    private var _binding: VB by autoCleaned()
    val binding: VB get() = _binding


    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    /**
     * Method to initialize view
     */
    abstract fun initView()

    /**
     * Method to use ui state
     */
    abstract fun render(state: STATE)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeState()
    }

    /**
     * Method to observe state
     **/
    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .shareIn(CoroutineScope(Dispatchers.Main), replay = 0, started = SharingStarted.Lazily)
            .distinctUntilChanged()
            .onEach { state -> render(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    /**
     * Method to set event to view model
     */
    fun setEvent(event: EVENT) {
        viewModel.setEvent(event)
    }


    /**
     * Method to set menu toolbar (optional)
     **/
    protected fun setMenuToolbar(
        @MenuRes menuLayout: Int,
        menuItemClickListener: (item: MenuItem) -> Boolean
    ) {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(menuLayout, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return menuItemClickListener(menuItem)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Method to inflate view binding
     */
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
}