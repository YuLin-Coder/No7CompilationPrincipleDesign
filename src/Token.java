class Token {
    final TokenType type;//token类型
    final String lexeme;//Token内容，TokenType类中的注释
    final Object literal;//列表存入
    final int line;//token所在源码行号

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
//打入字符串
//语法
//将字符序列转换为标记（token）序列的过程