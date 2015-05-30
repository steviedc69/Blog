/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
onload = function()
{
    fillBlogPosts();
    $('button#submit').click(makeBlog);
    $('button#newForm').click(openForm);
};

function fillBlogPosts()
{
    var request = new XMLHttpRequest();
    request.open("GET","http://localhost:8080/blog/api/blogs");
    request.onload = function()
    {
        if(request.status === 200)
        {
            $("#blogitems").empty();
            var blogs = JSON.parse(request.responseText);
            for(var i = 0; i < blogs.length;i++)
            {
                var item = $('<a class = "list-group-item" onclick ="getDetails('+blogs[i].id+')">');
                item.html ('<h4 class="list-group-item-heading">Title : '+blogs[i].title+' </h4><p class="list-group-item-text"> Author : '+blogs[i].owner+'</br>Onderwerp : '+blogs[i].onderwerp+'</p>');
                $("#blogitems").append(item);

            }
        }
        else
        {
        $("#error").html('<div class="alert alert-danger">Something went wrong with the shit</div>');
        
    }
    
    };
    request.send(null);
}

function getDetails(id){

    var request = new XMLHttpRequest();
    request.open("GET","http://localhost:8080/blog/api/blogs/"+id);
    request.onload = function(){
        if(request.status === 200)
        {
           $("#details").empty();
           var blog = JSON.parse(request.responseText);
               var item = $('<div class="panel panel-primary">');
                item.html('<div class="panel-heading"><h2>'+blog.title+'</h2></div>'+'<div class="panel-body"><p>Author : '+blog.owner+'</p>'+'<p>'+blog.content+'</p></div>');
                $("#details").append(item);
        }
        else
        {
            $("#error").html('<div class="alert alert-danger">Something went wrong with the shit</div>')
        }
    };
    
    request.send(null);

}


function makeBlog(){   
    var post = {};
    post.title = $("#title").val();
    post.owner = $("#owner").val();
    post.onderwerp = $("#onderwerp").val();
    post.content = $('textarea#content').val();
    
    var request = new XMLHttpRequest();
    request.open("POST","http://localhost:8080/blog/api/blogs");
    request.onload = function(){
        if(request.status === 201)
        {
            $("#error").empty();
            fillBlogPosts();
        }
        else
        {
          $("#error").html('<div class="alert alert-danger">Something went wrong with the shit</div>');
        }
    };
              //$("#error").html('<div class="alert alert-danger">hier bennik</div>');
         request.setRequestHeader("Content-Type","application/json");
         request.send(JSON.stringify(post));
}


function openForm()
{
    $("#details").empty();
    
    var form = $('<form class="form-horizontal" role="form">');
    form.html(' <div class="form-group">'+
                '<label for="title" class="col-sm-2 control-label">Title : </label>'+
                '<div class="col-sm-10">'+
                    '<input type="text" id="title" class="form-control" style="width: 50%">'+
               ' </div>'+
            '</div>'+
              '<div class="form-group">'+
                '<label for="onderwerp" class="col-sm-2 control-label">Subject : </label>'+
                '<div class="col-sm-10">'+
                   '<input type="text" id="onderwerp" class="form-control" style="width: 50%">'+
                '</div>'+
            '</div>'+
              '<div class="form-group">'+
                '<label for="owner" class="col-sm-2 control-label">Author : </label>'+
                '<div class="col-sm-10">'+
                    '<input type="text" id="owner" class="form-control" style="width: 50%">'+
                '</div>'+
            '</div>'+
              '<div class="form-group">'+
                '<label for="content" class="col-sm-2 control-label">Message : </label>'+
                '<div class="col-sm-10">'+
                   '<textarea id="content" class="form-control" style="width: 50%" rows="15" cols="500"></textarea>'+
                '</div>'+
            '</div>'+
           
                '<div class="form-group">'+
                      '<div class="col-sm-offset-2 col-sm-10">'+
      '<button type="submit" id="submit" class="btn btn-default">Submit</button>'+
    '</div>'+
                '</div>');
        
        $("#details").append(form);
}

function getReactions(id){
    
}
