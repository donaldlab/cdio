package cdio.strucs;

import libprotnmr.math.Quaternion;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class ThreeSphereSampler {
    // Static members:
    public static ArrayList<Quaternion> QList;
    public static final String Filename = "res/quaternions.txt";
    public static boolean IsInitialized = false;

    // Static methods:

    // Reads the List of Quaternions and stores in QList:
    public static void Initialize() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Filename)));
        String line;
        while((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            double a = Double.parseDouble(st.nextToken());
            double b = Double.parseDouble(st.nextToken());
            double c = Double.parseDouble(st.nextToken());
            double d = Double.parseDouble(st.nextToken());
            QList.add(new Quaternion(a, b, c ,d));
        }
        IsInitialized = true;
    }

    // Randomly samples a Quaternion from Qlist:
    public static Quaternion RandomQuaternion() throws IOException {
        if(!IsInitialized)
            Initialize();

        Random rand = new Random();
        Quaternion q =QList.get(rand.nextInt(QList.size()+1));
        return new Quaternion(q);
    }



}
