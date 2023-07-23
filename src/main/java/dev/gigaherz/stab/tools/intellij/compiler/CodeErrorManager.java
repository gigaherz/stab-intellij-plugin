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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class CodeErrorManager {
    private List<CodeError> errors = new ArrayList();
    private boolean[] errorsByScope = new boolean[16];
    private int scopes;
    private IntIterable disabledWarnings;
    private boolean disableWarnings;

    public CodeErrorManager() {
    }

    public final Iterable<CodeError> getErrors() {
        return this.errors;
    }

    public final int getErrorCount() {
        return this.errors.size();
    }

    public final void setErrorCount(int value) {
        while(value < this.errors.size()) {
            this.errors.remove(this.errors.size() - 1);
        }

    }

    public final boolean getHasErrors() {
        if (this.scopes == 0) {
            return this.errors.stream().anyMatch(p -> p.getLevel() == 0);
        } else {
            return this.errorsByScope[this.scopes];
        }
    }

    public final IntIterable getDisabledWarnings() {
        return this.disabledWarnings;
    }

    public final void setDisabledWarnings(IntIterable value) {
        this.disabledWarnings = value;
        this.disableWarnings = false;
    }

    public final void disableAllWarnings() {
        this.disableWarnings = true;
    }

    public final void disable() {
        ++this.scopes;
        if (this.scopes == this.errorsByScope.length) {
            boolean[] t = new boolean[this.errorsByScope.length * 2];
            System.arraycopy(this.errorsByScope, 0, t, 0, this.errorsByScope.length);
            this.errorsByScope = t;
        }

        this.errorsByScope[this.scopes] = false;
    }

    public final void restore() {
        --this.scopes;
    }

    public final boolean isDisabled() {
        return this.scopes > 0;
    }

    public final void addError(String filename, int id, int level, String message) {
        if (level == 0) {
            this.errorsByScope[this.scopes] = true;
        }
        if (this.scopes == 0) {
            if (level == 0 || (!disableWarnings && this.getDisabledWarnings() != null
                    && StreamSupport.stream(this.getDisabledWarnings().spliterator(), false).noneMatch(i -> i == id))) {
                this.errors.add(new CodeError(filename, id, level, message));
            }
        }
    }
}
