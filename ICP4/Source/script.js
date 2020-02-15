function getGithubInfo(user) {
    //1. Create an instance of XMLHttpRequest class and send a GET request using it.
    // The function should finally return the object(it now contains the response!)
    var username='https://api.github.com/users/'+user;
    console.log(username);
    $.ajax({
        type: "GET",
        url: username,
        dataType: 'json',

    }).done(function(data){
        showUser(data);

    }).fail(function(){
        console.log("Some Error Happened");
        noSuchUser(user);
    });
}

function showUser(user) {
    //2. set the contents of the h2 and the two div elements in the div '#profile' with the user content
    document.getElementById('imgAvatar').src=user.avatar_url;
    document.getElementById('textName').innerText=user.name;
    document.getElementById('textId').innerText=user.id;
    document.getElementById('textUrl').href=user.url;
    document.getElementById('textUrl').innerText=user.html_url;
    document.getElementById('textRepository').innerText=user.public_repos;
}

function noSuchUser(username) {
    //3. set the elements such that a suitable message is displayed
    if(data.message == "Not Found" || username == '') {
        alert("User not found");
    }
}

$(document).ready(function () {
    $(document).on('keypress', '#username', function (e) {
        //check if the enter(i.e return) key is pressed
        if (e.which == 13) {
            //get what the user enters
            username = $(this).val();
            //reset the text typed in the input
            $(this).val("");
            //get the user's information and store the respsonse
            response = getGithubInfo(username);
            //if the response is successful show the user's details
            if (response.status == 200) {
                showUser(JSON.parse(response.responseText));
                //else display suitable message
            } else {
                noSuchUser(username);
            }
        }
    })
});
