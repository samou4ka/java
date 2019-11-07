package readability;

public class SMOGIndex implements Strategy {
    public double calculateIndex(double... cnt) {
        double cntPolysyllables = cnt[1];
        double cntSentences = cnt[0];

        double score = 1.043 * Math.sqrt(cntPolysyllables * 30 / cntSentences) + 3.1291;
        String age = this.defineAges(score);
        System.out.println(String.format("Simple Measure of Gobbledygook: %.2f (about %s year olds).", score, age));

        return "Undefined age".equals(age) ? 0 : Double.parseDouble(age);
    }
}
