import java.util.ArrayList;
import java.util.List;

class Parser {
//语法解析器
    private final List<Token> tokens;
    private final List<Groove> grooves = new ArrayList<>();

    private int position;
    private int offset;

    private enum IfType {IF, IFF}

    private IfType syntaxIfType = IfType.IF;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Groove> resolver() {
        while (position < tokens.size()) {
            //如果当前的position+1 是":" 则执行if
            if (peek(TokenType.COLON)) {
                if (current().lexeme.length() == 3) {
                    offset = Integer.parseInt(current().lexeme.substring(1, 3));
                } else {
                    offset = current().lexeme.charAt(current().lexeme.length() - 1) - 48;
                }
                position += 2;
            }
            statement();
            position++;
        }
        return grooves;
    }

    private void statement() {
        Token current = tokens.get(position);

        switch (current.type) {
            case IDENTIFIER:
                if (peek(TokenType.EQUAL)) {
                    Token name = current();

                    position += 2;
                    expression();

                    emit(AddressByte.STORE, name.lexeme);
                }
                break;
            case IF:
            case IFF:
            case IFT:
                position++;

                expression();
                position++;

                if (current().type != TokenType.GOTO) {
                    throw new RuntimeException("语句丢失某个分支.");
                }

                switch (current.type) {
                    case IF:
                        syntaxIfType = IfType.IF;
                        break;
                    case IFF:
                        syntaxIfType = IfType.IFF;
                        break;
                }

                statement();

                break;
            case GOTO:
                position++;

                int jumpOffset;

                if (current().lexeme.length() == 3) {
                    jumpOffset = Integer.parseInt(current().lexeme.substring(1, 3));
                } else {
                    jumpOffset = current().lexeme.charAt(current().lexeme.length() - 1) - 48;
                }

                if (syntaxIfType == IfType.IFF) {
                    emit(AddressByte.JMP_F, jumpOffset);
                } else {
                    emit(AddressByte.JMP, jumpOffset);
                }

                syntaxIfType = IfType.IF;

                break;
        }
    }

    private void expression() {
        if (peek(TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH, TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS,
                TokenType.LESS_EQUAL, TokenType.BANG_EQ, TokenType.EQUAL_EQUAL)) {
            position++;

            Token left = previous();
            Token operator = current();

            position++;

            Token right = current();

            if (left.type == TokenType.NUMBER) emit(AddressByte.CONST, left.literal);
            else emit(AddressByte.CONST, left.lexeme);

            if (right.type == TokenType.NUMBER) emit(AddressByte.CONST, right.literal);
            else emit(AddressByte.CONST, right.lexeme);

            switch (operator.type) {
                case PLUS:
                    emit(AddressByte.ADD);
                    break;
                case MINUS:
                    emit(AddressByte.SUB);
                    break;
                case STAR:
                    emit(AddressByte.MUL);
                    break;
                case SLASH:
                    emit(AddressByte.DIV);
                    break;
                case GREATER:
                    emit(AddressByte.GR);
                    break;
                case LESS:
                    emit(AddressByte.LE);
                    break;
                case GREATER_EQUAL:
                    emit(AddressByte.GR_EQ);
                    break;
                case LESS_EQUAL:
                    emit(AddressByte.LE_EQ);
                    break;
                case EQUAL_EQUAL:
                    emit(AddressByte.EQ);
                    break;
                case BANG_EQ:
                    emit(AddressByte.NOT_EQ);
                    break;
            }
            return;
        }

        switch (current().type) {
            case TRUE:
                emit(AddressByte.L_T);
                break;
            case FALSE:
                emit(AddressByte.L_F);
                break;
            case K_MINUS:
                if (peek(TokenType.IDENTIFIER)) {
                    position++;

                    Token name = current();

                    emitValue(name.lexeme);
                    emit(AddressByte.K_MINUS);
                }
                break;
            default:
                emit(AddressByte.CONST, current().literal == null ? current().lexeme : current().literal);
        }
    }

    private boolean peek(TokenType... type) {
        for (TokenType x : type) {
            if (tokens.get(position + 1).type == x) {
                return true;
            }
        }
        return false;
    }

    private Token current() {
        return tokens.get(position);
    }

    private Token previous() {
        return tokens.get(position - 1);
    }

    private void emit(AddressByte x) {
        if (grooves.contains(new Groove(offset))) {
            grooves.get(grooves.indexOf(new Groove(offset))).addOpCode(x);
        } else {
            grooves.add(new Groove(offset).addOpCode(x));
        }
    }

    private void emitValue(Object obj) {
        if (Jvm.values.contains(new Value(offset))) {

            Jvm.values.get(Jvm.values.indexOf(new Value(offset))).values.add(obj);

        } else {
            Value value = new Value(offset);
            value.values.add(obj);

            Jvm.values.add(value);
        }
    }

    private void emit(AddressByte x, Object o) {
        emit(x);
        emitValue(o);
    }
}
