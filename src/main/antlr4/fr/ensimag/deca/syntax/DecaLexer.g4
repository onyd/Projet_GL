lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@header {
    import static java.lang.Integer.parseInt;
}

@members {
}



// Deca lexer rules.

//mots reserves

ASM:'asm';
CLASS: 'class';
EXTENDS: 'extends';
ELSE: 'else';
FALSE: 'false';
IF: 'if';
INSTANCEOF: 'instanceof';
NEW: 'new';
NULL: 'null';
READINT: 'ReadInt';
READFLOAT: 'ReadFloat';
PRINT: 'print';
PRINTLN: 'println';
PRINTLNX: 'printlnx';
PRINTX: 'printx';
PROTECTED: 'protected';
RETURN: 'return';
THIS: 'this';
TRUE: 'true';
WHILE: 'while';

//Identificateurs

fragment LETTER: 'a'..'z'|'A'..'Z';
fragment DIGIT: '0'..'9' ;
IDENT: (LETTER|'$'|'_')(LETTER|DIGIT|'$'|'_')*;
EOL: ('\n') { skip(); };

//Symboles speciaux

LT: '<';
GT: '>';
EQUALS: '=';
PLUS: '+';
MINUS: '-';
TIMES: '*';
SLASH: '/';
PERCENT: '%';
DOT: '.';
COMMA: ',';
OPARENT: '(';
CPARENT: ')';
OBRACE: '{';
CBRACE: '}';
EXCLAM: '!';
SEMI: ';';
EQEQ: '==';
NEQ: '!=';
GEQ: '>=';
LEQ: '<=';
AND: '&&';
OR: '||';

// Litteraux entiers

fragment POSITIVE_DIGIT: '1'..'9';
INT: '0'| POSITIVE_DIGIT DIGIT* {
try {
    parseInt(getText());
 }
 catch (NumberFormatException e) {
    System.out.println("The number is too large, it must be a positive signed integer on 32 bits");
    System.exit(1);
 }
 };

// Litteraux flottants

fragment NUM: DIGIT+;
fragment SIGN: '+'|'-'|;
fragment EXP: ('E'|'e') SIGN NUM;
fragment DEC: NUM '.' NUM;
fragment FLOATDEC: (DEC|DEC EXP)('F'|'f'|);
fragment DIGITHEX: '0'..'9'|'A'..'F'|'a'..'f';
fragment NUMHEX: DIGITHEX+;
fragment FLOATHEX: ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f'|);
FLOAT: FLOATDEC|FLOATHEX;


fragment STRING_CAR: ~('"' | '\\' | '\n' | '\t');
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';

COMMENT: '/*' .*? '*/'{ skip(); };
SINGLE_COMMENT: '//' .*? (EOL | EOF){ skip(); };

SEPARATOR: (SPACE | '\t' | EOL | '\r' | COMMENT | SINGLE_COMMENT){ skip(); };
SPACE : (' ' | '\t') { skip(); };

fragment FILENAME: (LETTER | DIGIT | '.' | '-' | '_')+;
INCLUDE: '#include' (' ')* '"' FILENAME '"' { doInclude(getText()); };