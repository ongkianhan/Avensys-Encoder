import java.util.*;

public class Enigma extends Cipher{


    HashMap<Integer, Character> ref_table = new HashMap<>();
    // https://stackoverflow.com/questions/12385284/how-to-select-a-random-key-from-a-hashmap-in-java
    // can use this for optimization in the future
    char offset;
    enum Mode {
        encode,
        decode
    }

    public int getValue_offset(char letter, int offset, HashMap<Integer, Character> table, Mode mode )
    {
        int new_pos = -1;
        for (Map.Entry<Integer, Character> entry : table.entrySet())
        {
            if (Objects.equals(letter, entry.getValue()))
            {
                new_pos = entry.getKey();
            }
        }
        Mode curr_mode = mode;
        if (new_pos == -1)
        {
            return -1;
        }

        //

        if (curr_mode == Mode.encode)
        {
            new_pos = (new_pos - offset + table.size()) % (table.size());
        }

        else if (curr_mode == Mode.decode)
        {
            new_pos = (new_pos + offset + table.size()) % (table.size());
        }

        return new_pos;
    }

    public Enigma() {
        setRef_table(getRef_table());


        Random rand = new Random();
        int randomNum = rand.nextInt(getRef_table().size() + 1);
        char offset = getRef_table().get(randomNum);


        setOffset(offset);



        // testing for cases B and F
        //setOffset('0');
        //System.out.printf("The offset for this randomization is: %c, %d\n", getOffset(), getValue_offset(offset, 0, getRef_table(), Mode.encode));
    }


    @Override
    public String encode(String plainText) throws IllegalArgumentException{

        if ( plainText == null)
        {
            throw new IllegalArgumentException("Input is not a string!\n");
        }

        char offset = getOffset();
        int offset_value = getValue_offset(offset, 0, getRef_table(), Mode.encode);
        // stringbuilder is faster than string
        StringBuilder sb = new StringBuilder();
        sb.append(offset);

        for (int i = 0; i < plainText.length(); i++)
        {
            char curr = plainText.charAt(i);
            int pos = getValue_offset(curr, offset_value, getRef_table(), Mode.encode);
            if (pos == -1)
            {
                sb.append(curr);
                continue;
            }
            sb.append(getRef_table().get(pos));
        }

        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public String decode(String encodedText) throws IllegalArgumentException {

        if ( encodedText == null)
        {
            throw new IllegalArgumentException("Input is not a string!\n");
        }

        char offset = encodedText.charAt(0);
        int offset_value = getValue_offset(offset, 0, getRef_table(), Mode.encode);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < encodedText.length(); i++)
        {
            char curr = encodedText.charAt(i);
            int pos = getValue_offset(curr, offset_value, getRef_table(), Mode.decode);
            if (pos == -1)
            {
                sb.append(curr);
                continue;
            }
            sb.append(getRef_table().get(pos));
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public void setOffset(char offset) {
        this.offset = offset;
    }

    public char getOffset(){
        return this.offset;
    }

    // initializes ref_table
    private void setRef_table(HashMap<Integer, Character> ref_table)
    {
        // index 0 - 25, A - Z, ASCII 65 - 90
        int index = 0;
        for (int i = 0; i < 26; i++)
        {
            ref_table.put(index, (char)('A' + i));
            index++;
        }
        // index 26 - 35, 0 - 9, ASCII 48 - 57
        for (int i = 0; i < 10; i ++)
        {
            ref_table.put( index, (char) ('0' + i));
            index++;
        }
        // index 36 - 43, ( - /, ASCII 40 - 47
        for (int i = 0; i < 8; i++)
        {
            ref_table.put( index, (char) ('(' + i));
            index++;
        }

    }

    private HashMap<Integer, Character> getRef_table(){
        return this.ref_table;
    }

    public static void main(String[] args)
    {
        Enigma test = new Enigma();
        test.encode("HELLO WORLD");
        test.decode("0ZW336 ,693V");
        //System.out.println(test.getRef_table());
    }
}
