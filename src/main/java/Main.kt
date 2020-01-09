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
    if (args.atLease(1)) {
        println("building")
        val qrCodeArgs = QRCodeArgs()
        qrCodeArgs.readQRCodeArgs(args)
        val deputyFileName = "jpg"
        val outputFile = File(qrCodeArgs.fileName)
        val qrCodeImage = createQRCode(qrCodeArgs)

        ImageIO.write(qrCodeImage, deputyFileName, outputFile)
    }
    println("complete")
}


fun createQRCode(qrCodeArgs: QRCodeArgs): BufferedImage {
    val hints = qrCodeBaseSetting(qrCodeArgs.margin, qrCodeArgs.errorCorrectionLevel)
    val format = BarcodeFormat.QR_CODE

    var bitMatrix: BitMatrix? = null

    try {
        bitMatrix = MultiFormatWriter().encode(qrCodeArgs.contents, format, qrCodeArgs.width, qrCodeArgs.height, hints)

    } catch (e: Exception) {
        e.printStackTrace()
    }

    val config = MatrixToImageConfig(Color.black.rgb, Color.white.rgb)

    return MatrixToImageWriter.toBufferedImage(bitMatrix, config)
}

fun qrCodeBaseSetting (margin: Int, errorCorrectionLevel: ErrorCorrectionLevel): Map<EncodeHintType, Any> {
    return mutableMapOf(
            EncodeHintType.CHARACTER_SET to CharacterSetECI.UTF8,
            EncodeHintType.ERROR_CORRECTION to errorCorrectionLevel,
            EncodeHintType.MARGIN to margin
    )
}

fun <T> Array<T>.atLease(size: Int): Boolean = this.size >= size

data class QRCodeArgs(
        var fileName: String = "test.jpg",
        var contents: String = "",
        var width: Int = 120,
        var height: Int = 120,
        var margin: Int = 0,
        var errorCorrectionLevel: ErrorCorrectionLevel = ErrorCorrectionLevel.L
) {
    fun readQRCodeArgs(args: Array<String>) {
        val maxLength = args.size
        for ((index, value) in args.withIndex()) {
            when (index) {
                maxLength - 1 -> { contents = value }
                else -> {
                    if (index > maxLength - 2) throw Exception("input error")
                    val nextValue = args[index + 1]
                    when (value.toLowerCase()) {
                        "-n", "--name" -> { fileName = nextValue}
                        "-w", "--width" -> { width = nextValue.toInt() }
                        "-h", "--height" -> { height = nextValue.toInt() }
                        "-m", "--margin" -> { margin = nextValue.toInt() }
                        "-l", "--level" -> { errorCorrectionLevel = toErrorCorrectionLevel(nextValue) }
                    }
                }
            }
        }
    }

    private fun toErrorCorrectionLevel(nextValue: String): ErrorCorrectionLevel {
        return when(nextValue.toUpperCase()) {
            "L" -> ErrorCorrectionLevel.L
            "M" -> ErrorCorrectionLevel.M
            "Q" -> ErrorCorrectionLevel.Q
            "H" -> ErrorCorrectionLevel.H
            else -> throw Exception("level must be in (L,M,Q,H)")
        }
    }
}