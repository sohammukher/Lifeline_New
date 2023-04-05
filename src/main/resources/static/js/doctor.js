document.addEventListener("DOMContentLoaded", function(event){
    var upcomingList = document.querySelector(".upcoming-appointments");
    var appointmentList = document.querySelector(".view-appointments");
    var refList = document.querySelector(".btn-info");
    var pastList = document.querySelector(".past-appointments");
    var allTables = document.querySelectorAll(".list-table");
    var loaderContainer = document.querySelector(".loader-container")
    var upcomingAppTable =  document.querySelector('.upApp-list-table');
    var repAppTable =  document.querySelector('.repApp-list-table');
    var pastAppTable =  document.querySelector('.paApp-list-table');
    let reject = document.querySelectorAll('.btn-danger');
    let schedule = document.querySelectorAll('.btn-success');
    let detailedScoreInfo = document.querySelectorAll('.btn-info');

   
   const getDataList = function () {
        return new Promise((resolve, reject) => {
            fetch("http://localhost:9999/doctor/getList", {
                method: "GET"      
            }).then(async (resp) => {
                const jsonResult = await resp.json();
                console.log(jsonResult);
                if (jsonResult && jsonResult.status && jsonResult.status.toLowerCase() === "success") {
                    resolve(jsonResult);
                } else {
                    reject();
                }
            });
        });
    }
   // var pastAppResult = {
	   var jsonResult = {
            pastApp: [{ name: "A", email: "at@yahoo.com",score: "90",schedule:"2023-03-12; 11:20pm",status:"Accepted"},
            { name: "S", email: "st@yahoo.com",score: "75",schedule:"2023-03-12; 11:20pm",status:"Refused"},
            { name: "G", email: "gt@yahoo.com",score: "85",schedule:"2023-03-12; 11:20pm",status:"Accepted"}],          
    };
    
    var upcomAppResult = {
		upcomingApp: [{ name: "AA", email: "aat@yahoo.com",score: "75"},
            { name: "SS", email: "sst@yahoo.com",score: "95"},
            { name: "GG", email: "ggt@yahoo.com",score: "65"}]
	}
   
   var assessment = {
	   assessment_result:[
		   {srno:"1", question:"Over the past 2 weeks, how often have you been bothered by any of the following problems: Little interest or pleasure in doing things?", ans:"Not At All"},
		   {srno:"2", question:"Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling down, depressed or hopless?", ans:"More Than Half the Days"},
		   {srno:"3", question:"Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble falling asleep, staying asleep, or sleeping too much?", ans:"Several Days"}	   
	   ]
   };
   
    var questions = [
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Little interest or pleasure in doing things?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling down, depressed or hopless?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble falling asleep, staying asleep, or sleeping too much?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling tired or having little energy?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Poor appetite or overeating?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Feeling bad about yourself - or that you\'re a failure or have let yourself or your family down?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Trouble concentrating on things, such as reading the newspaper or watching television?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Moving or speaking so slowly that other people could have noticed. Or, the opposite - being so fidgety or restless that you have been moving around a lot more than usual?",
        "Over the past 2 weeks, how often have you been bothered by any of the following problems: Thoughts that you would be better off dead or of hurting yourself in some way?"
    ]

    var responses = {
        "A":"Not At All",
        "B":"Several Days",
        "C":"More Than Half the Days",
        "D":"Nearly Every Day"
    }

   
   refList.addEventListener('click', function (event) {
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");
        }
        loaderContainer.classList.remove("display-none");
        setTimeout(async function () {
           // let pastAppData = await getDataList();
            //jsonResult = pastAppData;
            loaderContainer.classList.add("display-none");
            repAppTable.querySelector("tbody").innerHTML = "";
            count = 1;

            
           // var pastApp = jsonResult.pastApp;
            for (let index = 0; index < questions.length; index++) {
                const data = assessment_result[index];
                var rep_table_row = document.querySelector(".repApp-row.display-none");
                var elem = rep_table_row.cloneNode(true);
                elem.querySelector(".pat-srno").innerText = data.srno;
                elem.querySelector(".pat-question").innerText = data.question;
                elem.querySelector(".pat-ans").innerText = data.ans;
                repAppTable.querySelector("tbody").append(elem);
                elem.classList.remove("display-none");
            }
            repAppTable.classList.remove("display-none");
        }, 10);


    });
       // View Past Appointment Click event
    pastList.addEventListener('click', function (event) {
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");
        }
        loaderContainer.classList.remove("display-none");
        setTimeout(async function () {
           // let pastAppData = await getDataList();
            //jsonResult = pastAppData;
            loaderContainer.classList.add("display-none");
            pastAppTable.querySelector("tbody").innerHTML = "";
            count = 1;

            
            var pastApp = jsonResult.pastApp;
            for (let index = 0; index < pastApp.length; index++) {
                const data = pastApp[index];
                var papp_table_row = document.querySelector(".paApp-row.display-none");
                var elem = papp_table_row.cloneNode(true);
                elem.querySelector(".pat-name").innerText = data.name;
                elem.querySelector(".pat-email").innerText = data.email;
                elem.querySelector(".pat-score").innerText = data.score;
                elem.querySelector(".pat-schedule").innerText = data.schedule;
                elem.querySelector(".pat-status").innerText = data.status;
                pastAppTable.querySelector("tbody").append(elem);
                elem.classList.remove("display-none");
            }
            pastAppTable.classList.remove("display-none");
        }, 10);


    });
    
   
          // View Upcoming Appointment Click event
    upcomingList.addEventListener('click', function (event) {
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");
        }
        loaderContainer.classList.remove("display-none");
        setTimeout(async function () {
            //let upcomingAppData = await getDataList();
            //jsonResult = upcomingAppData;
            loaderContainer.classList.add("display-none");
            upcomingAppTable.querySelector("tbody").innerHTML = "";
            count = 1;

            
            var upcomingApp = upcomAppResult.upcomingApp;
            for (let index = 0; index < upcomingApp.length; index++) {
                const data = upcomingApp[index];
                var upApp_table_row = document.querySelector(".upApp-row.display-none");
                var elem = upApp_table_row.cloneNode(true);
                elem.querySelector(".pat-name").innerText = data.name;
                elem.querySelector(".pat-email").innerText = data.email;
                elem.querySelector(".pat-score").innerText = data.score;
                upcomingAppTable.querySelector("tbody").append(elem);
                elem.querySelector(".pat-accept-reject .btn-success").setAttribute("data-name", data.name);
                elem.querySelector(".pat-accept-reject .btn-success").setAttribute("data-email", data.email);
                elem.querySelector(".pat-accept-reject .btn-success").setAttribute("data-date", data.date);
                elem.querySelector(".pat-accept-reject .btn-success").setAttribute("data-time", data.time);
                elem.querySelector(".pat-accept-reject .btn-danger").setAttribute("data-email", data.email);
                elem.querySelector(".pat-accept-reject .btn-danger").setAttribute("data-message", data.message);
                elem.classList.remove("display-none");
            }
            upcomingAppTable.classList.remove("display-none");
        }, 10);


    });
    


 $(document).on("click",".RefpopupBtn",function(){
	 var refbtn =$(this);
	 var refparent = refbtn.parent().parent();
	 var refpname = refparent.find(".pat-name");
	 var refemail = refparent.find(".pat-email");
	 refparent.remove();
	 debugger;
 })
 
 $(document).on("click",".ModpopupBtn",function(){
	 var refbtn =$(this);
	 var refparent = refbtn.parent().parent();
	 var refpname = refparent.find(".pat-name").text();
	 var refemail = refparent.find(".pat-email").text();
	 $("#ModPopup").find("#name").val(refpname);
	 $("#ModPopup").find("#modd").val(refemail);
	 $("#ModPopup").css("display","block");
	 debugger;
 })
 
 $(document).on("click",".close",function(){
	 $("#ModPopup").css("display","none");
 })
 
 $(document).on("click",".close-button",function(){
	 $("#ModPopup").css("display","none");
 })
 
 
 $(document).on("click",".ModpopupBtn",function(){
	 var refbtn =$(this);
	 var refparent = refbtn.parent().parent();
	 var refpname = refparent.find(".pat-name").text();
	 var refemail = refparent.find(".pat-email").text();
	 $("#ModPopup").find("#name").val(refpname);
	 $("#ModPopup").find("#modd").val(refemail);
	 $("#ModPopup").css("display","block");
	 debugger;
 })
 
 $(document).on("click",".close",function(){
	 $("#ModPopup").css("display","none");
 })
 
 $(document).on("click",".close-button",function(){
	 $("#ModPopup").css("display","none");
 })
  
 
 










});
