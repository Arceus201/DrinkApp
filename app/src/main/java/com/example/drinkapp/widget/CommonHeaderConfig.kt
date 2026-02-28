package com.example.drinkapp.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Configuration data class for action buttons in CommonHeaderView
 * 
 * An action button must have either an icon OR text (but not both null).
 * This is validated when the button is added to the header.
 * 
 * @property iconRes Optional drawable resource ID for the button icon
 * @property text Optional text label for the button
 * @property textRes Optional string resource ID for the button text
 * @property contentDescription Accessibility description for the button
 * @property onClick Callback invoked when the button is clicked
 * @property enabled Whether the button is enabled (default: true)
 */
data class ActionButtonConfig(
    @DrawableRes var iconRes: Int? = null,
    var text: String? = null,
    @StringRes var textRes: Int? = null,
    var contentDescription: String? = null,
    var onClick: (() -> Unit)? = null,
    var enabled: Boolean = true
) {
    /**
     * Validates that the button configuration is valid
     * 
     * @throws IllegalArgumentException if both iconRes and text/textRes are null
     */
    fun validate() {
        if (iconRes == null && text == null && textRes == null) {
            throw IllegalArgumentException(
                "ActionButtonConfig must have either iconRes OR text/textRes"
            )
        }
    }
}

/**
 * DSL configuration class for CommonHeaderView
 * 
 * This class provides a fluent Kotlin DSL for configuring all aspects of the CommonHeaderView.
 * It allows for concise, readable header configuration in a single block.
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
 * @property title Optional title text as a String
 * @property titleRes Optional title text as a string resource ID
 * @property showBackButton Whether to show the back button (default: true)
 * @property showTitle Whether to show the title (default: true)
 * @property onBackClick Optional callback for back button clicks
 * @property backButtonEnabled Whether the back button is enabled (default: true)
 */
class CommonHeaderConfig {
    var title: String? = null
    @StringRes var titleRes: Int? = null
    var showBackButton: Boolean = true
    var showTitle: Boolean = true
    var onBackClick: (() -> Unit)? = null
    var backButtonEnabled: Boolean = true
    
    private val actions = mutableListOf<ActionButtonConfig>()
    
    /**
     * DSL method for adding action buttons
     * 
     * This method allows adding action buttons using a nested DSL block.
     * Multiple action buttons can be added by calling this method multiple times.
     * 
     * Example:
     * ```kotlin
     * action {
     *     iconRes = R.drawable.ic_add
     *     contentDescription = getString(R.string.add)
     *     onClick = { handleAdd() }
     * }
     * ```
     * 
     * @param block Configuration block for the action button
     */
    fun action(block: ActionButtonConfig.() -> Unit) {
        actions.add(ActionButtonConfig().apply(block))
    }
    
    /**
     * Internal method to get the list of configured action buttons
     * 
     * This method is used by CommonHeaderView to retrieve all action buttons
     * that were configured using the action() DSL method.
     * 
     * @return List of configured action buttons
     */
    internal fun getActions(): List<ActionButtonConfig> = actions
}
