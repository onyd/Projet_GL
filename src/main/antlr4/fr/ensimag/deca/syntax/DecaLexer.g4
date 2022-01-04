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
FALSE: 'false';
INSTANCEOF: 'instanceof';
NEW: 'new';
NULL: 'null';
READINT: 'readint';
READFLOAT: 'readfloat';
PRINT: 'print';
PRINTLN: 'prinln';
PRINTLNX: 'printlnx';
PRINTX: 'printx';
PROTECTED: 'protected';
RETURN: 'return';
THIS: 'this';
TRUE: 'true';
WHILE: 'while';

//Identificateurs

LETTER: 'a'..'z'|'A'..'Z';
fragment DIGIT: '0'..'9' ;
IDENT: (LETTER|'$'|'_')(LETTER|DIGIT|'$'|'_')*;
EOL: ('\n');//peut-être il faut ajouter le \t aussi.

//Symboles speciaux

LOWER: '<';
GREATER: '>';
EQUAL: '=';
PLUS: '+';
MINUS: '-';
TIMES: '*';
SLASH: '/';
PRECENT: '%';
DOT: '.';
COMA: ',';
OPAR: '(';
CPAR: ')';
OBRACE: '{';
CBRACE: '}';
DIFF: '!';
SEMI: ';';
ISEQUAL: '==';
ISDIFF: '!=';
GEQ: '>=';
LEQ: '<=';
AND: '&&';
OR: '||';

// Litteraux entiers

POSITIVE_DIGIT: '1'..'9';
INT: '0'| POSITIVE_DIGIT DIGIT*;

// Litteraux flottants

NUM: DIGIT+;
fragment SIGN: '+'|'-'|;
EXP: ('E'|'e') SIGN NUM;
DEC: NUM '.' NUM;
FLOATDEC: (DEC|DEC EXP)('F'|'f'|);
DIGITHEX: '0'..'9'|'A'..'F'|'a'..'f';
NUMHEX: DIGITHEX+;
FLOATHEX: ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f'|);
FLOAT: FLOATDEC|FLOATHEX;


STRING_CAR: ~('"' | '\\' | '\n' | '\t');
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';
COMMENT: '/*' .*? '*/'{ skip(); };
SINGLE_COMMENT: '////' .*? (EOL | EOF);
SEPARATOR: (SPACE | '\t' | '\n' | '\r' | COMMENT | SINGLE_COMMENT);
ELSE : 'else';
ELSEIF : 'elseif';
IF : 'if';
SPACE : ' '{ skip(); };
FILENAME:(LETTER|DIGIT|'.'|'-'|'_')+;
INCLUDE: '#include' (' ')* '"' FILENAME '"';