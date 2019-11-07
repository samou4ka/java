package readability;

public class ContextIndex {
    Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public double calculateIndex(double... cnt) {
        return this.strategy.calculateIndex(cnt);
    }

}
