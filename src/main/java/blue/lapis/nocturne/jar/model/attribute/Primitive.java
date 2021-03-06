/*
 * Nocturne
 * Copyright (c) 2015-2016, Lapis <https://github.com/LapisBlue>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package blue.lapis.nocturne.jar.model.attribute;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a primitive data type.
 */
public enum Primitive {

    BOOLEAN('Z'),
    BYTE('B'),
    CHAR('C'),
    DOUBLE('D'),
    FLOAT('F'),
    INT('I'),
    LONG('J'),
    SHORT('S'),
    VOID('V');

    private static Map<Character, Primitive> KEY_MAP;

    private final char key;

    /**
     * Constructs a new {@link Primitive} with the given key character.
     *
     * @param key The key character of the new {@link Primitive}
     */
    Primitive(char key) {
        this.key = key;
        updateKeyMap(); // has to be in different method because of Java limitations
    }

    /**
     * Gets the key character associated with this {@link Primitive} type.
     *
     * @return The key character associated with this {@link Primitive} type
     */
    public char getKey() {
        return key;
    }

    /**
     * Gets the {@link Primitive} type associated with the iven key character.
     *
     * @param key The key character to match
     * @return The {@link Primitive} type associated with the given key
     *     character
     * @throws IllegalArgumentException If the provided character cannot be
     *     matched to a {@link Primitive} type
     */
    public static Primitive getFromKey(char key) {
        Preconditions.checkArgument(KEY_MAP.containsKey(key), "Illegal primitive key: " + key);
        return KEY_MAP.get(key);
    }

    private void updateKeyMap() {
        if (KEY_MAP == null) {
            KEY_MAP = new HashMap<>();
        }
        KEY_MAP.put(getKey(), this);
    }

}
