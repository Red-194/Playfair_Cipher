//The program accepts only letters, digits and whitespaces as input. Entering any other may produce incorrect output or cause the program to crash.
import java.util.*;
public class Playfair
{
    static String key;
    char ch[]=new char[36];
    int indexA[]=new int[2], indexB[]=new int[2];
    static Scanner sc;

    public static void main(String args[])
    {
        sc=new Scanner(System.in);
        Playfair pf=new Playfair();
        String choice="";
        
        // The main code is put inside a do-while loop for ease of reuse, instead of calling the program again and again
        do{
            System.out.println("Enter the text: ");
            String st=(sc.nextLine()+" ").toLowerCase();
            System.out.println("Enter the key: ");
            key=sc.nextLine().toLowerCase();
            System.out.println("Encrypt/Decrypt");
            choice=sc.nextLine().toLowerCase();
            String wrd="",result="";
            char ch;
        
            pf.printGrid();

            for(int i=0;i<st.length();i++)
            {
                ch=st.charAt(i);
                if(ch!=' ')
                wrd+=ch;
                else if(ch==' ')
                {
                    if(choice.equals("encrypt"))
                    {
                        result+=pf.encrypt(wrd)+" ";
                        wrd="";
                    }
                    else if(choice.equals("decrypt"))
                    {
                        result+=pf.decrypt(wrd)+" ";
                        wrd="";
                    }
                }
            }

            System.out.println("The key is: "+ key);
            System.out.println("The encrypted/decrypted text is: "+result);
            System.out.println("-----------------------------------------------------------");
            System.out.println("Would you like to encrypt/decrypt another message? (Yes/No)");
            choice = sc.nextLine();

        }while(choice.equalsIgnoreCase("yes"));

        System.out.println("Thank you for using the program!");
        sc.close();
    }

    // This function encrypts the plaintext into the required ciphertext
    public String encrypt(String st) {

        String store = digram(st);
        store = store.replaceAll(" ", "");
        String di = "";
        char ch1, ch2;
        int x1, y1, x2, y2;
        StringBuilder res = new StringBuilder();
        int a, b;
        int max = 0;
        int len = store.length();
        for (int i = 2; i < len + 2; i += 2) {
            di = store.substring(i - 2, i);
            elementIndices(di.charAt(0), di.charAt(1));
            a = index(di.charAt(0));
            b = index(di.charAt(1));
            x1 = indexA[0];
            y1 = indexA[1];
            x2 = indexB[0];
            y2 = indexB[1];
            if (x1 == x2) { // same row
                max = 6 * x1 + 6;
                ch1 = ch[((y1 + 1) > 5) ? ((a + 1) - max) + 6 * x1 : (a + 1)];
                ch2 = ch[((y2 + 1) > 5) ? ((b + 1) - max) + 6 * x2 : (b + 1)];
                res.append(ch1).append(ch2);
            } else if (y1 == y2) { // same column
                max = 30;
                ch1 = ch[((x1 + 1) > 5) ? (a - max) : a + 6];
                ch2 = ch[((x2 + 1) > 5) ? (b - max) : b + 6];
                res.append(ch1).append(ch2);
            } else { // different row and column
                ch1 = ch[6 * x1 + y2];
                ch2 = ch[6 * x2 + y1];
                res.append(ch1).append(ch2);
            }
        }
        return res.toString();
    }

    // This functiond decrypts the ciphertext to plaintext
    public String decrypt(String st) {

        String store = digram(st);
        store = store.replaceAll(" ", "");
        String di = "";
        char ch1, ch2;
        int x1, y1, x2, y2;
        StringBuilder res = new StringBuilder();
        int a, b;
        int len = store.length();
        for (int i = 2; i < len + 2; i += 2) {
        di = store.substring(i - 2, i);
        elementIndices(di.charAt(0), di.charAt(1));
        a = index(di.charAt(0));
        b = index(di.charAt(1));
        x1 = indexA[0];
        y1 = indexA[1];
        x2 = indexB[0];
        y2 = indexB[1];
        if (x1 == x2) {
            ch1 = ch[((y1 - 1) < 0) ? 6 * x1 + 5 : a - 1];
            ch2 = ch[((y2 - 1) < 0) ? 6 * x2 + 5 : b - 1];
            res.append(ch1).append(ch2);
            } else if (y1 == y2) {
            ch1 = ch[((x1 - 1) < 0) ? 30 + y1 : a - 6];
            ch2 = ch[((x2 - 1) < 0) ? 30 + y2 : b - 6];
            res.append(ch1).append(ch2);
            } else {
            ch1 = ch[6 * x1 + y2];
            ch2 = ch[6 * x2 + y1];
            res.append(ch1).append(ch2);
            }
        }
        return removeFiller(res.toString()).trim();
    }
    
    //This function is responsible for splitting the plain text to digrams or pairs, adding fillers whereever required.
    public String digram(String s)
    {
        String temp=s;
        int n=0;
        while(true)
        {
            temp=temp.trim();
            temp=splitToPairs(temp);
            if(lettersRepeat(temp))
            {
                n=repetitionIndex(temp);
                temp=addFiller(temp,n);
            }
            else
            break;
        }
        return temp.replaceAll("X","x");
    }

