enum AddressByte {
    ///字节码
    CONST,
    STORE,

    ADD,//加
    SUB,//减
    MUL,//乘
    DIV,//除

    GR,//大于
    GR_EQ,//大于等于
    LE,//小于
    LE_EQ,//小于等于
    EQ,//等于
    NOT_EQ,//不等于

    K_MINUS,//

    JMP,//跳转
    JMP_F,//假跳转

    L_T,//逻辑真
    L_F//逻辑假
}
