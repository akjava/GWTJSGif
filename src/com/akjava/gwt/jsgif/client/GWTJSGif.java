package com.akjava.gwt.jsgif.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.akjava.gwt.html5.client.download.HTML5Download;
import com.akjava.gwt.lib.client.ImageElementListener;
import com.akjava.gwt.lib.client.ImageElementLoader;
import com.akjava.gwt.lib.client.ImageElementUtils;
import com.akjava.gwt.lib.client.LogUtils;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTJSGif implements EntryPoint {
	
	public void onModuleLoad() {
		
		Canvas canvas=Canvas.createIfSupported();
		List<ImageElement> images=Lists.newArrayList();
		List<String> list=Lists.newArrayList("#000","#888","#fff","#888","#ff0000");
		for(String color:list){
			canvas.getContext2d().setFillStyle(color);
			canvas.getContext2d().fillRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
			ImageElement element=ImageElementUtils.create(canvas.toDataUrl());
			images.add(element);
		}
		
		
		
		createGifAnime(images);
		
	}
	
	private void createGifAnime(List<ImageElement> elements){
		
		Stopwatch sp=Stopwatch.createStarted();
		GIFEncoder encoder=GIFEncoder.newGIFEncoder();
		encoder.setQuality(1);
		//
		encoder.setRepeat(0);
		encoder.setDelay(200);
		
		encoder.start();
		
		Canvas canvas=Canvas.createIfSupported();
		for(ImageElement element:elements){
			ImageElementUtils.copytoCanvas(element, canvas);
			encoder.addFrame(canvas.getContext2d());
		}

		encoder.setDispose(0xff0000);
		encoder.finish();
		JsArrayNumber jsarray=encoder.getStream();
		LogUtils.log("number:"+jsarray.length());
		sp.stop();
		LogUtils.log("create-gif:"+sp.elapsed(TimeUnit.MILLISECONDS));sp.reset();
		
		sp.start();
		
		byte[]bt=new byte[jsarray.length()];
		for(int i=0;i<jsarray.length();i++){
			bt[i]=(byte)jsarray.get(i);
		}
		sp.stop();
		LogUtils.log("copy array:"+sp.elapsed(TimeUnit.MILLISECONDS));sp.reset();
		
		
		sp.start();
		String base64=BaseEncoding.base64().encode(bt);
		
		String url=Base64Utils.toDataUrl("image/gif", base64);
		sp.stop();
		LogUtils.log("toDataUrl:"+sp.elapsed(TimeUnit.MILLISECONDS));sp.reset();
		
		Image img=new Image(url);
		
		RootPanel.get().add(img);
		
		Anchor a=HTML5Download.get().generateBase64DownloadLink(base64, "image/gif", "test.gif", "download", true);
		RootPanel.get().add(a);
	}
}
