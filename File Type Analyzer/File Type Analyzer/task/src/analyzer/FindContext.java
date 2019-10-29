package analyzer;

class FindContext{
    private Strategy strategy;

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public String find(String path, String pattern, String result){
        return this.strategy.find(path, pattern, result);
    }
}
