package com.david.laba.auxiliary

import androidx.annotation.DrawableRes
import com.david.laba.R


enum class MapIconType(@DrawableRes val icon: Int) {
    RESTAURANT(R.drawable.ic_map_food_delete),
    FASTFOOD(R.drawable.ic_map_food_delete),
    VAPE(R.drawable.ic_map_food_delete)
}