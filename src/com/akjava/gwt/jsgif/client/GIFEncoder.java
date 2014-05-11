package com.akjava.gwt.jsgif.client;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;

public class GIFEncoder extends JavaScriptObject{
protected GIFEncoder(){}
	public static native final boolean exists()/*-{
		if($wnd.GIFEncoder){
			return true;
		}else{
			return false;
		}
	}-*/;

	public final static native GIFEncoder newGIFEncoder()/*-{
	return new $wnd.GIFEncoder();
	}-*/;
	
	
	
	

	public final native void setRepeat(int repeat)/*-{
	this.setRepeat(repeat);
	}-*/;
	
	public final native void setDelay(int delay)/*-{
	this.setDelay(delay);
	}-*/;
	
	public final native void setQuality(int quality)/*-{
	this.setQuality(quality);
	}-*/;
	
	public final native void setTransparent(int rgbColor)/*-{
	this.setTransparent(rgbColor);
	}-*/;
	public final native void start()/*-{
	this.start();
	}-*/;
	
	public final native boolean finish()/*-{
	return this.finish();
	}-*/;
	
	public final native boolean addFrame(Context2d context)/*-{
	return this.addFrame(context);
	}-*/;
	
	public final native void setDispose(int number)/*-{
	this.setDispose(number);
	}-*/;

	public final native void setFrameRate(double number)/*-{
	this.setFrameRate(number);
	}-*/;
	
	public final native String getStreamData()/*-{
	return this.stream().getData();
	}-*/;
	
	public final native JsArrayNumber getStream()/*-{
	return this.stream().bin;
	}-*/;
	
	
}
