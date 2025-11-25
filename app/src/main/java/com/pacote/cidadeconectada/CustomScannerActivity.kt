package com.pacote.cidadeconectada

import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class CustomScannerActivity : CaptureActivity() {

    override fun initializeContent(): DecoratedBarcodeView {
        setContentView(R.layout.custom_scanner_layout)

        return findViewById(R.id.zxing_barcode_scanner)
    }
}