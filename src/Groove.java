import java.util.ArrayList;
import java.util.List;

//ÿ���� ������L2��k=2
class Groove {
    int k;

    List<AddressByte> bytes = new ArrayList<>();

    Groove(int k) {
        this.k = k;
    }

    //��Ӳ�����
    public Groove addOpCode(AddressByte x) {
        bytes.add(x);
        return this;
    }

    @SuppressWarnings("��鲻��������")
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