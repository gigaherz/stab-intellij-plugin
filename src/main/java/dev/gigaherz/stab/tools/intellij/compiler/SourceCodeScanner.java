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
import dev.gigaherz.stab.tools.intellij.lexer.StabKeyword;
import dev.gigaherz.stab.tools.intellij.lexer.StabToken;
import org.slf4j.Logger;

public class SourceCodeScanner implements IScanner
{
    public static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SourceCodeScanner.class);

    private int property$StartPosition;
    private StabKeyword keyword;
    private int property$EndOffset;
    private CodeErrorManager property$CodeErrorManager;
    private String property$Filename;
    private int property$TabWidth;
    private CharSequence property$Text;
    private int property$Position;
    private int property$Length;
    private int property$Next;

    public SourceCodeScanner(CodeErrorManager codeErrorManager, CharSequence text, int position, int length) {
        this.setCodeErrorManager(codeErrorManager);
        this.setText(text);
        this.setTabWidth(4);
        this.initialize(position, length, codeErrorManager.getErrorCount(), codeErrorManager.getDisabledWarnings());
    }

    public final int getStartPosition() {
        return this.property$StartPosition;
    }

    private void setStartPosition(int value) {
        this.property$StartPosition = value;
    }

    public final int getEndPosition() {
        return this.getPosition();
    }

    public final StabKeyword getKeyword() {
        return this.keyword;
    }

    public final RestorePoint createRestorePoint() {
        return new RestorePoint(this.getStartPosition(), this.getEndOffset() - this.getStartPosition() + 1, this.getCodeErrorManager().getErrorCount(), this.getCodeErrorManager().getDisabledWarnings());
    }

    public final void restore(RestorePoint restorePoint) {
        initialize(restorePoint.getStartPosition(), restorePoint.getTextLength(), restorePoint.getErrorCount(), restorePoint.getDisabledWarnings());
    }

    public void addError(ParseErrorId errorId, int level, String message) {
        this.getCodeErrorManager().addError(this.getFilename(), errorId.ordinal() + 1, level, message);
    }

    public StabToken scanOne() {
        this.setStartPosition(this.getPosition());
        switch (this.getNext()) {
            case -1:
                return StabToken.END_OF_STREAM;

            case '\r':
                if (advance() == '\n') {
                    advance();
                }
                return StabToken.NEW_LINE;

            case '\u0085':
            case '\u2028':
            case '\u2029':
            case '\n':
                advance();
                return StabToken.NEW_LINE;

            case ' ':
            case '\t':
            case 11: // VT
            case '\f':
                scanWhitespaces();
                return StabToken.WHITESPACE;

            case '{':
                advance();
                return StabToken.OPEN_BRACE;

            case '}':
                advance();
                return StabToken.CLOSE_BRACE;

            case '[':
                advance();
                return StabToken.OPEN_BRACKET;

            case ']':
                advance();
                return StabToken.CLOSE_BRACKET;

            case '(':
                advance();
                return StabToken.OPEN_PARENTHESIS;

            case ')':
                advance();
                return StabToken.CLOSE_PARENTHESIS;

            case ',':
                advance();
                return StabToken.COMMA;

            case ';':
                advance();
                return StabToken.SEMICOLON;

            case '~':
                advance();
                return StabToken.COMPLEMENT;

            case '>':
                if (advance() == '=') {
                    advance();
                    return StabToken.GREATER_THAN_OR_EQUAL;
                }
                return StabToken.GREATER_THAN;

            case '?':
                if (advance() == '?') {
                    advance();
                    return StabToken.NULL_COALESCING;
                }
                return StabToken.QUESTION_MARK;

            case '!':
                if (advance() == '=') {
                    advance();
                    return StabToken.NOT_EQUAL;
                }
                return StabToken.NOT;

            case ':':
                advance();
                return StabToken.COLON;

            case '*':
                if (advance() == '=') {
                    advance();
                    return StabToken.MULTIPLY_ASSIGN;
                }
                return StabToken.MULTIPLY;

            case '%':
                if (advance() == '=') {
                    advance();
                    return StabToken.MODULO_ASSIGN;
                }
                return StabToken.PERCENT;

            case '^':
                if (advance() == '=') {
                    advance();
                    return StabToken.XOR_ASSIGN;
                }
                return StabToken.XOR;

            case '<':
                switch (advance()) {
                    case '=':
                        advance();
                        return StabToken.LESS_THAN_OR_EQUAL;

                    case '<':
                        if (advance() == '=') {
                            advance();
                            return StabToken.LEFT_SHIFT_ASSIGN;
                        }
                        return StabToken.LEFT_SHIFT;

                    default:
                        return StabToken.LESS_THAN;
                }

            case '+':
                switch (advance()) {
                    case '+':
                        advance();
                        return StabToken.INCREMENT;

                    case '=':
                        advance();
                        return StabToken.ADD_ASSIGN;

                    default:
                        return StabToken.PLUS;
                }

            case '-':
                switch (advance()) {
                    case '-':
                        advance();
                        return StabToken.DECREMENT;

                    case '=':
                        advance();
                        return StabToken.SUBTRACT_ASSIGN;

                    default:
                        return StabToken.MINUS;
                }

            case '&':
                switch (advance()) {
                    case '&':
                        advance();
                        return StabToken.LOGICAL_AND;

                    case '=':
                        advance();
                        return StabToken.AND_ASSIGN;

                    default:
                        return StabToken.AND;
                }

            case '|':
                switch (advance()) {
                    case '|':
                        advance();
                        return StabToken.LOGICAL_OR;

                    case '=':
                        advance();
                        return StabToken.OR_ASSIGN;

                    default:
                        return StabToken.OR;
                }

            case '=':
                switch (advance()) {
                    case '=':
                        advance();
                        return StabToken.EQUAL;

                    case '>':
                        advance();
                        return StabToken.LAMBDA;

                    default:
                        return StabToken.ASSIGN;
                }

            case 'a':
                switch (advance()) {
                    case 'b':
                        return scanKeyword("bstract", StabKeyword.ABSTRACT);
                    case 's':
                        switch (advance()) {
                            case 'c':
                                return scanContextualKeyword("cending", StabKeyword.ASCENDING);
                            default:
                                return scanKeyword("", StabKeyword.AS);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'b':
                switch (advance()) {
                    case 'o':
                        return scanKeyword("oolean", StabKeyword.BOOLEAN);
                    case 'r':
                        return scanKeyword("reak", StabKeyword.BREAK);
                    case 'y':
                        switch (advance()) {
                            case 't':
                                return scanKeyword("te", StabKeyword.BYTE);
                            default:
                                return scanContextualKeyword("", StabKeyword.BY);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'c':
                switch (advance()) {
                    case 'a':
                        switch (advance()) {
                            case 's':
                                return scanKeyword("se", StabKeyword.CASE);
                            case 't':
                                return scanKeyword("tch", StabKeyword.CATCH);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'h':
                        switch (advance()) {
                            case 'a':
                                return scanKeyword("ar", StabKeyword.CHAR);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'l':
                        return scanKeyword("lass", StabKeyword.CLASS);
                    case 'o':
                        switch (advance()) {
                            case 'n':
                                switch (advance()) {
                                    case 't':
                                        return scanKeyword("tinue", StabKeyword.CONTINUE);
                                    default:
                                        return scanKeyword("", StabKeyword.NONE);
                                }
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'd':
                switch (advance()) {
                    case 'e':
                        switch (advance()) {
                            case 'f':
                                return scanKeyword("fault", StabKeyword.DEFAULT);
                            case 'l':
                                return scanKeyword("legate", StabKeyword.DELEGATE);
                            case 's':
                                return scanContextualKeyword("scending", StabKeyword.DESCENDING);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'o':
                        switch (advance()) {
                            case 'u':
                                return scanKeyword("uble", StabKeyword.DOUBLE);
                            default:
                                return scanKeyword("", StabKeyword.DO);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'e':
                switch (advance()) {
                    case 'l':
                        return scanKeyword("lse", StabKeyword.ELSE);
                    case 'n':
                        return scanKeyword("num", StabKeyword.ENUM);
                    case 'q':
                        return scanContextualKeyword("quals", StabKeyword.EQUALS);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'f':
                switch (advance()) {
                    case 'a':
                        return scanKeyword("alse", StabKeyword.FALSE);
                    case 'i':
                        switch (advance()) {
                            case 'n':
                                switch (advance()) {
                                    case 'a':
                                        switch (advance()) {
                                            case 'l':
                                                switch (advance()) {
                                                    case 'l':
                                                        return scanKeyword("ly", StabKeyword.FINALLY);
                                                    default:
                                                        return scanKeyword("", StabKeyword.FINAL);
                                                }
                                            default:
                                                return scanKeyword("", StabKeyword.NONE);
                                        }
                                    default:
                                        return scanKeyword("", StabKeyword.NONE);
                                }
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'l':
                        return scanKeyword("loat", StabKeyword.FLOAT);
                    case 'o':
                        switch (advance()) {
                            case 'r':
                                switch (advance()) {
                                    case 'e':
                                        return scanKeyword("each", StabKeyword.FOREACH);
                                    default:
                                        return scanKeyword("", StabKeyword.FOR);
                                }
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'r':
                        return scanContextualKeyword("rom", StabKeyword.FROM);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'g':
                switch (advance()) {
                    case 'e':
                        return scanContextualKeyword("et", StabKeyword.GET);
                    case 'o':
                        return scanKeyword("oto", StabKeyword.GOTO);
                    case 'r':
                        return scanContextualKeyword("roup", StabKeyword.GROUP);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'i':
                switch (advance()) {
                    case 'f':
                        return scanKeyword("f", StabKeyword.IF);
                    case 'n':
                        switch (advance()) {
                            case 's':
                                return scanKeyword("stanceof", StabKeyword.INSTANCEOF);
                            case 't':
                                switch (advance()) {
                                    case 'e':
                                        return scanKeyword("erface", StabKeyword.INTERFACE);
                                    case 'o':
                                        return scanContextualKeyword("o", StabKeyword.INTO);
                                    default:
                                        return scanKeyword("", StabKeyword.INT);
                                }
                            default:
                                return scanKeyword("", StabKeyword.IN);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'j':
                return scanContextualKeyword("join", StabKeyword.JOIN);

            case 'l':
                switch (advance()) {
                    case 'e':
                        return scanContextualKeyword("et", StabKeyword.LET);
                    case 'o':
                        switch (advance()) {
                            case 'n':
                                return scanKeyword("ng", StabKeyword.LONG);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'n':
                switch (advance()) {
                    case 'a':
                        return scanKeyword("ative", StabKeyword.NATIVE);
                    case 'e':
                        return scanKeyword("ew", StabKeyword.NEW);
                    case 'u':
                        return scanKeyword("ull", StabKeyword.NULL);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'o':
                switch (advance()) {
                    case 'n':
                        return scanContextualKeyword("n", StabKeyword.ON);
                    case 'r':
                        return scanContextualKeyword("rderby", StabKeyword.ORDERBY);
                    case 'v':
                        return scanKeyword("verride", StabKeyword.OVERRIDE);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'p':
                switch (advance()) {
                    case 'a':
                        switch (advance()) {
                            case 'c':
                                return scanKeyword("ckage", StabKeyword.PACKAGE);
                            case 'r':
                                switch (advance()) {
                                    case 'a':
                                        return scanKeyword("ams", StabKeyword.PARAMS);
                                    case 't':
                                        return scanContextualKeyword("tial", StabKeyword.PARTIAL);
                                    default:
                                        return scanKeyword("", StabKeyword.NONE);
                                }
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'r':
                        switch (advance()) {
                            case 'i':
                                return scanKeyword("ivate", StabKeyword.PRIVATE);
                            case 'o':
                                return scanKeyword("otected", StabKeyword.PROTECTED);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'u':
                        return scanKeyword("ublic", StabKeyword.PUBLIC);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'r':
                return scanKeyword("return", StabKeyword.RETURN);

            case 's':
                switch (advance()) {
                    case 'e':
                        switch (advance()) {
                            case 'l':
                                return scanContextualKeyword("lect", StabKeyword.SELECT);
                            case 't':
                                return scanContextualKeyword("t", StabKeyword.SET);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'h':
                        return scanKeyword("hort", StabKeyword.SHORT);
                    case 'i':
                        return scanKeyword("izeof", StabKeyword.SIZE_OF);
                    case 't':
                        switch (advance()) {
                            case 'a':
                                switch (advance()) {
                                    case 't':
                                        return scanKeyword("tic", StabKeyword.STATIC);
                                    default:
                                        return scanKeyword("", StabKeyword.NONE);
                                }
                            case 'r':
                                return scanKeyword("rictfp", StabKeyword.STRICTFP);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'u':
                        return scanKeyword("uper", StabKeyword.SUPER);
                    case 'w':
                        return scanKeyword("witch", StabKeyword.SWITCH);
                    case 'y':
                        return scanKeyword("ynchronized", StabKeyword.SYNCHRONIZED);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 't':
                switch (advance()) {
                    case 'h':
                        switch (advance()) {
                            case 'i':
                                return scanKeyword("is", StabKeyword.THIS);
                            case 'r':
                                return scanKeyword("row", StabKeyword.THROW);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'r':
                        switch (advance()) {
                            case 'a':
                                return scanKeyword("ansient", StabKeyword.TRANSIENT);
                            case 'u':
                                return scanKeyword("ue", StabKeyword.TRUE);
                            case 'y':
                                return scanKeyword("y", StabKeyword.TRY);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'y':
                        return scanKeyword("ypeof", StabKeyword.TYPE_OF);
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'u':
                switch (advance()) {
                    case 's':
                        switch (advance()) {
                            case 'i':
                                return scanKeyword("ing", StabKeyword.USING);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'v':
                switch (advance()) {
                    case 'a':
                        switch (advance()) {
                            case 'l':
                                return scanContextualKeyword("lue", StabKeyword.VALUE);
                            case 'r':
                                return scanContextualKeyword("r", StabKeyword.VAR);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    case 'i':
                        return scanKeyword("irtual", StabKeyword.VIRTUAL);
                    case 'o':
                        switch (advance()) {
                            case 'i':
                                return scanKeyword("id", StabKeyword.VOID);
                            case 'l':
                                return scanKeyword("latile", StabKeyword.VOLATILE);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'w':
                switch (advance()) {
                    case 'h':
                        switch (advance()) {
                            case 'e':
                                return scanContextualKeyword("ere", StabKeyword.WHERE);
                            case 'i':
                                return scanKeyword("ile", StabKeyword.WHILE);
                            default:
                                return scanKeyword("", StabKeyword.NONE);
                        }
                    default:
                        return scanKeyword("", StabKeyword.NONE);
                }

            case 'y':
                return scanContextualKeyword("yield", StabKeyword.YIELD);

            case '/':
                switch (advance()) {
                    case '/':
                        for (;;) {
                            switch (advance()) {
                                case -1:
                                case '\r':
                                case '\u2028':
                                case '\u2029':
                                case '\n':
                                    return StabToken.SINGLE_LINE_COMMENT;
                            }
                        }
                    case '*':
                        for (;;) {
                            switch (advance()) {
                                case -1:
                                    throw error(ParseErrorId.UnclosedDelimitedComment);

                                case '\r':
                                    break;

                                case '\n':
                                    break;

                                case '\u2028':
                                case '\u2029':
                                    break;

                                case '*':
                                    switch (advance()) {
                                        case -1:
                                            throw error(ParseErrorId.UnclosedDelimitedComment);
                                        case '/':
                                            advance();
                                            return StabToken.DELIMITED_COMMENT;
                                        default:
                                            break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    case '=':
                        advance();
                        return StabToken.DIVIDE_ASSIGN;
                    default:
                        return StabToken.DIVIDE;
                }

            case '.':
                switch (advance()) {
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
                        return scanDotNumber();
                }
                return StabToken.DOT;

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
                return scanNumber();

            case '\'':
                return scanCharacter();

            case '"':
                return scanString();

            case '@':
                switch (advance()) {
                    case '"':
                        return scanVerbatimString();
                    case '_':
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                    case '\\':
                        int unicode = scanUnicodeEscapeSequence();
                        if (!ParserHelper.isIdentifierStartChar(unicode)) {
                            throw error(ParseErrorId.InvalidEscapeSequence);
                        }
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                    default:
                        if (!ParserHelper.isIdentifierStartChar(this.getNext())) {
                            throw error(ParseErrorId.InvalidSourceCodeChar);
                        }
                        scanIdentifierPart();
                        return StabToken.VERBATIM_IDENTIFIER;
                }

            case '_':
                scanIdentifierPart();
                return StabToken.IDENTIFIER;

            case '\\':
                int unicode = scanUnicodeEscapeSequence();
                if (!ParserHelper.isIdentifierStartChar(unicode)) {
                    throw error(ParseErrorId.InvalidEscapeSequence);
                }
                scanIdentifierPart();
                return StabToken.IDENTIFIER;

            default:
                if (!ParserHelper.isIdentifierStartChar(this.getNext())) {
                    throw error(ParseErrorId.InvalidSourceCodeChar);
                }
                scanIdentifierPart();
                return StabToken.IDENTIFIER;
        }
    }

    private void scanWhitespaces() {
        while(true) {
            switch(this.advance()) {
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
            switch(this.advance()) {
                case -1:
                    return;
                case 92:
                    int unicode = this.scanUnicodeEscapeSequence();
                    if (ParserHelper.isIdentifierPartChar(unicode)) {
                        break;
                    }

                    return;
                default:
                    if (!ParserHelper.isIdentifierPartChar(this.getNext())) {
                        return;
                    }
            }
        }
    }

    private int scanUnicodeEscapeSequence() {
        int result = 0;
        int i;
        int value;
        switch(this.advance()) {
            case 85:
                for(i = 0; i < 8; ++i) {
                    if ((value = ParserHelper.scanHexDigit(this.advance())) == -1) {
                        throw this.error(ParseErrorId.HexadecimalDigitExpected);
                    }

                    result = result * 16 + value;
                }

                return result;
            case 117:
                for(i = 0; i < 4; ++i) {
                    if ((value = ParserHelper.scanHexDigit(this.advance())) == -1) {
                        throw this.error(ParseErrorId.HexadecimalDigitExpected);
                    }

                    result = result * 16 + value;
                }

                return result;
            default:
                throw this.error(ParseErrorId.InvalidEscapeSequence);
        }
    }

    private StabToken scanNumber() {
        while (true) {
            switch (advance()) {
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
                    switch (advance()) {
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
                            return scanDotNumber();
                    }
                    throw error(ParseErrorId.DecimalDigitsExpected);
                case 'e':
                case 'E':
                    return scanENumber();
                case 'x':
                case 'X':
                    return scanHexNumber();
                case 'l':
                case 'L':
                    advance();
                    return StabToken.LONG_LITERAL;
                case 'f':
                case 'F':
                    advance();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    advance();
                    return StabToken.DOUBLE_LITERAL;
                default:
                    return StabToken.DECIMAL_INTEGER_LITERAL;
            }
        }
    }

    private StabToken scanDotNumber() {
        while (true) {
            switch (advance()) {
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
                    advance();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    advance();
                    return StabToken.DOUBLE_LITERAL;
            }
        }
    }

    private StabToken scanENumber() {
        switch (advance()) {
            case '+':
            case '-':
                switch (advance()) {
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
                        return scanExponent();
                }
                break;
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
                return scanExponent();
        }
        throw error(ParseErrorId.DecimalDigitsExpected);
    }

    private StabToken scanExponent() {
        while (true) {
            switch (advance()) {
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
                    advance();
                    return StabToken.FLOAT_LITERAL;
                case 'd':
                case 'D':
                    advance();
                    return StabToken.DOUBLE_LITERAL;
                default:
                    return StabToken.REAL_LITERAL;
            }
        }
    }

    private StabToken scanHexNumber() {
        switch (advance()) {
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
                throw error(ParseErrorId.MalformedHexadecimalNumber);
        }
        while (true) {
            switch (advance()) {
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
                    advance();
                    return StabToken.HEXADECIMAL_LONG_LITERAL;
                default:
                    return StabToken.HEXADECIMAL_INTEGER_LITERAL;
            }
        }
    }

    private StabToken scanCharacter() {
        switch (advance()) {
            case -1:
            case '\r':
            case '\u2028':
            case '\u2029':
            case '\n':
                throw error(ParseErrorId.UnclosedChar);
            case '\'':
                throw error(ParseErrorId.MalformedChar);
            case '\\':
                switch (advance()) {
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
                        advance();
                        break;
                    case 'u':
                        int value = 0;
                        for (int i = 0; i < 4; i++) {
                            int digit;
                            if ((digit = ParserHelper.scanHexDigit(advance())) == -1) {
                                throw error(ParseErrorId.HexadecimalDigitExpected);
                            }
                            value = value * 16 + digit;
                            if (value > Character.MAX_VALUE) {
                                throw error(ParseErrorId.InvalidEscapeSequence);
                            }
                        }
                        advance();
                        break;
                    case 'U':
                        for (int i = 0; i < 8; i++) {
                            if (ParserHelper.scanHexDigit(advance()) == -1) {
                                throw error(ParseErrorId.HexadecimalDigitExpected);
                            }
                        }
                        advance();
                        break;
                    case 'x':
                        scanHexEscapeSequence();
                        break;

                    default:
                        throw error(ParseErrorId.InvalidEscapeSequence);
                }
                break;
            default:
                advance();
                break;
        }
        if (this.getNext() != '\'') {
            throw error(ParseErrorId.MalformedChar);
        }
        advance();
        return StabToken.CHARACTER_LITERAL;
    }

    private StabToken scanString() {
        for (;;) {
            switch (advance()) {
                case -1:
                case '\r':
                case '\u2028':
                case '\u2029':
                case '\n':
                    throw error(ParseErrorId.UnclosedString);
                case '"':
                    advance();
                    return StabToken.STRING_LITERAL;
                case '\\':
                    switch (advance()) {
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
                            for (int i = 0; i < 4; i++) {
                                if (ParserHelper.scanHexDigit(advance()) == -1) {
                                    throw error(ParseErrorId.HexadecimalDigitExpected);
                                }
                            }
                            break;
                        case 'U':
                            for (int i = 0; i < 8; i++) {
                                if (ParserHelper.scanHexDigit(advance()) == -1) {
                                    throw error(ParseErrorId.HexadecimalDigitExpected);
                                }
                            }
                            break;
                        case 'x':
                            scanHexEscapeSequence();
                            break;

                        default:
                            throw error(ParseErrorId.InvalidEscapeSequence);
                    }
                    break;
            }
        }
    }

    private void scanHexEscapeSequence() {
        switch (advance()) {
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
                switch (advance()) {
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
                        switch (advance()) {
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
                                switch (advance()) {
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
                                        advance();
                                        break;
                                }
                                break;
                        }
                        break;
                }
                break;

            default:
                throw error(ParseErrorId.InvalidEscapeSequence);
        }
    }

    private StabToken scanVerbatimString() {
        for (;;) {
            switch (advance()) {
                case -1:
                    throw error(ParseErrorId.UnclosedVerbatimString);
                case '"':
                    if (advance() != '"') {
                        return StabToken.VERBATIM_STRING_LITERAL;
                    }
                    advance();
                    break;
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
            if (this.getNext() == -1 || (char)this.getNext() != c) {
                isKeyword = false;
                break;
            }
            advance();
        }
        if (this.getNext() != -1 && ParserHelper.isIdentifierPartChar(this.getNext())) {
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

    public final CodeErrorManager getCodeErrorManager() {
        return this.property$CodeErrorManager;
    }

    private void setCodeErrorManager(CodeErrorManager value) {
        this.property$CodeErrorManager = value;
    }

    public final String getFilename() {
        return this.property$Filename;
    }

    public final void setFilename(String value) {
        this.property$Filename = value;
    }

    public final int getTabWidth() {
        return this.property$TabWidth;
    }

    public final void setTabWidth(int value) {
        this.property$TabWidth = value;
    }

    public final CharSequence getText() {
        return this.property$Text;
    }

    private void setText(CharSequence value) {
        this.property$Text = value;
    }

    public final int getPosition() {
        return this.property$Position;
    }

    protected final void setPosition(int value) {
        this.property$Position = value;
    }

    public final int getLength() {
        return this.property$Length;
    }

    private void setLength(int value) {
        this.property$Length = value;
    }

    protected final int getNext() {
        return this.property$Next;
    }

    private void setNext(int value) {
        this.property$Next = value;
    }

    protected final int getEndOffset() {
        return this.property$EndOffset;
    }

    private void setEndOffset(int value) {
        this.property$EndOffset = value;
    }

    protected final void initialize(int position, int length, int errorCount, IntIterable disabledWarnings) {
        this.setPosition(position - 1);
        this.setLength(length);
        this.setEndOffset(length + position - 1);
        if (this.getPosition() >= 0 && this.getPosition() <= this.getEndOffset()) {
            this.setNext(this.getText().charAt(this.getPosition()));
        } else {
            this.setNext(0);
        }

        this.getCodeErrorManager().setErrorCount(errorCount);
        this.getCodeErrorManager().setDisabledWarnings(disabledWarnings);
        this.advance();
    }

    protected final int advance() {
        if (this.getPosition() + 1 <= this.getEndOffset()) {
            this.setPosition(this.getPosition() + 1);
            char var10002 = this.getText().charAt(this.getPosition());
            this.setNext(var10002);
            return var10002;
        } else {
            this.setPosition(this.getPosition() + 1);
            this.setNext(-1);
            return -1;
        }
    }

    protected final CodeErrorException error(ParseErrorId errorId, Object... arguments) {
        this.addError(errorId, 0, CompilerResources.getMessage(errorId, arguments));
        var err = String.format("error %d: %s", errorId.ordinal() + 1, CompilerResources.getMessage(errorId, arguments));
        LOGGER.error(err);
        return new CodeErrorException(err);
    }
}
