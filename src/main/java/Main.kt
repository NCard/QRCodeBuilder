import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.CharacterSetECI
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.lang.Exception
import javax.imageio.ImageIO

fun main (args: Array<String>) {
    println("starting")
    if (args.hasArgs(2)) {
        println("building")
        val contents = args[0]
        val fileName = args[1]
        val deputyFileName = "jpg"
        val outputFile = File("$fileName.$deputyFileName")
        val qrCodeImage = createQRCode(contents, 120, 120, 0)

        ImageIO.write(qrCodeImage, deputyFileName, outputFile)
    }
    println("complete")
}


fun createQRCode(contents: String, width: Int, height: Int, margin: Int): BufferedImage {
    val hints = qrCodeBaseSetting(margin)
    val format = BarcodeFormat.QR_CODE

    var bitMatrix: BitMatrix? = null

    try {
        bitMatrix = MultiFormatWriter().encode(contents, format, width, height, hints)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    val config = MatrixToImageConfig(Color.black.rgb, Color.white.rgb)

    return MatrixToImageWriter.toBufferedImage(bitMatrix, config)
}

fun qrCodeBaseSetting (margin: Int): Map<EncodeHintType, Any> {
    return mutableMapOf(
            EncodeHintType.CHARACTER_SET to CharacterSetECI.UTF8,
            EncodeHintType.ERROR_CORRECTION to ErrorCorrectionLevel.L,
            EncodeHintType.MARGIN to margin
    )
}

fun <T> Array<T>.hasArgs(size: Int): Boolean = this.size == size