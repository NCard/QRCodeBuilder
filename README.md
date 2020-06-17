# QRCodeBuilder

## How to use

```
QRCodeBuilder [options] CONTENT

Option:
	-n, --name		Image name. Default "test.jpg". Default deputy filename ".jpg"
	-w, --width		Image width. Default 120
	-h, --height		Image height. Default 120
	-m, --margin		QRCode margin. Default 0
	-l, --level		QRCode level. Default "L"
				value:	
					L, M, Q, H
	-t, --type		Code type. Default "QR_CODE"
				value:	
					AZTEC,
    					CODABAR,
    					CODE_39,
    					CODE_93,
    					CODE_128,
    					DATA_MATRIX,
    					EAN_8,
    					EAN_13,
    					ITF,
    					MAXICODE,
    					PDF_417,
    					QR_CODE,
    					RSS_14,
    					RSS_EXPANDED,
    					UPC_A,
    					UPC_E,
    					UPC_EAN_EXTENSION

Example:
	QRCodeBuilder --name barCode --width 132 --height 77 --type CODE_128 "11110363240FZF"
```