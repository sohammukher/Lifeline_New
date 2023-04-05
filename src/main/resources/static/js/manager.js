
document.addEventListener("DOMContentLoaded", function(event){
    var doctorsList = document.querySelector('.view-doctors');
    var counselorList = document.querySelector(".view-counselor");
    var patientList = document.querySelector(".view-patient");
    var generateReport = document.querySelector(".generate-report");
    var allTables = document.querySelectorAll(".list-table");
    var skeletonContainer = document.querySelector(".skeleton-container")
    var doctorsTable =  document.querySelector('.doctor-list-table');
    var patientsTable =  document.querySelector('.Patient-list-table');
    var counsellorTable =  document.querySelector('.Counsellor-list-table');
    var rejectDoctor = document.querySelector(".d-reject");
    var acceptDoctor = document.querySelector(".d-accept");

    // const getDoctorsList =  function() {
    //     fetch("", {
    //       method: "GET",
    //     }).then(async (resp) => {
    //       const jsonResult = await resp.json();
    //       console.log(jsonResult);
          
    //     });
    // }
    var jsonResult = {
        waitlistedDoctors : [
          {name: "A", id: 1,email:"13"},
          {name: "A", id: 1,email:"13"},
          {name: "A", id: 1,email:"13"},
          {name: "A", id: 1,email:"13"}
        ],
        activeDoctors : [
            {name: "A", id: 1,email:"13"},
            {name: "A", id: 1,email:"13"},
          {name: "B", id: 4,email:"13ere"}
        ],
      };
      var counselorsResult = {
        waitlistedcounselors : [
          {name: "CCC", id: 32356781},
          {name: "SWW", id: 22345678765},
          {name: "Gwwwww", id: 544643},
          {name: "Bwww", id: 334446644}
        ],
        activecounselorr : [
          {name: "A2df3", id: 1447789},
          {name: "S32f23rf", id: 248446},
          {name: "Gff3f3f3", id: 34848},
          {name: "Bf3f3f3f", id: 45796}
        ],
      };
    // View Doctoes Click event
    doctorsList.addEventListener('click', function(event){

       
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                $(".doctor-list-table").find(".doctor-list-table").innerHTML = "";              
                doctorsTable.querySelector("tbody").innerHTML = "";
                $(".doctor-list-table").find(".empty-message").text("")
                count = 1 ;
                
                // For WaitListed Doctors
                
                fetch("http://localhost:9999/manager/getdoctorslist", {
                method: 'GET',
                })
                .then((response) => response.json())
                .then((resp) => {
                var waitlistedDoctors = resp.waitlistedDoctors;
                var activeDoctors = resp.activeDoctors;
                if(resp.status == "Success"){
                    $(".doctors-main-div").removeClass("display-none");

                    // For WaitListed Doctors 
                    if(waitlistedDoctors){                       
                        for (let index = 0; index < waitlistedDoctors.length; index++) {
                            const data = waitlistedDoctors[index];
                            var doc_table_row = document.querySelector(".doctor-row.display-none");
                            var elem = doc_table_row.cloneNode(true);
                            elem.querySelector(".d-name").innerText = data.name;
                            elem.querySelector(".d-id").innerText = data.id;
                            elem.querySelector(".d-email").innerText = data.email;
                            elem.classList.remove("display-none");
                            doctorsTable.querySelector("tbody").append(elem);
                            elem.querySelector(".d-accept-reject .btn-primary").remove();
                            elem.querySelector(".d-accept-reject .btn-success").setAttribute("id",data.id);
                            elem.querySelector(".d-accept-reject .btn-danger").setAttribute("id",data.id);
                            elem.querySelector(".d-accept-reject .btn-success").setAttribute("email-id",data.email);
                            elem.querySelector(".d-accept-reject .btn-danger").setAttribute("email-id",data.email);
                        }
                    }
                    // For Active Doctors 
                    if(activeDoctors){
                        for (let index = 0; index < activeDoctors.length; index++) {
                            const data = activeDoctors[index];
                            var doc_table_row = document.querySelector(".doctor-row.display-none");
                            var elem = doc_table_row.cloneNode(true);
                            elem.querySelector(".d-name").innerText = data.name;
                            elem.querySelector(".d-id").innerText = data.id;
                            elem.querySelector(".d-email").innerText = data.email;
                            elem.classList.remove("display-none");
                            doctorsTable.querySelector("tbody").append(elem);
                            elem.querySelector(".d-accept-reject .btn-success").remove();
                            elem.querySelector(".d-accept-reject .btn-danger").remove();
                        }
                    }
                }else{
                    $(".doctor-list-table").find(".empty-message").text("There are no doctors.")
                }                              
                })
                .catch((error) => {
                });
                 // Test
                
                doctorsTable.classList.remove("display-none");
            },1000);

            
    });
    // Counselor Click event
    counselorList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                counsellorTable.querySelector("tbody").innerHTML = "";
                $(".Counsellor-list-table").find(".empty-message").text("")
                count = 1 ;
                fetch("http://localhost:9999/manager/getcounsellorslist", {
                    method: 'GET',
                    })
                    .then((response) => response.json())
                    .then((resp) => {
                                   
                    if(resp.status == "Success"){
						$(".counsellor-main-div").removeClass("display-none"); 	 	
                        var waitlistedCounselor = resp.waitlistedcounsellors;
                        var activeCounselor = resp.activecounsellor;
                        if(waitlistedCounselor){
                            for (let index = 0; index < waitlistedCounselor.length; index++) {
                                const data = waitlistedCounselor[index];
                                var con_table_row = document.querySelector(".counsellor-row.display-none");
                                var elem = con_table_row.cloneNode(true);
                                elem.querySelector(".c-name").innerText = data.name;
                                elem.querySelector(".c-id").innerText = data.id;
                                elem.querySelector(".c-email").innerText = data.email;
                                elem.classList.remove("display-none");
                                counsellorTable.querySelector("tbody").append(elem);
                                elem.querySelector(".c-accept-reject .btn-primary").remove();
                                elem.querySelector(".c-accept-reject .btn-success").setAttribute("id",data.id);
                                elem.querySelector(".c-accept-reject .btn-danger").setAttribute("id",data.id);
                                elem.querySelector(".c-accept-reject .btn-success").setAttribute("email-id",data.email);
                                elem.querySelector(".c-accept-reject .btn-danger").setAttribute("email-id",data.email);
                                
                            }
                        }
                        
                        // For Active Counselor
                        if(activeCounselor) {
                            for (let index = 0; index < activeCounselor.length; index++) {
                                const data = activeCounselor[index];
                                var con_table_row = document.querySelector(".counsellor-row.display-none");
                                var elem = con_table_row.cloneNode(true);
                                elem.querySelector(".c-name").innerText = data.name;
                                elem.querySelector(".c-id").innerText = data.id;
                                elem.querySelector(".c-email").innerText = data.email;
                                elem.classList.remove("display-none");
                                counsellorTable.querySelector("tbody").append(elem);
                                elem.querySelector(".c-accept-reject .btn-success").remove();
                                elem.querySelector(".c-accept-reject .btn-danger").remove();
                            }
                        }
                    }else{
                        $(".Counsellor-list-table").find(".empty-message").text("There are no doctors.")
                    }                              
                    })
                    .catch((error) => {
                    });
                // For WaitListed Counselor
               
                
                counsellorTable.classList.remove("display-none");
            },1000);
    });
    // Doctor Accept or Reject
    $(document).on('click', '.d-button',function(){
        debugger;
        var data = new Object();
        // data.id = $(this).attr("id");
        data.id = $(this).attr("email-id");
        data.action = $(this).attr("data-action");
        fetch("http://localhost:9999/manager/updateUser", {
            method: 'POST',
            body: JSON.stringify(data),
            })
            .then((response) => response.json())
            .then((data) => {
            $(".view-doctors").click();
            })
            .catch((error) => {
            });
    });

    
    // Counselor Accept or Reject
    $(document).on('click', '.c-button',function(){
        debugger;
        var data = new Object();
        // data.id = $(this).attr("id");
        data.id = $(this).attr("email-id");
        data.action = $(this).attr("data-action");
        fetch("http://localhost:9999/manager/updateUser", {
            method: 'POST',
            body: JSON.stringify(data),
            })
            .then((response) => response.json())
            .then((data) => {
             $(".view-counselor").click();
            })
            .catch((error) => {
            });
    });
    
    patientList.addEventListener('click', function(event){
        for (let index = 0; index < allTables.length; index++) {
            var element = allTables[index];
            element.classList.add("display-none");              
        }
        skeletonContainer.classList.remove("display-none");
            setTimeout(function(){
                skeletonContainer.classList.add("display-none");
                patientsTable.classList.remove("display-none");
            },1000);
    });
});

