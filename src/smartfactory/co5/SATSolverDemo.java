package smartfactory.co5;

public class SATSolverDemo {
    public static class EvaluationResult {
        public boolean c1, c2, c3, c4;
        public boolean isSatisfied;

        public EvaluationResult(boolean c1, boolean c2, boolean c3, boolean c4, boolean isSatisfied) {
            this.c1 = c1;
            this.c2 = c2;
            this.c3 = c3;
            this.c4 = c4;
            this.isSatisfied = isSatisfied;
        }
    }

    public static EvaluationResult evaluate(boolean x1, boolean x2, boolean x3, boolean x4) {
        boolean c1 = x1 || x2 || !x3;
        boolean c2 = !x1 || x3 || x4;
        boolean c3 = !x2 || !x3 || x4;
        boolean c4 = x1 || !x4 || x2;
        boolean formula = c1 && c2 && c3 && c4;
        return new EvaluationResult(c1, c2, c3, c4, formula);
    }
}
