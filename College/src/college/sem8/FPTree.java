package college.sem8;

import java.util.*;

/**
 * Created by Yue on 19-Mar-16.
 */
public class FPTree {

    private static String items[] = {"EADB", "DACEB", "CABE", "BAD", "D", "DB", "ADE", "BC"};
    private static String sortedItems[];

    private static Pair[] counts;

    public static void initCounter() {
        counts = new Pair[5];

        int x = 0;
        for(char c = 'A'; c < 'F'; c++) {
            counts[x] = new Pair();
            counts[x].c = c;
            counts[x++].count = 0;
        }

        int cindex = 0;

        for(String item : items) {
            char[] data = item.toCharArray();

            for(char c : data) {
                cindex = ((int) c) - 65;
                counts[cindex].count++;
            }
        }

        Arrays.sort(counts);
    }

    public static void initSortedItems() {
        ArrayList<String> list = new ArrayList<>();
        for(String x : items) {

            StringBuilder sb = new StringBuilder();

            char[] data = x.toCharArray();
            Pair[] pairs = new Pair[data.length];

            int index = 0;
            for(char c : data) {
                pairs[index] = new Pair();
                pairs[index].c = c;

                for(Pair p : counts) {
                    if(p.c == c) {
                        pairs[index].count = p.count;
                        break;
                    }
                }

                index++;
            }

            Arrays.sort(pairs);

            for (Pair p : pairs)
                sb.append(p.c);

            list.add(sb.toString());
        }
        sortedItems = list.toArray(new String[0]);
    }

    private static class Pair implements Comparable<Pair> {

        char c;
        int count;

        @Override
        public int compareTo(Pair o) {
            if(this.count == o.count)
                return this.c - o.c;
            return -(this.count - o.count);
        }

        @Override
        public String toString() {
            return "{" + c + ", " + count + "}";
        }

        @Override
        public boolean equals(Object obj) {
            Pair t = null;
            if(obj instanceof Pair)
                t = (Pair) obj;

            if(t == null)
                return false;
            return t.c == this.c;
        }
    }

    public static class TrieNode {
        char c;
        HashMap<Character, TrieNode> children = new HashMap<>();
        boolean isLeaf;
        int count;

        public TrieNode() {}

        public TrieNode(char c){
            this.c = c;
            this.count = 1;
        }
    }

    public static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            HashMap<Character, TrieNode> children = root.children;

            for(int i=0; i<word.length(); i++){
                char c = word.charAt(i);

                TrieNode t;
                if(children.containsKey(c)){
                    t = children.get(c);
                    t.count++;
                }else{
                    t = new TrieNode(c);
                    children.put(c, t);
                }

                children = t.children;

                //set leaf node
                if(i==word.length()-1)
                    t.isLeaf = true;
            }
        }

        public boolean search(String word) {
            TrieNode t = searchNode(word);

            if(t != null && t.isLeaf)
                return true;
            else
                return false;
        }

        public TrieNode searchNode(String str){
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;

            System.out.print("Root -> ");

            for(int i=0; i<str.length(); i++){
                char c = str.charAt(i);
                if(children.containsKey(c)){
                    t = children.get(c);
                    children = t.children;

                    if(i < str.length() - 1)
                        System.out.print("(" + t.c + ", " + t.count + ") -> ");
                    else
                        System.out.print("(" + t.c + ", " + t.count + ")");
                }else{
                    return null;
                }
            }
            System.out.println();
            return t;
        }

    }

    public static void main(String[] args) {
        initCounter();
        initSortedItems();
        Trie trie = new Trie();

        for(String sitems : sortedItems) {
            trie.insert(sitems);
        }

        for(String check : sortedItems) {
            System.out.println("Checking " + check + ".");
            boolean exists = trie.search(check);
            System.out.println("Path exists : " + exists + "\n");
        }
    }


}
