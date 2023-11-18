import java.util.ArrayList;
import java.util.List;

//每个槽 类似于L2，k=2
class Groove {
    int k;

    List<AddressByte> bytes = new ArrayList<>();

    Groove(int k) {
        this.k = k;
    }

    //添加操作码
    public Groove addOpCode(AddressByte x) {
        bytes.add(x);
        return this;
    }

    @SuppressWarnings("检查不到参数类")
    @Override
    public boolean equals(Object o) {
        return k == ((Groove) o).k;
    }

    @Override
    public String toString() {
        return "Groove{" +
                "k=" + k +
                ", bytes=" + bytes +
                '}';
    }
}