/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.buf;

import java.io.Serializable;

/**
 * Base class for the *Chunk implementation to reduce duplication.
 */
public abstract class AbstractChunk implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;


    private int hashCode = 0;
    protected boolean hasHashCode = false;

    protected boolean isSet;

    protected int start;
    protected int end;


    /**
     * @return the start position of the data in the buffer
     */
    public int getStart() {
        return start;
    }


    public int getEnd() {
        return end;
    }


    public void setEnd(int i) {
        end = i;
    }


    // TODO: Deprecate offset and use start

    public int getOffset() {
        return start;
    }


    public void setOffset(int off) {
        if (end < off) {
            end = off;
        }
        start = off;
    }


    /**
     * @return the length of the data in the buffer
     */
    public int getLength() {
        return end - start;
    }


    public boolean isNull() {
        if (end > 0) {
            return false;
        }
        return !isSet;
    }


    /**
     * Resets the chunk to an uninitialized state.
     */
    public void recycle() {
        hasHashCode = false;
        isSet = false;
        start = 0;
        end = 0;
    }


    @Override
    public int hashCode() {
        if (hasHashCode) {
            return hashCode;
        }
        int code = 0;

        code = hash();
        hashCode = code;
        hasHashCode = true;
        return code;
    }


    public int hash() {
        int code = 0;
        for (int i = start; i < end; i++) {
            code = code * 37 + getBufferElement(i);
        }
        return code;
    }


    protected abstract int getBufferElement(int index);
}
