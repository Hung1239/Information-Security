//Chuong trinh Demo DSA 
import java.io.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import javax.swing.*; 
import java.security.*; 

public class DSA extends JFrame { 
public static DSA op; 
Container c = new Container(); 
JTextField txtf = new JTextField(); 
JTextArea TxtA1,TxtA2; 
JLabel lblopenFile,lblplainText,lblciphertext,lblwellcome; 
JButton btnbrows,btndsaS,btndsaV,btnclear,btnexit; 
JScrollPane JScollPanea1,JScollPanea2; 
PrivateKey priDSA ; 
PublicKey pubDSA ; 
byte MessageToByteDSA[],SignatureData[], cipherText1[];
//Ham tao 
DSA(){ 
        c=getContentPane(); 
        c.setLayout(null); 

        TxtA1 = new JTextArea("^A^  Ban co the soan thao tai day hoac nhap tu File Text  ^A^"); 
        JScollPanea1=new JScrollPane(TxtA1); 
        JScollPanea1.setBounds(70,200,400,150); 
        c.add(JScollPanea1); 
         
        TxtA2 = new JTextArea(); 
        TxtA2.setWrapStyleWord(true); 
        JScollPanea2=new JScrollPane(TxtA2); 
        JScollPanea2.setBounds(70,420,400,150); 
        c.add(JScollPanea2); 

        lblopenFile = new JLabel("Chon File : "); 
        lblopenFile.setBounds(70,100,200,20); 
        c.add(lblopenFile); 
         
        lblplainText = new JLabel("Ban goc can ki:"); 
        lblplainText.setBounds(70,175,150,20); 
        c.add(lblplainText); 
         
        lblciphertext = new JLabel("Ket qua:"); 
        lblciphertext.setBounds(70,395,150,20); 
        c.add(lblciphertext); 
         
        lblwellcome = new JLabel("DEMO Chu Ki So - DSS"); 
        lblwellcome.setBounds(70,30,400,35); 
        c.add(lblwellcome); 
         
        Font fon=new Font("Comic Sans MS", Font.ITALIC, 27); 
        lblwellcome.setFont(fon); 
        lblwellcome.setForeground(Color.BLUE); 
         
        txtf.setBounds(70,125,280,25); 
        c.add(txtf); 
         
        btnbrows = new JButton("Browse"); 
        btnbrows.setBounds(380,125,90,25); 
        c.add(btnbrows); 

        btndsaS = new JButton(" Tao chu ki"); 
        btndsaS.setBounds(520,230,135,50); 
        c.add(btndsaS); 

        btndsaV = new JButton("Kiem chung"); 
        btndsaV.setBounds(520,310,135,50); 
        c.add(btndsaV); 

        btnclear = new JButton(" Nhap lai tu dau"); 
        btnclear.setBounds(520,390,135,50); 
        c.add(btnclear); 

        btnexit = new JButton(" Thoat "); 
        btnexit.setBounds(520,470,135,50); 
        c.add(btnexit); 
         
btnbrows.addActionListener(new ActionListener(){ 
public void actionPerformed(ActionEvent e) { 
String str =loadFile(new Frame(), "Menu tim du lieu", "", ""); 
                    txtf.setText(str); 
                    if(txtf.getText().equals("nullnull")) 
                        { 
                        txtf.setText(""); 
                        } 
                    else{ 
                        TxtA1.setText(""); 
                            receiveData(str); 
                         } 
                } 
                }); 
btnclear.addActionListener(new ActionListener(){ 
public void actionPerformed(ActionEvent e) { 
                            clearText(); 
                            } 
                    }); 
         
btnexit.addActionListener(new ActionListener(){ 
public void actionPerformed(ActionEvent e) { 
                            System.exit(1); 
                        } 
                    }); 

btndsaS.addActionListener(new ActionListener(){ 
public void actionPerformed(ActionEvent e) { 
                            signature(); 
                            } 
                        }); 
btndsaV.addActionListener(new ActionListener(){ 
public void actionPerformed(ActionEvent e) { 
                        checkDigitalSignature(); 
                    } 
                    }); 
}  
// Ham load du lieu 

public String loadFile(Frame f, String title, String defDir, String fileType) 
{ 
FileDialog fd = new FileDialog(f, title, FileDialog.LOAD); 
fd.setFile(fileType); 
fd.setDirectory(defDir); 
fd.setLocation(500, 500); 
fd.setVisible(true); 
String URLFILE = fd.getDirectory()+(fd.getFile()); 
return URLFILE; 
}  
//Doc du lieu duoc load vao 

public void receiveData(String file) { //method  
try{ 
File infile = new File(file); 
DataInputStream inputFile = new DataInputStream(new 
FileInputStream(infile)); 
double tempLength = infile.length(); 
int dataLength = (int)tempLength; 
byte[] textByte = new byte[dataLength]; 
inputFile.read(textByte, 0, dataLength); 
String originalData = new String(textByte) ; 
TxtA1.append(originalData); 
inputFile.close(); 
}  
catch(Exception e) { 
    TxtA1.setText("Loi"); 
} 
} 
//Ham nhap moi 
void clearText(){  
    TxtA1.setText(""); 
    TxtA2.setText(""); 
    txtf.setText(""); 
} 

//Ham sinh chu ki 

public void signature(){ 
try { 
//tao cap khoa ngau nhien 
KeyPairGenerator genkey = KeyPairGenerator.getInstance("DSA"); 
genkey.initialize(1024, new SecureRandom()); 

KeyPair keyPair = genkey.generateKeyPair(); 
pubDSA = keyPair.getPublic(); 
priDSA = keyPair.getPrivate(); 

//Ghi khoa cong khai ra 1 file 
ObjectOutputStream keyFile = new ObjectOutputStream(new 
FileOutputStream("DSApublicKey.key")); 
keyFile.writeObject(pubDSA); 

String DataInput=TxtA1.getText(); 
MessageDigest digest = MessageDigest.getInstance("SHA1"); 
digest.update(DataInput.getBytes()); 
byte[] ByteMDG = digest.digest(); 


Signature signer = Signature.getInstance("DSA"); 
signer.initSign(priDSA); 
signer.update(ByteMDG); 
SignatureData = signer.sign(); 


FileOutputStream sigfos = new FileOutputStream("Sign.txt"); 
sigfos.write(SignatureData); 

sigfos.close(); 

TxtA2.append("\nVan ban goc:\t"+DataInput+"\n\n*****************************Chu ki*****************************\n\n"); 
for (int i=0; i < SignatureData.length; i++) 
{ 
    TxtA2.append( SignatureData[i]+"" ); 
} 
//TxtA2.append( SignatureData+"" ); 
    TxtA2.append("\n\n*****************************Ket thuc*****************************"); 
}catch(Exception e){} 
} 
//Ham kiem chung chu ki 

public void checkDigitalSignature() { 
try { 
MessageDigest digest_DSA_Check = MessageDigest.getInstance("SHA1"); 
digest_DSA_Check.update(TxtA1.getText().getBytes()); 
byte byteMDG_DSA2[] = digest_DSA_Check.digest(); 

Signature verifier = Signature.getInstance("DSA"); 
verifier.initVerify(pubDSA); 
verifier.update(byteMDG_DSA2); 
boolean checkSineture = verifier.verify(SignatureData); 
if (checkSineture == true){ 
JOptionPane.showMessageDialog(null," Chu ki dung","Chu ki da duoc xac thuc", 
		JOptionPane.INFORMATION_MESSAGE); 
}else { 
JOptionPane.showMessageDialog(null," Van ban da bi chinh sua ","Chu ki da duoc xac thuc ", 
		JOptionPane.ERROR_MESSAGE); 
} 
} catch(Exception e){} 
} 
//-------------------------------- End DSA -----------------------------------------// 

//Ham main 
public static void main(String[] args){ 
op=new DSA(); 
op.setTitle("Chuong trinh Demo tao chu ki dien tu  va kiem chung dung thuat toan DSA"); 
op.setSize(710,660); 
op.setLocation(300,90); 
op.setDefaultCloseOperation(EXIT_ON_CLOSE); 
op.setResizable(false); 
op.setVisible(true); 
        }  
}