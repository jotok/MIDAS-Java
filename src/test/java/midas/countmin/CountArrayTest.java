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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CountArrayTest {

    private int size;
    private CountArray countArray;

    @BeforeEach
    public void setUp() {
        size = 10;
        countArray = new CountArray(size);
    }

    @Test
    public void testNew() {
        for (int i = 0; i < size; i++) {
            assertEquals(0.0f, countArray.get(i));
        }
    }

    @Test
    public void testSetAll() {
        countArray.setAll(8.9f);
        for (int i = 0; i < size; i++) {
            assertEquals(8.9f, countArray.get(i));
        }
    }

    @Test
    public void testClearAll() {
        countArray.setAll(8.9f);

        int[] index = { 2, 5, 9 };
        countArray.add(index);

        countArray.clearAll();
        for (int i = 0; i < size; i++) {
            assertEquals(0.0f, countArray.get(i));
        }
    }

    @Test
    public void testAdd() {
        int[] index = { 2, 4, 6 };
        countArray.add(index, 1.5f);

        for (int i = 0; i < size; i++) {
            if (i == 2 || i == 4 || i == 6) {
                assertEquals(1.5f, countArray.get(i));
            } else {
                assertEquals(0.0f, countArray.get(i));
            }
        }

        int[] index2 = { 6, 8, 9 };
        countArray.add(index2);

        for (int i = 0; i < size; i++) {
            if (i == 2 || i == 4) {
                assertEquals(1.5f, countArray.get(i));
            } else if (i == 8 || i == 9) {
                assertEquals(1.0f, countArray.get(i));
            } else if (i == 6) {
                assertEquals(2.5f, countArray.get(i));
            } else {
                assertEquals(0.0f, countArray.get(i));
            }
        }
    }

    @Test
    public void testMultiplyAll() {
        int[] index = { 2, 4, 6 };
        countArray.add(index, 1.5f);

        int[] index2 = { 6, 8, 9 };
        countArray.add(index2);

        countArray.multiplyAll(2.0f);

        for (int i = 0; i < size; i++) {
            if (i == 2 || i == 4) {
                assertEquals(3.0f, countArray.get(i));
            } else if (i == 8 || i == 9) {
                assertEquals(2.0f, countArray.get(i));
            } else if (i == 6) {
                assertEquals(5.0f, countArray.get(i));
            } else {
                assertEquals(0.0f, countArray.get(i));
            }
        }
    }

    @Test
    public void testGet() {
        int[] index = { 2, 4, 6 };
        countArray.add(index, 1.5f);

        int[] index2 = { 6, 8, 9 };
        countArray.add(index2);

        int[] index3 = { 0, 1, 2 };
        assertEquals(0.0f, countArray.get(index3));

        index3 = new int[] { 2, 4, 6 };
        assertEquals(1.5f, countArray.get(index3));
    }
}
