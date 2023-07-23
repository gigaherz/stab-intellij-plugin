package dev.gigaherz.stab.tools.intellij;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.ui.IconManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class StabFileType extends LanguageFileType
{
    protected StabFileType()
    {
        super(StabLanguage.INSTANCE);
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getName()
    {
        return "StabSource";
    }

    @Override
    public @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Nls(capitalization = Nls.Capitalization.Sentence) String getDescription()
    {
        return "Stab language source code";
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getDefaultExtension()
    {
        return "stab";
    }

    @Override
    public @org.jetbrains.annotations.Nullable Icon getIcon()
    {
        return StabIcon;
    }

    // from AllIcons

    @SuppressWarnings("SameParameterValue")
    private static @NotNull Icon load(@NotNull String path, int cacheKey, int flags) {
        return IconManager.getInstance().loadRasterizedIcon(path, StabFileType.class.getClassLoader(), cacheKey, flags);
    }
    /** 16x16 */ public static final @NotNull Icon StabIcon = load("icons/stabolb.svg", 0x736b4587, 0);
}
