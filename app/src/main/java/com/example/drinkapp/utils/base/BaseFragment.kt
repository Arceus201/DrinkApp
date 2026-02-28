package com.example.drinkapp.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base class for all Fragments in DrinkApp.
 * 
 * ViewBinding Pattern for Fragments:
 * - Uses nullable _binding (private) to prevent memory leaks
 * - Provides non-null backing property for convenient access
 * - Binding is initialized in onCreateView
 * - Binding is set to null in onDestroyView (critical for memory management)
 * 
 * Rationale: Fragment views can be destroyed and recreated multiple times
 * while the Fragment instance remains alive. Nullable binding prevents
 * holding references to destroyed views, which would cause memory leaks.
 * 
 * Example usage:
 * ```
 * class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {
 *     override fun initView() {
 *         binding.textView.text = "Hello"
 *     }
 *     
 *     override fun onDestroyView() {
 *         // Clean up view-related resources here
 *         presenter?.onStop()
 *         super.onDestroyView()
 *     }
 * }
 * ```
 */
abstract class BaseFragment<T : ViewBinding> (
    private val bindingInflater: (inflater: LayoutInflater) -> T
) : Fragment(){
    
    private var _binding: T? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        handleEvent()
    }

    override fun onDestroyView() {
        // Subclasses should clean up resources before calling super
        _binding = null
        super.onDestroyView()
    }

    abstract fun initView()
    abstract fun initData()
    abstract  fun handleEvent()
}