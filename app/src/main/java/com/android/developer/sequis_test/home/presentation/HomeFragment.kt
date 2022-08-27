package com.android.developer.sequis_test.home.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.android.developer.sequis_test.R
import com.android.developer.sequis_test.core.domain.model.Picture
import com.android.developer.sequis_test.core.presentation.base.BaseFragment
import com.android.developer.sequis_test.core.presentation.extentions.hide
import com.android.developer.sequis_test.core.presentation.extentions.show
import com.android.developer.sequis_test.databinding.FragmentHomeBinding
import com.android.developer.sequis_test.home.presentation.adapter.PictureFooterAdapter
import com.android.developer.sequis_test.home.presentation.adapter.PicturesAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeState, HomeEvent, HomeViewModel>() {

    private val picturesAdapter: PicturesAdapter by lazy {
        PicturesAdapter { id, extras ->
            findNavController().navigate(HomeFragmentDirections.homeToDetail(id), navigatorExtras = extras)
        }
    }

    private val pictureFooterAdapter: PictureFooterAdapter by lazy {
        PictureFooterAdapter(picturesAdapter::retry)
    }

    override val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun initView() {
        postponeEnterTransition()

        with(binding) {
            mainLayout.setOnRefreshListener { picturesAdapter.refresh() }
            rvPictures.adapter = picturesAdapter.withLoadStateFooter(pictureFooterAdapter)

            fabUp.setOnClickListener { rvPictures.scrollToPosition(rvPictures.top) }
        }

        picturesAdapter.addLoadStateListener { loadState ->
            setEvent(
                HomeEvent.PerformGetLoadState(loadState)
            )
        }

    }

    override fun render(state: HomeState) {
        state.homeLoad?.setLayoutVisibility()
        state.pictures.updateRecyclerViewData()
    }

    private fun CombinedLoadStates.setLayoutVisibility() {
        with(binding) {
            when {
                source.refresh is LoadState.Loading && picturesAdapter.itemCount > 0 -> {
                    loadingLayout.root.hide()
                    mainLayout.show()

                    Timber.e("REFRESH LOADING AND HAS ITEMS")
                }

                source.refresh is LoadState.Loading -> {
                    loadingLayout.root.show()
                    errorLayout.root.hide()
                    Timber.e("REFRESH LOADING")
                }

                source.refresh is LoadState.NotLoading && picturesAdapter.itemCount > 0 -> {
                    loadingLayout.root.hide()
                    fabUp.show()
                    mainLayout.apply {
                        show()
                        isRefreshing = false
                    }

                    Timber.e("REFRESH NOT LOADING")
                }


                source.refresh is LoadState.Error -> {
                    with(errorLayout) {
                        tvDescError.text = (refresh as LoadState.Error).error.message
                        btnRetry.setOnClickListener { picturesAdapter.retry() }
                        root.show()
                    }

                    loadingLayout.root.hide()
                    mainLayout.hide()

                    Timber.e("REFRESH ERROR")
                }


                else -> Unit
            }

        }
    }

    /**
     * Update RecyclerView adapter data
     *
     * [Picture] is the data to submit to the [PicturesAdapter]
     */
    private fun PagingData<Picture>.updateRecyclerViewData() {


        with(binding.rvPictures) {
            // Postponed delay
            doOnPreDraw { startPostponedEnterTransition() }

            // Add data to the adapter
            picturesAdapter.submitData(viewLifecycleOwner.lifecycle, this@updateRecyclerViewData)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
}