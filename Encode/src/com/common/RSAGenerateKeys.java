package com.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAGenerateKeys {
	
	public static final String PUBLIC_KEY_FILE = "rsa_keypair/publicKey";
	public static final String PRIVATE_KEY_FILE = "rsa_keypair/privateKey";
	
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public RSAGenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}

	public static void main(String[] args) {
		try {
			new RSAGenerateKeys(1024).generateKeysToFile();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	public void generateKeysToFile() {
		try {
			System.out.println("Starting generate...");
			this.createKeys();
			this.writeToFile(PUBLIC_KEY_FILE, this.getPublicKey().getEncoded());
			this.writeToFile(PRIVATE_KEY_FILE, this.getPrivateKey().getEncoded());
			System.out.println("Generated!");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
