import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.CharacterSetECI
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage

lateinit var hints: MutableMap<EncodeHintType, Any>

fun main (args: Array<String>?) {
    if (args != null && args.isNotEmpty()) {
        val contents = args.build()
        val path = ""

        val qrCodeImage = createQRCode(contents, 300, 300, 0)


    }
}

fun createQRCode(contents: String, width: Int, height: Int, margin: Int): BufferedImage {
    hints = mutableMapOf(EncodeHintType.CHARACTER_SET to CharacterSetECI.UTF8)


    val qrCodeWriter = QRCodeWriter()
    val bitMetrix = qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, width, height)


}

fun <T> Array<T>.isNotEmpty(): Boolean = this.size > 0

fun Array<String>.build(): String {
    var res = ""
    for (element in this) {
        if (res != "") res += " "
        res += element
    }
}