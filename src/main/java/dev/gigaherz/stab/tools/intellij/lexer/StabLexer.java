package dev.gigaherz.stab.tools.intellij.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.lexer.RestartableLexer;
import com.intellij.lexer.TokenIterator;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class StabLexer extends Lexer implements RestartableLexer
{
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StabLexer.class);

    private CharSequence text = "";
    private int start = 0;
    private int end = 0;
    private int index = 0;

    private int tokenStart;
    private int tokenEnd;
    private int currentChar;

    private IElementType tokenType;
    private StabKeyword keyword;

    public StabLexer()
    {
    }

    @Override
    public int getStartState() {
        return 0;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public boolean isRestartableState(int i) {
        return true;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void start(@NotNull CharSequence charSequence, int startOffset, int endOffset, int initialState, TokenIterator tokenIterator)
    {
        start(charSequence, startOffset, endOffset, initialState);
    }

    @Override
    public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState)
    {
        setSource(buffer, startOffset, endOffset);

        restart(startOffset);
    }

    @Override
    public int getState()
    {
        return 0;
    }

    @Override
    public @Nullable IElementType getTokenType()
    {
        return tokenType;
    }

    @Override
    public int getTokenStart()
    {
        return this.tokenStart;
    }

    @Override
    public int getTokenEnd()
    {
        return this.tokenEnd ;
    }

    @Override
    public void advance()
    {
        scanAndApplyKeyword();
    }

    @Override
    public @NotNull LexerPosition getCurrentPosition()
    {
        return new RestorePoint(this.index);
    }

    @Override
    public void restore(@NotNull LexerPosition position)
    {
        restart(position.getOffset());
    }

    @Override
    public @NotNull CharSequence getBufferSequence()
    {
        return this.text;
    }

    @Override
    public int getBufferEnd()
    {
        return end;
    }


    private void setSource(@NotNull CharSequence buffer, int start, int end)
    {
        this.text = buffer;
        this.start = start;
        this.end = end;
    }

    protected final void restart(int position)
    {
        this.index = position;
        this.tokenStart = this.index;
        this.tokenEnd = this.index;
        this.currentChar = peekChar();

        scanAndApplyKeyword();
    }

    private int peekChar()
    {
        if (this.index >= start && this.index < end)
        {
            return this.getBufferSequence().charAt(this.index);
        }
        else
        {
            return -1;
        }
    }

    private int nextChar()
    {
        this.index++;
        this.currentChar = peekChar();
        return this.currentChar;
    }

    private void scanAndApplyKeyword()
    {
        StabToken currentLU;
        do
        {
            currentLU = scanOne();
        }
        while (currentLU == null);

        if (currentLU == StabToken.END_OF_STREAM)
            currentLU = null;

        tokenEnd = index;
        if (currentLU == StabToken.KEYWORD || currentLU == StabToken.CONTEXTUAL_KEYWORD)
        {
            tokenType = this.keyword;
        }
        else
        {
            tokenType = currentLU;
        }
    }

    @Nullable
    public StabToken scanOne() {
        this.tokenStart = this.index;
        switch (this.currentChar)
        {
            case -1 ->
            {
                return StabToken.END_OF_STREAM;
            }
            case '\r' ->
            {
                if (nextChar() == '\n')
                {
                    nextChar();
                }
                return StabToken.NEW_LINE;
            }
            case '\u0085', '\u2028', '\u2029', '\n' ->
            {
                nextChar();
                return StabToken.NEW_LINE;
            }
            case ' ', '\t', 11 /*VT*/, '\f' ->
            {
                scanWhitespaces();
                return StabToken.WHITESPACE;
            }
            case '{' ->
            {
                nextChar();
                return StabToken.OPEN_BRACE;
            }
            case '}' ->
            {
                nextChar();
                return StabToken.CLOSE_BRACE;
            }
            case '[' ->
            {
                nextChar();
                return StabToken.OPEN_BRACKET;
            }
            case ']' ->
            {
                nextChar();
                return StabToken.CLOSE_BRACKET;
            }
            case '(' ->
            {
                nextChar();
                return StabToken.OPEN_PARENTHESIS;
            }
            case ')' ->
            {
                nextChar();
                return StabToken.CLOSE_PARENTHESIS;
            }
            case ',' ->
            {
                nextChar();
                return StabToken.COMMA;
            }
            case ';' ->
            {
                nextChar();
                return StabToken.SEMICOLON;
            }
            case '~' ->
            {
                nextChar();
                return StabToken.COMPLEMENT;
            }
            case '>' ->
            {
                if (nextChar() == '=')
                {
                    nextChar();
                    return StabToken.GREATER_THAN_OR_EQUAL;
                }
                return StabToken.GREATER_THAN;
            }
            case '?' ->
            {
                if (nextChar() == '?')
                {
                    nextChar();
                    return StabToken.NULL_COALESCING;
                }
                return StabToken.QUESTION_MARK;
            }
            case '!' ->
            {
                if (nextChar() == '=')
                {
                    nextChar();
                    return StabToken.NOT_EQUAL;
                }
                return StabToken.NOT;
            }
            case ':' ->
            {
                nextChar();
                return StabToken.COLON;
            }
            case '*' ->
            {
                if (nextChar() == '=')
                {
                    nextChar();
                    return StabToken.MULTIPLY_ASSIGN;
                }
                return StabToken.MULTIPLY;
            }
            case '%' ->
            {
                if (nextChar() == '=')
                {
                    nextChar();
                    return StabToken.MODULO_ASSIGN;
                }
                return StabToken.PERCENT;
            }
            case '^' ->
            {
                if (nextChar() == '=')
                {
                    nextChar();
                    return StabToken.XOR_ASSIGN;
                }
                return StabToken.XOR;
            }
            case '<' ->
            {
                switch (nextChar())
                {
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.LESS_THAN_OR_EQUAL;
                    }
                    case '<' ->
                    {
                        if (nextChar() == '=')
                        {
                            nextChar();
                            return StabToken.LEFT_SHIFT_ASSIGN;
                        }
                        return StabToken.LEFT_SHIFT;
                    }
                    default ->
                    {
                        return StabToken.LESS_THAN;
                    }
                }
            }
            case '+' ->
            {
                switch (nextChar())
                {
                    case '+' ->
                    {
                        nextChar();
                        return StabToken.INCREMENT;
                    }
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.ADD_ASSIGN;
                    }
                    default ->
                    {
                        return StabToken.PLUS;
                    }
                }
            }
            case '-' ->
            {
                switch (nextChar())
                {
                    case '-' ->
                    {
                        nextChar();
                        return StabToken.DECREMENT;
                    }
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.SUBTRACT_ASSIGN;
                    }
                    default ->
                    {
                        return StabToken.MINUS;
                    }
                }
            }
            case '&' ->
            {
                switch (nextChar())
                {
                    case '&' ->
                    {
                        nextChar();
                        return StabToken.LOGICAL_AND;
                    }
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.AND_ASSIGN;
                    }
                    default ->
                    {
                        return StabToken.AND;
                    }
                }
            }
            case '|' ->
            {
                switch (nextChar())
                {
                    case '|' ->
                    {
                        nextChar();
                        return StabToken.LOGICAL_OR;
                    }
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.OR_ASSIGN;
                    }
                    default ->
                    {
                        return StabToken.OR;
                    }
                }
            }
            case '=' ->
            {
                switch (nextChar())
                {
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.EQUAL;
                    }
                    case '>' ->
                    {
                        nextChar();
                        return StabToken.LAMBDA;
                    }
                    default ->
                    {
                        return StabToken.ASSIGN;
                    }
                }
            }
            case 'a' ->
            {
                switch (nextChar())
                {
                    case 'b' ->
                    {
                        return scanKeyword("bstract", StabKeyword.ABSTRACT);
                    }
                    case 's' ->
                    {
                        if (nextChar() == 'c')
                        {
                            return scanContextualKeyword("cending", StabKeyword.ASCENDING);
                        }
                        return scanKeyword("", StabKeyword.AS);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'b' ->
            {
                switch (nextChar())
                {
                    case 'o' ->
                    {
                        return scanKeyword("oolean", StabKeyword.BOOLEAN);
                    }
                    case 'r' ->
                    {
                        return scanKeyword("reak", StabKeyword.BREAK);
                    }
                    case 'y' ->
                    {
                        if (nextChar() == 't')
                        {
                            return scanKeyword("te", StabKeyword.BYTE);
                        }
                        return scanContextualKeyword("", StabKeyword.BY);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'c' ->
            {
                switch (nextChar())
                {
                    case 'a' ->
                    {
                        return switch (nextChar())
                        {
                            case 's' -> scanKeyword("se", StabKeyword.CASE);
                            case 't' -> scanKeyword("tch", StabKeyword.CATCH);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'h' ->
                    {
                        if (nextChar() == 'a')
                        {
                            return scanKeyword("ar", StabKeyword.CHAR);
                        }
                        return scanKeyword("", StabKeyword.NONE);
                    }
                    case 'l' ->
                    {
                        return scanKeyword("lass", StabKeyword.CLASS);
                    }
                    case 'o' ->
                    {
                        if (nextChar() == 'n')
                        {
                            if (nextChar() == 't')
                            {
                                return scanKeyword("tinue", StabKeyword.CONTINUE);
                            }
                            return scanKeyword("", StabKeyword.NONE);
                        }
                        return scanKeyword("", StabKeyword.NONE);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'd' ->
            {
                switch (nextChar())
                {
                    case 'e' ->
                    {
                        return switch (nextChar())
                        {
                            case 'f' -> scanKeyword("fault", StabKeyword.DEFAULT);
                            case 'l' -> scanKeyword("legate", StabKeyword.DELEGATE);
                            case 's' -> scanContextualKeyword("scending", StabKeyword.DESCENDING);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'o' ->
                    {
                        if (nextChar() == 'u')
                        {
                            return scanKeyword("uble", StabKeyword.DOUBLE);
                        }
                        return scanKeyword("", StabKeyword.DO);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'e' ->
            {
                return switch (nextChar())
                {
                    case 'l' -> scanKeyword("lse", StabKeyword.ELSE);
                    case 'n' -> scanKeyword("num", StabKeyword.ENUM);
                    case 'q' -> scanContextualKeyword("quals", StabKeyword.EQUALS);
                    default -> scanKeyword("", StabKeyword.NONE);
                };
            }
            case 'f' ->
            {
                switch (nextChar())
                {
                    case 'a' ->
                    {
                        return scanKeyword("alse", StabKeyword.FALSE);
                    }
                    case 'i' ->
                    {
                        if (nextChar() == 'n')
                        {
                            if (nextChar() == 'a')
                            {
                                if (nextChar() == 'l')
                                {
                                    if (nextChar() == 'l')
                                    {
                                        return scanKeyword("ly", StabKeyword.FINALLY);
                                    }
                                    return scanKeyword("", StabKeyword.FINAL);
                                }
                                return scanKeyword("", StabKeyword.NONE);
                            }
                            return scanKeyword("", StabKeyword.NONE);
                        }
                        return scanKeyword("", StabKeyword.NONE);
                    }
                    case 'l' ->
                    {
                        return scanKeyword("loat", StabKeyword.FLOAT);
                    }
                    case 'o' ->
                    {
                        if (nextChar() == 'r')
                        {
                            if (nextChar() == 'e')
                            {
                                return scanKeyword("each", StabKeyword.FOREACH);
                            }
                            return scanKeyword("", StabKeyword.FOR);
                        }
                        return scanKeyword("", StabKeyword.NONE);
                    }
                    case 'r' ->
                    {
                        return scanContextualKeyword("rom", StabKeyword.FROM);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'g' ->
            {
                return switch (nextChar())
                {
                    case 'e' -> scanContextualKeyword("et", StabKeyword.GET);
                    case 'o' -> scanKeyword("oto", StabKeyword.GOTO);
                    case 'r' -> scanContextualKeyword("roup", StabKeyword.GROUP);
                    default -> scanKeyword("", StabKeyword.NONE);
                };
            }
            case 'i' ->
            {
                switch (nextChar())
                {
                    case 'f' ->
                    {
                        return scanKeyword("f", StabKeyword.IF);
                    }
                    case 'n' ->
                    {
                        switch (nextChar())
                        {
                            case 's' ->
                            {
                                return scanKeyword("stanceof", StabKeyword.INSTANCEOF);
                            }
                            case 't' ->
                            {
                                return switch (nextChar())
                                {
                                    case 'e' -> scanKeyword("erface", StabKeyword.INTERFACE);
                                    case 'o' -> scanContextualKeyword("o", StabKeyword.INTO);
                                    default -> scanKeyword("", StabKeyword.INT);
                                };
                            }
                            default ->
                            {
                                return scanKeyword("", StabKeyword.IN);
                            }
                        }
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'j' ->
            {
                return scanContextualKeyword("join", StabKeyword.JOIN);
            }
            case 'l' ->
            {
                switch (nextChar())
                {
                    case 'e' ->
                    {
                        return scanContextualKeyword("et", StabKeyword.LET);
                    }
                    case 'o' ->
                    {
                        if (nextChar() == 'n')
                        {
                            return scanKeyword("ng", StabKeyword.LONG);
                        }
                        return scanKeyword("", StabKeyword.NONE);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'n' ->
            {
                return switch (nextChar())
                {
                    case 'a' -> scanKeyword("ative", StabKeyword.NATIVE);
                    case 'e' -> scanKeyword("ew", StabKeyword.NEW);
                    case 'u' -> scanKeyword("ull", StabKeyword.NULL);
                    default -> scanKeyword("", StabKeyword.NONE);
                };
            }
            case 'o' ->
            {
                return switch (nextChar())
                {
                    case 'n' -> scanContextualKeyword("n", StabKeyword.ON);
                    case 'r' -> scanContextualKeyword("rderby", StabKeyword.ORDERBY);
                    case 'v' -> scanKeyword("verride", StabKeyword.OVERRIDE);
                    default -> scanKeyword("", StabKeyword.NONE);
                };
            }
            case 'p' ->
            {
                switch (nextChar())
                {
                    case 'a' ->
                    {
                        switch (nextChar())
                        {
                            case 'c' ->
                            {
                                return scanKeyword("ckage", StabKeyword.PACKAGE);
                            }
                            case 'r' ->
                            {
                                return switch (nextChar())
                                {
                                    case 'a' -> scanKeyword("ams", StabKeyword.PARAMS);
                                    case 't' -> scanContextualKeyword("tial", StabKeyword.PARTIAL);
                                    default -> scanKeyword("", StabKeyword.NONE);
                                };
                            }
                            default ->
                            {
                                return scanKeyword("", StabKeyword.NONE);
                            }
                        }
                    }
                    case 'r' ->
                    {
                        return switch (nextChar())
                        {
                            case 'i' -> scanKeyword("ivate", StabKeyword.PRIVATE);
                            case 'o' -> scanKeyword("otected", StabKeyword.PROTECTED);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'u' ->
                    {
                        return scanKeyword("ublic", StabKeyword.PUBLIC);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'r' ->
            {
                return scanKeyword("return", StabKeyword.RETURN);
            }
            case 's' ->
            {
                switch (nextChar())
                {
                    case 'e' ->
                    {
                        return switch (nextChar())
                        {
                            case 'l' -> scanContextualKeyword("lect", StabKeyword.SELECT);
                            case 't' -> scanContextualKeyword("t", StabKeyword.SET);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'h' ->
                    {
                        return scanKeyword("hort", StabKeyword.SHORT);
                    }
                    case 'i' ->
                    {
                        return scanKeyword("izeof", StabKeyword.SIZE_OF);
                    }
                    case 't' ->
                    {
                        switch (nextChar())
                        {
                            case 'a' ->
                            {
                                if (nextChar() == 't')
                                {
                                    return scanKeyword("tic", StabKeyword.STATIC);
                                }
                                return scanKeyword("", StabKeyword.NONE);
                            }
                            case 'r' ->
                            {
                                return scanKeyword("rictfp", StabKeyword.STRICTFP);
                            }
                            default ->
                            {
                                return scanKeyword("", StabKeyword.NONE);
                            }
                        }
                    }
                    case 'u' ->
                    {
                        return scanKeyword("uper", StabKeyword.SUPER);
                    }
                    case 'w' ->
                    {
                        return scanKeyword("witch", StabKeyword.SWITCH);
                    }
                    case 'y' ->
                    {
                        return scanKeyword("ynchronized", StabKeyword.SYNCHRONIZED);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 't' ->
            {
                switch (nextChar())
                {
                    case 'h' ->
                    {
                        return switch (nextChar())
                        {
                            case 'i' -> scanKeyword("is", StabKeyword.THIS);
                            case 'r' -> scanKeyword("row", StabKeyword.THROW);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'r' ->
                    {
                        return switch (nextChar())
                        {
                            case 'a' -> scanKeyword("ansient", StabKeyword.TRANSIENT);
                            case 'u' -> scanKeyword("ue", StabKeyword.TRUE);
                            case 'y' -> scanKeyword("y", StabKeyword.TRY);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'y' ->
                    {
                        return scanKeyword("ypeof", StabKeyword.TYPE_OF);
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'u' ->
            {
                if (nextChar() == 's')
                {
                    if (nextChar() == 'i')
                    {
                        return scanKeyword("ing", StabKeyword.USING);
                    }
                    return scanKeyword("", StabKeyword.NONE);
                }
                return scanKeyword("", StabKeyword.NONE);
            }
            case 'v' ->
            {
                switch (nextChar())
                {
                    case 'a' ->
                    {
                        return switch (nextChar())
                        {
                            case 'l' -> scanContextualKeyword("lue", StabKeyword.VALUE);
                            case 'r' -> scanContextualKeyword("r", StabKeyword.VAR);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    case 'i' ->
                    {
                        return scanKeyword("irtual", StabKeyword.VIRTUAL);
                    }
                    case 'o' ->
                    {
                        return switch (nextChar())
                        {
                            case 'i' -> scanKeyword("id", StabKeyword.VOID);
                            case 'l' -> scanKeyword("latile", StabKeyword.VOLATILE);
                            default -> scanKeyword("", StabKeyword.NONE);
                        };
                    }
                    default ->
                    {
                        return scanKeyword("", StabKeyword.NONE);
                    }
                }
            }
            case 'w' ->
            {
                if (nextChar() == 'h')
                {
                    return switch (nextChar())
                    {
                        case 'e' -> scanContextualKeyword("ere", StabKeyword.WHERE);
                        case 'i' -> scanKeyword("ile", StabKeyword.WHILE);
                        default -> scanKeyword("", StabKeyword.NONE);
                    };
                }
                return scanKeyword("", StabKeyword.NONE);
            }
            case 'y' ->
            {
                return scanContextualKeyword("yield", StabKeyword.YIELD);
            }
            case '/' ->
            {
                switch (nextChar())
                {
                    case '/' ->
                    {
                        for (; ; )
                        {
                            switch (nextChar())
                            {
                                case -1, '\r', '\u2028', '\u2029', '\n' ->
                                {
                                    return StabToken.SINGLE_LINE_COMMENT;
                                }
                            }
                        }
                    }
                    case '*' ->
                    {
                        for (; ; )
                        {
                            switch (nextChar())
                            {
                                case -1:
                                    return null;

                                case '\r':
                                    break;

                                case '\n':
                                    break;

                                case '\u2028':
                                case '\u2029':
                                    break;

                                case '*':
                                    switch (nextChar())
                                    {
                                        case -1 -> {
                                            return null;
                                        }
                                        case '/' ->
                                        {
                                            nextChar();
                                            return StabToken.DELIMITED_COMMENT;
                                        }
                                        default ->
                                        {
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    case '=' ->
                    {
                        nextChar();
                        return StabToken.DIVIDE_ASSIGN;
                    }
                    default ->
                    {
                        return StabToken.DIVIDE;
                    }
                }
            }
            case '.' ->
            {
                return switch (nextChar())
                {
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> scanDotNumber();
                    default -> StabToken.DOT;
                };
            }
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            {
                return scanNumber();
            }
            case '\'' ->
            {
                return scanCharacter();
            }
            case '"' ->
            {
                return scanString();
            }
            case '@' ->
            {
                switch (nextChar())
                {
                    case '"' ->
                    {
                        return scanVerbatimString();
                    }
                    case '_' ->
                    {
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                    }
                    case '\\' ->
                    {
                        int unicode = scanUnicodeEscapeSequence();
                        if (!Character.isJavaIdentifierStart(unicode))
                        {
                            return null;
                        }
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                    }
                    default ->
                    {
                        if (!Character.isJavaIdentifierStart(this.currentChar))
                        {
                            return null;
                        }
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                    }
                }
            }
            case '_' ->
            {
                scanIdentifierPart();
                return StabToken.IDENTIFIER;
            }
            case '\\' ->
            {
                int unicode = scanUnicodeEscapeSequence();
                if (!Character.isJavaIdentifierStart(unicode))
                {
                    return null;
                }
                scanIdentifierPart();
                return StabToken.IDENTIFIER;
            }
            default ->
            {
                if (!Character.isJavaIdentifierStart(this.currentChar))
                {
                    return null;
                }
                scanIdentifierPart();
                return StabToken.IDENTIFIER;
            }
        }
    }

    private void scanWhitespaces() {
        while(true) {
            switch(this.nextChar()) {
                case -1:
                default:
                    return;
                case 9:
                case 11:
                case 12:
                case 32:
            }
        }
    }

    private void scanIdentifierPart() {
        while(true) {
            switch (this.nextChar())
            {
                case -1 ->
                {
                    return;
                }
                case 92 ->
                {
                    int unicode = this.scanUnicodeEscapeSequence();
                    if (Character.isJavaIdentifierPart(unicode))
                    {
                        break;
                    }
                    return;
                }
                default ->
                {
                    if (!Character.isJavaIdentifierPart(this.currentChar))
                    {
                        return;
                    }
                }
            }
        }
    }

    private int scanHexDigit(int character) {
        return switch (character)
        {
            case 48, 49, 50, 51, 52, 53, 54, 55, 56, 57 -> character - 48;
            case 65, 66, 67, 68, 69, 70 -> 10 + (character - 65);
            case 97, 98, 99, 100, 101, 102 -> 10 + (character - 97);
            default -> -1;
        };
    }

    private int scanUnicodeEscapeSequence() {
        int result = 0;
        int i;
        int value;
        switch (this.nextChar())
        {
            case 85 ->
            {
                for (i = 0; i < 8; ++i)
                {
                    if ((value = scanHexDigit(this.nextChar())) == -1)
                    {
                        break;
                    }

                    result = result * 16 + value;
                }
                return result;
            }
            case 117 ->
            {
                for (i = 0; i < 4; ++i)
                {
                    if ((value = scanHexDigit(this.nextChar())) == -1)
                    {
                        break;
                    }

                    result = result * 16 + value;
                }
                return result;
            }
            default -> {
                return 0;
            }
        }
    }

    private StabToken scanNumber() {
        while (true) {
            switch (nextChar()) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                case '.':
                    switch (nextChar())
                    {
                        case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                        {
                            return scanDotNumber();
                        }
                    }
                    return StabToken.REAL_LITERAL;
                case 'e':
                case 'E':
                    return scanENumber();
                case 'x':
                case 'X':
                    return scanHexNumber();
                case 'l':
                case 'L':
                    nextChar();
                    return StabToken.LONG_LITERAL;
                case 'f':
                case 'F':
                    nextChar();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    nextChar();
                    return StabToken.DOUBLE_LITERAL;
                default:
                    return StabToken.DECIMAL_INTEGER_LITERAL;
            }
        }
    }

    private StabToken scanDotNumber() {
        while (true) {
            switch (nextChar()) {
                case 'e':
                case 'E':
                    return scanENumber();
                default:
                    return StabToken.REAL_LITERAL;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                case 'f':
                case 'F':
                    nextChar();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    nextChar();
                    return StabToken.DOUBLE_LITERAL;
            }
        }
    }

    private StabToken scanENumber() {
        switch (nextChar())
        {
            case '+', '-' ->
            {
                switch (nextChar())
                {
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                    {
                        return scanExponent();
                    }
                }
            }
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
            {
                return scanExponent();
            }
        }
        return null;
    }

    private StabToken scanExponent() {
        while (true) {
            switch (nextChar()) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    break;
                case 'f':
                case 'F':
                    nextChar();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    nextChar();
                    return StabToken.DOUBLE_LITERAL;
                default:
                    return StabToken.REAL_LITERAL;
            }
        }
    }

    private StabToken scanHexNumber() {
        switch (nextChar()) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
                break;
            default:
                return null;
        }
        while (true) {
            switch (nextChar()) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                    break;
                case 'l':
                case 'L':
                    nextChar();
                    return StabToken.HEXADECIMAL_LONG_LITERAL;
                default:
                    return StabToken.HEXADECIMAL_INTEGER_LITERAL;
            }
        }
    }

    private StabToken scanCharacter() {
        switch (nextChar())
        {
            case -1, '\r', '\u2028', '\u2029', '\n' -> {
                return StabToken.CHARACTER_LITERAL;
            }
            case '\'' -> {
                nextChar();
                return StabToken.CHARACTER_LITERAL;
            }
            case '\\' ->
            {
                switch (nextChar())
                {
                    case '"', '\\', '\'', '0', 'a', 'b', 'f', 'n', 'r', 't', 'v' -> nextChar();
                    case 'u' ->
                    {
                        int value = 0;
                        for (int i = 0; i < 4; i++)
                        {
                            int digit;
                            if ((digit = scanHexDigit(nextChar())) == -1)
                            {
                                break;
                            }
                            value = value * 16 + digit;
                            if (value > Character.MAX_VALUE)
                            {
                                value &= Character.MAX_VALUE;
                            }
                        }
                        nextChar();
                    }
                    case 'U' ->
                    {
                        for (int i = 0; i < 8; i++)
                        {
                            if (scanHexDigit(nextChar()) == -1)
                            {
                                break;
                            }
                        }
                        nextChar();
                    }
                    case 'x' -> scanHexEscapeSequence();
                    default -> {
                        return null;
                    }
                }
            }
            default -> nextChar();
        }
        if (this.currentChar != '\'') {
            return StabToken.CHARACTER_LITERAL;
        }
        nextChar();
        return StabToken.CHARACTER_LITERAL;
    }

    private StabToken scanString() {
        for (;;) {
            switch (nextChar())
            {
                case -1, '\r', '\u2028', '\u2029', '\n' -> {
                    return StabToken.STRING_LITERAL;
                }
                case '"' ->
                {
                    nextChar();
                    return StabToken.STRING_LITERAL;
                }
                case '\\' ->
                {
                    switch (nextChar())
                    {
                        case '"':
                        case '\\':
                        case '\'':
                        case '0':
                        case 'a':
                        case 'b':
                        case 'f':
                        case 'n':
                        case 'r':
                        case 't':
                        case 'v':
                            break;
                        case 'u':
                            for (int i = 0; i < 4; i++)
                            {
                                if (scanHexDigit(nextChar()) == -1)
                                {
                                    break;
                                }
                            }
                            break;
                        case 'U':
                            for (int i = 0; i < 8; i++)
                            {
                                if (scanHexDigit(nextChar()) == -1)
                                {
                                    break;
                                }
                            }
                            break;
                        case 'x':
                            scanHexEscapeSequence();
                            break;

                        default:
                            return null;
                    }
                }
            }
        }
    }

    private void scanHexEscapeSequence() {
        switch (nextChar())
        {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' ->
            {
                switch (nextChar())
                {
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' ->
                    {
                        switch (nextChar())
                        {
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' ->
                            {
                                switch (nextChar())
                                {
                                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F' ->
                                            nextChar();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private StabToken scanVerbatimString() {
        for (;;) {
            switch (nextChar())
            {
                case -1 -> {
                    return StabToken.VERBATIM_STRING_LITERAL;
                }
                case '"' ->
                {
                    if (nextChar() != '"')
                    {
                        return StabToken.VERBATIM_STRING_LITERAL;
                    }
                    nextChar();
                }
            }
        }
    }

    private StabToken scanKeyword(String suffix, StabKeyword keyword) {
        return scanKeyword(suffix, keyword, false);
    }

    private StabToken scanContextualKeyword(String suffix, StabKeyword keyword) {
        return scanKeyword(suffix, keyword, true);
    }

    private StabToken scanKeyword(String suffix, StabKeyword keyword, boolean contextual) {
        boolean isKeyword = true;
        for (int i = 0; i < suffix.length(); i++) {
            int c = suffix.charAt(i);
            if (this.currentChar == -1 || (char)this.currentChar != c) {
                isKeyword = false;
                break;
            }
            nextChar();
        }
        if (this.currentChar != -1 && Character.isJavaIdentifierPart(this.currentChar))
        {
            scanIdentifierPart();
            return StabToken.IDENTIFIER;
        }
        if (isKeyword && keyword != StabKeyword.NONE) {
            this.keyword = keyword;
            return (contextual) ? StabToken.CONTEXTUAL_KEYWORD : StabToken.KEYWORD;
        } else {
            this.keyword = StabKeyword.NONE;
            return StabToken.IDENTIFIER;
        }
    }

    private record RestorePoint(int startPosition) implements LexerPosition
    {
        @Override
        public int getOffset()
        {
            return startPosition;
        }

        @Override
        public int getState()
        {
            return 0;
        }
    }
}
