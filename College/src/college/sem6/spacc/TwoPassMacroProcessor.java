package college.sem6.spacc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class TwoPassMacroProcessor {
	private static final String OUTPUT_TXT = "Output.txt";
	private static final String ARGTAB_TXT = "Argtab.txt";
	private static final String DEFTAB_TXT = "Deftab.txt";
	private static final String NAMTAB_TXT = "Namtab.txt";
	private static BufferedReader input;
	private static PrintWriter output;
	private static PrintWriter nameWriter;
	private static PrintWriter defWriter;
	private static PrintWriter argWriter;
	
	private static String la, mne, opnd;
	
	private static String name;
	private static int nameId, nameDefIndex;
	
	private static String defName, defOpnd;
	private static int defIndex;
	
	private static String argName;
	private static int argIndex;
	
	
	public static void main(String inputArgs[]) throws IOException {
		String inputFileName = "";
		if(inputArgs.length != 0) 
			inputFileName = inputArgs[0];
		else
			inputFileName = "input.txt";
		
		File inputFile = new File(inputFileName);
		File nameTableFile = new File(NAMTAB_TXT);
		File definitionTableFile = new File(DEFTAB_TXT);
		File argumentTableFile = new File(ARGTAB_TXT);
		File outputFile = new File(OUTPUT_TXT);
		
		nameTableFile.createNewFile();
		definitionTableFile.createNewFile();
		argumentTableFile.createNewFile();
		outputFile.createNewFile();
		
		input = new BufferedReader(new FileReader(inputFile));
		nameWriter = new PrintWriter(new BufferedWriter(new FileWriter(nameTableFile)), true);
		defWriter = new PrintWriter(new BufferedWriter(new FileWriter(definitionTableFile)), true);
		argWriter = new PrintWriter(new BufferedWriter(new FileWriter(argumentTableFile)), true);
		output = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)), true);
		
		int argIndex = 0, defIndex = 0, nameIndex = 0, argCounter = 0;
		//Pass 1
		read();
		while(!mne.equalsIgnoreCase("END")) {
			if(mne.equalsIgnoreCase("MACRO")) {
				nameWriter.println(nameIndex++ + " " + la + " " + defIndex);
				defWriter.println(defIndex++ + " " + la + " " + opnd);
				
				String opnds[] = opnd.split(",");
 				for(int i = 0; i < opnds.length; i++) {
 					opnds[i] = opnds[i].substring(1,  opnds[i].length());
					argWriter.println(argIndex++ + " " + opnds[i]);
				}
				
				read();
				while(!mne.equalsIgnoreCase("MEND")) {
					if(opnd.contains("&")) {
						for(int i = 0; i < opnds.length; i++) 
							if(opnd.substring(1, opnd.length()).equalsIgnoreCase(opnds[i])) {
								opnd = "?" + opnds[i];
							}
					}
					defWriter.println(defIndex++ + " " + mne + " " + opnd);
					read();
				}
				defWriter.println(defIndex++ + " " + mne + " " + "-");
			}
			read();
		}
		input.close();
		nameWriter.close();
		defWriter.close();
		argWriter.close();
		
		Path namePath = FileSystems.getDefault().getPath(NAMTAB_TXT);
		Path defPath = FileSystems.getDefault().getPath(DEFTAB_TXT);
		Path argPath = FileSystems.getDefault().getPath(ARGTAB_TXT);
		
		input = new BufferedReader(new FileReader(inputFile));
		List<String> nameText = Files.readAllLines(namePath);
		List<String> defText = Files.readAllLines(defPath);
		List<String> argText = Files.readAllLines(argPath);
		
		//Pass 2
		read();
		while(!mne.equalsIgnoreCase("END")) {
			if(mne.equalsIgnoreCase("MACRO")) {
				while(!mne.equalsIgnoreCase("MEND")) {
					read();
				}
				read();
				continue;
			}
			for(String n : nameText) {
				readNameTable(n);
				if(name.equals(mne)) {
					break;
				}
			}
			
			if(name.equals(mne)) {
				int defI = nameDefIndex;
				readDefTable(defText.get(defI++));
				output.println("." + "\t" + defName + "\t" + opnd);
				readDefTable(defText.get(defI++));
				while(!defName.equalsIgnoreCase("MEND")) {
					if(opnd.contains("?")) {
						readArgTable(argText.remove(0));
						output.println("-" + "\t" + defName + "\t" + argName);
					}
					else
						output.println("-" + "\t" + defName + "\t" + defOpnd);
					readDefTable(defText.get(defI++));
				}
			}
			else {
				output.println(la + "\t" + mne + "\t" + opnd);
			}
			read();
		}
		
		output.println(la + "\t" + mne + "\t" + opnd);
		output.close();
		
		System.out.println("Processed");
	}
	
	private static void read() throws IOException {
		String inputBuffer[] = input.readLine().split(" ");
		la = inputBuffer[0];
		mne = inputBuffer[1];
		opnd = inputBuffer[2];
		//System.out.println("Read : " + Arrays.toString(inputBuffer));
	}
	
	private static void readNameTable(String nameData) {
		String nameBuffer[] = nameData.split(" ");
		nameId = Integer.parseInt(nameBuffer[0]);
		name = nameBuffer[1];
		nameDefIndex = Integer.parseInt(nameBuffer[2]);
	}
	
	private static void readDefTable(String defData) {
		String defBuffer[] = defData.split(" ");
		defIndex = Integer.parseInt(defBuffer[0]);
		defName = defBuffer[1];
		defOpnd = defBuffer[2];
	}
	
	private static void readArgTable(String argData) {
		String argBuffer[] = argData.split(" ");
		argIndex = Integer.parseInt(argBuffer[0]);
		argName = argBuffer[1];
	}

}
/*
#include<stdio.h>
#include<conio.h>
#include<string.h>
#include<stdlib.h>
void main()
{
	FILE *f1, *f2, *f3, *f4, *f5;
	int len, i, pos = 1;
	char arg[20], mne[20], opnd[20], la[20], name[20], mne1[20], opnd1[20], pos1[10], pos2[10];
	f1 = fopen("input.txt", "r");
	f2 = fopen("namtab.txt", "w+");
	f3 = fopen("deftab.txt", "w+");
	f4 = fopen("argtab.txt", "w+");
	f5 = fopen("op.txt", "w+");
	fscanf(f1, "%s%s%s", la, mne, opnd);
	while (strcmp(mne, "END") != 0)
	{
		if (strcmp(mne, "MACRO") == 0)
		{
			fprintf(f2, "%s\n", la);
			fseek(f2, SEEK_SET, 0);
			fprintf(f3, "%s\t%s\n", la, opnd);
			fscanf(f1, "%s%s%s", la, mne, opnd);
			while (strcmp(mne, "MEND") != 0)
			{
				if (opnd[0] == '&')
				{
					_itoa(pos, pos1, 5);
					strcpy(pos2, "?");
					strcpy(opnd, strcat(pos2, pos1));
					pos = pos + 1;
				}
				fprintf(f3, "%s\t%s\n", mne, opnd);
				fscanf(f1, "%s%s%s", la, mne, opnd);
			}
			fprintf(f3, "%s", mne);
		}
		else
		{
			fscanf(f2, "%s", name);
			if (strcmp(mne, name) == 0)
			{
				len = strlen(opnd);
				for (i = 0; i < len; i++)
				{
					if (opnd[i] != ',')
						fprintf(f4, "%c", opnd[i]);
					else
						fprintf(f4, "\n");
				}
				fseek(f3, SEEK_SET, 0);
				fseek(f4, SEEK_SET, 0);
				fscanf(f3, "%s%s", mne1, opnd1);
				fprintf(f5, ".\t%s\t%s\n", mne1, opnd);
				fscanf(f3, "%s%s", mne1, opnd1);
				while (strcmp(mne1, "MEND") != 0)
				{
					if ((opnd[0] == '?'))
					{
						fscanf(f4, "%s", arg);
						fprintf(f5, "-\t%s\t%s\n", mne1, arg);
					}
					else
						fprintf(f5, "-\t%s\t%s\n", mne1, opnd1);
					fscanf(f3, "%s%s", mne1, opnd1);
				}
			}
			else
				fprintf(f5, "%s\t%s\t%s\n", la, mne, opnd);
		}
		fscanf(f1, "%s%s%s", la, mne, opnd);
	}
	fprintf(f5, "%s\t%s\t%s", la, mne, opnd);
	fclose(f1);
	fclose(f2);
	fclose(f3);
	fclose(f4);
	fclose(f5);
	printf("files to be viewed \n");
	printf("1. argtab.txt\n");
	printf("2. namtab.txt\n");
	printf("3. deftab.txt\n");
	printf("4. op.txt\n");
	getchar();
}
*/