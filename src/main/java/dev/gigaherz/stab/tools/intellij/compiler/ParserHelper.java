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
public class ParserHelper {
    public static final int decodeHexadecimalInteger(String value) {
        int result = 0;
        int ndigits = 0;

        for(int i = 2; i < value.length(); ++i) {
            int digit = scanHexDigit(value.charAt(i));
            if (ndigits != 0 || digit != 0) {
                result <<= 4;
                result |= digit;
                ++ndigits;
                if (ndigits == 9) {
                    throw new NumberFormatException(value);
                }
            }
        }

        return result;
    }

    public static final long decodeHexadecimalLong(String value) {
        long result = (long)0;
        int ndigits = 0;

        for(int i = 2; i < value.length(); ++i) {
            int digit = scanHexDigit(value.charAt(i));
            if (ndigits != 0 || digit != 0) {
                result <<= 4;
                result |= (long)digit;
                ++ndigits;
                if (ndigits == 17) {
                    throw new NumberFormatException(value);
                }
            }
        }

        return result;
    }

    public static final char unescapeChar(CharSequence text, int offset, int length) {
        char result = (char)0;
        int end = offset + length;

        for(int i = offset; i < end; ++i) {
            char c = text.charAt(i);
            if (c != '\\') {
                result = c;
            } else {
                ++i;
                int var10000;
                int value;
                int d;
                switch(c = text.charAt(i)) {
                    case '"':
                    case '\'':
                    case '\\':
                        result = c;
                        break;
                    case '0':
                        result = (char)0;
                        break;
                    case 'U':
                        value = 0;

                        for(d = 0; d < 8; ++d) {
                            var10000 = value * 16;
                            ++i;
                            value = var10000 + scanHexDigit(text.charAt(i));
                        }

                        result = (char)value;
                        break;
                    case 'a':
                        result = (char)7;
                        break;
                    case 'b':
                        result = (char)8;
                        break;
                    case 'f':
                        result = (char)12;
                        break;
                    case 'n':
                        result = (char)10;
                        break;
                    case 'r':
                        result = (char)13;
                        break;
                    case 't':
                        result = (char)9;
                        break;
                    case 'u':
                        value = 0;

                        for(d = 0; d < 4; ++d) {
                            var10000 = value * 16;
                            ++i;
                            value = var10000 + scanHexDigit(text.charAt(i));
                        }

                        result = (char)value;
                        break;
                    case 'v':
                        result = (char)11;
                        break;
                    case 'x':
                        ++i;
                        value = scanHexDigit(text.charAt(i));
                        d = scanHexDigit(text.charAt(i + 1));
                        if (d > -1) {
                            value = value * 16 + d;
                            ++i;
                            if ((d = scanHexDigit(text.charAt(i + 1))) > -1) {
                                value = value * 16 + d;
                                ++i;
                                if ((d = scanHexDigit(text.charAt(i + 1))) > -1) {
                                    value = value * 16 + d;
                                    ++i;
                                }
                            }
                        }

                        result = (char)value;
                }
            }
        }

        return result;
    }

    public static final String unescapeString(CharSequence text, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        int end = offset + length;

        for(int i = offset; i < end; ++i) {
            char c = text.charAt(i);
            if (c != '\\') {
                sb.append(c);
            } else {
                ++i;
                int var10000;
                int value;
                int d;
                switch(c = text.charAt(i)) {
                    case '"':
                    case '\'':
                    case '\\':
                        sb.append(c);
                        break;
                    case '0':
                        sb.append((char)0);
                        break;
                    case 'U':
                        value = 0;

                        for(d = 0; d < 4; ++d) {
                            var10000 = value * 16;
                            ++i;
                            value = var10000 + scanHexDigit(text.charAt(i));
                        }

                        sb.append((char)value);
                        value = 0;

                        for(d = 0; d < 4; ++d) {
                            var10000 = value * 16;
                            ++i;
                            value = var10000 + scanHexDigit(text.charAt(i));
                        }

                        sb.append((char)value);
                        break;
                    case 'a':
                        sb.append((char)7);
                        break;
                    case 'b':
                        sb.append((char)8);
                        break;
                    case 'f':
                        sb.append((char)12);
                        break;
                    case 'n':
                        sb.append((char)10);
                        break;
                    case 'r':
                        sb.append((char)13);
                        break;
                    case 't':
                        sb.append((char)9);
                        break;
                    case 'u':
                        value = 0;

                        for(d = 0; d < 4; ++d) {
                            var10000 = value * 16;
                            ++i;
                            value = var10000 + scanHexDigit(text.charAt(i));
                        }

                        sb.append((char)value);
                        break;
                    case 'v':
                        sb.append((char)11);
                        break;
                    case 'x':
                        ++i;
                        value = scanHexDigit(text.charAt(i));
                        d = scanHexDigit(text.charAt(i + 1));
                        if (d > -1) {
                            value = value * 16 + d;
                            ++i;
                            if ((d = scanHexDigit(text.charAt(i + 1))) > -1) {
                                value = value * 16 + d;
                                ++i;
                                if ((d = scanHexDigit(text.charAt(i + 1))) > -1) {
                                    value = value * 16 + d;
                                    ++i;
                                }
                            }
                        }

                        sb.append((char)value);
                }
            }
        }

        return sb.toString();
    }

