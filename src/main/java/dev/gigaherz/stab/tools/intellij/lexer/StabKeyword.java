package dev.gigaherz.stab.tools.intellij.lexer;

import com.intellij.psi.tree.IElementType;
import dev.gigaherz.stab.tools.intellij.StabLanguage;

public class StabKeyword extends IElementType
{
    private StabKeyword(String debugName)
    {
        super(debugName, StabLanguage.INSTANCE);
    }

    public static final StabKeyword NONE = new StabKeyword("NONE");
    public static final StabKeyword ABSTRACT = new StabKeyword("ABSTRACT");
    public static final StabKeyword AS = new StabKeyword("AS");
    public static final StabKeyword ASCENDING = new StabKeyword("ASCENDING");
    public static final StabKeyword BOOLEAN = new StabKeyword("BOOLEAN");
    public static final StabKeyword BREAK = new StabKeyword("BREAK");
    public static final StabKeyword BY = new StabKeyword("BY");
    public static final StabKeyword BYTE = new StabKeyword("BYTE");
    public static final StabKeyword CASE = new StabKeyword("CASE");
    public static final StabKeyword CATCH = new StabKeyword("CATCH");
    public static final StabKeyword CHAR = new StabKeyword("CHAR");
    public static final StabKeyword CLASS = new StabKeyword("CLASS");
    public static final StabKeyword CONTINUE = new StabKeyword("CONTINUE");
    public static final StabKeyword DEFAULT = new StabKeyword("DEFAULT");
    public static final StabKeyword DELEGATE = new StabKeyword("DELEGATE");
    public static final StabKeyword DESCENDING = new StabKeyword("DESCENDING");
    public static final StabKeyword DOUBLE = new StabKeyword("DOUBLE");
    public static final StabKeyword DO = new StabKeyword("DO");
    public static final StabKeyword ELSE = new StabKeyword("ELSE");
    public static final StabKeyword ENUM = new StabKeyword("ENUM");
    public static final StabKeyword EQUALS = new StabKeyword("EQUALS");
    public static final StabKeyword FALSE = new StabKeyword("FALSE");
    public static final StabKeyword FINAL = new StabKeyword("FINAL");
    public static final StabKeyword FINALLY = new StabKeyword("FINALLY");
    public static final StabKeyword FLOAT = new StabKeyword("FLOAT");
    public static final StabKeyword FOR = new StabKeyword("FOR");
    public static final StabKeyword FOREACH = new StabKeyword("FOREACH");
    public static final StabKeyword FROM = new StabKeyword("FROM");
    public static final StabKeyword GET = new StabKeyword("GET");
    public static final StabKeyword GOTO = new StabKeyword("GOTO");
    public static final StabKeyword GROUP = new StabKeyword("GROUP");
    public static final StabKeyword IF = new StabKeyword("IF");
    public static final StabKeyword IN = new StabKeyword("IN");
    public static final StabKeyword INSTANCEOF = new StabKeyword("INSTANCEOF");
    public static final StabKeyword INT = new StabKeyword("INT");
    public static final StabKeyword INTERFACE = new StabKeyword("INTERFACE");
    public static final StabKeyword INTO = new StabKeyword("INTO");
    public static final StabKeyword JOIN = new StabKeyword("JOIN");
    public static final StabKeyword LET = new StabKeyword("LET");
    public static final StabKeyword LONG = new StabKeyword("LONG");
    public static final StabKeyword PACKAGE = new StabKeyword("PACKAGE");
    public static final StabKeyword NATIVE = new StabKeyword("NATIVE");
    public static final StabKeyword NEW = new StabKeyword("NEW");
    public static final StabKeyword NULL = new StabKeyword("NULL");
    public static final StabKeyword ON = new StabKeyword("ON");
    public static final StabKeyword ORDERBY = new StabKeyword("ORDERBY");
    public static final StabKeyword OVERRIDE = new StabKeyword("OVERRIDE");
    public static final StabKeyword PARAMS = new StabKeyword("PARAMS");
    public static final StabKeyword PARTIAL = new StabKeyword("PARTIAL");
    public static final StabKeyword PRIVATE = new StabKeyword("PRIVATE");
    public static final StabKeyword PROTECTED = new StabKeyword("PROTECTED");
    public static final StabKeyword PUBLIC = new StabKeyword("PUBLIC");
    public static final StabKeyword RETURN = new StabKeyword("RETURN");
    public static final StabKeyword SELECT = new StabKeyword("SELECT");
    public static final StabKeyword SET = new StabKeyword("SET");
    public static final StabKeyword SHORT = new StabKeyword("SHORT");
    public static final StabKeyword SIZE_OF = new StabKeyword("SIZE_OF");
    public static final StabKeyword STATIC = new StabKeyword("STATIC");
    public static final StabKeyword STRICTFP = new StabKeyword("STRICTFP");
    public static final StabKeyword SUPER = new StabKeyword("SUPER");
    public static final StabKeyword SWITCH = new StabKeyword("SWITCH");
    public static final StabKeyword SYNCHRONIZED = new StabKeyword("SYNCHRONIZED");
    public static final StabKeyword THIS = new StabKeyword("THIS");
    public static final StabKeyword THROW = new StabKeyword("THROW");
    public static final StabKeyword TRANSIENT = new StabKeyword("TRANSIENT");
    public static final StabKeyword TRUE = new StabKeyword("TRUE");
    public static final StabKeyword TRY = new StabKeyword("TRY");
    public static final StabKeyword TYPE_OF = new StabKeyword("TYPE_OF");
    public static final StabKeyword USING = new StabKeyword("USING");
    public static final StabKeyword VALUE = new StabKeyword("VALUE");
    public static final StabKeyword VAR = new StabKeyword("VAR");
    public static final StabKeyword VIRTUAL = new StabKeyword("VIRTUAL");
    public static final StabKeyword VOID = new StabKeyword("VOID");
    public static final StabKeyword VOLATILE = new StabKeyword("VOLATILE");
    public static final StabKeyword WHERE = new StabKeyword("WHERE");
    public static final StabKeyword WHILE = new StabKeyword("WHILE");
    public static final StabKeyword YIELD = new StabKeyword("YIELD");
}
