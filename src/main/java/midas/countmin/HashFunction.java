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

import java.util.Random;

public class HashFunction {
    public static final int DEFAULT_MAGIC_NUMBER = 104729;

    private final int rows;
    private final int columns;
    private final int magicNumber;
    private final int[] param1;
    private final int[] param2;

    public HashFunction(int rows, int columns, int magicNumber) {
        this.rows = rows;
        this.columns = columns;
        this.magicNumber = magicNumber;
        param1 = new int[rows];
        param2 = new int[rows];

        Random rng = new Random();
        for (int i = 0; i < rows; i++) {
            param1[i] = rng.nextInt(Integer.MAX_VALUE) + 1;
            param2[i] = rng.nextInt(Integer.MAX_VALUE) + 1;
        }
    }

    public HashFunction(int rows, int columns) {
        this(rows, columns, DEFAULT_MAGIC_NUMBER);
    }

    public void hash(int a, int[] index) {
        for (int i = 0; i < rows; i++) {
            index[i] = (a * param1[i] + param2[i]) % columns;
            index[i] += i * columns + (index[i] < 0 ? columns : 0);
        }
    }

    public void hash(int a, int b, int[] index) {
        hash(a + magicNumber * b, index);
    }

}
