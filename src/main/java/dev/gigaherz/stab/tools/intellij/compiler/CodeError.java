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
public class CodeError {
    private String property$Filename;
    private int property$Id;
    private int property$Level;
    private String property$Message;

    public CodeError(String filename, int id, int level, String message) {
        this.setFilename(filename);
        this.setId(id);
        this.setLevel(level);
        this.setMessage(message);
    }

    public final String getFilename() {
        return this.property$Filename;
    }

    private void setFilename(String value) {
        this.property$Filename = value;
    }

    public final int getId() {
        return this.property$Id;
    }

    private void setId(int value) {
        this.property$Id = value;
    }

    public final int getLevel() {
        return this.property$Level;
    }

    private void setLevel(int value) {
        this.property$Level = value;
    }

    public final String getMessage() {
        return this.property$Message;
    }

    private void setMessage(String value) {
        this.property$Message = value;
    }
}
