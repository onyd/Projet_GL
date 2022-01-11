// Generated from /home/anthony/Documents/GL/gl28/src/main/antlr4/fr/ensimag/deca/syntax/DecaLexer.g4 by ANTLR 4.9.2

    import static java.lang.Integer.parseInt;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DecaLexer extends AbstractDecaLexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ASM=1, CLASS=2, EXTENDS=3, ELSE=4, FALSE=5, IF=6, INSTANCEOF=7, NEW=8, 
		NULL=9, READINT=10, READFLOAT=11, PRINT=12, PRINTLN=13, PRINTLNX=14, PRINTX=15, 
		PROTECTED=16, RETURN=17, THIS=18, TRUE=19, WHILE=20, IDENT=21, EOL=22, 
		LT=23, GT=24, EQUALS=25, PLUS=26, MINUS=27, TIMES=28, SLASH=29, PERCENT=30, 
		DOT=31, COMMA=32, OPARENT=33, CPARENT=34, OBRACE=35, CBRACE=36, EXCLAM=37, 
		SEMI=38, EQEQ=39, NEQ=40, GEQ=41, LEQ=42, AND=43, OR=44, INT=45, FLOAT=46, 
		STRING=47, MULTI_LINE_STRING=48, COMMENT=49, SINGLE_COMMENT=50, SEPARATOR=51, 
		SPACE=52, INCLUDE=53;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ASM", "CLASS", "EXTENDS", "ELSE", "FALSE", "IF", "INSTANCEOF", "NEW", 
			"NULL", "READINT", "READFLOAT", "PRINT", "PRINTLN", "PRINTLNX", "PRINTX", 
			"PROTECTED", "RETURN", "THIS", "TRUE", "WHILE", "LETTER", "DIGIT", "IDENT", 
			"EOL", "LT", "GT", "EQUALS", "PLUS", "MINUS", "TIMES", "SLASH", "PERCENT", 
			"DOT", "COMMA", "OPARENT", "CPARENT", "OBRACE", "CBRACE", "EXCLAM", "SEMI", 
			"EQEQ", "NEQ", "GEQ", "LEQ", "AND", "OR", "POSITIVE_DIGIT", "INT", "NUM", 
			"SIGN", "EXP", "DEC", "FLOATDEC", "DIGITHEX", "NUMHEX", "FLOATHEX", "FLOAT", 
			"STRING_CAR", "STRING", "MULTI_LINE_STRING", "COMMENT", "SINGLE_COMMENT", 
			"SEPARATOR", "SPACE", "FILENAME", "INCLUDE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'asm'", "'class'", "'extends'", "'else'", "'false'", "'if'", "'instanceof'", 
			"'new'", "'null'", "'readint'", "'readfloat'", "'print'", "'println'", 
			"'printlnx'", "'printx'", "'protected'", "'return'", "'this'", "'true'", 
			"'while'", null, null, "'<'", "'>'", "'='", "'+'", "'-'", "'*'", "'/'", 
			"'%'", "'.'", "','", "'('", "')'", "'{'", "'}'", "'!'", "';'", "'=='", 
			"'!='", "'>='", "'<='", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASM", "CLASS", "EXTENDS", "ELSE", "FALSE", "IF", "INSTANCEOF", 
			"NEW", "NULL", "READINT", "READFLOAT", "PRINT", "PRINTLN", "PRINTLNX", 
			"PRINTX", "PROTECTED", "RETURN", "THIS", "TRUE", "WHILE", "IDENT", "EOL", 
			"LT", "GT", "EQUALS", "PLUS", "MINUS", "TIMES", "SLASH", "PERCENT", "DOT", 
			"COMMA", "OPARENT", "CPARENT", "OBRACE", "CBRACE", "EXCLAM", "SEMI", 
			"EQEQ", "NEQ", "GEQ", "LEQ", "AND", "OR", "INT", "FLOAT", "STRING", "MULTI_LINE_STRING", 
			"COMMENT", "SINGLE_COMMENT", "SEPARATOR", "SPACE", "INCLUDE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}




	public DecaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DecaLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 23:
			EOL_action((RuleContext)_localctx, actionIndex);
			break;
		case 47:
			INT_action((RuleContext)_localctx, actionIndex);
			break;
		case 60:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 61:
			SINGLE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 62:
			SEPARATOR_action((RuleContext)_localctx, actionIndex);
			break;
		case 63:
			SPACE_action((RuleContext)_localctx, actionIndex);
			break;
		case 65:
			INCLUDE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void EOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 skip(); 
			break;
		}
	}
	private void INT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:

			try {
			    parseInt(getText());
			 }
			 catch (NumberFormatException e) {
			    System.out.println("The number is too large, it must be a positive signed integer on 32 bits");
			    System.exit(1);
			 }
			 
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 skip(); 
			break;
		}
	}
	private void SINGLE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		}
	}
	private void SEPARATOR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			 skip(); 
			break;
		}
	}
	private void SPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			 skip(); 
			break;
		}
	}
	private void INCLUDE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			 doInclude(getText()); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\67\u01f7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t"+
		"=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\5\30\u0113\n\30\3\30\3\30\3\30\7\30"+
		"\u0118\n\30\f\30\16\30\u011b\13\30\3\31\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$"+
		"\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3*\3*\3*\3+\3+\3+\3,\3,\3,\3-\3-\3"+
		"-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\61\3\61\3\61\7\61\u0157\n\61\f\61\16\61"+
		"\u015a\13\61\3\61\3\61\5\61\u015e\n\61\3\62\6\62\u0161\n\62\r\62\16\62"+
		"\u0162\3\63\3\63\5\63\u0167\n\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3"+
		"\65\3\66\3\66\3\66\3\66\5\66\u0175\n\66\3\66\3\66\5\66\u0179\n\66\3\67"+
		"\3\67\38\68\u017e\n8\r8\168\u017f\39\39\39\39\59\u0186\n9\39\39\39\39"+
		"\39\39\39\39\59\u0190\n9\3:\3:\5:\u0194\n:\3;\3;\3<\3<\3<\3<\3<\3<\7<"+
		"\u019e\n<\f<\16<\u01a1\13<\3<\3<\3=\3=\3=\3=\3=\3=\3=\7=\u01ac\n=\f=\16"+
		"=\u01af\13=\3=\3=\3>\3>\3>\3>\7>\u01b7\n>\f>\16>\u01ba\13>\3>\3>\3>\3"+
		">\3>\3?\3?\3?\3?\7?\u01c5\n?\f?\16?\u01c8\13?\3?\3?\5?\u01cc\n?\3?\3?"+
		"\3@\3@\3@\3@\3@\3@\5@\u01d6\n@\3@\3@\3A\3A\3A\3B\3B\3B\6B\u01e0\nB\rB"+
		"\16B\u01e1\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\7C\u01ee\nC\fC\16C\u01f1\13C"+
		"\3C\3C\3C\3C\3C\4\u01b8\u01c6\2D\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\2-\2/\27\61"+
		"\30\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'Q(S)U*W+Y,[-]."+
		"_\2a/c\2e\2g\2i\2k\2m\2o\2q\2s\60u\2w\61y\62{\63}\64\177\65\u0081\66\u0083"+
		"\2\u0085\67\3\2\f\4\2C\\c|\4\2&&aa\4\2--//\4\2GGgg\4\2HHhh\5\2\62;CHc"+
		"h\4\2RRrr\5\2\13\f$$^^\4\2\13\13\"\"\4\2/\60aa\2\u020a\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2"+
		"\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33"+
		"\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2"+
		"\'\3\2\2\2\2)\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2"+
		"\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O"+
		"\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2"+
		"\2\2\2]\3\2\2\2\2a\3\2\2\2\2s\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2"+
		"\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0085\3\2\2\2\3\u0087\3\2"+
		"\2\2\5\u008b\3\2\2\2\7\u0091\3\2\2\2\t\u0099\3\2\2\2\13\u009e\3\2\2\2"+
		"\r\u00a4\3\2\2\2\17\u00a7\3\2\2\2\21\u00b2\3\2\2\2\23\u00b6\3\2\2\2\25"+
		"\u00bb\3\2\2\2\27\u00c3\3\2\2\2\31\u00cd\3\2\2\2\33\u00d3\3\2\2\2\35\u00db"+
		"\3\2\2\2\37\u00e4\3\2\2\2!\u00eb\3\2\2\2#\u00f5\3\2\2\2%\u00fc\3\2\2\2"+
		"\'\u0101\3\2\2\2)\u0106\3\2\2\2+\u010c\3\2\2\2-\u010e\3\2\2\2/\u0112\3"+
		"\2\2\2\61\u011c\3\2\2\2\63\u011f\3\2\2\2\65\u0121\3\2\2\2\67\u0123\3\2"+
		"\2\29\u0125\3\2\2\2;\u0127\3\2\2\2=\u0129\3\2\2\2?\u012b\3\2\2\2A\u012d"+
		"\3\2\2\2C\u012f\3\2\2\2E\u0131\3\2\2\2G\u0133\3\2\2\2I\u0135\3\2\2\2K"+
		"\u0137\3\2\2\2M\u0139\3\2\2\2O\u013b\3\2\2\2Q\u013d\3\2\2\2S\u013f\3\2"+
		"\2\2U\u0142\3\2\2\2W\u0145\3\2\2\2Y\u0148\3\2\2\2[\u014b\3\2\2\2]\u014e"+
		"\3\2\2\2_\u0151\3\2\2\2a\u015d\3\2\2\2c\u0160\3\2\2\2e\u0166\3\2\2\2g"+
		"\u0168\3\2\2\2i\u016c\3\2\2\2k\u0174\3\2\2\2m\u017a\3\2\2\2o\u017d\3\2"+
		"\2\2q\u0185\3\2\2\2s\u0193\3\2\2\2u\u0195\3\2\2\2w\u0197\3\2\2\2y\u01a4"+
		"\3\2\2\2{\u01b2\3\2\2\2}\u01c0\3\2\2\2\177\u01d5\3\2\2\2\u0081\u01d9\3"+
		"\2\2\2\u0083\u01df\3\2\2\2\u0085\u01e3\3\2\2\2\u0087\u0088\7c\2\2\u0088"+
		"\u0089\7u\2\2\u0089\u008a\7o\2\2\u008a\4\3\2\2\2\u008b\u008c\7e\2\2\u008c"+
		"\u008d\7n\2\2\u008d\u008e\7c\2\2\u008e\u008f\7u\2\2\u008f\u0090\7u\2\2"+
		"\u0090\6\3\2\2\2\u0091\u0092\7g\2\2\u0092\u0093\7z\2\2\u0093\u0094\7v"+
		"\2\2\u0094\u0095\7g\2\2\u0095\u0096\7p\2\2\u0096\u0097\7f\2\2\u0097\u0098"+
		"\7u\2\2\u0098\b\3\2\2\2\u0099\u009a\7g\2\2\u009a\u009b\7n\2\2\u009b\u009c"+
		"\7u\2\2\u009c\u009d\7g\2\2\u009d\n\3\2\2\2\u009e\u009f\7h\2\2\u009f\u00a0"+
		"\7c\2\2\u00a0\u00a1\7n\2\2\u00a1\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3"+
		"\f\3\2\2\2\u00a4\u00a5\7k\2\2\u00a5\u00a6\7h\2\2\u00a6\16\3\2\2\2\u00a7"+
		"\u00a8\7k\2\2\u00a8\u00a9\7p\2\2\u00a9\u00aa\7u\2\2\u00aa\u00ab\7v\2\2"+
		"\u00ab\u00ac\7c\2\2\u00ac\u00ad\7p\2\2\u00ad\u00ae\7e\2\2\u00ae\u00af"+
		"\7g\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1\7h\2\2\u00b1\20\3\2\2\2\u00b2\u00b3"+
		"\7p\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5\7y\2\2\u00b5\22\3\2\2\2\u00b6\u00b7"+
		"\7p\2\2\u00b7\u00b8\7w\2\2\u00b8\u00b9\7n\2\2\u00b9\u00ba\7n\2\2\u00ba"+
		"\24\3\2\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7c\2\2\u00be"+
		"\u00bf\7f\2\2\u00bf\u00c0\7k\2\2\u00c0\u00c1\7p\2\2\u00c1\u00c2\7v\2\2"+
		"\u00c2\26\3\2\2\2\u00c3\u00c4\7t\2\2\u00c4\u00c5\7g\2\2\u00c5\u00c6\7"+
		"c\2\2\u00c6\u00c7\7f\2\2\u00c7\u00c8\7h\2\2\u00c8\u00c9\7n\2\2\u00c9\u00ca"+
		"\7q\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7v\2\2\u00cc\30\3\2\2\2\u00cd\u00ce"+
		"\7r\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7k\2\2\u00d0\u00d1\7p\2\2\u00d1"+
		"\u00d2\7v\2\2\u00d2\32\3\2\2\2\u00d3\u00d4\7r\2\2\u00d4\u00d5\7t\2\2\u00d5"+
		"\u00d6\7k\2\2\u00d6\u00d7\7p\2\2\u00d7\u00d8\7v\2\2\u00d8\u00d9\7n\2\2"+
		"\u00d9\u00da\7p\2\2\u00da\34\3\2\2\2\u00db\u00dc\7r\2\2\u00dc\u00dd\7"+
		"t\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7p\2\2\u00df\u00e0\7v\2\2\u00e0\u00e1"+
		"\7n\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3\7z\2\2\u00e3\36\3\2\2\2\u00e4\u00e5"+
		"\7r\2\2\u00e5\u00e6\7t\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8\7p\2\2\u00e8"+
		"\u00e9\7v\2\2\u00e9\u00ea\7z\2\2\u00ea \3\2\2\2\u00eb\u00ec\7r\2\2\u00ec"+
		"\u00ed\7t\2\2\u00ed\u00ee\7q\2\2\u00ee\u00ef\7v\2\2\u00ef\u00f0\7g\2\2"+
		"\u00f0\u00f1\7e\2\2\u00f1\u00f2\7v\2\2\u00f2\u00f3\7g\2\2\u00f3\u00f4"+
		"\7f\2\2\u00f4\"\3\2\2\2\u00f5\u00f6\7t\2\2\u00f6\u00f7\7g\2\2\u00f7\u00f8"+
		"\7v\2\2\u00f8\u00f9\7w\2\2\u00f9\u00fa\7t\2\2\u00fa\u00fb\7p\2\2\u00fb"+
		"$\3\2\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe\7j\2\2\u00fe\u00ff\7k\2\2\u00ff"+
		"\u0100\7u\2\2\u0100&\3\2\2\2\u0101\u0102\7v\2\2\u0102\u0103\7t\2\2\u0103"+
		"\u0104\7w\2\2\u0104\u0105\7g\2\2\u0105(\3\2\2\2\u0106\u0107\7y\2\2\u0107"+
		"\u0108\7j\2\2\u0108\u0109\7k\2\2\u0109\u010a\7n\2\2\u010a\u010b\7g\2\2"+
		"\u010b*\3\2\2\2\u010c\u010d\t\2\2\2\u010d,\3\2\2\2\u010e\u010f\4\62;\2"+
		"\u010f.\3\2\2\2\u0110\u0113\5+\26\2\u0111\u0113\t\3\2\2\u0112\u0110\3"+
		"\2\2\2\u0112\u0111\3\2\2\2\u0113\u0119\3\2\2\2\u0114\u0118\5+\26\2\u0115"+
		"\u0118\5-\27\2\u0116\u0118\t\3\2\2\u0117\u0114\3\2\2\2\u0117\u0115\3\2"+
		"\2\2\u0117\u0116\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\60\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d\7\f\2"+
		"\2\u011d\u011e\b\31\2\2\u011e\62\3\2\2\2\u011f\u0120\7>\2\2\u0120\64\3"+
		"\2\2\2\u0121\u0122\7@\2\2\u0122\66\3\2\2\2\u0123\u0124\7?\2\2\u01248\3"+
		"\2\2\2\u0125\u0126\7-\2\2\u0126:\3\2\2\2\u0127\u0128\7/\2\2\u0128<\3\2"+
		"\2\2\u0129\u012a\7,\2\2\u012a>\3\2\2\2\u012b\u012c\7\61\2\2\u012c@\3\2"+
		"\2\2\u012d\u012e\7\'\2\2\u012eB\3\2\2\2\u012f\u0130\7\60\2\2\u0130D\3"+
		"\2\2\2\u0131\u0132\7.\2\2\u0132F\3\2\2\2\u0133\u0134\7*\2\2\u0134H\3\2"+
		"\2\2\u0135\u0136\7+\2\2\u0136J\3\2\2\2\u0137\u0138\7}\2\2\u0138L\3\2\2"+
		"\2\u0139\u013a\7\177\2\2\u013aN\3\2\2\2\u013b\u013c\7#\2\2\u013cP\3\2"+
		"\2\2\u013d\u013e\7=\2\2\u013eR\3\2\2\2\u013f\u0140\7?\2\2\u0140\u0141"+
		"\7?\2\2\u0141T\3\2\2\2\u0142\u0143\7#\2\2\u0143\u0144\7?\2\2\u0144V\3"+
		"\2\2\2\u0145\u0146\7@\2\2\u0146\u0147\7?\2\2\u0147X\3\2\2\2\u0148\u0149"+
		"\7>\2\2\u0149\u014a\7?\2\2\u014aZ\3\2\2\2\u014b\u014c\7(\2\2\u014c\u014d"+
		"\7(\2\2\u014d\\\3\2\2\2\u014e\u014f\7~\2\2\u014f\u0150\7~\2\2\u0150^\3"+
		"\2\2\2\u0151\u0152\4\63;\2\u0152`\3\2\2\2\u0153\u015e\7\62\2\2\u0154\u0158"+
		"\5_\60\2\u0155\u0157\5-\27\2\u0156\u0155\3\2\2\2\u0157\u015a\3\2\2\2\u0158"+
		"\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015b\3\2\2\2\u015a\u0158\3\2"+
		"\2\2\u015b\u015c\b\61\3\2\u015c\u015e\3\2\2\2\u015d\u0153\3\2\2\2\u015d"+
		"\u0154\3\2\2\2\u015eb\3\2\2\2\u015f\u0161\5-\27\2\u0160\u015f\3\2\2\2"+
		"\u0161\u0162\3\2\2\2\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163d\3"+
		"\2\2\2\u0164\u0167\t\4\2\2\u0165\u0167\3\2\2\2\u0166\u0164\3\2\2\2\u0166"+
		"\u0165\3\2\2\2\u0167f\3\2\2\2\u0168\u0169\t\5\2\2\u0169\u016a\5e\63\2"+
		"\u016a\u016b\5c\62\2\u016bh\3\2\2\2\u016c\u016d\5c\62\2\u016d\u016e\7"+
		"\60\2\2\u016e\u016f\5c\62\2\u016fj\3\2\2\2\u0170\u0175\5i\65\2\u0171\u0172"+
		"\5i\65\2\u0172\u0173\5g\64\2\u0173\u0175\3\2\2\2\u0174\u0170\3\2\2\2\u0174"+
		"\u0171\3\2\2\2\u0175\u0178\3\2\2\2\u0176\u0179\t\6\2\2\u0177\u0179\3\2"+
		"\2\2\u0178\u0176\3\2\2\2\u0178\u0177\3\2\2\2\u0179l\3\2\2\2\u017a\u017b"+
		"\t\7\2\2\u017bn\3\2\2\2\u017c\u017e\5m\67\2\u017d\u017c\3\2\2\2\u017e"+
		"\u017f\3\2\2\2\u017f\u017d\3\2\2\2\u017f\u0180\3\2\2\2\u0180p\3\2\2\2"+
		"\u0181\u0182\7\62\2\2\u0182\u0186\7z\2\2\u0183\u0184\7\62\2\2\u0184\u0186"+
		"\7Z\2\2\u0185\u0181\3\2\2\2\u0185\u0183\3\2\2\2\u0186\u0187\3\2\2\2\u0187"+
		"\u0188\5o8\2\u0188\u0189\7\60\2\2\u0189\u018a\5o8\2\u018a\u018b\t\b\2"+
		"\2\u018b\u018c\5e\63\2\u018c\u018f\5c\62\2\u018d\u0190\t\6\2\2\u018e\u0190"+
		"\3\2\2\2\u018f\u018d\3\2\2\2\u018f\u018e\3\2\2\2\u0190r\3\2\2\2\u0191"+
		"\u0194\5k\66\2\u0192\u0194\5q9\2\u0193\u0191\3\2\2\2\u0193\u0192\3\2\2"+
		"\2\u0194t\3\2\2\2\u0195\u0196\n\t\2\2\u0196v\3\2\2\2\u0197\u019f\7$\2"+
		"\2\u0198\u019e\5u;\2\u0199\u019a\7^\2\2\u019a\u019e\7$\2\2\u019b\u019c"+
		"\7^\2\2\u019c\u019e\7^\2\2\u019d\u0198\3\2\2\2\u019d\u0199\3\2\2\2\u019d"+
		"\u019b\3\2\2\2\u019e\u01a1\3\2\2\2\u019f\u019d\3\2\2\2\u019f\u01a0\3\2"+
		"\2\2\u01a0\u01a2\3\2\2\2\u01a1\u019f\3\2\2\2\u01a2\u01a3\7$\2\2\u01a3"+
		"x\3\2\2\2\u01a4\u01ad\7$\2\2\u01a5\u01ac\5u;\2\u01a6\u01ac\5\61\31\2\u01a7"+
		"\u01a8\7^\2\2\u01a8\u01ac\7$\2\2\u01a9\u01aa\7^\2\2\u01aa\u01ac\7^\2\2"+
		"\u01ab\u01a5\3\2\2\2\u01ab\u01a6\3\2\2\2\u01ab\u01a7\3\2\2\2\u01ab\u01a9"+
		"\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad\u01ae\3\2\2\2\u01ae"+
		"\u01b0\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b1\7$\2\2\u01b1z\3\2\2\2\u01b2"+
		"\u01b3\7\61\2\2\u01b3\u01b4\7,\2\2\u01b4\u01b8\3\2\2\2\u01b5\u01b7\13"+
		"\2\2\2\u01b6\u01b5\3\2\2\2\u01b7\u01ba\3\2\2\2\u01b8\u01b9\3\2\2\2\u01b8"+
		"\u01b6\3\2\2\2\u01b9\u01bb\3\2\2\2\u01ba\u01b8\3\2\2\2\u01bb\u01bc\7,"+
		"\2\2\u01bc\u01bd\7\61\2\2\u01bd\u01be\3\2\2\2\u01be\u01bf\b>\4\2\u01bf"+
		"|\3\2\2\2\u01c0\u01c1\7\61\2\2\u01c1\u01c2\7\61\2\2\u01c2\u01c6\3\2\2"+
		"\2\u01c3\u01c5\13\2\2\2\u01c4\u01c3\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6"+
		"\u01c7\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01cb\3\2\2\2\u01c8\u01c6\3\2"+
		"\2\2\u01c9\u01cc\5\61\31\2\u01ca\u01cc\7\2\2\3\u01cb\u01c9\3\2\2\2\u01cb"+
		"\u01ca\3\2\2\2\u01cc\u01cd\3\2\2\2\u01cd\u01ce\b?\5\2\u01ce~\3\2\2\2\u01cf"+
		"\u01d6\5\u0081A\2\u01d0\u01d6\7\13\2\2\u01d1\u01d6\5\61\31\2\u01d2\u01d6"+
		"\7\17\2\2\u01d3\u01d6\5{>\2\u01d4\u01d6\5}?\2\u01d5\u01cf\3\2\2\2\u01d5"+
		"\u01d0\3\2\2\2\u01d5\u01d1\3\2\2\2\u01d5\u01d2\3\2\2\2\u01d5\u01d3\3\2"+
		"\2\2\u01d5\u01d4\3\2\2\2\u01d6\u01d7\3\2\2\2\u01d7\u01d8\b@\6\2\u01d8"+
		"\u0080\3\2\2\2\u01d9\u01da\t\n\2\2\u01da\u01db\bA\7\2\u01db\u0082\3\2"+
		"\2\2\u01dc\u01e0\5+\26\2\u01dd\u01e0\5-\27\2\u01de\u01e0\t\13\2\2\u01df"+
		"\u01dc\3\2\2\2\u01df\u01dd\3\2\2\2\u01df\u01de\3\2\2\2\u01e0\u01e1\3\2"+
		"\2\2\u01e1\u01df\3\2\2\2\u01e1\u01e2\3\2\2\2\u01e2\u0084\3\2\2\2\u01e3"+
		"\u01e4\7%\2\2\u01e4\u01e5\7k\2\2\u01e5\u01e6\7p\2\2\u01e6\u01e7\7e\2\2"+
		"\u01e7\u01e8\7n\2\2\u01e8\u01e9\7w\2\2\u01e9\u01ea\7f\2\2\u01ea\u01eb"+
		"\7g\2\2\u01eb\u01ef\3\2\2\2\u01ec\u01ee\7\"\2\2\u01ed\u01ec\3\2\2\2\u01ee"+
		"\u01f1\3\2\2\2\u01ef\u01ed\3\2\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01f2\3\2"+
		"\2\2\u01f1\u01ef\3\2\2\2\u01f2\u01f3\7$\2\2\u01f3\u01f4\5\u0083B\2\u01f4"+
		"\u01f5\7$\2\2\u01f5\u01f6\bC\b\2\u01f6\u0086\3\2\2\2\33\2\u0112\u0117"+
		"\u0119\u0158\u015d\u0162\u0166\u0174\u0178\u017f\u0185\u018f\u0193\u019d"+
		"\u019f\u01ab\u01ad\u01b8\u01c6\u01cb\u01d5\u01df\u01e1\u01ef\t\3\31\2"+
		"\3\61\3\3>\4\3?\5\3@\6\3A\7\3C\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}