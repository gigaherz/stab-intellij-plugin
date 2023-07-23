package dev.gigaherz.stab.tools.intellij.compiler;
/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class RestorePoint {
    private int property$StartPosition;
    private int property$TextLength;
    private int property$ErrorCount;
    private IntIterable property$DisabledWarnings;

    RestorePoint(int startPosition, int textLength, int errorCount, IntIterable disabledWarnings) {
        this.setStartPosition(startPosition);
        this.setTextLength(textLength);
        this.setErrorCount(errorCount);
        this.setDisabledWarnings(disabledWarnings);
    }

    public final int getStartPosition() {
        return this.property$StartPosition;
    }

    private void setStartPosition(int value) {
        this.property$StartPosition = value;
    }

    public final int getTextLength() {
        return this.property$TextLength;
    }

    private void setTextLength(int value) {
        this.property$TextLength = value;
    }

    public final int getErrorCount() {
        return this.property$ErrorCount;
    }

    private void setErrorCount(int value) {
        this.property$ErrorCount = value;
    }

    public final IntIterable getDisabledWarnings() {
        return this.property$DisabledWarnings;
    }

    private void setDisabledWarnings(IntIterable value) {
        this.property$DisabledWarnings = value;
    }
}
