import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> res = new HashMap<>();
        for (char c : inputSymbols) {
            res.merge(c, 1, Integer::sum);
        }
        return res;
    }
    public static void main(String[] args) {
        char[] chars = FileUtils.readFile(args[0]);
        Map<Character, Integer> freqtable = buildFrequencyTable(chars);
        BinaryTrie bTrie = new BinaryTrie(freqtable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bTrie);
        ow.writeObject(chars.length);
        Map<Character, BitSequence> lookupTable = bTrie.buildLookupTable();
        List<BitSequence> seqs = new ArrayList<>();
        for (char c : chars) {
            seqs.add(lookupTable.get(c));
        }
        BitSequence seq = BitSequence.assemble(seqs);
        ow.writeObject(seq);
    }
}