    public void printGrid()
    {
        key = key.toLowerCase();

        Set<Character> keySet = new LinkedHashSet<>();
        for(char c: key.toCharArray()){
            if(Character.isLetterOrDigit(c))
                keySet.add(c);
        }

        System.out.println(keySet.toString());

        for(char c='a'; c<='z'; c++){
            if(!keySet.contains(c)){
                keySet.add(c);
            }
        }

        for(char c='0';c<='9';c++){
            if(!keySet.contains(c))
                keySet.add(c);
        }

        ch = keySet.toString().replaceAll("[\\[\\],\\s]", "").toCharArray();

        System.out.println("\n-----------");
        int count = 0;
        for(char c: keySet){
            System.out.print(c+" ");
            count++;
            if(count==6){
                System.out.println();
                count = 0;
            }
        }
        System.out.println("-----------\n");

    }
    public String removeFiller(String st)
    {
        st=st.trim()+" ";
        String wrd="";
        char ch;String result="";
        int len=st.length();
        outer:
        for(int i=0;i<len;i++)
        {
            ch=st.charAt(i);
            if(ch!=' ')
            wrd+=ch;
            else if(ch==' ')
            {
                String word="";
                char c,a1,a2;
                for(int j=0;j<wrd.length();j++)
                {
                    c=wrd.charAt(j);
                    if(c!='x')
                    word+=c;
                    else if(c=='x')
                    {
                        if(j==0)
                        word+=c;
                        else if(j>0 && j<wrd.length()-1)
                        {
                            a1=wrd.charAt(j-1);
                            a2=wrd.charAt(j+1);
                            if(a1!=a2)
                            word+=c;
                            else if(a1==a2)
                            {
                                if(isVowel(a1) && isVowel(a2)) //checks if the letters are vowels as most of the time, only vowels have repeating consecutive  instances
                                {
                                    if((j-1)%2!=0)
                                    word+=c;
                                }
                            }
                        }
                        else if(j==wrd.length()-1)
                        {
                            if(word.length()%2!=0)
                            {
                                result+=word;
                                break outer;
                            }
                        }
                    }
                }
                result+=word+" ";
                wrd="";
            }
        }
        return result;
    }

    //Everything below this are the functions used by the above functions
    public boolean isVowel(char ch)
    {
        if(ch=='a' || ch=='e' ||ch=='i' || ch=='o' || ch=='u')
        return true;
        else 
        return false;
    }
    public String splitToPairs(String s)
    {
        s=s.replaceAll(" ","");
        if(s.endsWith("XX"))
        s=s.substring(0,s.lastIndexOf('X')-1);
        int len=s.length();
        String res="";
        if(len%2==0)
        {
            for(int i=2;i-2<len;i+=2)
            res+=s.substring(i-2,i)+" ";
        }
        else if(len%2!=0)
        {
            s+="X";
            for(int i=2;i-2<len;i+=2)
            res+=s.substring(i-2,i)+" ";
        }
        res=res.trim();
        if(res.endsWith("XX"))
        res=res.substring(0,res.lastIndexOf('X')-1);
        return res;
    }

    public boolean lettersRepeat(String s)
    {
        String store="";
        char a,b;
        s=s.replaceAll(" ","");
        int len=s.length();
        for(int i=2;i-2<len;i+=2)
        {
            store=s.substring(i-2,i);
            a=store.charAt(0);
            b=store.charAt(1);
            if(a==b)
            return true;
        }
        return false;
    }

    public int repetitionIndex(String s)
    {
        String store="";
        char a,b;
        s=s.replaceAll(" ","");
        int len=s.length();
        for(int i=2;i-2<len;i+=2) //takes out two letters at a time and returns index of the repeating letter
        {
            store=s.substring(i-2,i);
            a=store.charAt(0);
            b=store.charAt(1);
            if(a==b)
            return i-1;
        }
        return -1;
    }

    public String addFiller(String s,int n){

        s=s.replaceAll(" ","");
        String res="";
        res=s.substring(0,n)+"X"+s.substring(n);
        return res;
    }

    public int index(char c)
    {
        for(int i=0;i<36;i++)
        if(c==ch[i])
        return i;
        return -1;
    }
    public void elementIndices(char a,char b)
    {
        int index1=index(a),index2=index(b);
        int x=(index1<=5)?0:(index1<=11)?1:(index1<=17)?2:(index1<=23)?3:(index1<=29)?4:5; // finds row index (x)
        int y=(x==0)?index1:(x==1)?index1-6:(x==2)?index1-12:(x==3)?index1-18:(x==4)?index1-24:index1-30; //finds column index (y)
        indexA[0]=x;indexA[1]=y;
        x=(index2<=5)?0:(index2<=11)?1:(index2<=17)?2:(index2<=23)?3:(index2<=29)?4:5;
        y=(x==0)?index2:(x==1)?index2-6:(x==2)?index2-12:(x==3)?index2-18:(x==4)?index2-24:index2-30;
        indexB[0]=x;indexB[1]=y;
    }
}
