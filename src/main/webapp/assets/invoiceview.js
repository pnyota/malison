var invoiceview = angular.module('Invoiceview', []);

invoiceview.controller('ViewCtrl', ['$scope', '$http', function($scope,$http){
	$scope.invoices  = [];
	$scope.invoicedetails = false;
	$scope.invoicestable = true;
	$scope.selectedinvoices = [];
	$scope.invoice = [];
	$scope.jobs = [];
	$scope.w = {};

	$scope.showinvoicedetails = function (status) {
		if($scope.selectedinvoices.length ==1){
			$scope.invoicedetails = !status;
			$scope.invoicestable = status;

			$http({
				url:'./api/invoice/getjobs',
				method: 'POST',
				data: JSON.stringify($scope.selectedinvoices),
				headers: {'Content-Type':'application/JSON'}
			}).success(function(data){
				$scope.jobs = data.job;
			});
			$http({
				url:'./api/invoice/getinvoice',
				method: 'POST',
				data: JSON.stringify($scope.selectedinvoices),
				headers: {'Content-Type':'application/JSON'}
			}).success(function(data){
				$scope.invoice = data;
				
			});
		}else {
			alert("illegal number of invoices selected, \n Please select one invoice")
		}
	 
	};
	$scope.deleteInvoices = function () {
		$http({
			url:'./api/invoice/delete',
			method:'POST',
			data: JSON.stringify($scope.selectedinvoices),
			headers: {'Content-Type':'application/JSON'}
		}).success(function(data) {
			$scope.selectedinvoices = [];
			getinvoices(); 
		});
	};

	function getinvoices(){
		$http.get("./api/invoice/get").
		success(function(data){
			$scope.invoices = data.invoice;
		});
	};
	getinvoices();

	var updateSelected = function (action, id){
		if (action === 'add' && $scope.selectedinvoices.indexOf(id) === -1)
			$scope.selectedinvoices.push(id);
		if (action === 'remove' && $scope.selectedinvoices.indexOf(id) !== -1)
			$scope.selectedinvoices.splice($scope.selectedinvoices.indexOf(id), 1);
	};
	
	$scope.updateSelection = function  ($event, id) {
		 var checkbox = $event.target;
		 var action = (checkbox.checked ? 'add': 'remove');
		 updateSelected (action, id);
	};

	$scope.makeInvoice = function () {
		 $http({
		 	url: "./api/invoice/create",
		 	method: "POST", 
		 	data: JSON.stringify($scope.selectedinvoices),
		 	headers: {'Content-Type':'application/JSON'},
		 	responseType: "arraybuffer"
		}).success(function(data){
				$scope.selectedinvoices = [];
				$scope.w = data;
			 var file = new Blob([data], {type: 'application/pdf'});
	            var fileURL = URL.createObjectURL(file);
	           window.open(fileURL, "Download");
		});
};

}]);