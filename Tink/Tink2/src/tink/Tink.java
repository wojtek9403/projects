package tink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.IntStream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Tink {
	
	public static void main(String[] args) {

		String path = "";

		if (args.length == 0) {

			System.out.println(
					"Welcome in simple text analyser, if you haven't pass path to file as param do it bellow");
			System.err.print("\ntxt file absolute path: ");

			try (Scanner sc = new Scanner(System.in)) {
				path = sc.nextLine();
				if (path.isBlank()) {
					while (path.isBlank()) {
						System.err.println("retype relative path to file: ");
						path = sc.nextLine().replaceAll("\\\\", File.separator);
					}
				}
			} catch (RuntimeException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
		} 
		
		if (args.length == 1 || !path.isBlank()) {

			try {

				if (path.isBlank() || path == null)
					path = args[0];
								
				File f = new File(path);

				try {
					Date date = new Date();
					String d = date.toString().replaceAll(" ", "").replaceAll(":", "").toString();

					File outdir = new File("results");
					if (!outdir.exists())
						outdir.mkdir();
					File out = new File("results/" + "out_" + d + ".txt");
					out.createNewFile();

					BufferedWriter pw = new BufferedWriter(new PrintWriter(out));
					StringBuilder sb = new StringBuilder();

					String source = Files.readString(f.toPath());
					String[] tabx = source.trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");

					System.out.println("file contains: " + tabx.length + " words");

					String[] distincts = Arrays.stream(tabx).distinct().toArray(String[]::new);

					int min = IntStream.range(0, tabx.length).map(i -> tabx[i].length()).summaryStatistics().getMin();					

					System.err.print("shortest words: ");
					Arrays.stream(distincts).filter(s -> s.length() == min).forEach(s -> sb.append(s).append(" "));
					System.out.println(sb.toString());
					sb.delete(0, sb.length() - 1);

					int max = IntStream.range(0, tabx.length).map(i -> tabx[i].length()).summaryStatistics().getMax();

					System.err.print("longest words: ");
					Arrays.stream(distincts).filter(s -> s.length() == max).forEach(s -> sb.append(s).append(" "));
					System.out.println(sb.toString());
					sb.delete(0, sb.length() - 1);

					double average = IntStream.range(0, tabx.length).map(i -> tabx[i].length()).summaryStatistics()
							.getAverage();
					System.out.println("Average word length: " + average);

					Map<String, Integer> resultMap = new TreeMap<>();
					List<String> listofIn = Arrays.asList(tabx);

					Arrays.stream(tabx).filter(s -> s.length() >= 2)
							.forEach(s -> resultMap.put(s, Collections.frequency(listofIn, s)));

					resultMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
							.forEach(e -> {
								try {
									pw.write(e.getKey() + " ilosc: " + e.getValue() + "\n");
								} catch (IOException e1) {
									System.out.println("something went wrong with writing down results to file !\n");
									e1.printStackTrace();
								}
							});

					pw.flush();
					pw.close();

					System.err.println(
							"your output file's name is: " + out.getName() + " (path to file is: " + out.getAbsolutePath() + " )");

					byte[] bytes = Files.readAllBytes(out.toPath());
					Checksum crc32 = new CRC32();
					crc32.update(bytes, 0, bytes.length);
					System.err.println("output file's checksum: " + crc32.getValue());

				} catch (IOException ex) {
					System.err.println("no such file with path like: " + ex.getMessage()
							+ " reboot program and try again ! \n");
					ex.printStackTrace();
				}
				
				System.gc();
				
			} catch (RuntimeException e) {
				System.out.println(" Runtime ex occured: " + e.getMessage() + "\n");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("you can pass only one path as program parameter !");
		}

	}
}
