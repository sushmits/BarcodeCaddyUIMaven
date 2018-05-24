

function updateTextInput(slideAmount , sliderAmountVar) {
	var sliderInput = document.getElementById(sliderAmountVar);
	sliderInput.value = slideAmount;

}

function updateSlider(slideAmount , sliderAmountVar) {
	var slider = document.getElementById(sliderAmountVar);
	slider.value = slideAmount;

}



function onclickDistanceMatrix()
{
window.open('http://girihlet.com/ravi/web/barcode_diversity/tmp/mat89380.html');

}

function updateImage()
{
	var randomNumber = Math.random();
	var ran = 0;
	if(randomNumber>0.5)
	{
		ran=1;
	}
	else
	{
		ran=0;
	}

		$("#img_graph").attr("src","http://girihlet.com/ravi/web/barcode_diversity/bargraph.cgi?perf=tmp/fig5431.perf");
}