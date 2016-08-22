var invoice = angular.module('Invoice', []);

invoice.controller ('InvoiceCtrl',['$scope','$http', '$window', function($scope,$http,$window){
	
	$scope.jobs=[];
	$scope.selectedjobs = [];
	$scope.company= "";
	$scope.invoice = [];
	$scope.currency = "Ksh";
	
	function getjobs(){
		$http.get("./api/jobapi/invoice").
		success(function(data){
		$scope.jobs = data.job;
		});
	};
	$scope.updatejob = function () {
		$http.get("./api/jobapi/invoice").
		success(function(data){
		$scope.jobs = data.job;
		});
		 
	};

	$scope.showAddCompany= true;
	$scope.showAddJobs= false;

	$scope.showhideCompany = function (status) {
		$scope.showAddCompany = status;
		$scope.showAddJobs = !status; 
	};

	getjobs();
	var updateSelected = function (action, id){
		if (action === 'add' && $scope.selectedjobs.indexOf(id) === -1)
			$scope.selectedjobs.push(id);
		if (action === 'remove' && $scope.selectedjobs.indexOf(id) !== -1)
			$scope.selectedjobs.splice($scope.selectedjobs.indexOf(id), 1);
	};
	
	$scope.updateSelection = function  ($event, id) {
		 var checkbox = $event.target;
		 var action = (checkbox.checked ? 'add': 'remove');
		 updateSelected (action, id);
	};

	$scope.isSelected = function (id) {
		 return $scope.selectedjobs.indexOf(id) >= 0;
	};

	/*function mergeInvoice(){
		$scope.invoice.push($scope.company);
		$scope.invoice.push($scope.selectedjobs);
	};*/

	
	
	$scope.createInvoice =function(){
		//mergeInvoice();
		if($scope.selectedjobs.length != 0 && $scope.company.length != 0 && $scope.currency.length != 0){
		$scope.invoice = {company:$scope.company, currency:$scope.currency, selectedjobs:$scope.selectedjobs }
		$http({
			url: './api/jobapi/createinvoice',
			method: 'POST',
			data: JSON.stringify($scope.invoice),
			headers: {'Content-Type':'application/JSON'},
			responseType: "arraybuffer"
		}).success(function(data){
				$scope.selectedjobs = [];
				getjobs();
			 var file = new Blob([data], {type: 'application/pdf'});
	            var fileURL = URL.createObjectURL(file);
	           window.open(fileURL, "Download");
		});
	}else {
		$scope.selectedjobs = [];
		$scope.company = "";
		$scope.invoice = [];
		alert("you have not selected any jobs for the invoice,\n or You have not entered company name");
	}
		
		};

	
	
}])
