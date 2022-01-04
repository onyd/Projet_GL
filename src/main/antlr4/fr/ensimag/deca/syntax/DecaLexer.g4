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

LETTER: 'a'..'z'|'A'..'Z';
fragment DIGIT: '0'..'9' ;
IDENT: (LETTER|'$'|'_')(LETTER|DIGIT|'$'|'_')*;
EOL: ('\n');
STRING_CAR: ~('"' | '\\' | '\n' | '\t');
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';
COMMENT: '/*' .*? '*/'{ skip(); };
SINGLE_COMMENT: '////' .*? (EOL | EOF);
SEPARATOR: (SPACE | '\t' | '\n' | '\r' | COMMENT | SINGLE_COMMENT);