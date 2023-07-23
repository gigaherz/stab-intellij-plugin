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
import dev.gigaherz.stab.tools.intellij.StabResourceManager;
import dev.gigaherz.stab.tools.intellij.lexer.StabLexer;

import java.util.Locale;

public class CompilerResources
{
    private static StabResourceManager resourceManager;
    private static Locale property$ResourceLocale;

    static {
        setResourceLocale(Locale.getDefault());
    }

    static final StabResourceManager getResourceManager() {
        if (resourceManager == null) {
            resourceManager = new StabResourceManager(CompilerResources.class, "messages");
        }

        return resourceManager;
    }

    static final Locale getResourceLocale() {
        return property$ResourceLocale;
    }

    static final void setResourceLocale(Locale value) {
        property$ResourceLocale = value;
    }

    /*public static final String getMessage(StabLexer.ParseErrorId id, Object... arguments) {
        return getResourceManager().getMessage(getResourceLocale(), id.toString(), arguments);
    }*/

    public CompilerResources() {
    }
}
