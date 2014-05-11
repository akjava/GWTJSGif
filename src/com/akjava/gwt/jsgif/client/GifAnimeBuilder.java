package com.akjava.gwt.jsgif.client;

import java.util.List;

import com.akjava.gwt.lib.client.ImageElementUtils;
import com.google.common.io.BaseEncoding;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.ImageElement;

public class GifAnimeBuilder {
	private GIFEncoder encoder;
private GifAnimeBuilder(GIFEncoder encoder,List<ImageElement> elements){
	this.encoder=encoder;
	this.elements=elements;
}

private int repeatTime;
private int quality=10;
private int delay=100;
private Integer transparent=null;
//TODO support transparent
public static GifAnimeBuilder from(List<ImageElement> elements){
	if(!GIFEncoder.exists()){
		throw new RuntimeException("GifEncoder not found.need include LZWEncoder.js,NeuQuant.js,GIFEncoder.js");
	}
	return new GifAnimeBuilder(GIFEncoder.newGIFEncoder(),elements);
}
List<ImageElement> elements;

public GifAnimeBuilder lowQuality(){
	this.quality=20;
	return this;
}

public GifAnimeBuilder highQuality(){
	this.quality=1;
	return this;
}
public GifAnimeBuilder setQuality(int quality){
	this.quality=quality;
	return this;
}

public GifAnimeBuilder loop(){
	repeatTime=0;
	return this;
}

public GifAnimeBuilder transparent(int color){
	transparent=color;
	return this;
}

public GifAnimeBuilder repeat(int repeatAt){
	repeatTime=repeatAt;
	return this;
}

public GifAnimeBuilder delay(int delay){
	this.delay=delay;
	return this;
}
private JsArrayNumber bytes;
public void generate(){
	if(bytes!=null){
		return;
	}
	encoder.setQuality(quality);
	encoder.setRepeat(repeatTime);
	encoder.setDelay(delay);
	encoder.start();
	if(transparent!=null){
		encoder.setTransparent(transparent);
	}
	
	Canvas canvas=Canvas.createIfSupported();
	for(ImageElement element:elements){
		ImageElementUtils.copytoCanvas(element, canvas);
		encoder.addFrame(canvas.getContext2d());
	}
	//encoder.setTransparent(0xff0000);
	encoder.finish();
	bytes=encoder.getStream();
}

public JsArrayNumber toArrayNumber(){
	generate();
	return bytes;
}

public byte[] toByte(){
	generate();
	byte[]bt=new byte[bytes.length()];
	for(int i=0;i<bytes.length();i++){
		bt[i]=(byte)bytes.get(i);
	}
	return bt;
}

public String toBase64(){
	generate();
	return BaseEncoding.base64().encode(toByte());
}
//TODO remove my lib
public String toDataUrl(){
	generate();
	return Base64Utils.toDataUrl("image/gif", toBase64());
}

}
