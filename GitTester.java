import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GitTester {
    public static void main(String[] args) throws IOException {
        Git.init(args);
        new FileWriter("git/index").close();
        new File("myProgram/scripts").mkdirs();
        new File("myProgram/docs").mkdirs();

        FileWriter w = new FileWriter("myProgram/Hello.txt");
        w.write("hmoka");
        w.close();
        w = new FileWriter("myProgram/scripts/miao.txt");
        w.write("boka");
        w.close();
        w = new FileWriter("myProgram/badem.txt");
        w.write("badem");
        w.close();
        w = new FileWriter("myProgram/README.md");
        w.write("readme");
        w.close();
        w = new FileWriter("myProgram/docs/World.txt");
        w.write("world");
        w.close();


        Git.createBlob("myProgram/Hello.txt");

        Git.createBlob("myProgram/scripts/miao.txt");
        Git.createBlob("myProgram/badem.txt");
        System.out.println("index rn (should be 3 lines, all same hash, no dupes):");
        System.out.println(Files.readString(Path.of("git/index")));

        w = new FileWriter("myProgram/Hello.txt");
        w.write("hello again");
        w.close();
        Git.createBlob("myProgram/Hello.txt");

        System.out.println(Files.readString(Path.of("git/index")));

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Path.of("git/index")));
        if (lines.size() == 3) {
            System.out.println("3.1 moka");
        } else {
            System.out.println("3.1 Bad");
        }


        ArrayList<String> list = new ArrayList<>();
        list.add("blob 0a4d55a8d778e5022fab701977c5d840bbc486d0 myProgram/docs/Hello.txt");
        list.add("blob 483b5e082cf5502b303ba3dd4f3469a49fd3421f myProgram/docs/World.txt");
        list.add("blob 4377a91cdfd44db9a9bbf056849c7da0fc6cc7be myProgram/README.md");
        String h = Git.createTree(list, "myProgram/docs");
        System.out.println("tree hash: " + h);
        System.out.println("tree file (should be Hello.txt and World.txt, NO README):");
        System.out.println(Files.readString(Path.of("git/objects/" + h)));
        String h2 = Git.createTree(list, "myProgram/docs");
        System.out.println("same hash twice? " + h.equals(h2));

    }
}
