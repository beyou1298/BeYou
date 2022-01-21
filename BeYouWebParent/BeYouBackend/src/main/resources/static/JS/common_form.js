$(document).ready(function(){
    //We can use jQuery to handle the click event of this Cancel button. And we assign an ID for this button buttonCancel
    $("#buttonCancel").on("click",function(){
        /* And on the click event of this button, execute a function... function here.And in this function, we just...
        ..set the location of the window...location... to the URL of the users listing module*/
        window.location= moduleURL; //So here we can use Thymeleaf syntax for the URL of the users listing... user module here
    });

    //code to show thumbnail image
    $("#fileImage").change(function(){
        if(!checkFileSize(this)){
            return;
        }
        showImageThumbnail(this);
    
    });
    
});
//show image to new function
function showImageThumbnail(fileInput){
    var file=fileInput.files[0];
    var reader=new FileReader();
    reader.onload=function(e){
        $("#thumbnail").attr("src",e.target.result);
    };
    reader.readAsDataURL(file);
}

function checkFileSize(fileInput){
    fileSize=fileInput.files[0].size;

    if(fileSize > MAX_FILE_SIZE){
        fileInput.setCustomValidity("You must choose an image less than "+MAX_FILE_SIZE + " bytes!");
        fileInput.reportValidity();

        return false;
    }
    else{
        fileInput.setCustomValidity("");
        
        return true;
    }
}

function showModelDialog(title,message){
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function showErrorModal(message){
    showModelDialog("Error",message);
}

function showWarningModal(message){
    showModelDialog("Warning",message);
}
