package buisnessLogic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpSession;

import org.apache.tika.Tika;

public class BinaryContentHandler implements Runnable {
	
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public File getThFile() {
		return ThFile;
	}

	public void setThFile(File thFile) {
		ThFile = thFile;
	}

	HttpSession session;

	File ThFile;

	public boolean create_bin_file() {

		String where = "userImg/" + session.getAttribute("user").toString().charAt(0) + "/"
				+ session.getAttribute("user").toString() + "/other/" + "file.bin";
		File f = new File(where);

		String files = "userImg/" + session.getAttribute("user").toString().charAt(0) + "/"
				+ session.getAttribute("user").toString() + "/pic";
		File fx = new File(files);

		String filesV = "userImg/" + session.getAttribute("user").toString().charAt(0) + "/"
				+ session.getAttribute("user").toString() + "/vids";
		File fxV = new File(filesV);

		if (!f.exists()) {
			System.err.println("plik nie istnial wiec go robie");

			try {
				f.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			byte[] data = null;

			List<List<byte[]>> listOfContents = new LinkedList<>();

			File[] arr = fx.listFiles();
			if (arr.length > 0) {
				System.err.println(arr.length + " pics");
				List<byte[]> listoPics = new LinkedList<>();

				for (File fil : arr) {

					try (InputStream os = new FileInputStream(fil)) {

						byte[] x = os.readAllBytes();
						listoPics.add(x);

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				listOfContents.add(listoPics);
			}

			File[] arrV = fxV.listFiles();
			if (arrV.length > 0) {
				System.err.println(arr.length + " vids");
				List<byte[]> listoVids = new LinkedList<>();

				for (File fil : arrV) {

					try (InputStream os = new FileInputStream(fil)) {

						byte[] x = os.readAllBytes();
						listoVids.add(x);

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				listOfContents.add(listoVids);

			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {

				oos.writeObject(listOfContents);
				oos.flush();
				data = bos.toByteArray();

				GZIPOutputStream gout = new GZIPOutputStream(new FileOutputStream(f));
				gout.write(data);
				gout.flush();
				gout.close();

				System.err.println("plik nie istnial i został zrobiony");

				return true;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public void run() {

		String where = "userImg/" + session.getAttribute("user").toString().charAt(0) + "/"
				+ session.getAttribute("user").toString() + "/other/" + "file.bin";
		File f = new File(where);

		if (!this.create_bin_file()) {
			
			System.err.println("plik jest wiec dopisuje");

			List<List<byte[]>> listout = new LinkedList<>();
			List<byte[]> listOfPics = new LinkedList<>();
			List<byte[]> listOfVids = new LinkedList<>();
			listout.add(listOfPics);
			listout.add(listOfVids);

			try {

				var ginx = new GZIPInputStream(new FileInputStream(f));
				byte[] bs = ginx.readAllBytes();

				ByteArrayInputStream in = new ByteArrayInputStream(bs);
				ObjectInputStream is = new ObjectInputStream(in);

				listout = (List<List<byte[]>>) is.readObject();

				ginx.close();
				in.close();
				is.close();

				if (ThFile.getName().endsWith(".mp4")) {
					
					System.err.println("dopisuje video");
					listout.get(1).add(Files.readAllBytes(ThFile.toPath()));				
					Tika tika=new Tika();
					String whatty = tika.detect(Files.readAllBytes(ThFile.toPath()));
					System.err.println(whatty);
					
					
				} else {
					
					System.err.println("dopisuje pic");
					Tika tika=new Tika();
					String whatty = tika.detect(Files.readAllBytes(ThFile.toPath()));
					System.err.println(whatty);
					listout.get(0).add(Files.readAllBytes(ThFile.toPath()));
				}
				
				System.err.println("po dopisaniu");


				byte[] datax = null;

				ByteArrayOutputStream bosx = new ByteArrayOutputStream();

				try (ObjectOutputStream oosx = new ObjectOutputStream(bosx)) {

					oosx.writeObject(listout);
					oosx.flush();
					datax = bosx.toByteArray();
					oosx.close();
					bosx.close();

					GZIPOutputStream gout = new GZIPOutputStream(new FileOutputStream(f));
					gout.write(datax);
					gout.flush();
					gout.close();
					System.err.println("koniec bloku dopisujacego do istniejacego pliku");

				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
	}

}
