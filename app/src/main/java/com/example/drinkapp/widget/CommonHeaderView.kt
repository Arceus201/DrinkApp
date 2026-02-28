package com.example.drinkapp.widget

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.AbsSavedState
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.drinkapp.R
import com.example.drinkapp.databinding.LayoutCommonHeaderBinding
import com.google.android.material.button.MaterialButton

/**
 * CommonHeaderView - A reusable header component for DrinkApp
 * 
 * This custom view provides a consistent header UI across all Activities with:
 * - Back button (configurable visibility and behavior)
 * - Title text (configurable text and visibility)
 * - Action buttons (dynamically added/removed)
 * 
 * The view uses ViewBinding internally and supports theme customization.
 */
class CommonHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    
    private val binding: LayoutCommonHeaderBinding
    
    /**
     * Internal state management for the header configuration
     */
    private data class HeaderState(
        var title: String? = null,
        var showBackButton: Boolean = true,
        var showTitle: Boolean = true,
        var backButtonEnabled: Boolean = true,
        var onBackClickListener: OnClickListener? = null
    )
    
    private val state = HeaderState()
    
    init {
        // Inflate the layout using ViewBinding
        binding = LayoutCommonHeaderBinding.inflate(
            LayoutInflater.from(context), this
        )
        
        // Apply theme attributes
        applyThemeAttributes(attrs)
        
        setupDefaultBehavior()
    }
    
    /**
     * Apply theme attributes to the header UI elements
     */
    private fun applyThemeAttributes(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CommonHeaderView,
            0,
            0
        )
        
        try {
            // Background color
            val backgroundColor = typedArray.getColor(
                R.styleable.CommonHeaderView_headerBackgroundColor,
                context.getColorFromAttr(com.google.android.material.R.attr.colorSurface)
            )
            setBackgroundColor(backgroundColor)
            
            // Title color
            val titleColor = typedArray.getColor(
                R.styleable.CommonHeaderView_headerTitleColor,
                context.getColorFromAttr(com.google.android.material.R.attr.colorOnSurface)
            )
            binding.textTitle.setTextColor(titleColor)
            
            // Icon tint
            val iconTint = typedArray.getColor(
                R.styleable.CommonHeaderView_headerIconTint,
                context.getColorFromAttr(com.google.android.material.R.attr.colorOnSurface)
            )
            binding.buttonBack.imageTintList = ColorStateList.valueOf(iconTint)
            
            // Elevation
            val elevation = typedArray.getDimension(
                R.styleable.CommonHeaderView_headerElevation,
                resources.getDimension(R.dimen.elevation_sm)
            )
            this.elevation = elevation
            
        } finally {
            typedArray.recycle()
        }
    }
    
    /**
     * Helper extension function to get color from theme attribute
     */
    private fun Context.getColorFromAttr(@AttrRes attrRes: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrRes, typedValue, true)
        return typedValue.data
    }
    
    /**
     * Set up default behavior for the header
     */
    private fun setupDefaultBehavior() {
        // Set default back button behavior to call Activity.finish()
        binding.buttonBack.setOnClickListener {
            if (state.backButtonEnabled) {
                // If custom listener is set, use it; otherwise use default behavior
                if (state.onBackClickListener != null) {
                    state.onBackClickListener?.onClick(it)
                } else {
                    // Default behavior: finish the Activity
                    (context as? Activity)?.finish()
                }
            }
        }
    }
    
    /**
     * Set the visibility of the back button
     * 
     * @param visible true to show the back button, false to hide it
     * 
     * Hidden elements use View.GONE to not allocate space in the layout.
     */
    fun setBackButtonVisible(visible: Boolean) {
        state.showBackButton = visible
        binding.buttonBack.visibility = if (visible) VISIBLE else GONE
    }
    
    /**
     * Set the visibility of the title text
     * 
     * @param visible true to show the title, false to hide it
     * 
     * Hidden elements use View.GONE to not allocate space in the layout.
     */
    fun setTitleVisible(visible: Boolean) {
        state.showTitle = visible
        binding.textTitle.visibility = if (visible) VISIBLE else GONE
    }
    
    /**
     * Set the title text from a String
     * 
     * @param title The title text to display
     * 
     * The title will be displayed with the app's standard title text style
     * (textAppearanceTitleLarge) and will be ellipsized with "..." at the end
     * if it exceeds the available width.
     */
    fun setTitle(title: String) {
        state.title = title
        binding.textTitle.text = title
    }
    
    /**
     * Set the title text from a string resource ID
     * 
     * @param titleRes The string resource ID for the title text
     * 
     * The title will be displayed with the app's standard title text style
     * (textAppearanceTitleLarge) and will be ellipsized with "..." at the end
     * if it exceeds the available width.
     * 
     * @throws android.content.res.Resources.NotFoundException if the resource ID is invalid
     */
    fun setTitle(@androidx.annotation.StringRes titleRes: Int) {
        val title = context.getString(titleRes)
        setTitle(title)
    }
    
    /**
     * Set a custom click listener for the back button
     * 
     * @param listener The click listener to invoke when the back button is clicked,
     *                 or null to restore default behavior (Activity.finish())
     * 
     * The listener will only be invoked if the back button is enabled.
     * If no custom listener is set, the default behavior is to call Activity.finish().
     */
    fun setOnBackClickListener(listener: OnClickListener?) {
        state.onBackClickListener = listener
    }
    
    /**
     * Set whether the back button is enabled
     * 
     * @param enabled true to enable the back button, false to disable it
     * 
     * When disabled:
     * - The back button will have reduced opacity (alpha = 0.5f)
     * - Click events will be ignored
     * - The button remains visible but non-interactive
     */
    fun setBackButtonEnabled(enabled: Boolean) {
        state.backButtonEnabled = enabled
        binding.buttonBack.isEnabled = enabled
        binding.buttonBack.alpha = if (enabled) 1.0f else 0.5f
    }
    
    // ========== Action Button Management ==========
    
    /**
     * Add an action button to the header
     * 
     * @param config Configuration for the action button (icon, text, callback, etc.)
     * @return The created button View
     * @throws IllegalArgumentException if the config is invalid (no icon or text)
     * 
     * Action buttons are added to the right side of the header in the order they are added.
     * Buttons are spaced consistently using spacing_sm (8dp).
     */
    fun addActionButton(config: ActionButtonConfig): View {
        // Validate configuration
        config.validate()
        
        // Create the appropriate button type
        val button = when {
            config.iconRes != null -> createIconButton(config)
            config.text != null || config.textRes != null -> createTextButton(config)
            else -> throw IllegalArgumentException("ActionButtonConfig must have either icon or text")
        }
        
        // Add spacing before the button (except for the first button)
        if (binding.layoutActions.childCount > 0) {
            val spacing = resources.getDimensionPixelSize(R.dimen.spacing_sm)
            val layoutParams = button.layoutParams as LinearLayout.LayoutParams
            layoutParams.marginStart = spacing
        }
        
        // Add button to the container
        binding.layoutActions.addView(button)
        
        return button
    }
    
    /**
     * Create an icon button from the configuration
     * 
     * @param config The action button configuration
     * @return An ImageButton configured with the icon and callback
     */
    private fun createIconButton(config: ActionButtonConfig): ImageButton {
        val button = ImageButton(context).apply {
            // Set the icon
            setImageResource(config.iconRes!!)
            
            // Apply theme tint
            val iconTint = context.getColorFromAttr(
                com.google.android.material.R.attr.colorOnSurface
            )
            imageTintList = ColorStateList.valueOf(iconTint)
            
            // Set background - resolve attribute to drawable resource
            val outValue = TypedValue()
            context.theme.resolveAttribute(
                android.R.attr.selectableItemBackgroundBorderless,
                outValue,
                true
            )
            setBackgroundResource(outValue.resourceId)
            
            // Set size
            val size = resources.getDimensionPixelSize(R.dimen.touch_target_min)
            layoutParams = LinearLayout.LayoutParams(size, size)
            
            // Set content description
            contentDescription = config.contentDescription ?: ""
            
            // Set enabled state
            isEnabled = config.enabled
            alpha = if (config.enabled) 1.0f else 0.5f
            
            // Set click listener
            setOnClickListener {
                if (config.enabled) {
                    config.onClick?.invoke()
                }
            }
        }
        
        return button
    }
    
    /**
     * Create a text button from the configuration
     * 
     * @param config The action button configuration
     * @return A MaterialButton configured with the text and callback
     */
    private fun createTextButton(config: ActionButtonConfig): MaterialButton {
        val button = MaterialButton(
            context,
            null,
            com.google.android.material.R.attr.materialButtonOutlinedStyle
        ).apply {
            // Set the text
            text = when {
                config.text != null -> config.text
                config.textRes != null -> context.getString(config.textRes!!)
                else -> ""
            }
            
            // Set layout params
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            
            // Set content description
            contentDescription = config.contentDescription ?: text
            
            // Set enabled state
            isEnabled = config.enabled
            alpha = if (config.enabled) 1.0f else 0.5f
            
            // Set click listener
            setOnClickListener {
                if (config.enabled) {
                    config.onClick?.invoke()
                }
            }
        }
        
        return button
    }
    
    /**
     * Remove an action button from the header
     * 
     * @param view The button view to remove
     * 
     * This operation is idempotent - removing a button that's not present is a no-op.
     */
    fun removeActionButton(view: View) {
        binding.layoutActions.removeView(view)
    }
    
    /**
     * Remove all action buttons from the header
     * 
     * This clears all dynamically added action buttons, leaving the header
     * with only the back button and title.
     */
    fun clearActionButtons() {
        binding.layoutActions.removeAllViews()
    }
    
    // ========== Configuration DSL ==========
    
    /**
     * Configure the header using a DSL block
     * 
     * This method provides a fluent Kotlin DSL for configuring all aspects of the header
     * in a single, readable block. It applies all configuration properties in the correct order.
     * 
     * Example usage:
     * ```kotlin
     * binding.commonHeader.configure {
     *     title = getString(R.string.page_category_manager)
     *     showBackButton = true
     *     onBackClick = { finish() }
     *     
     *     action {
     *         iconRes = R.drawable.ic_add
     *         contentDescription = getString(R.string.add_category)
     *         onClick = { showAddCategoryDialog() }
     *     }
     * }
     * ```
     * 
     * @param block Configuration block using CommonHeaderConfig DSL
     * 
     * The method applies configuration in this order:
     * 1. Title (from title or titleRes)
     * 2. Visibility states (back button, title)
     * 3. Back button behavior (click listener, enabled state)
     * 4. Action buttons (clear existing, add new ones)
     */
    fun configure(block: CommonHeaderConfig.() -> Unit) {
        val config = CommonHeaderConfig().apply(block)
        
        // Apply title (prioritize direct title over titleRes)
        config.title?.let { setTitle(it) }
            ?: config.titleRes?.let { setTitle(it) }
        
        // Apply visibility states
        setBackButtonVisible(config.showBackButton)
        setTitleVisible(config.showTitle)
        
        // Apply back button behavior
        config.onBackClick?.let { callback ->
            setOnBackClickListener { callback() }
        }
        setBackButtonEnabled(config.backButtonEnabled)
        
        // Apply action buttons
        clearActionButtons()
        config.getActions().forEach { actionConfig ->
            addActionButton(actionConfig)
        }
    }
    
    // ========== Testing Infrastructure ==========
    
    /**
     * Get the current title text (for testing purposes)
     * 
     * @return The current title text, or null if no title is set
     * 
     * This method is provided for testing purposes to query the current
     * configuration state of the header.
     */
    fun getCurrentTitle(): String? {
        return state.title
    }
    
    /**
     * Check if the back button is currently visible (for testing purposes)
     * 
     * @return true if the back button is visible, false otherwise
     * 
     * This method is provided for testing purposes to query the current
     * configuration state of the header.
     */
    fun isBackButtonVisible(): Boolean {
        return state.showBackButton && binding.buttonBack.visibility == VISIBLE
    }
    
    /**
     * Check if the title is currently visible (for testing purposes)
     * 
     * @return true if the title is visible, false otherwise
     * 
     * This method is provided for testing purposes to query the current
     * configuration state of the header.
     */
    fun isTitleVisible(): Boolean {
        return state.showTitle && binding.textTitle.visibility == VISIBLE
    }
    
    /**
     * Get the number of action buttons currently in the header (for testing purposes)
     * 
     * @return The count of action buttons
     * 
     * This method is provided for testing purposes to query the current
     * configuration state of the header.
     */
    fun getActionButtonCount(): Int {
        return binding.layoutActions.childCount
    }
    
    /**
     * Programmatically trigger a back button click (for testing purposes)
     * 
     * This method simulates a user clicking the back button, invoking the
     * configured callback if the button is enabled. This is useful for
     * automated testing of back button behavior.
     * 
     * Note: This method will only trigger the callback if the back button
     * is enabled. If disabled, this method is a no-op.
     */
    fun performBackButtonClick() {
        if (state.backButtonEnabled) {
            binding.buttonBack.performClick()
        }
    }
    
    // ========== State Preservation ==========
    
    /**
     * Save the current header configuration state
     * 
     * This method preserves the header's configuration across configuration changes
     * (e.g., screen rotation). The following state is preserved:
     * - Title text
     * - Back button visibility and enabled state
     * - Title visibility
     * - Action button configurations (icon, text, enabled state)
     * 
     * Note: onClick callbacks CANNOT be preserved as they are not Parcelable.
     * After state restoration, callbacks must be reconfigured by the Activity.
     * 
     * @return Parcelable containing the saved state
     */
    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        
        // Save current state
        savedState.title = state.title
        savedState.showBackButton = state.showBackButton
        savedState.showTitle = state.showTitle
        savedState.backButtonEnabled = state.backButtonEnabled
        
        // Save action button states (without onClick callbacks)
        savedState.actionButtons = (0 until binding.layoutActions.childCount).mapNotNull { index ->
            val button = binding.layoutActions.getChildAt(index)
            when (button) {
                is ImageButton -> {
                    // Extract state from ImageButton
                    ActionButtonState(
                        iconRes = null, // We can't reliably extract the resource ID
                        text = null,
                        contentDescription = button.contentDescription?.toString(),
                        enabled = button.isEnabled
                    )
                }
                is MaterialButton -> {
                    // Extract state from MaterialButton
                    ActionButtonState(
                        iconRes = null,
                        text = button.text?.toString(),
                        contentDescription = button.contentDescription?.toString(),
                        enabled = button.isEnabled
                    )
                }
                else -> null
            }
        }
        
        return savedState
    }
    
    /**
     * Restore the header configuration state
     * 
     * This method restores the header's configuration after a configuration change.
     * If the saved state is corrupted or invalid, the method falls back to default
     * configuration gracefully without crashing.
     * 
     * Note: onClick callbacks CANNOT be restored as they are not Parcelable.
     * After state restoration, callbacks must be reconfigured by the Activity.
     * 
     * @param state The saved state to restore, or null for default state
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        try {
            if (state is SavedState) {
                super.onRestoreInstanceState(state.superState)
                
                // Restore title
                state.title?.let { setTitle(it) }
                
                // Restore visibility states
                setBackButtonVisible(state.showBackButton)
                setTitleVisible(state.showTitle)
                
                // Restore back button enabled state
                setBackButtonEnabled(state.backButtonEnabled)
                
                // Restore action buttons (without onClick callbacks)
                // Note: Callbacks must be reconfigured by the Activity after restoration
                clearActionButtons()
                state.actionButtons.forEach { buttonState ->
                    val config = ActionButtonConfig(
                        iconRes = buttonState.iconRes,
                        text = buttonState.text,
                        contentDescription = buttonState.contentDescription,
                        onClick = null, // Callbacks cannot be preserved
                        enabled = buttonState.enabled
                    )
                    
                    // Only add button if it has valid configuration
                    if (config.iconRes != null || config.text != null) {
                        addActionButton(config)
                    }
                }
            } else {
                // Fallback to default behavior if state is not SavedState
                super.onRestoreInstanceState(state)
            }
        } catch (e: Exception) {
            // Handle corrupted state gracefully - fall back to defaults
            // Log the error but don't crash
            android.util.Log.w(
                "CommonHeaderView",
                "Failed to restore state, using defaults",
                e
            )
            super.onRestoreInstanceState(null)
        }
    }
    
    /**
     * Parcelable data class for preserving action button state
     * 
     * Note: onClick callbacks cannot be preserved as they are not Parcelable.
     * After state restoration, callbacks must be reconfigured by the Activity.
     * 
     * @property iconRes The drawable resource ID for the button icon (if any)
     * @property text The text label for the button (if any)
     * @property contentDescription The accessibility description
     * @property enabled Whether the button is enabled
     */
    data class ActionButtonState(
        val iconRes: Int?,
        val text: String?,
        val contentDescription: String?,
        val enabled: Boolean
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte()
        )
        
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(iconRes)
            parcel.writeString(text)
            parcel.writeString(contentDescription)
            parcel.writeByte(if (enabled) 1 else 0)
        }
        
        override fun describeContents(): Int = 0
        
        companion object CREATOR : Parcelable.Creator<ActionButtonState> {
            override fun createFromParcel(parcel: Parcel): ActionButtonState {
                return ActionButtonState(parcel)
            }
            
            override fun newArray(size: Int): Array<ActionButtonState?> {
                return arrayOfNulls(size)
            }
        }
    }
    
    /**
     * SavedState inner class for preserving CommonHeaderView state across configuration changes
     * 
     * This class extends BaseSavedState to preserve the header's configuration state
     * (title, visibility states, action buttons) across configuration changes like screen rotation.
     * 
     * Note: onClick callbacks cannot be preserved as they are not Parcelable.
     * After state restoration, callbacks must be reconfigured by the Activity.
     * 
     * @property title The header title text
     * @property showBackButton Whether the back button is visible
     * @property showTitle Whether the title is visible
     * @property backButtonEnabled Whether the back button is enabled
     * @property actionButtons List of action button states
     */
    private class SavedState : AbsSavedState {
        var title: String? = null
        var showBackButton: Boolean = true
        var showTitle: Boolean = true
        var backButtonEnabled: Boolean = true
        var actionButtons: List<ActionButtonState> = emptyList()
        
        constructor(superState: Parcelable?) : super(superState)
        
        constructor(parcel: Parcel, loader: ClassLoader?) : super(parcel, loader) {
            title = parcel.readString()
            showBackButton = parcel.readByte() != 0.toByte()
            showTitle = parcel.readByte() != 0.toByte()
            backButtonEnabled = parcel.readByte() != 0.toByte()
            actionButtons = parcel.createTypedArrayList(ActionButtonState.CREATOR) ?: emptyList()
        }
        
        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeString(title)
            out.writeByte(if (showBackButton) 1 else 0)
            out.writeByte(if (showTitle) 1 else 0)
            out.writeByte(if (backButtonEnabled) 1 else 0)
            out.writeTypedList(actionButtons)
        }
        
        companion object CREATOR : Parcelable.Creator<SavedState> {
            override fun createFromParcel(parcel: Parcel): SavedState {
                return SavedState(parcel, null)
            }
            
            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}
