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

import static java.lang.Math.max;
import static midas.AnomalyDetector.computeScore;

import midas.countmin.CountArray;
import midas.countmin.HashFunction;

public class RelationalAnomalyDetector {

    public static final float DEFAULT_DECAY_FACTOR = 0.5f;

    private int currentTimestamp;
    private final float decayFactor;
    private final int[] indexEdge;
    private final int[] indexSource;
    private final int[] indexDest;
    private final HashFunction hashFunction;
    private final CountArray currentEdgeCount;
    private final CountArray totalEdgeCount;
    private final CountArray currentSourceCount;
    private final CountArray totalSourceCount;
    private final CountArray currentDestCount;
    private final CountArray totalDestCount;

    public RelationalAnomalyDetector(int rows, int columns, float decayFactor) {
        currentTimestamp = 1;
        this.decayFactor = decayFactor;
        indexEdge = new int[rows];
        indexSource = new int[rows];
        indexDest = new int[rows];
        hashFunction = new HashFunction(rows, columns);
        currentEdgeCount = new CountArray(rows * columns);
        totalEdgeCount = new CountArray(rows * columns);
        currentSourceCount = new CountArray(rows * columns);
        totalSourceCount = new CountArray(rows * columns);
        currentDestCount = new CountArray(rows * columns);
        totalDestCount = new CountArray(rows * columns);
    }

    public float updateAndScore(int source, int destination, int timestamp) {
        if (timestamp > currentTimestamp) {
            currentEdgeCount.multiplyAll(decayFactor);
            currentSourceCount.multiplyAll(decayFactor);
            currentDestCount.multiplyAll(decayFactor);
            currentTimestamp = timestamp;
        }

        hashFunction.hash(source, destination, indexEdge);
        currentEdgeCount.add(indexEdge);
        totalEdgeCount.add(indexEdge);
        float edgeScore = computeScore(indexEdge, currentEdgeCount, totalEdgeCount, timestamp);

        hashFunction.hash(source, indexSource);
        currentSourceCount.add(indexSource);
        totalSourceCount.add(indexSource);
        float sourceScore = computeScore(indexSource, currentSourceCount, totalSourceCount, timestamp);

        hashFunction.hash(destination, indexDest);
        currentDestCount.add(indexDest);
        totalDestCount.add(indexDest);
        float destScore = computeScore(indexDest, currentDestCount, totalDestCount, timestamp);

        return max(max(edgeScore, sourceScore), destScore);
    }
}
