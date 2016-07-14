 var job = angular.module('Job',[]);
job.controller ('JobCtrl',['$scope','$http', function($scope, $http){

$scope.job={};
$scope.jobs=[];
$scope.selectedjobs = [];


$scope.showJobs = true;
$scope.showJobDetails = false;

$scope.addJob =function(){
	$http({
		url: './api/jobapi/create',
		method: 'POST',
		data: $.param($scope.job),
		headers: {'Content-Type':'application/x-www-form-urlencoded'}

	}).success(function(data){
		$scope.job={};
		getjobs();
	});

};

function getjobs(){
	$http.get("./api/jobapi/list").
	success(function(data){
	$scope.jobs = data.job;
	});
};

getjobs();


	var updateSelected = function (action, id){
		if (action === 'add' && $scope.selectedjobs.indexOf(id) === -1)
			$scope.selectedjobs.push(id);
		if (action === 'remove' && $scope.selectedjobs.indexOf(id) !== -1)
			$scope.selectedjobs.splice($scope.selectedjobs.indexOf(id), 1);
	};
	
	$scope.updateSelection = function  ($event, id, invoiced) {
		if(invoiced == false){
		 var checkbox = $event.target;
		 var action = (checkbox.checked ? 'add': 'remove');
		 updateSelected (action, id);
		}else{
			getjobs();
			alert("The job is invoiced,\n Delete the invoice first")
		}
	};
	$scope.isSelected = function (id) {
		 return $scope.selectedjobs.indexOf(id) >= 0;
	};

	$scope.deleteJobs = function () {
		 $http({
		 	url: './api/jobapi/delete',
		 	method: 'POST',
		 	data: JSON.stringify($scope.selectedjobs),
		 	headers: {'Content-Type':'application/json'}
		 }).success(function(data) {
		 	$scope.selectedjobs = [];
		 	getjobs();
		 });
	};
	function showhidejobs (status) {
					$scope.showJobs = status;
					$scope.showJobDetails= !status; 
					$scope.showcreatejob = false;
				};
	$scope.createNewJob = function (status) {
		 			$scope.showJobs = status;
					$scope.showJobDetails= false; 
					$scope.showcreatejob = !status;
	};

	$scope.showdetails = function (status) {
		 $scope.showJobs = status;
		 $scope.showJobDetails = !status;
		 $scope.showcreatejob = false;
	};
	$scope.date = {};
	$scope.getSelectedJob = function(){
		if($scope.selectedjobs.length == 1){
		$http({
			url: './api/jobapi/selectedjob',
		 	method: 'POST',
		 	data: JSON.stringify($scope.selectedjobs),
		 	headers: {'Content-Type':'application/json'}
		}
			).success(function(data){
				$scope.job = data;
				var k = new Date($scope.job.date);
				$scope.job.date = k;
				showhidejobs(false);
				$scope.selectedjobs = [];
			});
		}else{
			alert("Select only one Job to Edit");
		}
	};

$scope.editJob =function(){
	$http({
		url: './api/jobapi/edit',
		method: 'POST',
		data: $.param($scope.job),
		headers: {'Content-Type':'application/x-www-form-urlencoded'}

	}).success(function(data){
		$scope.job={};
		getjobs();
	});
};



}])