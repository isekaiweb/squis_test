package com.android.developer.sequis_test.detail.presentation

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.sequis_test.R
import com.android.developer.sequis_test.core.domain.model.Comment
import com.android.developer.sequis_test.core.domain.relations.PictureAndComments
import com.android.developer.sequis_test.core.presentation.base.BaseFragment
import com.android.developer.sequis_test.core.presentation.extentions.setImgUrl
import com.android.developer.sequis_test.databinding.FragmentDetailBinding
import com.android.developer.sequis_test.detail.presentation.adapter.CommentsAdapter
import com.android.developer.sequis_test.detail.presentation.util.listJson
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


@AndroidEntryPoint
class DetailFragment :
    BaseFragment<FragmentDetailBinding, DetailState, DetailEvent, DetailViewModel>() {


    private val commentsAdapter: CommentsAdapter by lazy { CommentsAdapter() }
    override val viewModel: DetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().setDuration(400.toLong())

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 400.toLong()
            scrimColor = Color.TRANSPARENT
            drawingViewId = R.id.nav_host_fragment
        }

    }

    override fun initView() {
        setMenuToolbar(R.menu.detail_menu) { menu ->
            when (menu.itemId) {
                R.id.addComment -> {
                    setEvent(DetailEvent.PerformAddComment(generateComment()) {
                        binding.rvComments.smoothScrollToPosition(0)
                    })
                    true
                }

                else -> false
            }
        }
        binding.rvComments.adapter = commentsAdapter

        setSwipeDelete()
    }


    /**
     * Method to set menu toolbar
     **/
    private fun setMenuToolbar(
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

    override fun render(state: DetailState) {
        state.pictureAndComments?.setView()
    }

    private fun PictureAndComments.setView() {
        with(binding) {
            ViewCompat.setTransitionName(img, "img_${picture.id}")

            img.setImgUrl(picture.url)
        }

        //set comments layout
        commentsAdapter.submitList(comments)
    }


    private fun generateComment(): Comment {
        val firstName = requireActivity().listJson(jsonRaw = R.raw.first_names).shuffled()[0]
        val lastName = requireActivity().listJson(jsonRaw = R.raw.last_names).shuffled()[0]
        val author = "$firstName $lastName"

        var content = ""
        val nouns =
            requireActivity().listJson(jsonRaw = R.raw.nouns).shuffled().take(10)
        val verbs =
            requireActivity().listJson(jsonRaw = R.raw.verbs).shuffled().take(10)


        for (i in 0 until 10) {
            content += "${nouns[i]} ${verbs[i]} "
        }

        val currentTimeAt = System.currentTimeMillis()
        return Comment(
            currentTimeAt = currentTimeAt,
            author = author,
            content = content,
            pictureId = viewModel.currentState.pictureAndComments!!.picture.id
        )
    }

    private fun setSwipeDelete() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                /**
                 * Change background color when swiping
                 **/
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.error_color
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftPadding(
                        0, 0f,
                        resources.getDimensionPixelSize(
                            R.dimen.dimen_12
                        ).toFloat(),
                        0f,
                    )
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val comment = commentsAdapter.currentList[position]
                setEvent(DetailEvent.PerformDeleteComment(comment))
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvComments)
        }
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
}