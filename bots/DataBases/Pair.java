package bots.DataBases;

//?: when do u doing to use it ?
//ANSWER: every time i want to save pair of informatin in diffrent types of classe, look at Analyze.Attack.CanAttackOption
public class Pair<T, K> {
    private T first;
    private K second;

    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public K getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
