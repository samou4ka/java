package readability;

public class ARIndex implements Strategy {
    public double calculateIndex(double... cnt) {
        double cntCharacters = cnt[2];
        double cntWords = cnt[1];
        double cntSentences = cnt[0];

        double score = 4.71 * cntCharacters / cntWords + 0.5 * cntWords / cntSentences - 21.43;
        String age = this.defineAges(score);

        System.out.println(String.format("Automated Readability Index: %.2f (about %s year olds).", score, age));

        return "Undefined age".equals(age) ? 0 : Double.parseDouble(age);
    }
}
