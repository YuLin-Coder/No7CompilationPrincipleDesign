class Token {
    final TokenType type;//token����
    final String lexeme;//Token���ݣ�TokenType���е�ע��
    final Object literal;//�б����
    final int line;//token����Դ���к�

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        if (literal != null) {
            return type + " " + lexeme + " " + literal + " ";
        }
        return type + " " + lexeme + " ";
    }
}
//�����ַ���
//�﷨
//���ַ�����ת��Ϊ��ǣ�token�����еĹ���