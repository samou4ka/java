package readability;

public class CLIndex implements Strategy {
    public double calculateIndex(double... cnt) {
        double cntCharacters = cnt[2];
        double cntWords = cnt[1];
        double cntSentences = cnt[0];

        double L = cntCharacters * 100 / cntWords;
        double S = cntSentences * 100 / cntWords;
        double score = 0.0588 * L - 0.296 * S - 15.8;
        String age = this.defineAges(score);
        System.out.println(String.format("Coleman–Liau index: %.2f (about %s year olds).", score, age));

        return "Undefined age".equals(age) ? 0 : Double.parseDouble(age);
    }
}
