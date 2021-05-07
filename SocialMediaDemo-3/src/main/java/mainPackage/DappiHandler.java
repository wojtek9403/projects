package mainPackage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Dappi_Handler")
public class DappiHandler {

	// http://localhost:8080/Dappi_Handler/1119a646-1ce0
	@GetMapping(value = "/1119a646-1ce0", consumes = "application/json", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] getContents(@RequestHeader("Login") String whoAmI, @RequestHeader("WhatToFetch") String WhatToFetch) {

		String where = "userImg/" + whoAmI.charAt(0) + "/" + whoAmI + "/other/" + "file.bin";
		System.out.println("path:" + where);

		File f = new File(where);
		List<List<byte[]>> listOfContent = new ArrayList<>();

		try {
			var fis = new FileInputStream(where);
			var gis = new GZIPInputStream(fis);
			var objis = new ObjectInputStream(gis);

			listOfContent = (List<List<byte[]>>) objis.readObject();
			objis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		var baos = new ByteArrayOutputStream();

		try {
			var oos = new ObjectOutputStream(baos);

			if (WhatToFetch.equals("Pics")) {
				oos.writeObject(listOfContent.get(0));
			} else {
				oos.writeObject(listOfContent.get(1));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos.toByteArray();
	}

}
