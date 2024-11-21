package com.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.common.MD5Hashing;
import com.common.RSACommon;
import com.common.RSAGenerateKeys;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {

	private JPanel contentPane;

	private JTextField textDesKey;
	private JTextArea textDesOriginal;
	private JTextArea textDesEncrypted;
	private JTextArea textDesDecrypted;

	private JTextArea textRsaOriginal;
	private JTextArea textRsaEncrypted;
	private JTextArea textRsaDecrypted;

	private JTextField textMd5FileUrl;
	private File fileMd5;
	private JTextPane textMd5FileHashing;

	static Main frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 979, 717);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setToolTipText("");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panelDES = new JPanel();
		panelDES.setBorder(new TitledBorder(null, "DES Encrytion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDES.setBounds(10, 11, 955, 175);
		contentPane.add(panelDES);
		panelDES.setLayout(null);

		textDesKey = new JTextField();
		textDesKey.setBounds(63, 19, 116, 20);
		panelDES.add(textDesKey);
		textDesKey.setColumns(10);

		textDesOriginal = new JTextArea();
		textDesOriginal.setBounds(10, 75, 210, 86);
		panelDES.add(textDesOriginal);

		JButton btnDesEncrypt = new JButton("Encrypt >>");
		btnDesEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				encryptDES();

			}
		});
		btnDesEncrypt.setBounds(230, 104, 124, 23);
		panelDES.add(btnDesEncrypt);

		textDesEncrypted = new JTextArea();
		textDesEncrypted.setBounds(364, 75, 210, 86);
		panelDES.add(textDesEncrypted);

		textDesDecrypted = new JTextArea();
		textDesDecrypted.setBounds(721, 75, 210, 86);
		panelDES.add(textDesDecrypted);

		JButton btnDesDecrypt = new JButton("Decrypt >>");
		btnDesDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decryptDES();
			}
		});
		btnDesDecrypt.setBounds(584, 104, 127, 23);
		panelDES.add(btnDesDecrypt);

		JLabel lblDesKey = new JLabel("Key :");
		lblDesKey.setBounds(10, 22, 46, 14);
		panelDES.add(lblDesKey);

		JLabel lblNewLabel_1 = new JLabel("Original text :");
		lblNewLabel_1.setBounds(10, 50, 83, 14);
		panelDES.add(lblNewLabel_1);

		JLabel lblEncrytedText = new JLabel("Encryted text:");
		lblEncrytedText.setBounds(364, 50, 83, 14);
		panelDES.add(lblEncrytedText);

		JLabel lblDecryptedText = new JLabel("Decrypted text:");
		lblDecryptedText.setBounds(723, 50, 103, 14);
		panelDES.add(lblDecryptedText);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "MD5 Hashing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 389, 955, 294);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Hashing File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 143, 935, 142);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JButton btnMd5Open = new JButton("Open file :");
		btnMd5Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int status = fileChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					fileMd5 = fileChooser.getSelectedFile();
					textMd5FileUrl.setText(fileMd5.getAbsolutePath());
				}

			}
		});
		btnMd5Open.setBounds(10, 23, 119, 23);
		panel_3.add(btnMd5Open);

		textMd5FileUrl = new JTextField();
		textMd5FileUrl.setBounds(139, 24, 786, 20);
		panel_3.add(textMd5FileUrl);
		textMd5FileUrl.setColumns(10);

		JButton btnMd5Check = new JButton("Check MD5 :");
		btnMd5Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					String result = MD5Hashing.getMD5(fileMd5);
					textMd5FileHashing.setText(result);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, ex.getMessage() + "!!!");
					ex.printStackTrace();
				}
			}
		});
		btnMd5Check.setBounds(10, 56, 119, 23);
		panel_3.add(btnMd5Check);

		textMd5FileHashing = new JTextPane();
		textMd5FileHashing.setBounds(139, 55, 786, 73);
		panel_3.add(textMd5FileHashing);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Hashing String", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 23, 935, 109);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		final JTextArea textMd5Input = new JTextArea();
		textMd5Input.setBounds(10, 44, 386, 48);
		panel_4.add(textMd5Input);

		final JTextArea textMd5Output = new JTextArea();
		textMd5Output.setBounds(539, 44, 386, 48);
		panel_4.add(textMd5Output);

		JLabel lblInputText = new JLabel("Input Text :");
		lblInputText.setBounds(10, 19, 77, 14);
		panel_4.add(lblInputText);
		lblInputText.setFont(new Font("Tahoma", Font.BOLD, 11));

		JButton btnMd5Generate = new JButton("Generate >>");
		btnMd5Generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String input = textMd5Input.getText();
				String result = MD5Hashing.getMD5(input);
				textMd5Output.setText(result);

			}
		});
		btnMd5Generate.setBounds(405, 57, 124, 23);
		panel_4.add(btnMd5Generate);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "RSA Encrytion", 
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setLayout(null);
		panel_2.setBounds(10, 197, 955, 181);
		contentPane.add(panel_2);

		textRsaOriginal = new JTextArea();
		textRsaOriginal.setBounds(10, 73, 210, 93);
		panel_2.add(textRsaOriginal);

		JButton btnMd5Encrypt = new JButton("Encrypt >>");
		btnMd5Check.setForeground(Color.RED);
		btnMd5Encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encryptRSA();
			}
		});
		btnMd5Encrypt.setBounds(230, 111, 124, 23);
		panel_2.add(btnMd5Encrypt);

		textRsaEncrypted = new JTextArea();
		textRsaEncrypted.setBounds(361, 73, 210, 93);
		panel_2.add(textRsaEncrypted);

		textRsaDecrypted = new JTextArea();
		textRsaDecrypted.setBounds(714, 73, 219, 93);
		panel_2.add(textRsaDecrypted);

		JButton btnMd5Decrypt = new JButton("Decrypt >>");
		btnMd5Decrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decryptRSA();
			}
		});
		btnMd5Decrypt.setBounds(581, 111, 124, 23);
		panel_2.add(btnMd5Decrypt);

		JLabel label_1 = new JLabel("Original text :");
		label_1.setBounds(10, 48, 83, 14);
		panel_2.add(label_1);

		JLabel label_2 = new JLabel("Encryted text:");
		label_2.setBounds(361, 48, 83, 14);
		panel_2.add(label_2);

		JLabel label_3 = new JLabel("Decrypted text:");
		label_3.setBounds(714, 48, 103, 14);
		panel_2.add(label_3);

		JButton btnRsaGenerate = new JButton("Generate keypair");
		btnRsaGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new RSAGenerateKeys(1024).generateKeysToFile();
				} catch (NoSuchAlgorithmException | NoSuchProviderException e1) {
					JOptionPane.showMessageDialog(frame, e1.getMessage() + "!!!");
					e1.printStackTrace();
				}
			}
		});
		btnRsaGenerate.setBounds(10, 23, 210, 23);
		panel_2.add(btnRsaGenerate);
	}

	public void encryptDES() {
		try {
			String originalText = textDesOriginal.getText();
			String key = textDesKey.getText();
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] byteEncrypted = cipher.doFinal(originalText.getBytes());
			String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
			textDesEncrypted.setText(encrypted);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, ex.getMessage() + "!!!");
			ex.printStackTrace();
		}
	}

	public void decryptDES() {
		try {
			String encryptText = textDesEncrypted.getText();
			String key = textDesKey.getText();
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
			textDesDecrypted.setText(new String(decrypted));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, ex.getMessage() + "!!!");
			ex.printStackTrace();
		}
	}

	public void encryptRSA() {
		try {
			String originalText = textRsaOriginal.getText();
			PublicKey publicKey = RSACommon.getPublicKey();

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			byte[] byteEncrypted = cipher.doFinal(originalText.getBytes());
			String encrypted = Base64.getEncoder().encodeToString(byteEncrypted);
			textRsaEncrypted.setText(encrypted);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, ex.getMessage() + "!!!");
			ex.printStackTrace();
		}
	}

	public void decryptRSA() {
		try {
			String encryptText = textRsaEncrypted.getText();
			PrivateKey privateKey = RSACommon.getPrivateKey();

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptText));
			textRsaDecrypted.setText(new String(decrypted));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, ex.getMessage() + "!!!");
			ex.printStackTrace();
		}
	}
}
