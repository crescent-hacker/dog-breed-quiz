package com.airwallex.feature.shared.ui.base

import androidx.fragment.app.FragmentActivity
import com.airwallex.core.util.EventBus
import com.airwallex.feature.shared.data.model.dto.AppEvent

abstract class AbstractActivity: FragmentActivity() {
    abstract val appEventBus: EventBus<AppEvent>
}
