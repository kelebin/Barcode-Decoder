package ProjetoPdf.ConductorPdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.javascript.host.html.Image;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import br.com.caelum.stella.boleto.bancos.Bancos;
import br.com.caelum.stella.boleto.bancos.GeradorDeLinhaDigitavel;

public class Validar {
	// static WebDriver driver;

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
		return resultadoFinal;
	}

	/*
	 * public static String ConversorPdfToString(File file) { String convertido =
	 * null; linhaDigi(convertido); return convertido;
	 * 
	 * }
	 */

	public static String linhaDigi(String string) {

		GeradorDeLinhaDigitavel geraLinhaDigitavel = new GeradorDeLinhaDigitavel();
		//String resultadoFinal = null;
		String a = "23791486220000000001111060000000100100022220";

		String linhaDigitavelGerada = geraLinhaDigitavel.geraLinhaDigitavelPara(a, Bancos.ITAU.getBanco());
		System.out.println("Linha digitável gerada: \n" + linhaDigitavelGerada);
		return linhaDigitavelGerada;
	}

	public static void main(String[] args) throws NotFoundException, ChecksumException, FormatException, IOException {
		String linha = null;
		/*
		 * System.out.println(decode(new
		 * File("C:\\Users\\Kevin\\Pictures\\Fatura - Visualizar Segunda Via_191218-1.PNG"
		 * ))); System.out.println(decode(new
		 * File("C:\\Users\\Kevin\\Pictures\\Fatura - Visualizar Segunda Via_191218.PNG"
		 * ))); System.out.println(decode(new
		 * File("C:\\Users\\Kevin\\Pictures\\Fatura - Visualizar Segunda Via_191218.PNG"
		 * )));
		 */
		System.out.println(decode(new File("C:\\Users\\Kevin\\Pictures\\cortado.PNG")));
		//System.out.println(decode(new File("C:\\Users\\Kevin\\Pictures\\Fatura - Visualizar Segunda Via_191218.PNG")));
		linhaDigi(linha);
	}

}
