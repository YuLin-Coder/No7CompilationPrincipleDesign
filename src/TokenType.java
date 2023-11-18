enum TokenType {
    PLUS,//加号
    MINUS, //减号
    SLASH,// /
    STAR,

    LBRACKET,//括号
    RBRACKET,

    COLON,//冒号：

    EQUAL,
    EQUAL_EQUAL,
    BANG_EQ,//！=
    BANG,//！
    GREATER,
    GREATER_EQUAL,
    LESS,
    LESS_EQUAL,

    IDENTIFIER,//变量名
    NUMBER,//数值

    TRUE,
    FALSE,
    IF,
    GOTO,

    IFT,//逻辑真
    IFF,//逻辑假
    K_MINUS,
    EOF//结尾
}
//枚举类型