// Generated from /home/anthony/Documents/GL/gl28/src/main/antlr4/fr/ensimag/deca/syntax/DecaLexer.g4 by ANTLR 4.9.2
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
		PROTECTED=16, RETURN=17, THIS=18, TRUE=19, WHILE=20, LETTER=21, IDENT=22, 
		EOL=23, LT=24, GT=25, EQUALS=26, PLUS=27, MINUS=28, TIMES=29, SLASH=30, 
		PERCENT=31, DOT=32, COMMA=33, OPARENT=34, CPARENT=35, OBRACE=36, CBRACE=37, 
		EXCLAM=38, SEMI=39, EQEQ=40, NEQ=41, GEQ=42, LEQ=43, AND=44, OR=45, INT=46, 
		NUM=47, EXP=48, DEC=49, FLOATDEC=50, DIGITHEX=51, NUMHEX=52, FLOATHEX=53, 
		FLOAT=54, STRING=55, MULTI_LINE_STRING=56, COMMENT=57, SINGLE_COMMENT=58, 
		SEPARATOR=59, SPACE=60, FILENAME=61, INCLUDE=62;
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
			"'while'", null, null, null, "'<'", "'>'", "'='", "'+'", "'-'", "'*'", 
			"'/'", "'%'", "'.'", "','", "'('", "')'", "'{'", "'}'", "'!'", "';'", 
			"'=='", "'!='", "'>='", "'<='", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ASM", "CLASS", "EXTENDS", "ELSE", "FALSE", "IF", "INSTANCEOF", 
			"NEW", "NULL", "READINT", "READFLOAT", "PRINT", "PRINTLN", "PRINTLNX", 
			"PRINTX", "PROTECTED", "RETURN", "THIS", "TRUE", "WHILE", "LETTER", "IDENT", 
			"EOL", "LT", "GT", "EQUALS", "PLUS", "MINUS", "TIMES", "SLASH", "PERCENT", 
			"DOT", "COMMA", "OPARENT", "CPARENT", "OBRACE", "CBRACE", "EXCLAM", "SEMI", 
			"EQEQ", "NEQ", "GEQ", "LEQ", "AND", "OR", "INT", "NUM", "EXP", "DEC", 
			"FLOATDEC", "DIGITHEX", "NUMHEX", "FLOATHEX", "FLOAT", "STRING", "MULTI_LINE_STRING", 
			"COMMENT", "SINGLE_COMMENT", "SEPARATOR", "SPACE", "FILENAME", "INCLUDE"
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
		}
	}
	private void EOL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			 skip(); 
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			 skip(); 
			break;
		}
	}
	private void SINGLE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			 skip(); 
			break;
		}
	}
	private void SEPARATOR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			 skip(); 
			break;
		}
	}
	private void SPACE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			 skip(); 
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2@\u0205\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
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
		"\u015a\13\61\5\61\u015c\n\61\3\62\6\62\u015f\n\62\r\62\16\62\u0160\3\63"+
		"\3\63\5\63\u0165\n\63\3\64\3\64\3\64\3\64\3\65\3\65\3\65\3\65\3\66\3\66"+
		"\3\66\3\66\5\66\u0173\n\66\3\66\3\66\5\66\u0177\n\66\3\67\3\67\38\68\u017c"+
		"\n8\r8\168\u017d\39\39\39\39\59\u0184\n9\39\39\39\39\39\39\39\39\59\u018e"+
		"\n9\3:\3:\5:\u0192\n:\3;\3;\3<\3<\3<\3<\3<\3<\7<\u019c\n<\f<\16<\u019f"+
		"\13<\3<\3<\3=\3=\3=\3=\3=\3=\3=\7=\u01aa\n=\f=\16=\u01ad\13=\3=\3=\3>"+
		"\3>\3>\3>\7>\u01b5\n>\f>\16>\u01b8\13>\3>\3>\3>\3>\3>\3?\3?\3?\3?\7?\u01c3"+
		"\n?\f?\16?\u01c6\13?\3?\3?\5?\u01ca\n?\3?\3?\3@\3@\3@\3@\5@\u01d2\n@\3"+
		"@\3@\3A\3A\3A\3B\6B\u01da\nB\rB\16B\u01db\3B\6B\u01df\nB\rB\16B\u01e0"+
		"\3B\6B\u01e4\nB\rB\16B\u01e5\3B\6B\u01e9\nB\rB\16B\u01ea\3B\3B\6B\u01ef"+
		"\nB\rB\16B\u01f0\3C\3C\3C\3C\3C\3C\3C\3C\3C\3C\7C\u01fd\nC\fC\16C\u0200"+
		"\13C\3C\3C\3C\3C\4\u01b6\u01c4\2D\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\2/\30"+
		"\61\31\63\32\65\33\67\349\35;\36=\37? A!C\"E#G$I%K&M\'O(Q)S*U+W,Y-[.]"+
		"/_\2a\60c\61e\2g\62i\63k\64m\65o\66q\67s8u\2w9y:{;}<\177=\u0081>\u0083"+
		"?\u0085@\3\2\f\4\2C\\c|\4\2&&aa\4\2--//\4\2GGgg\4\2HHhh\5\2\62;CHch\4"+
		"\2RRrr\5\2\13\f$$^^\4\2\13\f\17\17\4\2\13\13\"\"\2\u0221\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2"+
		"\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2"+
		"\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2"+
		"M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3"+
		"\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2w\3\2\2\2\2"+
		"y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083"+
		"\3\2\2\2\2\u0085\3\2\2\2\3\u0087\3\2\2\2\5\u008b\3\2\2\2\7\u0091\3\2\2"+
		"\2\t\u0099\3\2\2\2\13\u009e\3\2\2\2\r\u00a4\3\2\2\2\17\u00a7\3\2\2\2\21"+
		"\u00b2\3\2\2\2\23\u00b6\3\2\2\2\25\u00bb\3\2\2\2\27\u00c3\3\2\2\2\31\u00cd"+
		"\3\2\2\2\33\u00d3\3\2\2\2\35\u00db\3\2\2\2\37\u00e4\3\2\2\2!\u00eb\3\2"+
		"\2\2#\u00f5\3\2\2\2%\u00fc\3\2\2\2\'\u0101\3\2\2\2)\u0106\3\2\2\2+\u010c"+
		"\3\2\2\2-\u010e\3\2\2\2/\u0112\3\2\2\2\61\u011c\3\2\2\2\63\u011f\3\2\2"+
		"\2\65\u0121\3\2\2\2\67\u0123\3\2\2\29\u0125\3\2\2\2;\u0127\3\2\2\2=\u0129"+
		"\3\2\2\2?\u012b\3\2\2\2A\u012d\3\2\2\2C\u012f\3\2\2\2E\u0131\3\2\2\2G"+
		"\u0133\3\2\2\2I\u0135\3\2\2\2K\u0137\3\2\2\2M\u0139\3\2\2\2O\u013b\3\2"+
		"\2\2Q\u013d\3\2\2\2S\u013f\3\2\2\2U\u0142\3\2\2\2W\u0145\3\2\2\2Y\u0148"+
		"\3\2\2\2[\u014b\3\2\2\2]\u014e\3\2\2\2_\u0151\3\2\2\2a\u015b\3\2\2\2c"+
		"\u015e\3\2\2\2e\u0164\3\2\2\2g\u0166\3\2\2\2i\u016a\3\2\2\2k\u0172\3\2"+
		"\2\2m\u0178\3\2\2\2o\u017b\3\2\2\2q\u0183\3\2\2\2s\u0191\3\2\2\2u\u0193"+
		"\3\2\2\2w\u0195\3\2\2\2y\u01a2\3\2\2\2{\u01b0\3\2\2\2}\u01be\3\2\2\2\177"+
		"\u01d1\3\2\2\2\u0081\u01d5\3\2\2\2\u0083\u01ee\3\2\2\2\u0085\u01f2\3\2"+
		"\2\2\u0087\u0088\7c\2\2\u0088\u0089\7u\2\2\u0089\u008a\7o\2\2\u008a\4"+
		"\3\2\2\2\u008b\u008c\7e\2\2\u008c\u008d\7n\2\2\u008d\u008e\7c\2\2\u008e"+
		"\u008f\7u\2\2\u008f\u0090\7u\2\2\u0090\6\3\2\2\2\u0091\u0092\7g\2\2\u0092"+
		"\u0093\7z\2\2\u0093\u0094\7v\2\2\u0094\u0095\7g\2\2\u0095\u0096\7p\2\2"+
		"\u0096\u0097\7f\2\2\u0097\u0098\7u\2\2\u0098\b\3\2\2\2\u0099\u009a\7g"+
		"\2\2\u009a\u009b\7n\2\2\u009b\u009c\7u\2\2\u009c\u009d\7g\2\2\u009d\n"+
		"\3\2\2\2\u009e\u009f\7h\2\2\u009f\u00a0\7c\2\2\u00a0\u00a1\7n\2\2\u00a1"+
		"\u00a2\7u\2\2\u00a2\u00a3\7g\2\2\u00a3\f\3\2\2\2\u00a4\u00a5\7k\2\2\u00a5"+
		"\u00a6\7h\2\2\u00a6\16\3\2\2\2\u00a7\u00a8\7k\2\2\u00a8\u00a9\7p\2\2\u00a9"+
		"\u00aa\7u\2\2\u00aa\u00ab\7v\2\2\u00ab\u00ac\7c\2\2\u00ac\u00ad\7p\2\2"+
		"\u00ad\u00ae\7e\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7q\2\2\u00b0\u00b1"+
		"\7h\2\2\u00b1\20\3\2\2\2\u00b2\u00b3\7p\2\2\u00b3\u00b4\7g\2\2\u00b4\u00b5"+
		"\7y\2\2\u00b5\22\3\2\2\2\u00b6\u00b7\7p\2\2\u00b7\u00b8\7w\2\2\u00b8\u00b9"+
		"\7n\2\2\u00b9\u00ba\7n\2\2\u00ba\24\3\2\2\2\u00bb\u00bc\7t\2\2\u00bc\u00bd"+
		"\7g\2\2\u00bd\u00be\7c\2\2\u00be\u00bf\7f\2\2\u00bf\u00c0\7k\2\2\u00c0"+
		"\u00c1\7p\2\2\u00c1\u00c2\7v\2\2\u00c2\26\3\2\2\2\u00c3\u00c4\7t\2\2\u00c4"+
		"\u00c5\7g\2\2\u00c5\u00c6\7c\2\2\u00c6\u00c7\7f\2\2\u00c7\u00c8\7h\2\2"+
		"\u00c8\u00c9\7n\2\2\u00c9\u00ca\7q\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc"+
		"\7v\2\2\u00cc\30\3\2\2\2\u00cd\u00ce\7r\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0"+
		"\7k\2\2\u00d0\u00d1\7p\2\2\u00d1\u00d2\7v\2\2\u00d2\32\3\2\2\2\u00d3\u00d4"+
		"\7r\2\2\u00d4\u00d5\7t\2\2\u00d5\u00d6\7k\2\2\u00d6\u00d7\7p\2\2\u00d7"+
		"\u00d8\7v\2\2\u00d8\u00d9\7n\2\2\u00d9\u00da\7p\2\2\u00da\34\3\2\2\2\u00db"+
		"\u00dc\7r\2\2\u00dc\u00dd\7t\2\2\u00dd\u00de\7k\2\2\u00de\u00df\7p\2\2"+
		"\u00df\u00e0\7v\2\2\u00e0\u00e1\7n\2\2\u00e1\u00e2\7p\2\2\u00e2\u00e3"+
		"\7z\2\2\u00e3\36\3\2\2\2\u00e4\u00e5\7r\2\2\u00e5\u00e6\7t\2\2\u00e6\u00e7"+
		"\7k\2\2\u00e7\u00e8\7p\2\2\u00e8\u00e9\7v\2\2\u00e9\u00ea\7z\2\2\u00ea"+
		" \3\2\2\2\u00eb\u00ec\7r\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7q\2\2\u00ee"+
		"\u00ef\7v\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7e\2\2\u00f1\u00f2\7v\2\2"+
		"\u00f2\u00f3\7g\2\2\u00f3\u00f4\7f\2\2\u00f4\"\3\2\2\2\u00f5\u00f6\7t"+
		"\2\2\u00f6\u00f7\7g\2\2\u00f7\u00f8\7v\2\2\u00f8\u00f9\7w\2\2\u00f9\u00fa"+
		"\7t\2\2\u00fa\u00fb\7p\2\2\u00fb$\3\2\2\2\u00fc\u00fd\7v\2\2\u00fd\u00fe"+
		"\7j\2\2\u00fe\u00ff\7k\2\2\u00ff\u0100\7u\2\2\u0100&\3\2\2\2\u0101\u0102"+
		"\7v\2\2\u0102\u0103\7t\2\2\u0103\u0104\7w\2\2\u0104\u0105\7g\2\2\u0105"+
		"(\3\2\2\2\u0106\u0107\7y\2\2\u0107\u0108\7j\2\2\u0108\u0109\7k\2\2\u0109"+
		"\u010a\7n\2\2\u010a\u010b\7g\2\2\u010b*\3\2\2\2\u010c\u010d\t\2\2\2\u010d"+
		",\3\2\2\2\u010e\u010f\4\62;\2\u010f.\3\2\2\2\u0110\u0113\5+\26\2\u0111"+
		"\u0113\t\3\2\2\u0112\u0110\3\2\2\2\u0112\u0111\3\2\2\2\u0113\u0119\3\2"+
		"\2\2\u0114\u0118\5+\26\2\u0115\u0118\5-\27\2\u0116\u0118\t\3\2\2\u0117"+
		"\u0114\3\2\2\2\u0117\u0115\3\2\2\2\u0117\u0116\3\2\2\2\u0118\u011b\3\2"+
		"\2\2\u0119\u0117\3\2\2\2\u0119\u011a\3\2\2\2\u011a\60\3\2\2\2\u011b\u0119"+
		"\3\2\2\2\u011c\u011d\7\f\2\2\u011d\u011e\b\31\2\2\u011e\62\3\2\2\2\u011f"+
		"\u0120\7>\2\2\u0120\64\3\2\2\2\u0121\u0122\7@\2\2\u0122\66\3\2\2\2\u0123"+
		"\u0124\7?\2\2\u01248\3\2\2\2\u0125\u0126\7-\2\2\u0126:\3\2\2\2\u0127\u0128"+
		"\7/\2\2\u0128<\3\2\2\2\u0129\u012a\7,\2\2\u012a>\3\2\2\2\u012b\u012c\7"+
		"\61\2\2\u012c@\3\2\2\2\u012d\u012e\7\'\2\2\u012eB\3\2\2\2\u012f\u0130"+
		"\7\60\2\2\u0130D\3\2\2\2\u0131\u0132\7.\2\2\u0132F\3\2\2\2\u0133\u0134"+
		"\7*\2\2\u0134H\3\2\2\2\u0135\u0136\7+\2\2\u0136J\3\2\2\2\u0137\u0138\7"+
		"}\2\2\u0138L\3\2\2\2\u0139\u013a\7\177\2\2\u013aN\3\2\2\2\u013b\u013c"+
		"\7#\2\2\u013cP\3\2\2\2\u013d\u013e\7=\2\2\u013eR\3\2\2\2\u013f\u0140\7"+
		"?\2\2\u0140\u0141\7?\2\2\u0141T\3\2\2\2\u0142\u0143\7#\2\2\u0143\u0144"+
		"\7?\2\2\u0144V\3\2\2\2\u0145\u0146\7@\2\2\u0146\u0147\7?\2\2\u0147X\3"+
		"\2\2\2\u0148\u0149\7>\2\2\u0149\u014a\7?\2\2\u014aZ\3\2\2\2\u014b\u014c"+
		"\7(\2\2\u014c\u014d\7(\2\2\u014d\\\3\2\2\2\u014e\u014f\7~\2\2\u014f\u0150"+
		"\7~\2\2\u0150^\3\2\2\2\u0151\u0152\4\63;\2\u0152`\3\2\2\2\u0153\u015c"+
		"\7\62\2\2\u0154\u0158\5_\60\2\u0155\u0157\5-\27\2\u0156\u0155\3\2\2\2"+
		"\u0157\u015a\3\2\2\2\u0158\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u015c"+
		"\3\2\2\2\u015a\u0158\3\2\2\2\u015b\u0153\3\2\2\2\u015b\u0154\3\2\2\2\u015c"+
		"b\3\2\2\2\u015d\u015f\5-\27\2\u015e\u015d\3\2\2\2\u015f\u0160\3\2\2\2"+
		"\u0160\u015e\3\2\2\2\u0160\u0161\3\2\2\2\u0161d\3\2\2\2\u0162\u0165\t"+
		"\4\2\2\u0163\u0165\3\2\2\2\u0164\u0162\3\2\2\2\u0164\u0163\3\2\2\2\u0165"+
		"f\3\2\2\2\u0166\u0167\t\5\2\2\u0167\u0168\5e\63\2\u0168\u0169\5c\62\2"+
		"\u0169h\3\2\2\2\u016a\u016b\5c\62\2\u016b\u016c\7\60\2\2\u016c\u016d\5"+
		"c\62\2\u016dj\3\2\2\2\u016e\u0173\5i\65\2\u016f\u0170\5i\65\2\u0170\u0171"+
		"\5g\64\2\u0171\u0173\3\2\2\2\u0172\u016e\3\2\2\2\u0172\u016f\3\2\2\2\u0173"+
		"\u0176\3\2\2\2\u0174\u0177\t\6\2\2\u0175\u0177\3\2\2\2\u0176\u0174\3\2"+
		"\2\2\u0176\u0175\3\2\2\2\u0177l\3\2\2\2\u0178\u0179\t\7\2\2\u0179n\3\2"+
		"\2\2\u017a\u017c\5m\67\2\u017b\u017a\3\2\2\2\u017c\u017d\3\2\2\2\u017d"+
		"\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017ep\3\2\2\2\u017f\u0180\7\62\2\2"+
		"\u0180\u0184\7z\2\2\u0181\u0182\7\62\2\2\u0182\u0184\7Z\2\2\u0183\u017f"+
		"\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0186\5o8\2\u0186"+
		"\u0187\7\60\2\2\u0187\u0188\5o8\2\u0188\u0189\t\b\2\2\u0189\u018a\5e\63"+
		"\2\u018a\u018d\5c\62\2\u018b\u018e\t\6\2\2\u018c\u018e\3\2\2\2\u018d\u018b"+
		"\3\2\2\2\u018d\u018c\3\2\2\2\u018er\3\2\2\2\u018f\u0192\5k\66\2\u0190"+
		"\u0192\5q9\2\u0191\u018f\3\2\2\2\u0191\u0190\3\2\2\2\u0192t\3\2\2\2\u0193"+
		"\u0194\n\t\2\2\u0194v\3\2\2\2\u0195\u019d\7$\2\2\u0196\u019c\5u;\2\u0197"+
		"\u0198\7^\2\2\u0198\u019c\7$\2\2\u0199\u019a\7^\2\2\u019a\u019c\7^\2\2"+
		"\u019b\u0196\3\2\2\2\u019b\u0197\3\2\2\2\u019b\u0199\3\2\2\2\u019c\u019f"+
		"\3\2\2\2\u019d\u019b\3\2\2\2\u019d\u019e\3\2\2\2\u019e\u01a0\3\2\2\2\u019f"+
		"\u019d\3\2\2\2\u01a0\u01a1\7$\2\2\u01a1x\3\2\2\2\u01a2\u01ab\7$\2\2\u01a3"+
		"\u01aa\5u;\2\u01a4\u01aa\5\61\31\2\u01a5\u01a6\7^\2\2\u01a6\u01aa\7$\2"+
		"\2\u01a7\u01a8\7^\2\2\u01a8\u01aa\7^\2\2\u01a9\u01a3\3\2\2\2\u01a9\u01a4"+
		"\3\2\2\2\u01a9\u01a5\3\2\2\2\u01a9\u01a7\3\2\2\2\u01aa\u01ad\3\2\2\2\u01ab"+
		"\u01a9\3\2\2\2\u01ab\u01ac\3\2\2\2\u01ac\u01ae\3\2\2\2\u01ad\u01ab\3\2"+
		"\2\2\u01ae\u01af\7$\2\2\u01afz\3\2\2\2\u01b0\u01b1\7\61\2\2\u01b1\u01b2"+
		"\7,\2\2\u01b2\u01b6\3\2\2\2\u01b3\u01b5\13\2\2\2\u01b4\u01b3\3\2\2\2\u01b5"+
		"\u01b8\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7\u01b9\3\2"+
		"\2\2\u01b8\u01b6\3\2\2\2\u01b9\u01ba\7,\2\2\u01ba\u01bb\7\61\2\2\u01bb"+
		"\u01bc\3\2\2\2\u01bc\u01bd\b>\3\2\u01bd|\3\2\2\2\u01be\u01bf\7\61\2\2"+
		"\u01bf\u01c0\7\61\2\2\u01c0\u01c4\3\2\2\2\u01c1\u01c3\13\2\2\2\u01c2\u01c1"+
		"\3\2\2\2\u01c3\u01c6\3\2\2\2\u01c4\u01c5\3\2\2\2\u01c4\u01c2\3\2\2\2\u01c5"+
		"\u01c9\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c7\u01ca\5\61\31\2\u01c8\u01ca\7"+
		"\2\2\3\u01c9\u01c7\3\2\2\2\u01c9\u01c8\3\2\2\2\u01ca\u01cb\3\2\2\2\u01cb"+
		"\u01cc\b?\4\2\u01cc~\3\2\2\2\u01cd\u01d2\5\u0081A\2\u01ce\u01d2\t\n\2"+
		"\2\u01cf\u01d2\5{>\2\u01d0\u01d2\5}?\2\u01d1\u01cd\3\2\2\2\u01d1\u01ce"+
		"\3\2\2\2\u01d1\u01cf\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3"+
		"\u01d4\b@\5\2\u01d4\u0080\3\2\2\2\u01d5\u01d6\t\13\2\2\u01d6\u01d7\bA"+
		"\6\2\u01d7\u0082\3\2\2\2\u01d8\u01da\5+\26\2\u01d9\u01d8\3\2\2\2\u01da"+
		"\u01db\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01dc\3\2\2\2\u01dc\u01de\3\2"+
		"\2\2\u01dd\u01df\5-\27\2\u01de\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0"+
		"\u01de\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e3\3\2\2\2\u01e2\u01e4\7\60"+
		"\2\2\u01e3\u01e2\3\2\2\2\u01e4\u01e5\3\2\2\2\u01e5\u01e3\3\2\2\2\u01e5"+
		"\u01e6\3\2\2\2\u01e6\u01e8\3\2\2\2\u01e7\u01e9\7/\2\2\u01e8\u01e7\3\2"+
		"\2\2\u01e9\u01ea\3\2\2\2\u01ea\u01e8\3\2\2\2\u01ea\u01eb\3\2\2\2\u01eb"+
		"\u01ec\3\2\2\2\u01ec\u01ed\7a\2\2\u01ed\u01ef\3\2\2\2\u01ee\u01d9\3\2"+
		"\2\2\u01ef\u01f0\3\2\2\2\u01f0\u01ee\3\2\2\2\u01f0\u01f1\3\2\2\2\u01f1"+
		"\u0084\3\2\2\2\u01f2\u01f3\7%\2\2\u01f3\u01f4\7k\2\2\u01f4\u01f5\7p\2"+
		"\2\u01f5\u01f6\7e\2\2\u01f6\u01f7\7n\2\2\u01f7\u01f8\7w\2\2\u01f8\u01f9"+
		"\7f\2\2\u01f9\u01fa\7g\2\2\u01fa\u01fe\3\2\2\2\u01fb\u01fd\7\"\2\2\u01fc"+
		"\u01fb\3\2\2\2\u01fd\u0200\3\2\2\2\u01fe\u01fc\3\2\2\2\u01fe\u01ff\3\2"+
		"\2\2\u01ff\u0201\3\2\2\2\u0200\u01fe\3\2\2\2\u0201\u0202\7$\2\2\u0202"+
		"\u0203\5\u0083B\2\u0203\u0204\7$\2\2\u0204\u0086\3\2\2\2\36\2\u0112\u0117"+
		"\u0119\u0158\u015b\u0160\u0164\u0172\u0176\u017d\u0183\u018d\u0191\u019b"+
		"\u019d\u01a9\u01ab\u01b6\u01c4\u01c9\u01d1\u01db\u01e0\u01e5\u01ea\u01f0"+
		"\u01fe\7\3\31\2\3>\3\3?\4\3@\5\3A\6";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}