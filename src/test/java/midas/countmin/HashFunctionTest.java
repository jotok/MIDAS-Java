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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashFunctionTest {
    private int rows;
    private int columns;
    private HashFunction hashFunction;

    @BeforeEach
    public void setUp() {
        rows = 3;
        columns = 2;
        hashFunction = new HashFunction(rows, columns);
    }

    @Test
    public void testHash() {
        int[] index = new int[rows];
        int a = 123;
        hashFunction.hash(a, index);

        for (int i = 0; i < rows; i++) {
            assertTrue(i * columns <= index[i] && index[i] < (i + 1) * columns);
        }

        int b = 456;
        hashFunction.hash(a, b, index);
        for (int i = 0; i < rows; i++) {
            assertTrue(i * columns <= index[i] && index[i] < (i + 1) * columns);
        }
    }
}
