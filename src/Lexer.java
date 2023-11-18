import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//�ʷ�������
class Lexer {
    //�ؼ����ֵ䣬ÿ�δ�Դ����ȡ�����ź�Ҫ�����ж��Ƿ�Ϊ�ؼ���
    private static final Map<String, TokenType> keywords;
    //��ʼ��
    static {
        keywords = new HashMap<>();///��¼�˵�ǰ�ؼ��ʼ���
        keywords.put("true", TokenType.TRUE);
        keywords.put("false", TokenType.FALSE);
        keywords.put("if", TokenType.IF);
        keywords.put("goto", TokenType.GOTO);
        keywords.put("iftrue", TokenType.IFT);
        keywords.put("iffalse", TokenType.IFF);
        keywords.put("minus", TokenType.K_MINUS);
    }

    private final String source;
    //����������Ҫ���ص�Token
    private final List<Token> tokens = new ArrayList<>();
    //��Դ���л�ȡ�ַ�������
    private int start = 0;
    private int current = 0;
    //��¼Token��Դ���е��к�
    private int line = 1;

    Lexer(String source) {
        this.source = source;
    }

    List<Token> scanTokens() throws Exception {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(TokenType.EOF, "", null, line));
        return tokens;
    }

    private void scanToken() throws Exception {
        char c = advance();
        switch (c) {
            case ':':
                addToken(TokenType.COLON);
                break;
            case '[':
                addToken(TokenType.LBRACKET);
                break;
            case ']':
                addToken(TokenType.RBRACKET);
                break;
            case '-':
                addToken(TokenType.MINUS);
                break;
            case '+':
                addToken(TokenType.PLUS);
                break;
            case '*':
                addToken(TokenType.STAR);
                break;
            case '=':
                addToken(match('=') ? TokenType.EQUAL_EQUAL : TokenType.EQUAL);
                break;
            case '<':
                addToken(match('=') ? TokenType.LESS_EQUAL : TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? TokenType.GREATER_EQUAL : TokenType.GREATER);
                break;
            case '/':
                if (match('/')) {
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(TokenType.SLASH);
                }
                break;
            case '!':
                addToken(match('=') ? TokenType.BANG_EQ : TokenType.BANG);
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            default:
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    throw new Exception("Unexpected character: " + c);
                }
                break;
        }
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);

        TokenType type = keywords.get(text);
        if (type == null) type = TokenType.IDENTIFIER;
        addToken(type);
    }

    private void number() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        addToken(TokenType.NUMBER,
                Double.parseDouble(source.substring(start, current)));
    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}