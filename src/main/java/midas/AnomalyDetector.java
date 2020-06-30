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

package midas;

import midas.countmin.CountArray;
import midas.countmin.HashFunction;

public class AnomalyDetector {

    private int currentTimestamp;
    private final int[] index;
    private final HashFunction hashFunction;
    private final CountArray currentCount;
    private final CountArray totalCount;

    public AnomalyDetector(int rows, int columns) {
        currentTimestamp = 1;
        index = new int[rows];
        hashFunction = new HashFunction(rows, columns);
        currentCount = new CountArray(rows * columns);
        totalCount = new CountArray(rows * columns);
    }

    protected static float computeScore(int[] index, CountArray currentCount, CountArray totalCount, int timestamp) {
        return computeScore(currentCount.get(index), totalCount.get(index), timestamp);
    }

    protected static float computeScore(float a, float s, float t) {
        if (s == 0.0f || t - 1 == 0.0f) {
            return 0.0f;
        }

        float temp = (a - s / t) * t;
        return temp * temp / (s * (t - 1));
    }

    public float updateAndScore(int source, int destination, int timestamp) {
        if (timestamp > currentTimestamp) {
            currentCount.clearAll();
            currentTimestamp = timestamp;
        }

        hashFunction.hash(source, destination, index);
        currentCount.add(index);
        totalCount.add(index);

        return computeScore(currentCount.get(index), totalCount.get(index), timestamp);
    }
}
