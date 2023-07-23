package dev.gigaherz.stab.tools.intellij;
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
import java.text.MessageFormat;
import java.util.*;

public class StabResourceManager
{
    private Map<Locale, ResourceBundle> resourceBundles = new HashMap();
    private Class<?> targetPackageClass;
    private String shortName;

    public StabResourceManager(Class<?> c, String shortName) {
        this.targetPackageClass = c;
        this.shortName = shortName;
    }

    public final String getMessage(Locale locale, String key, Object... args) {
        ResourceBundle rb = this.getResourceBundle(locale);
        if (rb != null) {
            try {
                return MessageFormat.format(rb.getString(key), args);
            } catch (Throwable var6) {
            }
        }

        return key;
    }

    private synchronized ResourceBundle getResourceBundle(Locale locale) {
        ResourceBundle result = (ResourceBundle)this.resourceBundles.get(locale);
        if (result == null) {
            result = this.loadResources(locale);
            if (result != null) {
                this.resourceBundles.put(locale, result);
            }
        }

        return result;
    }

    private ResourceBundle loadResources(Locale locale) {
        try {
            return ResourceBundle.getBundle((new StringBuilder()).append(this.targetPackageClass.getPackage().getName()).append(".").append(this.shortName).toString(), locale);
        } catch (MissingResourceException var3) {
            return null;
        }
    }
}
