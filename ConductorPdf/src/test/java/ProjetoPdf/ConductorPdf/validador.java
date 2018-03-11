package ProjetoPdf.ConductorPdf;

import static org.junit.Assert.assertEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.junit.Test;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import br.com.caelum.stella.boleto.bancos.Bancos;
import br.com.caelum.stella.boleto.bancos.GeradorDeLinhaDigitavel;



/*  
1 - > converter pdf pra string------OK
2 - > pegar linha digitável---------OK
3 - > converter pdf pra png---------OK/mais ou menos
4 - > cortar a imagem---------------OK
5 - > decodificar imagem------------OK
6 - > converter raw text em linha digitavel---OK
7 - > comparar com linha digitavel obtida no pdf---OK
*/




public class validador {
	
		 static  String linhaPreDecode = null;
		 static  String linhaPosDecode = null;
		 static  String linhaText 	   = null;
	
		 
		
		 
		 
		 static String getText(File pdfFile) throws IOException {
			    PDDocument doc = PDDocument.load(pdfFile);
			    return new PDFTextStripper().getText(doc);
			}
		 
		 
		 
		 public static String encontrarLinhaDigitavel(String texto) {

		        final Pattern PATTERN_LINHA_DIGITAVEL = Pattern.compile("(\\d{5}\\.\\d{5}\\s{1,2}\\d{5}\\.\\d{6}\\s{1,2}\\d{5}\\.\\d{6}\\s{1,2}\\d{1}\\s{1,2}\\d{14})");
		        
		        String linhaDigitavel = null;
		        java.util.regex.Matcher matcher = PATTERN_LINHA_DIGITAVEL.matcher(texto);

		        boolean achou = false;
		        while (matcher.find()) {
		            achou = true;
		            System.out.println("Linha Digitavel: " + matcher.group(1));
		            linhaDigitavel = matcher.group(1);
		        }

		        if (!achou)
		            System.err.println("Não encontrou a Linha Digitável no boleto.");
		        linhaText = linhaDigitavel;
		        return linhaText;
		        //return linhaDigitavel;
		    } 
		 
		 
		 
		 
		  
		 
		 public static String decode(File file) {
			
			

		if (file == null || file.getName().trim().isEmpty())
			throw new IllegalArgumentException("Arquivo não encontrado ou nome de arquivo invalido");
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException ioe) {
			try {
				throw new Exception(ioe.getMessage());
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		if (image == null)
			throw new IllegalArgumentException("Decodificação da imagem falhou");
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		MultiFormatReader barcodeReader = new MultiFormatReader();
		Result resultado;
		String resultadoFinal = null;
		try {

			resultado = barcodeReader.decode(bitmap);

			resultadoFinal = String.valueOf(resultado.getText());
		} catch (Exception e) {
			e.printStackTrace();

		}

		//return linhaDigi(resultadoFinal);
		linhaPreDecode =  resultadoFinal; 
		return linhaPreDecode;
		//return resultadoFinal;
	}

	

	
	
	
	public static String converterLinhaDigitavel(String string) {

		GeradorDeLinhaDigitavel geraLinhaDigitavel = new GeradorDeLinhaDigitavel();
		//String resultadoFinal = null;
		/*String a = "03391000000000000009211907200009200275200102";*/

		String linhaDigitavelGerada = geraLinhaDigitavel.geraLinhaDigitavelPara(linhaPreDecode, Bancos.ITAU.getBanco());
		System.out.println("Linha digitável gerada: \n" + linhaDigitavelGerada);
		linhaPosDecode = linhaDigitavelGerada;
		return linhaPosDecode;
		//return linhaDigitavelGerada;
	}
	
	
	
	
	
	
	
	public static void cortarImagem() throws IOException {
		
		BufferedImage src = ImageIO.read(new File("C:\\Users\\Kevin\\Pictures\\BoletoBancario.PNG"));

		int x = 10, y = 350, w = 460, h = 70;

		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		dst.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);

		  ImageIO.write(dst, "png", new File("C:\\Users\\Kevin\\Pictures\\BoletoBancarioCortado.PNG"));
		
	}
	
	
	
	
	
		@Test
		public void comparaLinha() {
	
			assertEquals(linhaPosDecode,linhaText);		
		}
		
		
		
		
		
		
		public static void convertPDFbox() throws IOException {
				
				PDDocument document = PDDocument.load(new File(""));
				PDFRenderer pdfRenderer = new PDFRenderer(document);
				for (int page = 0; page < document.getNumberOfPages(); ++page)
				{ 
				    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

				    // suffix in filename will be used as the file format
				    try {
						ImageIOUtil.writeImage(bim, "" + "-" + (page+1) + ".png", 300);
					} catch (IOException e) {
						
						System.err.println(e.getMessage());
						e.printStackTrace();
					}
				}
				document.close();
			}
		
		
		
		
		
		
		
		
		public static void main(String[] args) throws NotFoundException, ChecksumException, FormatException, IOException {
		String linha = null;		
		System.out.println(decode(new File("C:\\Users\\Kevin\\Pictures\\123456.PNG")));		
		System.out.println("Raw Text :" + linhaPreDecode);
		converterLinhaDigitavel(linha);
		//cortarImagem();
		
			//PDFparaText();
			
			try {
			    String text = getText(new File("C:\\Users\\Kevin\\Pictures\\Fatura - Visualizar Segunda Via_191218.pdf"));
			    //System.out.println("Text in PDF: " + text);
			    encontrarLinhaDigitavel(text);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			
	}

}
