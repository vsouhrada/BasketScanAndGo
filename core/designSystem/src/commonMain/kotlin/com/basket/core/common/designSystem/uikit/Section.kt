package com.basket.core.common.designSystem.uikit

import com.basket.core.common.designSystem.uikit.widget.Field

interface Section {
    val title: Field<String>
    val order: Int
    val isSelectable: Boolean
    val isClickable: Boolean
}
