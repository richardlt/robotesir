// VERSION: 1.5 LAST UPDATE: 26.07.2010
/*
 * THIS IS FREE SCRIPT BUT LEAVE THIS COMMENT IF
 * YOU WANT USE THIS CODE ON YOUR SITE
 * 
 * Made by Wilq32, wilq32@gmail.com, Wroclaw, Poland, 01.2009
 * http://wilq32.blogspot.com
 * 
 */
(function(f){for(var g,j=document.getElementsByTagName("head")[0].style,i="transformProperty WebkitTransform OTransform".split(" "),h=0;h<i.length;h++)if(j[i[h]]!==undefined)g=i[h];jQuery.fn.extend({ImageRotate:function(a){if(!(this.Wilq32&&this.Wilq32.PhotoEffect)){a=f.extend(true,{},a);return(new Wilq32.PhotoEffect(this.get(0),a))._temp}},rotate:function(a){if(this.length!==0)if(typeof a!="undefined"){if(typeof a=="number")a={angle:a};for(var b=[],c=0,d=this.length;c<d;c++){var e=this.get(c);typeof e.Wilq32==
"undefined"?b.push(f(f(e).ImageRotate(a))):e.Wilq32.PhotoEffect._rotate(a.angle)}return b}},rotateAnimation:function(a){if(this.length!==0)if(typeof a!="undefined"){if(typeof a=="number")a={animateAngle:a};for(var b=[],c=0,d=this.length;c<d;c++){var e=this.get(c);typeof e.Wilq32=="undefined"?b.push(f(f(e).ImageRotate(a))):e.Wilq32.PhotoEffect.rotateAnimation(a)}return b}}});Wilq32={};Wilq32.PhotoEffect=function(){return g?function(a,b){this._img=a;this._parameters=b||{};this._parameters.animateAngle=
b.animateAngle||0;this._parameters.angle=this._angle=b.angle||0;a.Wilq32={PhotoEffect:this};this._BindEvents(this._img,this._parameters.bind);this._rotate(this._parameters.angle);this._parameters.angle!=this._parameters.animateAngle&&this.rotateAnimation(this._parameters)}:function(a,b){this._img=a;this._parameters=b||{};this._parameters.className=a.className;this._parameters.id=a.getAttribute("id");this._parameters.animateAngle=b.animateAngle||0;this._parameters.angle=this._angle=b.angle||0;this._temp=
document.createElement("span");this._temp.style.display="inline-block";this._temp.Wilq32={PhotoEffect:this};a.parentNode.insertBefore(this._temp,a);this._img._ref=this;this._img.complete?this._Loader():jQuery(this._img).bind("load",function(){this._ref._Loader.call(this._ref)})}}();Wilq32.PhotoEffect.prototype.rotateAnimation=function(a){this._parameters.animateAngle=a.animateAngle;this._parameters.callback=a.callback||this._parameters.callback||function(){};this._animateStart()};if(jQuery.browser.msie)Wilq32.PhotoEffect.prototype.createVMLNode=
function(){document.createStyleSheet().addRule(".rvml","behavior:url(#default#VML)");try{!document.namespaces.rvml&&document.namespaces.add("rvml","urn:schemas-microsoft-com:vml");return function(b){return document.createElement("<rvml:"+b+' class="rvml">')}}catch(a){return function(b){return document.createElement("<"+b+' xmlns="urn:schemas-microsoft.com:vml" class="rvml">')}}}();Wilq32.PhotoEffect.prototype._BindEvents=function(a,b){if(b)for(var c in b)if(b.hasOwnProperty(c))for(var d in b[c])b[c].hasOwnProperty(d)&&
jQuery(a).bind(d,b[c][d])};Wilq32.PhotoEffect.prototype._Loader=function(){return jQuery.browser.msie?function(){var a=this._img.src;this._temp.setAttribute("id",this._parameters.id);this._temp.className=this._parameters.className;var b=this._img.width,c=this._img.height;this._img.parentNode.removeChild(this._img);this._img._widthMax=this._img._heightMax=Math.sqrt(c*c+b*b);this._img._heightMax=Math.sqrt(c*c+b*b);this._vimage=this.createVMLNode("image");this._vimage._ref=this;this._vimage.style.height=
c+"px";this._vimage.style.width=b+"px";this._temp.style.position="relative";this._vimage.style.position="absolute";this._temp.style.width=this._temp.style.height=this._img._heightMax+"px";this._vimage.src=a;this._temp.appendChild(this._vimage);this._temp.style.width=b+"px";this._temp.style.height=c+"px";this._vimage.style.top="0px";this._vimage.style.left="0px";this._BindEvents(this._temp,this._parameters.bind);this._rotate(this._parameters.angle);this._parameters.angle!=this._parameters.animateAngle&&
this.rotateAnimation(this._parameters)}:function(){this._temp.setAttribute("id",this._parameters.id);this._temp.className=this._parameters.className;var a=this._img.width,b=this._img.height;this._img.parentNode.removeChild(this._img);this._img._widthMax=this._img._heightMax=Math.sqrt(b*b+a*a);this._canvas=document.createElement("canvas");this._canvas._ref=this;this._canvas.height=b;this._canvas.width=a;this._canvas.setAttribute("width",a);this._canvas.Wilq32=this._temp.Wilq32;this._temp.appendChild(this._canvas);
this._temp.style.width=a+"px";this._temp.style.height=b+"px";this._canvas.style.position="relative";this._canvas.style.left=-(this._img._widthMax-a)/2+"px";this._canvas.style.top=-(this._img._widthMax-b)/2+"px";this._BindEvents(this._canvas,this._parameters.bind);this._cnv=this._canvas.getContext("2d");this._rotate(this._parameters.angle);this._parameters.angle!=this._parameters.animateAngle&&this.rotateAnimation(this._parameters)}}();Wilq32.PhotoEffect.prototype._animateStart=function(){this._timer&&
clearTimeout(this._timer);this._animate()};Wilq32.PhotoEffect.prototype._animate=function(){if(this._canvas||this._vimage||this._img)this._angle-=(this._angle-this._parameters.animateAngle)*0.1;if(typeof this._parameters.minAngle!="undefined")if(this._angle<this._parameters.minAngle)this._angle=this._parameters.minAngle;if(typeof this._parameters.maxAngle!="undefined")if(this._angle>this._parameters.maxAngle)this._angle=this._parameters.maxAngle;var a=!!Math.round(this._angle*100-this._parameters.animateAngle*
100)==0&&!!this._timer;this._parameters.callback&&a&&this._parameters.callback();if(a&&!this._parameters.animatedGif)clearTimeout(this._timer);else{if(this._canvas||this._vimage||this._img)this._rotate(~~(this._angle*10)/10);var b=this;this._timer=setTimeout(function(){b._animate.call(b)},10)}};Wilq32.PhotoEffect.prototype._rotate=function(){return jQuery.browser.msie?function(a){this._vimage.style.rotation=a}:g?function(a){this._img.style[g]="rotate("+a+"deg)"}:function(a){if(this._img.width)if(typeof a==
"number"){a=a%360*Math.PI/180;var b=this._img.width,c=this._img.height,d=this._img._widthMax-b,e=this._img._heightMax-c;this._canvas.width=b+d;this._canvas.height=c+e;this._cnv.save();this._cnv.translate(d/2,e/2);this._cnv.translate(b/2,c/2);this._cnv.rotate(a);this._cnv.translate(-b/2,-c/2);this._cnv.drawImage(this._img,0,0);this._cnv.restore()}}}()})(jQuery);