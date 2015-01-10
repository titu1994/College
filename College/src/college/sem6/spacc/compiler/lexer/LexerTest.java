package college.sem6.spacc.compiler.lexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import college.sem6.spacc.compiler.lexer.Lexer.Category;

public class LexerTest {

	public static void main(String[] args) throws IOException {
		BufferedReader bb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter a sentence : ");
		String input = bb.readLine();

		File f = File.createTempFile("temp", "txt");
		FileWriter writer = new FileWriter(f);
		PrintWriter pr = new PrintWriter(writer, true);
		pr.println(input);
		pr.close();
		
		FileReader fr = new FileReader(f);
		BufferedReader bfr = new BufferedReader(fr);
		Lexer lexer = new Lexer(bfr);
		
		Category category;
		
		while(!(category = lexer.nextToken()).equals(Category.EOF)) {
			System.out.println(category + " " + lexer.lastLexeme);
		}
		
	}

}