    public static final String unescapeVerbatimString(CharSequence text, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        int end = offset + length;

        for(int i = offset; i < end; ++i) {
            char c = text.charAt(i);
            if (c == '"') {
                ++i;
            }

            sb.append(c);
        }

        return sb.toString();
    }

    public static final int scanHexDigit(int character) {
        switch(character) {
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                return character - 48;
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
                return 10 + (character - 65);
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
                return 10 + (character - 97);
            default:
                return -1;
        }
    }

    public static final boolean isIdentifierStartChar(int c) {
        return Character.isJavaIdentifierStart(c);
    }

    public static final boolean isIdentifierPartChar(int c) {
        return Character.isJavaIdentifierPart(c);
    }

    public static final void unescapeIdentifier(StringBuilder sb, CharSequence text, int offset, int length) {
        int end = offset + length;
        if (text.charAt(offset) == '@') {
            ++offset;
        }

        for(int i = offset; i < end; ++i) {
            char c = text.charAt(i);
            if (c != '\\') {
                sb.append(c);
            } else {
                ++i;
                c = text.charAt(i);
                int var10000;
                int value;
                int j;
                if (c == 'u') {
                    value = 0;

                    for(j = 0; j < 4; ++j) {
                        var10000 = value * 16;
                        ++i;
                        value = var10000 + scanHexDigit(text.charAt(i));
                    }

                    sb.append((char)value);
                } else {
                    value = 0;

                    for(j = 0; j < 4; ++j) {
                        var10000 = value * 16;
                        ++i;
                        value = var10000 + scanHexDigit(text.charAt(i));
                    }

                    sb.append((char)value);
                    value = 0;

                    for(j = 0; j < 4; ++j) {
                        var10000 = value * 16;
                        ++i;
                        value = var10000 + scanHexDigit(text.charAt(i));
                    }

                    sb.append((char)value);
                }
            }
        }

    }

    public static final String decodeDocumentation(CharSequence text, int offset, int length) {
        int end = offset + length;
        ++offset;
        StringBuilder sb = new StringBuilder();
        int i;
        char c;
        if (text.charAt(offset) == '*') {
            offset += 2;

            label86:
            for(i = offset; i < end; ++i) {
                c = text.charAt(i);
                switch(c) {
                    case '\r':
                        sb.append(c);
                        if (text.charAt(i + 1) == '\n') {
                            ++i;
                        }
                    case '\n':
                    case '\u2028':
                    case '\u2029':
                        sb.append(c);
                        boolean done = false;

                        while(true) {
                            if (i + 1 >= end || done) {
                                continue label86;
                            }

                            switch(text.charAt(i + 1)) {
                                case '\t':
                                case '\u000b':
                                case '\f':
                                case ' ':
                                    ++i;
                                    break;
                                case '*':
                                    if (text.charAt(i + 2) != '/') {
                                        ++i;
                                        if (text.charAt(i + 1) == ' ') {
                                            ++i;
                                        }
                                    }
                                default:
                                    done = true;
                            }
                        }
                    case '*':
                        if (text.charAt(i + 1) != '/') {
                            sb.append(c);
                        } else {
                            ++i;
                        }
                        break;
                    default:
                        sb.append(c);
                }
            }
        } else {
            offset += 2;
            if (offset < end && text.charAt(offset) == ' ') {
                ++offset;
            }

            for(i = offset; i < end; ++i) {
                c = text.charAt(i);
                switch(c) {
                    case '\r':
                        sb.append(c);
                        if (text.charAt(i + 1) == '\n') {
                            c = '\n';
                            ++i;
                        }
                    case '\n':
                    case '\u2028':
                    case '\u2029':
                        sb.append(c);

                        do {
                            ++i;
                        } while(i < end && text.charAt(i) != '/');

                        i += 2;
                        if (i + 1 < end && text.charAt(i + 1) == ' ') {
                            ++i;
                        }
                        break;
                    default:
                        sb.append(c);
                }
            }
        }

        return sb.toString();
    }

    public ParserHelper() {
    }
}
