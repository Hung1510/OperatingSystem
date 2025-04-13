public interface ReplacementPolicy {
    public Result refer(int value);

    public void remove(int value);

    public class Result {
        private boolean found;
        private int value;
        private int position;
        private int replaceValue;

        public Result(boolean found, int value, int position, int replaceValue) {
            this.found = found;
            this.value = value;
            this.position = position;
            this.replaceValue = replaceValue;
        }

        public boolean isFound() {
            return found;
        }

        public void setFound(boolean found) {
            this.found = found;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getReplaceValue() {
            return replaceValue;
        }

        public void setReplaceValue(int replaceValue) {
            this.replaceValue = replaceValue;
        }

    }
}
