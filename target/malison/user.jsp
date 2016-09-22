<!DOCTYPE html>
<html ng-app="User">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>Malison App | Registration Page</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="./assets/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="./assets/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="./assets/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition register-page" ng-controller="UserCtrl">
<div class="register-box">
  <div class="register-logo">
    <a href="../../index2.jsp"><b>Malison</b>webapp</a>
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">Register a new user</p>

    <form name="userform" ng-submit="addUser()">
      <div class="form-group has-feedback" ng-class="{'has-error':!userform.firstname.$valid}">
        <input type="text" name="firstname" class="form-control" placeholder="First name" ng-model="user.firstname" required="">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <div  ng-show="userform.firstname.$error.required">Required</div>
      </div>
      <div class="form-group has-feedback" ng-class="{'has-error':!userform.lastname.$valid}">
        <input type="text" name="lastname" class="form-control" placeholder="Last name" ng-model="user.lastname" required = "">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <div ng-show="userform.lastname.$error.required">Required</div>
      </div>
      <div class="form-group has-feedback" ng-class="{'has-error':!userform.username.$valid}">
        <input type="text" name="username" class="form-control" placeholder="Username" ng-change="checkName()" ng-model="user.username" required = "">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <div ng-show="userform.username.$error.required">Required</div>
        <div ng-show="usedName">Try another username</div>
      </div>
      <div class="form-group has-feedback" ng-class="{'has-error':!userform.position.$valid}">
        <input type="text" name="position" class="form-control" placeholder="Position" ng-model="user.position" required ="">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <div ng-show="userform.position.$error.required">Required</div>
      </div> 
      <div class="form-group has-feedback" ng-class="{'has-error':!userform.password.$valid}">
        <input type="password" name="password" class="form-control" placeholder="Password" ng-model="user.password" required="">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <div ng-show="userform.password.$error.required">Required</div>
      </div>
      <div class="form-group has-feedback">
        <input type="password" name="passwordCompare" class="form-control" placeholder="Retype password" ng-model="passwordCompare" ng-match ="user.password" required = "">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
        <div ng-show="userform.passwordCompare.$error.match">Passwords do not match</div>
      </div>
      <div class="row">
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" ng-disabled="!userform.$valid" value="submit" class="btn btn-primary btn-block btn-flat">Register</button>
        </div>
        <!-- /.col -->
      </div>
    </form>

    <a href="login.jsp" class="text-center">I am already a user</a>
  </div>
  <!-- /.form-box -->
</div>
<script src="./assets/scripts/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="./assets/scripts/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script type="text/javascript" src="./assets/angular/angular-1.5.7/angular.min.js"></script>
<script type="text/javascript" src="./assets/user.js"></script>
</body>

</html>
