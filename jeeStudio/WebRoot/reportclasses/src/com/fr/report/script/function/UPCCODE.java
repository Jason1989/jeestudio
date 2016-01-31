package com.fr.report.script.function;

import java.awt.image.BufferedImage;

import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import com.fr.report.script.NormalFunction;

public class UPCCODE extends NormalFunction {
	/**
	 * 
	 */

	public Object run(Object[] args) {
		if(args == null || args.length < 1) {
			return "参数不对，必须有一个参数";
		}  
		try {
			UPCABean bean = new UPCABean();  
			final int dpi = Integer.parseInt(args[1].toString());
			bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); // makes the narrow
			bean.doQuietZone(false);
				BitmapCanvasProvider canvas = new BitmapCanvasProvider(
						dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
				// Generate the barcode
				bean.generateBarcode(canvas, "" + args[0]);
				// Signal end of generation
				canvas.finish();
				// return image
				return canvas.getBufferedImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return args[0];		
	}	
}

