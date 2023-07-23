package dev.gigaherz.stab.tools.intellij;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import dev.gigaherz.stab.tools.intellij.lexer.StabToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StabBracePairMatcher implements PairedBraceMatcher
{
    private static final BracePair[] PAIRS = {
            new BracePair(StabToken.OPEN_BRACE, StabToken.CLOSE_BRACE, true),
            new BracePair(StabToken.OPEN_BRACKET, StabToken.CLOSE_BRACKET, true),
            new BracePair(StabToken.OPEN_PARENTHESIS, StabToken.CLOSE_PARENTHESIS, true),
    };

    @Override
    public BracePair @NotNull [] getPairs()
    {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType, @Nullable final IElementType contextType) {
        return true; /*contextType == StabToken.PUNCTUATION ||
                contextType == StabToken.WHITESPACE ||
                isRBraceToken(contextType);*/
    }

    private static boolean isRBraceToken(IElementType type) {
        for (BracePair pair : PAIRS) {
            if (type == pair.getRightBraceType()) return true;
        }
        return false;
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset)
    {
        return openingBraceOffset;
    }
}
