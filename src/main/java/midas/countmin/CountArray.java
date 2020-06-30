/*
 * Copyright 2020 Joshua Tokle (jotok). All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package midas.countmin;

import java.util.Arrays;

public class CountArray {
    private final float[] counts;

    public CountArray(int size) {
        counts = new float[size];
    }

    public void setAll(float value) {
        Arrays.fill(counts, value);
    }

    public void clearAll() {
        setAll(0.0f);
    }

    public void add(int[] index, float value) {
        for (int i : index) {
            counts[i] += value;
        }
    }

    public void add(int[] index) {
        add(index, 1.0f);
    }

    public void multiplyAll(float value) {
        for (int i = 0; i < counts.length; i++) {
            counts[i] *= value;
        }
    }

    public float get(int[] index) {
        float minValue = Float.POSITIVE_INFINITY;
        for (int i : index) {
            minValue = Math.min(minValue, counts[i]);
        }
        return minValue;
    }

    float get(int index) {
        return counts[index];
    }
}
