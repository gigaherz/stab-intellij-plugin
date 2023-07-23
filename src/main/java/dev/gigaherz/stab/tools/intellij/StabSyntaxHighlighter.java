package dev.gigaherz.stab.tools.intellij;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.tree.IElementType;
import dev.gigaherz.stab.tools.intellij.lexer.StabKeyword;
import dev.gigaherz.stab.tools.intellij.lexer.StabToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class StabSyntaxHighlighter extends SyntaxHighlighterBase
{
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StabSyntaxHighlighter.class);

    public static final Map<StabToken, TextAttributesKey[]> FORMATTING_KEYS = new HashMap<>();
    public static final Map<StabKeyword, TextAttributesKey[]> KEYWORD_KEYS = new HashMap<>();

    static
    {

        addFormatting(StabKeyword.ABSTRACT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.AS, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.ASCENDING, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.BOOLEAN, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.BREAK, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.BY, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.BYTE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.CASE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.CATCH, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.CHAR, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.CLASS, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.CONTINUE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DEFAULT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DELEGATE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DESCENDING, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DOUBLE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DO, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.ELSE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.ENUM, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.EQUALS, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FALSE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FINAL, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FINALLY, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FLOAT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FOR, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FOREACH, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FROM, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.GET, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.GOTO, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.GROUP, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.IF, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.IN, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.INSTANCEOF, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.INT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.INTERFACE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.INTO, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.JOIN, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.LET, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.LONG, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PACKAGE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.NATIVE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.NEW, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.NULL, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.ON, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.ORDERBY, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.OVERRIDE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PARAMS, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PARTIAL, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PRIVATE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PROTECTED, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.PUBLIC, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.RETURN, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SELECT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SET, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SHORT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SIZE_OF, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.STATIC, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.STRICTFP, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SUPER, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SWITCH, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SYNCHRONIZED, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.THIS, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.THROW, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.TRANSIENT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.TRUE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.TRY, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.TYPE_OF, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.USING, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.VALUE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.VAR, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.VIRTUAL, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.VOID, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.VOLATILE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.WHERE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.WHILE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.YIELD, FormatKeys.KEYWORD2);

        addFormatting(StabToken.ADD_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.AND, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.AND_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.CHARACTER_LITERAL, FormatKeys.STRING);
        addFormatting(StabToken.CLOSE_BRACE, FormatKeys.BRACES);
        addFormatting(StabToken.CLOSE_BRACKET, FormatKeys.BRACKETS);
        addFormatting(StabToken.CLOSE_PARENTHESIS, FormatKeys.PARENTHESES);
        addFormatting(StabToken.COLON, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.COMMA, FormatKeys.COMMA);
        addFormatting(StabToken.COMPLEMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.CONTEXTUAL_KEYWORD, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DECIMAL_INTEGER_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.DECREMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DELIMITED_COMMENT, FormatKeys.STAB_BLOCK_COMMENT);
        addFormatting(StabToken.DIVIDE, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DIVIDE_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DOT, FormatKeys.DOT);
        addFormatting(StabToken.DOUBLE_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.FLOAT_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.GREATER_THAN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.GREATER_THAN_OR_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.HEXADECIMAL_INTEGER_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.HEXADECIMAL_LONG_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.IDENTIFIER, FormatKeys.IDENTIFIER);
        addFormatting(StabToken.INCREMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.KEYWORD, FormatKeys.KEYWORD);
        addFormatting(StabToken.LAMBDA, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LEFT_SHIFT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LEFT_SHIFT_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LESS_THAN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LESS_THAN_OR_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LOGICAL_AND, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LOGICAL_OR, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LONG_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.MINUS, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MODULO_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MULTIPLY, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MULTIPLY_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NOT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NOT_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NULL_COALESCING, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.OPEN_BRACE, FormatKeys.BRACES);
        addFormatting(StabToken.OPEN_BRACKET, FormatKeys.BRACKETS);
        addFormatting(StabToken.OPEN_PARENTHESIS, FormatKeys.PARENTHESES);
        addFormatting(StabToken.OR, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.OR_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.PERCENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.PLUS, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.QUESTION_MARK, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.REAL_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.SEMICOLON, FormatKeys.SEMICOLON);
        addFormatting(StabToken.SINGLE_LINE_COMMENT, FormatKeys.LINE_COMMENT);
        addFormatting(StabToken.STRING_LITERAL, FormatKeys.STRING);
        addFormatting(StabToken.SUBTRACT_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.VERBATIM_IDENTIFIER, FormatKeys.IDENTIFIER);
        addFormatting(StabToken.VERBATIM_STRING_LITERAL, FormatKeys.STRING);
        addFormatting(StabToken.XOR, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.XOR_ASSIGN, FormatKeys.OPERATION_SIGN);
    }

    private static void addFormatting(StabToken lu, TextAttributesKey... formattings)
    {
        FORMATTING_KEYS.put(lu, formattings);
    }

    private static void addFormatting(StabKeyword kw, TextAttributesKey... formattings)
    {
        KEYWORD_KEYS.put(kw, formattings);
    }

    final StabLexer lexer = new StabLexer();

    public StabSyntaxHighlighter(Project project, @Nullable VirtualFile virtualFile)
    {
        if (virtualFile == null)
            LOGGER.info("Stab Syntax Highlighter initialized...");
        else
            LOGGER.info(String.format("Stab Syntax Highlighter initialized for %s...", virtualFile.getName()));
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer()
    {
        return lexer;
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType iElementType)
    {
        if (iElementType instanceof StabKeyword keyword)
        {
            if (KEYWORD_KEYS.containsKey(keyword))
            {
                return KEYWORD_KEYS.get(keyword);
            }
            else if (FORMATTING_KEYS.containsKey(StabToken.KEYWORD))
            {
                return FORMATTING_KEYS.get(StabToken.KEYWORD);
            }
        }
        else if (iElementType instanceof StabToken token)
        {
            if (FORMATTING_KEYS.containsKey(token))
            {
                return FORMATTING_KEYS.get(token);
            }
        }
        return new TextAttributesKey[0];
    }

    private static class FormatKeys {
        public static final TextAttributesKey LINE_COMMENT
                = TextAttributesKey.createTextAttributesKey("STAB_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
        public static final TextAttributesKey STAB_BLOCK_COMMENT
                = TextAttributesKey.createTextAttributesKey("STAB_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
        public static final TextAttributesKey KEYWORD
                = TextAttributesKey.createTextAttributesKey("STAB_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
        public static final TextAttributesKey KEYWORD2
                = TextAttributesKey.createTextAttributesKey("STAB_KEYWORD2", DefaultLanguageHighlighterColors.KEYWORD);
        public static final TextAttributesKey NUMBER
                = TextAttributesKey.createTextAttributesKey("STAB_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
        public static final TextAttributesKey STRING
                = TextAttributesKey.createTextAttributesKey("STAB_STRING", DefaultLanguageHighlighterColors.STRING);
        public static final TextAttributesKey IDENTIFIER
                = TextAttributesKey.createTextAttributesKey("STAB_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
        public static final TextAttributesKey OPERATION_SIGN
                = TextAttributesKey.createTextAttributesKey("STAB_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);
        public static final TextAttributesKey PARENTHESES
                = TextAttributesKey.createTextAttributesKey("STAB_PARENTH", DefaultLanguageHighlighterColors.PARENTHESES);
        public static final TextAttributesKey BRACKETS
                = TextAttributesKey.createTextAttributesKey("STAB_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
        public static final TextAttributesKey BRACES
                = TextAttributesKey.createTextAttributesKey("STAB_BRACES", DefaultLanguageHighlighterColors.BRACES);
        public static final TextAttributesKey COMMA
                = TextAttributesKey.createTextAttributesKey("STAB_COMMA", DefaultLanguageHighlighterColors.COMMA);
        public static final TextAttributesKey DOT
                = TextAttributesKey.createTextAttributesKey("STAB_DOT", DefaultLanguageHighlighterColors.DOT);
        public static final TextAttributesKey SEMICOLON
                = TextAttributesKey.createTextAttributesKey("STAB_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    }

    public static class Factory extends SyntaxHighlighterFactory
    {
        @NotNull
        @Override
        public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile)
        {
            return new StabSyntaxHighlighter(project, virtualFile);
        }
    }
}
