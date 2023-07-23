package dev.gigaherz.stab.tools.intellij;

import com.intellij.lang.Language;

public class StabLanguage extends Language
{
    public static final StabLanguage INSTANCE = new StabLanguage();

    protected StabLanguage()
    {
        super("StabLanguage");
    }
}
