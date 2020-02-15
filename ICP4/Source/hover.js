function upDate(previewPic) {
    /* In this function you should
       1) change the url for the background image of the div with the id = "image"
       to the source file of the preview image

       2) Change the text  of the div with the id = "image"
       to the alt text of the preview image
       */

    console.log(previewPic);
    $('#image').css('background-image', 'url(' + previewPic.src + ')'); // adding the image on mouse hovering
    $('#image').html(previewPic.alt); //adding alternative text to the image
}

function unDo() {
    /* In this function you should
   1) Update the url for the background image of the div with the id = "image"
   back to the orginal-image.  You can use the css code to see what that original URL was

   2) Change the text  of the div with the id = "image"
   back to the original text.  You can use the html code to see what that original text was
   */

    $('#image').css('background-image','none'); // making the background image as none
    $('#image').html("Hover over an image below to display here."); // adding the text after mouse has been hovered out
}
