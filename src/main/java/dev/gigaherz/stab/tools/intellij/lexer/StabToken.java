package dev.gigaherz.stab.tools.intellij.lexer;

import com.intellij.psi.tree.IElementType;
import dev.gigaherz.stab.tools.intellij.StabLanguage;


public class StabToken extends IElementType
{
    private StabToken(String debugName)
    {
        super(debugName, StabLanguage.INSTANCE);
    }

    public static final StabToken ERROR = new StabToken("ERROR");
    public static final StabToken END_OF_STREAM = new StabToken("END_OF_STREAM");
    public static final StabToken ADD_ASSIGN = new StabToken("ADD_ASSIGN");
    public static final StabToken AND = new StabToken("AND");
    public static final StabToken AND_ASSIGN = new StabToken("AND_ASSIGN");
    public static final StabToken ASSIGN = new StabToken("ASSIGN");
    public static final StabToken CHARACTER_LITERAL = new StabToken("CHARACTER_LITERAL");
    public static final StabToken CLOSE_BRACE = new StabToken("CLOSE_BRACE");
    public static final StabToken CLOSE_BRACKET = new StabToken("CLOSE_BRACKET");
    public static final StabToken CLOSE_PARENTHESIS = new StabToken("CLOSE_PARENTHESIS");
    public static final StabToken COLON = new StabToken("COLON");
    public static final StabToken COMMA = new StabToken("COMMA");
    public static final StabToken COMPLEMENT = new StabToken("COMPLEMENT");
    public static final StabToken CONTEXTUAL_KEYWORD = new StabToken("CONTEXTUAL_KEYWORD");
    public static final StabToken DECIMAL_INTEGER_LITERAL = new StabToken("DECIMAL_INTEGER_LITERAL");
    public static final StabToken DECREMENT = new StabToken("DECREMENT");
    public static final StabToken DELIMITED_COMMENT = new StabToken("DELIMITED_COMMENT");
    public static final StabToken DIVIDE = new StabToken("DIVIDE");
    public static final StabToken DIVIDE_ASSIGN = new StabToken("DIVIDE_ASSIGN");
    public static final StabToken DOT = new StabToken("DOT");
    public static final StabToken DOUBLE_LITERAL = new StabToken("DOUBLE_LITERAL");
    public static final StabToken EQUAL = new StabToken("EQUAL");
    public static final StabToken FLOAT_LITERAL = new StabToken("FLOAT_LITERAL");
    public static final StabToken GREATER_THAN = new StabToken("GREATER_THAN");
    public static final StabToken GREATER_THAN_OR_EQUAL = new StabToken("GREATER_THAN_OR_EQUAL");
    public static final StabToken HEXADECIMAL_INTEGER_LITERAL = new StabToken("HEXADECIMAL_INTEGER_LITERAL");
    public static final StabToken HEXADECIMAL_LONG_LITERAL = new StabToken("HEXADECIMAL_LONG_LITERAL");
    public static final StabToken IDENTIFIER = new StabToken("IDENTIFIER");
    public static final StabToken INCREMENT = new StabToken("INCREMENT");
    public static final StabToken KEYWORD = new StabToken("KEYWORD");
    public static final StabToken LAMBDA = new StabToken("LAMBDA");
    public static final StabToken LEFT_SHIFT = new StabToken("LEFT_SHIFT");
    public static final StabToken LEFT_SHIFT_ASSIGN = new StabToken("LEFT_SHIFT_ASSIGN");
    public static final StabToken LESS_THAN = new StabToken("LESS_THAN");
    public static final StabToken LESS_THAN_OR_EQUAL = new StabToken("LESS_THAN_OR_EQUAL");
    public static final StabToken LOGICAL_AND = new StabToken("LOGICAL_AND");
    public static final StabToken LOGICAL_OR = new StabToken("LOGICAL_OR");
    public static final StabToken LONG_LITERAL = new StabToken("LONG_LITERAL");
    public static final StabToken MINUS = new StabToken("MINUS");
    public static final StabToken MODULO_ASSIGN = new StabToken("MODULO_ASSIGN");
    public static final StabToken MULTIPLY = new StabToken("MULTIPLY");
    public static final StabToken MULTIPLY_ASSIGN = new StabToken("MULTIPLY_ASSIGN");
    public static final StabToken NEW_LINE = new StabToken("NEW_LINE");
    public static final StabToken NOT = new StabToken("NOT");
    public static final StabToken NOT_EQUAL = new StabToken("NOT_EQUAL");
    public static final StabToken NULL_COALESCING = new StabToken("NULL_COALESCING");
    public static final StabToken OPEN_BRACE = new StabToken("OPEN_BRACE");
    public static final StabToken OPEN_BRACKET = new StabToken("OPEN_BRACKET");
    public static final StabToken OPEN_PARENTHESIS = new StabToken("OPEN_PARENTHESIS");
    public static final StabToken OR = new StabToken("OR");
    public static final StabToken OR_ASSIGN = new StabToken("OR_ASSIGN");
    public static final StabToken PERCENT = new StabToken("PERCENT");
    public static final StabToken PLUS = new StabToken("PLUS");
    public static final StabToken QUESTION_MARK = new StabToken("QUESTION_MARK");
    public static final StabToken REAL_LITERAL = new StabToken("REAL_LITERAL");
    public static final StabToken SEMICOLON = new StabToken("SEMI_COLON");
    public static final StabToken SINGLE_LINE_COMMENT = new StabToken("SINGLE_LINE_COMMENT");
    public static final StabToken STRING_LITERAL = new StabToken("STRING_LITERAL");
    public static final StabToken SUBTRACT_ASSIGN = new StabToken("SUBTRACT_ASSIGN");
    public static final StabToken VERBATIM_IDENTIFIER = new StabToken("VERBATIM_IDENTIFIER");
    public static final StabToken VERBATIM_STRING_LITERAL = new StabToken("VERBATIM_STRING_LITERAL");
    public static final StabToken XOR = new StabToken("XOR");
    public static final StabToken XOR_ASSIGN = new StabToken("XOR_ASSIGN");
    public static final StabToken WHITESPACE = new StabToken("WHITESPACE");
}
