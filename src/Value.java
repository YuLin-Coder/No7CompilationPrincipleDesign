import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Value {
    int k;
    int i;

    List<Object> values = new ArrayList<>();

    Value(int k) {
        this.k = k;
    }

    @SuppressWarnings("检查不到参数类")
    @Override
    public boolean equals(Object o) {
        return k == ((Value) o).k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(k, values);
    }

    @Override
    public String toString() {
        return "Value{" +
                "k=" + k +
                ", i=" + i +
                ", values=" + values +
                '}';
    }
}
//值类型，用于虚拟机执行内的