import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//ÐéÄâ»ú
class Jvm {

    private final List<Groove> grooves;

    private final List<Object> env = new ArrayList<>();
    private final HashMap<String, Integer> names = new HashMap<>();

    static List<Value> values = new ArrayList<>();

    private int offset;

    Jvm(List<Groove> grooves) {
        this.grooves = grooves;
    }

    public void execute() {
        for (Groove groove : grooves) {
            runBytes(groove.k);
        }

        System.out.println("names: " + names);
        System.out.println("env: " + env + "\n");

        names.forEach((x, y) -> System.out.format("%-5s -> %s\n", x, env.get(y)));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private void runBytes(int k) {
        offset = k;

        grooves.get(grooves.indexOf(new Groove(k))).bytes.forEach(x -> {
            switch (x) {
                case CONST: {
                    Object obj = eachValue();

                    if (obj instanceof String) {
                        env.add(getIdent((String) obj));
                    } else {
                        env.add(obj);
                    }
                    break;
                }
                case L_F:
                case L_T:
                    env.add(x == AddressByte.L_T ? "true" : "false");
                    break;
                case STORE:
                    names.put((String) eachValue(), env.size() - 1);
                    break;
                case K_MINUS:
                    env.add((Double) env.get(names.get(eachValue())) - 1);
                    break;
                case ADD:
                case SUB:
                case MUL:
                case DIV:
                case GR:
                case LE:
                case GR_EQ:
                case LE_EQ: {

                    double right = (double) env.get(env.size() - 1);
                    double left = (double) env.get(env.size() - 2);

                    switch (x) {
                        case ADD:
                            env.add(left + right);
                            break;
                        case SUB:
                            env.add(left - right);
                            break;
                        case MUL:
                            env.add(left * right);
                            break;
                        case DIV:
                            env.add(left / right);
                            break;
                        case GR:
                            env.add(left > right);
                            break;
                        case LE:
                            env.add(left < right);
                            break;
                        case GR_EQ:
                            env.add(left >= right);
                            break;
                        case LE_EQ:
                            env.add(left <= right);
                            break;
                        case EQ:
                            env.add(left == right);
                            break;
                        case NOT_EQ:
                            env.add(left != right);
                            break;
                    }
                    break;
                }
                case JMP:
                case JMP_F:
                    if (env.get(env.size() - 1) instanceof Boolean) {
                        switch (x) {
                            case JMP:
                                if ((boolean) env.get(env.size() - 1)) {
                                    runBytes((Integer) eachValue());
                                } else {
                                    eachValue();
                                }
                                break;
                            case JMP_F:
                                if (!(boolean) env.get(env.size() - 1)) {
                                    runBytes((Integer) eachValue());
                                } else {
                                    eachValue();
                                }
                                break;
                        }
                    } else {
                        runBytes((Integer) eachValue());
                    }
                    break;
            }
        });
    }

    private Object eachValue() {
        Value x = values.get(values.indexOf(new Value(offset)));
        //
        return x.values.get(x.i++);
    }

    private Object getIdent(String name) {
        if (!names.containsKey(name)) {
            throw new RuntimeException("undefined identifier: " + name);
        }
        return env.get(names.get(name));
    }
}
