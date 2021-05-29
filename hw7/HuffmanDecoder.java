public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie bTrie = (BinaryTrie) or.readObject();
        int length = (int) or.readObject();
        BitSequence seq = (BitSequence) or.readObject();
        char[] chars = new char[length];
        Match m;
        for (int i = 0; i < length; i++) {
            m = bTrie.longestPrefixMatch(seq);
            chars[i] = m.getSymbol();
            seq = seq.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], chars);
    }
}
