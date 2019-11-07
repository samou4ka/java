package readability;

public class FKIndex implements Strategy {
    public double calculateIndex(double... cnt) {
        double cntSyllables = cnt[2];
        double cntWords = cnt[1];
        double cntSentences = cnt[0];

        double score = 0.39 * cntWords / cntSentences + 11.8 * cntSyllables / cntWords - 15.59;
        String age = this.defineAges(score);
        System.out.println(String.format("Flesch–Kincaid readability tests: %.2f (about %s year olds).", score, age));

        return "Undefined age".equals(age) ? 0 : Double.parseDouble(age);
    }
}
