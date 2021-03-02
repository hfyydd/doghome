/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.IntOffset
import com.example.androiddevchallenge.ui.DetailPage
import com.example.androiddevchallenge.ui.DogList
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: DogViewModel by viewModels()
        setContent {

            // A surface container using the 'background' color from the theme
            Box {
                DogList(dogs = viewModel.dogs, viewModel)
                val openOffset by animateFloatAsState(
                    if (viewModel.currentDog == null) {
                        1f
                    } else {
                        0f
                    }
                )
                if (viewModel.currentDog != null) {
                    DetailPage(
                        Modifier.percentOffsetX(openOffset),
                        dogInput = viewModel.currentDog
                    ) {
                        viewModel.endDog()
                    }
                }
            }

        }
    }

    override fun onBackPressed() {
        val viewModel: DogViewModel by viewModels()
        if (viewModel.currentDog != null) {
            viewModel.endDog()
        } else {
            super.onBackPressed()
        }
    }
}


fun Modifier.percentOffsetX(percent: Float) = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative(IntOffset((placeable.width * percent).roundToInt(), 0))
    }
}
