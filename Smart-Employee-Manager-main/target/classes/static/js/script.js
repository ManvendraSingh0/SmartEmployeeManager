console.log("this is script file");
const toggle=()=>{
if(  $(".sidebar").is(":visible"))
{  
    $(".sidebar").css("display","none") ;  
       $(".content").css("margin-left","0px") ; 
}
else
{
	 $(".sidebar").css("display","block") ; 
	  $(".content").css("margin-left","18%") ;
}	
}
 document.querySelectorAll('input[type=checkbox]').forEach(function (checkbox) {
         checkbox.addEventListener('change', function () {
             if (this.checked) {
                 // Show the search input field when a checkbox is selected
                 document.getElementById("search-input").style.display = "block";
                 // Uncheck other checkboxes
                 document.querySelectorAll('input[type=checkbox]').forEach(function (otherCheckbox) {
                     if (otherCheckbox !== checkbox) {
                         otherCheckbox.checked = false;
                     }
                 });
             } else {
                 // Hide the search input field when no checkbox is selected
                 document.getElementById("search-input").style.display = "none";
             }
         });
     });
     const search = () => {
console.log("searching..");
         let query = $("#search-input").val();
         if (query == "") {
             $(".search-result").hide();
         } else {
             let criteria = "";
             // Check which criteria checkbox is selected
             document.querySelectorAll('input[type=checkbox]').forEach(function (checkbox) {
                 if (checkbox.checked) {
                     criteria = checkbox.value;
                 }
             });

             // Build the URL with selected criteria
             let url = `http://localhost:8089/search/${criteria}/${query}`;

             // Perform the search
             fetch(url)
                 .then((response) => {
                     return response.json();
                 })
                 .then((data) => {
                 console.log(data);
                     let text = `<div class='list-group'>`;
                     data.forEach((fname) => {
                         text += `<a href='/user/${fname.cid}' class='list-group-item list-group-action'>${fname.name}, ${fname.secondName}, ${fname.email}</a>`;
                     });
                     text += `</div>`;
                     $(".search-result").html(text);
                     $(".search-result").show();
                 });
         }
     };