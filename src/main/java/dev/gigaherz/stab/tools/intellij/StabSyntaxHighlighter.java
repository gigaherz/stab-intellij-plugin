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

    static {
        addFormatting(StabToken.SINGLE_LINE_COMMENT, FormatKeys.LINE_COMMENT);
        addFormatting(StabToken.DELIMITED_COMMENT, FormatKeys.STAB_BLOCK_COMMENT);

        addFormatting(StabToken.DOT, FormatKeys.DOT);
        addFormatting(StabToken.COMMA, FormatKeys.COMMA);
        addFormatting(StabToken.SEMICOLON, FormatKeys.STAB_SEMICOLON);
        addFormatting(StabToken.OPEN_BRACE, FormatKeys.BRACES);
        addFormatting(StabToken.CLOSE_BRACE, FormatKeys.BRACES);
        addFormatting(StabToken.OPEN_BRACKET, FormatKeys.BRACKETS);
        addFormatting(StabToken.CLOSE_BRACKET, FormatKeys.BRACKETS);
        addFormatting(StabToken.OPEN_PARENTHESIS, FormatKeys.PARENTHESES);
        addFormatting(StabToken.CLOSE_PARENTHESIS, FormatKeys.PARENTHESES);

        addFormatting(StabToken.DECIMAL_INTEGER_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.HEXADECIMAL_INTEGER_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.LONG_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.HEXADECIMAL_LONG_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.FLOAT_LITERAL, FormatKeys.NUMBER);
        addFormatting(StabToken.DOUBLE_LITERAL, FormatKeys.NUMBER);

        addFormatting(StabToken.STRING_LITERAL, FormatKeys.STRING);
        addFormatting(StabToken.VERBATIM_STRING_LITERAL, FormatKeys.STRING);

        addFormatting(StabToken.CHARACTER_LITERAL, FormatKeys.STRING);

        addFormatting(StabToken.KEYWORD, FormatKeys.KEYWORD);
        addFormatting(StabKeyword.NEW, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.OVERRIDE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.BYTE, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.SHORT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.INT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.LONG, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.FLOAT, FormatKeys.KEYWORD2);
        addFormatting(StabKeyword.DOUBLE, FormatKeys.KEYWORD2);

        //addFormatting(LexicalUnit.Identifier, JavaHighlightingColors.);
        //addFormatting(LexicalUnit.VerbatimStringLiteral, JavaHighlightingColors.ide);

        addFormatting(StabToken.ADD_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.AND_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DIVIDE_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LEFT_SHIFT_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MODULO_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MULTIPLY_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.OR_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.XOR_ASSIGN,    FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.AND, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.CHARACTER_LITERAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.COLON, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.COMPLEMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.CONTEXTUAL_KEYWORD, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DECREMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.DIVIDE, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.GREATER_THAN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.GREATER_THAN_OR_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.INCREMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LAMBDA, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LEFT_SHIFT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LESS_THAN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LESS_THAN_OR_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LOGICAL_AND, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LOGICAL_OR, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.LONG_LITERAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MINUS, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.MULTIPLY, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NOT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NOT_EQUAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.NULL_COALESCING, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.OR, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.PERCENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.PLUS, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.QUESTION_MARK, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.REAL_LITERAL, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.SEMICOLON, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.SINGLE_LINE_COMMENT, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.SUBTRACT_ASSIGN, FormatKeys.OPERATION_SIGN);
        addFormatting(StabToken.XOR, FormatKeys.OPERATION_SIGN);
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
            LOGGER.warn("Stab Syntax Highlighter initialized...");
        else
            LOGGER.warn(String.format("Stab Syntax Highlighter initialized for %s...", virtualFile.getName()));
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
        if (iElementType instanceof StabToken token)
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
        public static final TextAttributesKey STAB_SEMICOLON
                = TextAttributesKey.createTextAttributesKey("STAB_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);

        public static final TextAttributesKey VALID_STRING_ESCAPE
                = TextAttributesKey.createTextAttributesKey("STAB_VALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
        public static final TextAttributesKey INVALID_STRING_ESCAPE
                = TextAttributesKey.createTextAttributesKey("STAB_INVALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

        public static final TextAttributesKey LOCAL_VARIABLE_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("LOCAL_VARIABLE_ATTRIBUTES", DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
        public static final TextAttributesKey PARAMETER_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("PARAMETER_ATTRIBUTES", DefaultLanguageHighlighterColors.PARAMETER);
        public static final TextAttributesKey LAMBDA_PARAMETER_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("LAMBDA_PARAMETER_ATTRIBUTES", PARAMETER_ATTRIBUTES);
        public static final TextAttributesKey REASSIGNED_LOCAL_VARIABLE_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("REASSIGNED_LOCAL_VARIABLE_ATTRIBUTES", DefaultLanguageHighlighterColors.REASSIGNED_LOCAL_VARIABLE);
        public static final TextAttributesKey REASSIGNED_PARAMETER_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("REASSIGNED_PARAMETER_ATTRIBUTES", DefaultLanguageHighlighterColors.REASSIGNED_PARAMETER);
        public static final TextAttributesKey INSTANCE_FIELD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("INSTANCE_FIELD_ATTRIBUTES", DefaultLanguageHighlighterColors.INSTANCE_FIELD);
        public static final TextAttributesKey INSTANCE_FINAL_FIELD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("INSTANCE_FINAL_FIELD_ATTRIBUTES", INSTANCE_FIELD_ATTRIBUTES);
        public static final TextAttributesKey STATIC_FIELD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_FIELD_ATTRIBUTES", DefaultLanguageHighlighterColors.STATIC_FIELD);
        public static final TextAttributesKey STATIC_FIELD_IMPORTED_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_FIELD_IMPORTED_ATTRIBUTES", STATIC_FIELD_ATTRIBUTES);
        public static final TextAttributesKey STATIC_FINAL_FIELD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_FINAL_FIELD_ATTRIBUTES", STATIC_FIELD_ATTRIBUTES);
        public static final TextAttributesKey STATIC_FINAL_FIELD_IMPORTED_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_FINAL_FIELD_IMPORTED_ATTRIBUTES", STATIC_FINAL_FIELD_ATTRIBUTES);
        public static final TextAttributesKey CLASS_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("CLASS_NAME_ATTRIBUTES", DefaultLanguageHighlighterColors.CLASS_NAME);
        public static final TextAttributesKey ANONYMOUS_CLASS_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ANONYMOUS_CLASS_NAME_ATTRIBUTES", CLASS_NAME_ATTRIBUTES);
        public static final TextAttributesKey IMPLICIT_ANONYMOUS_CLASS_PARAMETER_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("IMPLICIT_ANONYMOUS_CLASS_PARAMETER_ATTRIBUTES", CLASS_NAME_ATTRIBUTES);
        public static final TextAttributesKey TYPE_PARAMETER_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("TYPE_PARAMETER_NAME_ATTRIBUTES", DefaultLanguageHighlighterColors.PARAMETER);
        public static final TextAttributesKey INTERFACE_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("INTERFACE_NAME_ATTRIBUTES", DefaultLanguageHighlighterColors.INTERFACE_NAME);
        public static final TextAttributesKey ENUM_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ENUM_NAME_ATTRIBUTES", CLASS_NAME_ATTRIBUTES);
        public static final TextAttributesKey ABSTRACT_CLASS_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ABSTRACT_CLASS_NAME_ATTRIBUTES", CLASS_NAME_ATTRIBUTES);
        public static final TextAttributesKey METHOD_CALL_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("METHOD_CALL_ATTRIBUTES", DefaultLanguageHighlighterColors.FUNCTION_CALL);
        public static final TextAttributesKey METHOD_DECLARATION_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("METHOD_DECLARATION_ATTRIBUTES", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
        public static final TextAttributesKey STATIC_METHOD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_METHOD_ATTRIBUTES", DefaultLanguageHighlighterColors.STATIC_METHOD);
        public static final TextAttributesKey STATIC_METHOD_CALL_IMPORTED_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("STATIC_METHOD_IMPORTED_ATTRIBUTES", STATIC_METHOD_ATTRIBUTES);
        public static final TextAttributesKey ABSTRACT_METHOD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ABSTRACT_METHOD_ATTRIBUTES", METHOD_CALL_ATTRIBUTES);
        public static final TextAttributesKey INHERITED_METHOD_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("INHERITED_METHOD_ATTRIBUTES", METHOD_CALL_ATTRIBUTES);
        public static final TextAttributesKey CONSTRUCTOR_CALL_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("CONSTRUCTOR_CALL_ATTRIBUTES", DefaultLanguageHighlighterColors.FUNCTION_CALL);
        public static final TextAttributesKey CONSTRUCTOR_DECLARATION_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("CONSTRUCTOR_DECLARATION_ATTRIBUTES", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
        public static final TextAttributesKey ANNOTATION_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ANNOTATION_NAME_ATTRIBUTES", DefaultLanguageHighlighterColors.METADATA);
        public static final TextAttributesKey ANNOTATION_ATTRIBUTE_NAME_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ANNOTATION_ATTRIBUTE_NAME_ATTRIBUTES", DefaultLanguageHighlighterColors.METADATA);
        public static final TextAttributesKey ANNOTATION_ATTRIBUTE_VALUE_ATTRIBUTES
                = TextAttributesKey.createTextAttributesKey("ANNOTATION_ATTRIBUTE_VALUE_ATTRIBUTES", DefaultLanguageHighlighterColors.METADATA);
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
