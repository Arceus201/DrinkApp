package com.example.drinkapp.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding

/**
 * Base class for all Activities in DrinkApp.
 *
 * ViewBinding Pattern for Activities:
 * - Uses lateinit var for binding (non-nullable)
 * - Binding is initialized in onCreate before setContentView
 * - No cleanup needed in onDestroy (Activity lifecycle handles it)
 *
 * Rationale: Activities have a simple lifecycle where the view exists
 * for the entire Activity lifetime, so lateinit is safe and simpler.
 * Unlike Fragments, Activity views are not destroyed and recreated
 * independently of the Activity instance, eliminating memory leak concerns.
 *
 * Pattern Benefits:
 * - Non-nullable type provides compile-time safety
 * - No backing property needed (simpler than Fragment pattern)
 * - Automatic initialization check via lateinit
 * - Consistent with Android best practices for Activities
 *
 * Example usage:
 * ```
 * class MyActivity : BaseActivity<ActivityMyBinding>(ActivityMyBinding::inflate) {
 *     override fun initView() {
 *         binding.textView.text = "Hello"
 *         binding.button.setOnClickListener { /* ... */ }
 *     }
 *
 *     override fun initData() {
 *         // Load data
 *     }
 *
 *     override fun handleEvent() {
 *         // Set up event handlers
 *     }
 *
 *     override fun onDestroy() {
 *         // Clean up resources (presenters, timers, jobs, listeners)
 *         presenter?.onStop()
 *         super.onDestroy()
 *     }
 * }
 * ```
 *
 * Resource Cleanup Guidelines:
 * - Call presenter.onStop() in onDestroy before super.onDestroy()
 * - Cancel timers and scheduled tasks in onDestroy
 * - Cancel coroutine jobs in onDestroy
 * - Unregister listeners and callbacks in onDestroy
 * - Always call super.onDestroy() as the last statement
 */
abstract class BaseActivity<T: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater)-> T
): AppCompatActivity() {
    
    /**
     * ViewBinding instance providing type-safe access to views.
     * Initialized in onCreate before setContentView.
     * Accessible throughout the Activity lifecycle.
     */
    protected lateinit var binding: T
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable Edge-to-Edge before binding initialization
        enableEdgeToEdge()
        
        // Initialize binding before setContentView
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        
        // Setup WindowInsets after setContentView
        setupWindowInsets()

        initView()
        initData()
        handleEvent()
    }
    
    /**
     * Enables Edge-to-Edge display mode for Android 15+ mandatory requirement.
     * 
     * This method makes the app draw behind the system bars (status bar and navigation bar),
     * providing a modern, immersive user experience. Required for Android 15+ (API 35+).
     * 
     * Called automatically in onCreate before binding initialization.
     */
    private fun enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
    
    /**
     * Sets up WindowInsets handling for Edge-to-Edge display.
     * 
     * This method applies system bar insets (status bar and navigation bar) as padding
     * to the root view, ensuring content is not obscured by system UI elements.
     * 
     * **Default Behavior:**
     * - Applies top insets as top padding (for status bar)
     * - Applies bottom insets as bottom padding (for navigation bar)
     * - Preserves left and right padding
     * 
     * **When to Override:**
     * Override this method in Activities that need custom insets handling, such as:
     * - Activities with bottom buttons/navigation (apply insets to button container)
     * - Activities with toolbars (apply top insets to toolbar only)
     * - Activities with special layouts (custom insets distribution)
     * 
     * **Example - Activity with Bottom Button:**
     * ```kotlin
     * override fun setupWindowInsets() {
     *     ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
     *         val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
     *         
     *         // Apply top insets to toolbar or content area
     *         binding.toolbar.updatePadding(top = insets.top)
     *         
     *         // Apply bottom insets to bottom button container
     *         binding.bottomButtonContainer.updatePadding(bottom = insets.bottom)
     *         
     *         WindowInsetsCompat.CONSUMED
     *     }
     * }
     * ```
     * 
     * **Example - Activity with Toolbar Only:**
     * ```kotlin
     * override fun setupWindowInsets() {
     *     ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
     *         val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
     *         
     *         // Apply only top insets to toolbar
     *         binding.toolbar.updatePadding(top = insets.top)
     *         
     *         WindowInsetsCompat.CONSUMED
     *     }
     * }
     * ```
     * 
     * Called automatically in onCreate after setContentView.
     */
    protected open fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = insets.top,
                bottom = insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * Initialize views and set up UI components.
     * Called after binding is initialized and content view is set.
     */
    abstract fun initView()

    /**
     * Load and initialize data for the Activity.
     * Called after initView.
     */
    abstract fun initData()

    /**
     * Set up event handlers and listeners.
     * Called after initData.
     */
    abstract fun handleEvent()

}