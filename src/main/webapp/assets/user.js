(function () {
    'use strict';
var user = angular.module('User', []);

user.controller('UserCtrl', ['$scope', '$http', function($scope, $http) {
     $scope.user = {};
     $scope.usernames = [];
     $scope.usedName = false;

$scope.checkName = function () {
     if ($scope.usernames.indexOf($scope.user.username)=== -1){
        $scope.usedName = false;
        $scope.userform.username.$valid = true;
        $scope.userform.$valid = true;
     }else{
        $scope.usedName = true;
        $scope.userform.username.$valid = false;
        $scope.userform.$valid = false;
     }
};
function getusers() {
  $http.get("./api/user/getusers").
  success(function(data) {
       $scope.usernames = data;
  }) ;  
};
getusers();


$scope.addUser = function () {
     
     $http({
        url: './api/user/create',
        method: 'POST',
        data: $.param($scope.user),
        headers: {'Content-Type':'application/x-www-form-urlencoded'}

     }).success(function(data) {
          $scope.user = {};
     });
};

}]); 

var directiveId = 'ngMatch';
user.directive(directiveId, ['$parse', function ($parse) {
 
var directive = {
link: link,
restrict: 'A',
require: '?ngModel'
};
return directive;
 
function link(scope, elem, attrs, ctrl) {
// if ngModel is not defined, we don't need to do anything
if (!ctrl) return;
if (!attrs[directiveId]) return;
 
var firstPassword = $parse(attrs[directiveId]);
 
var validator = function (value) {
var temp = firstPassword(scope),
v = value === temp;
ctrl.$setValidity('match', v);
return value;
}
 
ctrl.$parsers.unshift(validator);
ctrl.$formatters.push(validator);
attrs.$observe(directiveId, function () {
validator(ctrl.$viewValue);
});
 
}
}]);
})();