import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        runFile("tmp/block1.i");
    }

    private static void runFile(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    private static void run(String source) throws Exception {
        System.out.println(source);


        //得到所有的文件中的词分析值
        List<Token> tokens = new Lexer(source).scanTokens();


        for (int i = 1; i < tokens.size(); i++) {
            System.out.print(i + " ");
            System.out.println(tokens.get(i - 1));
        }

        List<Groove> grooves = new Parser(tokens).resolver();
        grooves.forEach(System.out::println);

        Jvm.values.forEach(System.out::println);

        new Jvm(grooves).execute();

        Jvm.values.forEach(System.out::println);
    }
}