lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
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
READINT: 'readint';
READFLOAT: 'readfloat';
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
INT: '0'| POSITIVE_DIGIT DIGIT*;

// Litteraux flottants

NUM: DIGIT+;
fragment SIGN: '+'|'-'|;
EXP: ('E'|'e') SIGN NUM;
fragment DEC: NUM '.' NUM;
fragment FLOATDEC: (DEC|DEC EXP)('F'|'f'|);
DIGITHEX: '0'..'9'|'A'..'F'|'a'..'f';
NUMHEX: DIGITHEX+;
fragment FLOATHEX: ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f'|);
FLOAT: FLOATDEC|FLOATHEX;


fragment STRING_CAR: ~('"' | '\\' | '\n' | '\t');
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';
COMMENT: '/*' .*? '*/'{ skip(); };
SINGLE_COMMENT: '//' .*? (EOL | EOF){ skip(); };
SEPARATOR: (SPACE | '\t' | '\n' | '\r' | COMMENT | SINGLE_COMMENT){ skip(); };
SPACE : (' ' | '\t') { skip(); };
FILENAME: (LETTER + DIGIT * '.' * '-' * '_'?)+;
INCLUDE: '#include' (' ')* '"' FILENAME '"' { doInclude(getText()); };